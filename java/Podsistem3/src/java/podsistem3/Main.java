package podsistem3;

import entiteti.Korisnik;
import entiteti.Narudzbina;
import entiteti.Transakcija;
import entiteti.Stavka;
import entiteti.Artikal;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.TemporaryQueue;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;

    @Resource(lookup = "jms/Queue3")
    private static Queue queue3;

    @Resource(lookup = "jms/Queue2")
    private static Queue queue2;

    @Resource(lookup = "jms/ResponseQueue")
    private static Queue responseQueue;

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
        EntityManager em = emf.createEntityManager();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println("Podsistem3 pokrenut i slusa jms/Queue3");

        try {
            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(queue3);
            JMSProducer producer = context.createProducer();

            while (true) {
                Message msg = consumer.receive();
                if (!(msg instanceof TextMessage)) {
                    continue;
                }

                TextMessage txtMsg = (TextMessage) msg;
                int operacija = txtMsg.getIntProperty("operacija");

                em.clear();

                switch (operacija) {

                    case 3: // za kreiranje korisnika iz podsistema1
                        try {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "OK");
                        } catch (Exception e) {
                        }
                        break;

                    case 14:
                        System.out.println("DEBUG: P3 usao u case 14 za korisnika: " + txtMsg.getStringProperty("param1"));

                        String kIme14 = txtMsg.getStringProperty("param1");
                        String adresaDostave14 = txtMsg.getStringProperty("param3");
                        String gradDostave14 = txtMsg.getStringProperty("param4");

                        try {
                            String korpaCorrelationId = java.util.UUID.randomUUID().toString();

                            TextMessage zahtevZaKorpu = context.createTextMessage();
                            zahtevZaKorpu.setIntProperty("operacija", 19);
                            zahtevZaKorpu.setStringProperty("param1", kIme14);
                            zahtevZaKorpu.setJMSCorrelationID(korpaCorrelationId);

                            System.out.println("DEBUG: P3 salje zahtev na queue2...");
                            producer.send(queue2, zahtevZaKorpu);

                            System.out.println("DEBUG: P3 ceka odgovor od P2...");
                            javax.jms.JMSConsumer korpaConsumer = context.createConsumer(
                                    responseQueue, "JMSCorrelationID = '" + korpaCorrelationId + "'"
                            );
                            Message odgovor = korpaConsumer.receive(5000);
                            korpaConsumer.close();

                            if (odgovor == null) {
                                System.out.println("DEBUG: P3 NIJE DOBIO ODGOVOR od P2!");
                                posaljiOdgovor(context, producer, responseQueue, txtMsg,
                                        "Greska: Podsistem 2 nije odgovorio na zahtev za korpu.");
                                break;
                            }

                            System.out.println("DEBUG: P3 primio odgovor od P2.");
                            String tekstKorpe = ((TextMessage) odgovor).getText();

                            if (tekstKorpe.contains("prazna") || tekstKorpe.contains("nema kreiranu korpu")) {
                                posaljiOdgovor(context, producer, responseQueue, txtMsg,
                                        "Greska: Nemoguce izvrsiti placanje. Korpa je prazna.");
                                break;
                            }

                            List<Korisnik> lokalniKorisnici = em.createQuery(
                                    "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :ime", Korisnik.class)
                                    .setParameter("ime", kIme14).getResultList();

                            if (lokalniKorisnici.isEmpty()) {
                                posaljiOdgovor(context, producer, responseQueue, txtMsg,
                                        "Greska: Korisnik ne postoji u Podsistemu 3.");
                                break;
                            }
                            Korisnik kupac = lokalniKorisnici.get(0);

                            java.math.BigDecimal ukupnaCena = new java.math.BigDecimal("0.00");
                            try {
                                String marker = "UKUPNA CENA KORPE: ";
                                if (tekstKorpe.contains(marker)) {
                                    String cenaStr = tekstKorpe.substring(
                                            tekstKorpe.indexOf(marker) + marker.length()).trim();
                                    ukupnaCena = new java.math.BigDecimal(cenaStr);
                                }
                            } catch (Exception e) {
                                posaljiOdgovor(context, producer, responseQueue, txtMsg,
                                        "Greska: Nije moguce parsirati ukupnu cenu korpe.");
                                break;
                            }

                            java.util.Date trenutnoVreme = new java.util.Date();

                            em.getTransaction().begin();

                            Narudzbina narudzbina = new Narudzbina();
                            narudzbina.setKupacId(kupac);
                            narudzbina.setAdresaDostave(adresaDostave14);
                            narudzbina.setGradDostave(gradDostave14);
                            narudzbina.setVremeKreiranja(trenutnoVreme);
                            narudzbina.setUkupnaCena(ukupnaCena);
                            em.persist(narudzbina);
                            em.flush();

                            String[] linije = tekstKorpe.split("\n");
                            for (String linija : linije) {
                                if (!linija.contains("ID:") || !linija.contains("Kolicina:")) {
                                    continue;
                                }
                                try {
                                    String idDeo = linija.substring(linija.indexOf("ID:") + 3);
                                    int artikalId = Integer.parseInt(idDeo.split(",")[0].trim());

                                    String kolDeo = linija.substring(linija.indexOf("Kolicina:") + 9);
                                    int kolicina = Integer.parseInt(kolDeo.split(",")[0].trim());

                                    String cenaDeo = linija.substring(linija.indexOf("Cena:") + 5);
                                    java.math.BigDecimal jedinicnaCena = new java.math.BigDecimal(cenaDeo.trim());

                                    Artikal artikal = em.find(Artikal.class, artikalId);
                                    if (artikal != null) {
                                        Stavka stavka = new Stavka();
                                        stavka.setNarudzbinaId(narudzbina);
                                        stavka.setArtikalId(artikal);
                                        stavka.setKolicina(kolicina);
                                        stavka.setJedinicnaCena(jedinicnaCena);
                                        em.persist(stavka);
                                    }
                                } catch (Exception e) {
                                    System.out.println("Preskocena linija: " + linija);
                                }
                            }

                            Transakcija transakcija = new Transakcija();
                            transakcija.setNarudzbinaId(narudzbina);
                            transakcija.setSuma(ukupnaCena);
                            transakcija.setVremePlacanja(trenutnoVreme);
                            em.persist(transakcija);

                            em.getTransaction().commit();

                            TextMessage zahtevZaPraznjenje = context.createTextMessage();
                            zahtevZaPraznjenje.setIntProperty("operacija", 24);
                            zahtevZaPraznjenje.setStringProperty("param1", kIme14);
                            producer.send(queue2, zahtevZaPraznjenje);

                            posaljiOdgovor(context, producer, responseQueue, txtMsg,
                                    "Uspesno izvrseno placanje. Kreirana narudzbina ID: "
                                    + narudzbina.getNarudzbinaId() + " u iznosu od: " + ukupnaCena);

                        } catch (Exception ex) {
                            if (em.getTransaction().isActive()) {
                                em.getTransaction().rollback();
                            }
                            posaljiOdgovor(context, producer, responseQueue, txtMsg,
                                    "Greska prilikom placanja: " + ex.getMessage());
                        }
                        break;

                    case 21: // narudzbine korisnika
                        String kIme21 = txtMsg.getStringProperty("param1");

                        try {
                            List<Korisnik> korisnici21 = em.createQuery(
                                    "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :ime", Korisnik.class)
                                    .setParameter("ime", kIme21).getResultList();

                            if (korisnici21.isEmpty()) {
                                posaljiOdgovor(context, producer, responseQueue, txtMsg,
                                        "Greska: Korisnik ne postoji.");
                                break;
                            }

                            List<Narudzbina> narudzbine = em.createQuery(
                                    "SELECT n FROM Narudzbina n WHERE n.kupacId.korisnickoIme = :ime",
                                    Narudzbina.class)
                                    .setParameter("ime", kIme21).getResultList();

                            if (narudzbine.isEmpty()) {
                                posaljiOdgovor(context, producer, responseQueue, txtMsg,
                                        "Nemate kreiranih narudzbina.");
                                break;
                            }

                            StringBuilder sb21 = new StringBuilder();
                            sb21.append("VASE NARUDZBINE:\n");
                            for (Narudzbina n : narudzbine) {
                                sb21.append("ID Narudzbine: ").append(n.getNarudzbinaId())
                                        .append(", Ukupna Cena: ").append(n.getUkupnaCena())
                                        .append(", Adresa: ").append(n.getAdresaDostave())
                                        .append(", Grad: ").append(n.getGradDostave())
                                        .append(", Vreme: ").append(sdf.format(n.getVremeKreiranja()))
                                        .append("\n");
                            }
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, sb21.toString());

                        } catch (Exception ex) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg,
                                    "Greska: " + ex.getMessage());
                        }
                        break;

                    case 22: // sve narudzbine 
                        String kIme22 = txtMsg.getStringProperty("param1");

                        try {
                            List<Korisnik> lista = em.createQuery(
                                    "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :ime", Korisnik.class)
                                    .setParameter("ime", kIme22).getResultList();

                            if (lista.isEmpty()) {
                                posaljiOdgovor(context, producer, responseQueue, txtMsg,
                                        "GRESKA: Pristup odbijen. Samo registrovani korisnik ima dozvolu.");
                                break;
                            }

                            List<Narudzbina> sveNarudzbine = em.createQuery(
                                    "SELECT n FROM Narudzbina n", Narudzbina.class).getResultList();

                            if (sveNarudzbine.isEmpty()) {
                                posaljiOdgovor(context, producer, responseQueue, txtMsg, "Nema narudzbina u sistemu.");
                                break;
                            }

                            StringBuilder sb22 = new StringBuilder();
                            sb22.append("SVE NARUDZBINE U SISTEMU:\n");
                            for (Narudzbina n : sveNarudzbine) {
                                sb22.append("ID: ").append(n.getNarudzbinaId())
                                        .append(", Kupac: ").append(n.getKupacId().getKorisnickoIme())
                                        .append(", Cena: ").append(n.getUkupnaCena())
                                        .append(", Adresa: ").append(n.getAdresaDostave())
                                        .append(", Grad: ").append(n.getGradDostave())
                                        .append(", Vreme: ").append(sdf.format(n.getVremeKreiranja()))
                                        .append("\n");
                            }
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, sb22.toString());

                        } catch (Exception ex) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: " + ex.getMessage());
                        }
                        break;

                    case 23: // sve transakcije
                        String kIme23 = txtMsg.getStringProperty("param1");

                        try {
                            List<Korisnik> lista23 = em.createQuery(
                                    "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :ime", Korisnik.class)
                                    .setParameter("ime", kIme23).getResultList();

                            if (lista23.isEmpty()) {
                                posaljiOdgovor(context, producer, responseQueue, txtMsg,
                                        "GRESKA: Pristup odbijen. Samo registrovani korisnik može videti sve transakcije.");
                                break;
                            }

                            List<Transakcija> sveTransakcije = em.createQuery(
                                    "SELECT t FROM Transakcija t", Transakcija.class).getResultList();

                            if (sveTransakcije.isEmpty()) {
                                posaljiOdgovor(context, producer, responseQueue, txtMsg, "Nema zabeleženih transakcija.");
                                break;
                            }

                            StringBuilder sb23 = new StringBuilder();
                            sb23.append("SVE TRANSAKCIJE U SISTEMU:\n");
                            for (Transakcija t : sveTransakcije) {
                                sb23.append("ID Transakcije: ").append(t.getTransakcijaId())
                                        .append(", Narudžbina ID: ").append(t.getNarudzbinaId().getNarudzbinaId())
                                        .append(", Suma: ").append(t.getSuma())
                                        .append(", Vreme plaćanja: ").append(sdf.format(t.getVremePlacanja()))
                                        .append("\n");
                            }
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, sb23.toString());

                        } catch (Exception ex) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: " + ex.getMessage());
                        }
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void posaljiOdgovor(JMSContext context, JMSProducer producer,
            Queue responseQueue, TextMessage zahtev, String tekst) throws Exception {
        TextMessage odg = context.createTextMessage(tekst);
        odg.setJMSCorrelationID(zahtev.getJMSCorrelationID());
        producer.send(responseQueue, odg);
    }
}
