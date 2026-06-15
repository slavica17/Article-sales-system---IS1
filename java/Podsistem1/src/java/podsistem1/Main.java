package podsistem1;

import entiteti.Grad;
import entiteti.Korisnik;
import java.util.List;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Main {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;

    @Resource(lookup = "jms/Queue1")
    private static Queue requestQueue;

    @Resource(lookup = "jms/Queue2")
    private static Queue queue2;

    @Resource(lookup = "jms/Queue3")
    private static Queue queue3;

    @Resource(lookup = "jms/ResponseQueue")
    private static Queue responseQueue;

    public static void main(String[] args) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
            EntityManager em = emf.createEntityManager();

            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(requestQueue);
            JMSProducer producer = context.createProducer();

            System.out.println("Podsistem1 pokrenut i slusa jms/Queue1");

            while (true) {
                Message msg = consumer.receive();
                if (!(msg instanceof TextMessage)) continue;

                TextMessage txtMsg = (TextMessage) msg;
                int operacija = txtMsg.getIntProperty("operacija");

                switch (operacija) {

                    case 1: // provera korisnika sa kredencijalima
                        String proveraKIme = txtMsg.getStringProperty("param1");
                        String proveraSifra = txtMsg.getStringProperty("param2");

                        List<Korisnik> nadjeni = em.createQuery(
                            "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :kIme AND k.sifra = :sifra", Korisnik.class)
                            .setParameter("kIme", proveraKIme)
                            .setParameter("sifra", proveraSifra)
                            .getResultList();

                        posaljiOdgovor(context, producer, responseQueue, txtMsg,
                            !nadjeni.isEmpty() ? "OK" : "NOT_FOUND");
                        break;

                    case 2: // kreiranje grada
                        String adminIme2 = txtMsg.getStringProperty("param1");
                        String adminSif2 = txtMsg.getStringProperty("param2");
                        String nazivGrada = txtMsg.getStringProperty("param3");

                        List<Korisnik> adminProvera2 = em.createQuery(
                            "SELECT k FROM Korisnik k JOIN k.ulogaList u WHERE k.korisnickoIme = :kIme AND k.sifra = :sifra AND u.naziv = 'Administrator'",
                            Korisnik.class)
                            .setParameter("kIme", adminIme2)
                            .setParameter("sifra", adminSif2)
                            .getResultList();

                        if (adminProvera2.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Nemate administratorske privilegije.");
                            break;
                        }

                        Grad grad = new Grad();
                        grad.setNaziv(nazivGrada);

                        try {
                            em.getTransaction().begin();
                            em.persist(grad);
                            em.getTransaction().commit();
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Uspesno kreiran grad " + nazivGrada);
                        } catch (Exception e) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska pri kreiranju grada.");
                        } finally {
                            if (em.getTransaction().isActive()) em.getTransaction().rollback();
                        }
                        break;

                    case 3: // kreiranje korisnika
                        String adminIme3 = txtMsg.getStringProperty("param1");
                        String adminSif3 = txtMsg.getStringProperty("param2");
                        String korisnickoIme = txtMsg.getStringProperty("param3");
                        String sifra = txtMsg.getStringProperty("param4");
                        String ime = txtMsg.getStringProperty("param5");
                        String prezime = txtMsg.getStringProperty("param6");
                        String adresa = txtMsg.getStringProperty("param7");
                        String nazivGradaKor = txtMsg.getStringProperty("param8");

                        List<Korisnik> adminProvera3 = em.createQuery(
                            "SELECT k FROM Korisnik k JOIN k.ulogaList u WHERE k.korisnickoIme = :kIme AND k.sifra = :sifra AND u.naziv = 'Administrator'",
                            Korisnik.class)
                            .setParameter("kIme", adminIme3)
                            .setParameter("sifra", adminSif3)
                            .getResultList();

                        if (adminProvera3.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Nemate administratorske privilegije.");
                            break;
                        }

                        List<Grad> nadjeniGradovi = em.createQuery(
                            "SELECT g FROM Grad g WHERE g.naziv = :naziv", Grad.class)
                            .setParameter("naziv", nazivGradaKor)
                            .getResultList();

                        if (nadjeniGradovi.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Grad ne postoji.");
                            break;
                        }

                        Korisnik noviKorisnik = new Korisnik();
                        noviKorisnik.setKorisnickoIme(korisnickoIme);
                        noviKorisnik.setSifra(sifra);
                        noviKorisnik.setIme(ime);
                        noviKorisnik.setPrezime(prezime);
                        noviKorisnik.setAdresa(adresa);
                        noviKorisnik.setGradId(nadjeniGradovi.get(0));
                        noviKorisnik.setStanjeNovca(java.math.BigDecimal.ZERO);

                        try {
                            em.getTransaction().begin();
                            em.persist(noviKorisnik);
                            em.getTransaction().commit();

                            TextMessage porukaSinhronizacije = context.createTextMessage("Kreiran korisnik");
                            porukaSinhronizacije.setIntProperty("operacija", 3);
                            porukaSinhronizacije.setIntProperty("korisnikId", noviKorisnik.getKorisnikId());
                            porukaSinhronizacije.setStringProperty("korisnickoIme", korisnickoIme);

                            producer.send(queue2, porukaSinhronizacije);
                            producer.send(queue3, porukaSinhronizacije);

                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Uspesno kreiran korisnik: " + korisnickoIme);

                        } catch (Exception e) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska pri kreiranju korisnika.");
                        } finally {
                            if (em.getTransaction().isActive()) em.getTransaction().rollback();
                        }
                        break;

                    case 4: // dodavanje novca
                        String adminIme4 = txtMsg.getStringProperty("param1");
                        String adminSif4 = txtMsg.getStringProperty("param2");
                        String kImeNovac = txtMsg.getStringProperty("param3");
                        java.math.BigDecimal iznos = new java.math.BigDecimal(txtMsg.getStringProperty("param4"));

                        List<Korisnik> adminProvera4 = em.createQuery(
                            "SELECT k FROM Korisnik k JOIN k.ulogaList u WHERE k.korisnickoIme = :kIme AND k.sifra = :sifra AND u.naziv = 'Administrator'",
                            Korisnik.class)
                            .setParameter("kIme", adminIme4)
                            .setParameter("sifra", adminSif4)
                            .getResultList();

                        if (adminProvera4.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Nemate administratorske privilegije.");
                            break;
                        }

                        List<Korisnik> korZaNovac = em.createQuery(
                            "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :kIme", Korisnik.class)
                            .setParameter("kIme", kImeNovac)
                            .getResultList();

                        if (korZaNovac.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Korisnik ne postoji!");
                            break;
                        }

                        Korisnik kNovac = korZaNovac.get(0);
                        kNovac.setStanjeNovca(kNovac.getStanjeNovca().add(iznos));

                        try {
                            em.getTransaction().begin();
                            em.merge(kNovac);
                            em.getTransaction().commit();
                            posaljiOdgovor(context, producer, responseQueue, txtMsg,
                                "Uspesno dodat novac. Novo stanje: " + kNovac.getStanjeNovca());
                        } catch (Exception e) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska pri dodavanju novca.");
                        } finally {
                            if (em.getTransaction().isActive()) em.getTransaction().rollback();
                        }
                        break;

                    case 5: // promena adrese i grada
                        String adminIme5 = txtMsg.getStringProperty("param1");
                        String adminSif5 = txtMsg.getStringProperty("param2");
                        String kImeIzmena = txtMsg.getStringProperty("param3");
                        String novaAdresa = txtMsg.getStringProperty("param4");
                        String noviGradNaziv = txtMsg.getStringProperty("param5");

                        List<Korisnik> adminProvera5 = em.createQuery(
                            "SELECT k FROM Korisnik k JOIN k.ulogaList u WHERE k.korisnickoIme = :kIme AND k.sifra = :sifra AND u.naziv = 'Administrator'",
                            Korisnik.class)
                            .setParameter("kIme", adminIme5)
                            .setParameter("sifra", adminSif5)
                            .getResultList();

                        if (adminProvera5.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Nemate administratorske privilegije.");
                            break;
                        }

                        List<Korisnik> korZaIzmenu = em.createQuery(
                            "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :kIme", Korisnik.class)
                            .setParameter("kIme", kImeIzmena)
                            .getResultList();

                        List<Grad> noviGrad = em.createQuery(
                            "SELECT g FROM Grad g WHERE g.naziv = :naziv", Grad.class)
                            .setParameter("naziv", noviGradNaziv)
                            .getResultList();

                        if (korZaIzmenu.isEmpty() || noviGrad.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Korisnik ili grad ne postoje!");
                            break;
                        }

                        Korisnik kIzmena = korZaIzmenu.get(0);
                        kIzmena.setAdresa(novaAdresa);
                        kIzmena.setGradId(noviGrad.get(0));

                        try {
                            em.getTransaction().begin();
                            em.merge(kIzmena);
                            em.getTransaction().commit();

                            TextMessage msgUpd = context.createTextMessage("Izmenjen korisnik");
                            msgUpd.setIntProperty("operacija", 5);
                            msgUpd.setStringProperty("korisnickoIme", kImeIzmena);
                            msgUpd.setStringProperty("adresa", novaAdresa);
                            msgUpd.setStringProperty("grad", noviGradNaziv);

                            producer.send(queue2, msgUpd);
                            producer.send(queue3, msgUpd);

                            posaljiOdgovor(context, producer, responseQueue, txtMsg,
                                "Uspesno izmenjeni podaci za korisnika " + kImeIzmena);

                        } catch (Exception e) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska pri izmeni adrese.");
                        } finally {
                            if (em.getTransaction().isActive()) em.getTransaction().rollback();
                        }
                        break;

                    case 15: // dohvati sve gradove
                        String adminIme15 = txtMsg.getStringProperty("param1");
                        String adminSif15 = txtMsg.getStringProperty("param2");

                        List<Korisnik> adminProvera15 = em.createQuery(
                            "SELECT k FROM Korisnik k JOIN k.ulogaList u WHERE k.korisnickoIme = :kIme AND k.sifra = :sifra AND u.naziv = 'Administrator'",
                            Korisnik.class)
                            .setParameter("kIme", adminIme15)
                            .setParameter("sifra", adminSif15)
                            .getResultList();

                        if (adminProvera15.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Nemate administratorske privilegije.");
                            break;
                        }

                        List<Grad> sviGradovi = em.createQuery("SELECT g FROM Grad g", Grad.class).getResultList();
                        StringBuilder sb15 = new StringBuilder();
                        for (Grad g : sviGradovi) {
                            sb15.append("ID: ").append(g.getGradId())
                                .append(", Naziv: ").append(g.getNaziv()).append("\n");
                        }
                        posaljiOdgovor(context, producer, responseQueue, txtMsg, sb15.toString());
                        break;

                    case 16: // dohvati sve korisnike
                        String adminIme16 = txtMsg.getStringProperty("param1");
                        String adminSif16 = txtMsg.getStringProperty("param2");

                        List<Korisnik> adminProvera16 = em.createQuery(
                            "SELECT k FROM Korisnik k JOIN k.ulogaList u WHERE k.korisnickoIme = :kIme AND k.sifra = :sifra AND u.naziv = 'Administrator'",
                            Korisnik.class)
                            .setParameter("kIme", adminIme16)
                            .setParameter("sifra", adminSif16)
                            .getResultList();

                        if (adminProvera16.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Nemate administratorske privilegije.");
                            break;
                        }

                        List<Korisnik> sviKorisnici = em.createQuery("SELECT k FROM Korisnik k", Korisnik.class).getResultList();
                        StringBuilder sb16 = new StringBuilder();
                        for (Korisnik k : sviKorisnici) {
                            sb16.append("Korisnicko ime: ").append(k.getKorisnickoIme())
                                .append(", Ime: ").append(k.getIme())
                                .append(", Prezime: ").append(k.getPrezime())
                                .append(", Adresa: ").append(k.getAdresa())
                                .append(", Grad: ").append(k.getGradId().getNaziv())
                                .append(", Stanje novca: ").append(k.getStanjeNovca()).append("\n");
                        }
                        posaljiOdgovor(context, producer, responseQueue, txtMsg, sb16.toString());
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