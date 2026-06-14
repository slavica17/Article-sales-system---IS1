package podsistem2;

import entiteti.Artikal;
import entiteti.Kategorija;
import entiteti.Korisnik;
import entiteti.Korpa;
import entiteti.Listazelja;
import entiteti.Stavkakorpe;
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

    @Resource(lookup = "jms/Queue2")
    private static Queue queue2;

    @Resource(lookup = "jms/ResponseQueue")
    private static Queue responseQueue;

    public static void main(String[] args) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
            EntityManager em = emf.createEntityManager();

            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(queue2);
            JMSProducer producer = context.createProducer();

            System.out.println("Podsistem2 pokrenut slusa jms/Queue2");

            while (true) {
                Message msg = consumer.receive();
                if (!(msg instanceof TextMessage)) continue;

                TextMessage txtMsg = (TextMessage) msg;
                int operacija = txtMsg.getIntProperty("operacija");

                switch (operacija) {

                    case 3: // kreiranje korisnika iz Podsistem1
                        try {
                            int kId = txtMsg.getIntProperty("korisnikId");
                            String kIme = txtMsg.getStringProperty("korisnickoIme");

                            Korisnik noviKorisnik = new Korisnik();
                            noviKorisnik.setKorisnikId(kId);
                            noviKorisnik.setKorisnickoIme(kIme);

                            entiteti.Korpa novaKorpa = new entiteti.Korpa();
                            novaKorpa.setKorisnikId(noviKorisnik);
                            novaKorpa.setUkupnaCena(java.math.BigDecimal.ZERO);

                            em.getTransaction().begin();
                            em.persist(noviKorisnik);
                            em.persist(novaKorpa);
                            em.getTransaction().commit();

                            System.out.println("Podsistem2: Uspesno sinhronizovan korisnik " + kIme + " i kreirana prazna korpa.");
                        } catch (Exception e) {
                            System.out.println("Greska pri sinhronizaciji korisnika u Podsistemu 2: " + e.getMessage());
                        } finally {
                            if (em.getTransaction().isActive()) em.getTransaction().rollback();
                        }
                        break;

                    case 5: // promena adrese iz Podsistem1
                        System.out.println("Podsistem2: Primljena notifikacija o promeni adrese.");
                        break;

                    case 6: // kreiranje kategorije
                        String adminKategorija6 = txtMsg.getStringProperty("param1");
                        String nazivKat6 = txtMsg.getStringProperty("param3");
                        String nadKatNaziv6 = txtMsg.getStringProperty("param4");

                        List<Korisnik> adminProvera6 = em.createQuery(
                                "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :ime", Korisnik.class)
                                .setParameter("ime", adminKategorija6)
                                .getResultList();

                        if (adminProvera6.isEmpty() || adminProvera6.get(0).getKorisnikId() != 1) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, 
                                "GRESKA: Pristup odbijen. Samo administrator može kreirati kategorije.");
                            break;
                        }

                        List<Kategorija> postojeca6 = em.createQuery(
                                "SELECT k FROM Kategorija k WHERE k.naziv = :naziv", Kategorija.class)
                                .setParameter("naziv", nazivKat6)
                                .getResultList();

                        if (!postojeca6.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Kategorija vec postoji.");
                            break;
                        }

                        Kategorija novaKategorija6 = new Kategorija();
                        novaKategorija6.setNaziv(nazivKat6);

                        if (nadKatNaziv6 != null && !nadKatNaziv6.trim().isEmpty()) {
                            List<Kategorija> nadKategorije6 = em.createQuery(
                                    "SELECT k FROM Kategorija k WHERE k.naziv = :naziv", Kategorija.class)
                                    .setParameter("naziv", nadKatNaziv6)
                                    .getResultList();

                            if (nadKategorije6.isEmpty()) {
                                posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Zadata nadkategorija ne postoji.");
                                break;
                            }
                            novaKategorija6.setNadkategorijaId(nadKategorije6.get(0));
                        } else {
                            novaKategorija6.setNadkategorijaId(null);
                        }

                        try {
                            em.getTransaction().begin();
                            em.persist(novaKategorija6);
                            em.getTransaction().commit();
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Uspesno kreirana kategorija " + nazivKat6);
                        } catch (Exception e) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska pri kreiranju kategorije.");
                        } finally {
                            if (em.getTransaction().isActive()) em.getTransaction().rollback();
                        }
                        break;    
                    
                    case 7: // kreiranje artikla
                        String prodavacIme7 = txtMsg.getStringProperty("param1");
                        String artikalNaziv7 = txtMsg.getStringProperty("param3");
                        String artikalOpis7 = txtMsg.getStringProperty("param4");
                        String artikalCenaStr7 = txtMsg.getStringProperty("param5");
                        String artikalPopustStr7 = txtMsg.getStringProperty("param6");
                        String kategorijaStr7 = txtMsg.getStringProperty("param7");

                        List<Korisnik> prodavacLista7 = em.createQuery(
                            "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :ime", Korisnik.class)
                            .setParameter("ime", prodavacIme7)
                            .getResultList();

                        if (prodavacLista7.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Korisnik ne postoji ili pogresna sifra.");
                            break;
                        }
                        Korisnik prodavac7 = prodavacLista7.get(0);

                        List<Artikal> postojeciArtikal7 = em.createQuery(
                            "SELECT a FROM Artikal a WHERE a.naziv = :naziv", Artikal.class)
                            .setParameter("naziv", artikalNaziv7)
                            .getResultList();

                        if (!postojeciArtikal7.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Artikal sa tim imenom vec postoji.");
                            break;
                        }

                        List<Kategorija> kategorije7 = em.createQuery(
                            "SELECT k FROM Kategorija k WHERE k.naziv = :naziv", Kategorija.class)
                            .setParameter("naziv", kategorijaStr7)
                            .getResultList();

                        if (kategorije7.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Kategorija ne postoji.");
                            break;
                        }

                        try {
                            Artikal noviArtikal7 = new Artikal();
                            noviArtikal7.setNaziv(artikalNaziv7);
                            noviArtikal7.setOpis(artikalOpis7);
                            noviArtikal7.setCena(new java.math.BigDecimal(artikalCenaStr7));
                            noviArtikal7.setKorisnikId(prodavac7);
                            noviArtikal7.setKategorijaId(kategorije7.get(0));
                            noviArtikal7.setKolicinaNaStanju(0);

                            if (artikalPopustStr7 != null && !artikalPopustStr7.trim().isEmpty()) {
                                noviArtikal7.setProcenatPopusta(new java.math.BigDecimal(artikalPopustStr7));
                            } else {
                                noviArtikal7.setProcenatPopusta(java.math.BigDecimal.ZERO);
                            }

                            em.getTransaction().begin();
                            em.persist(noviArtikal7);
                            em.getTransaction().commit();
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Uspesno kreiran artikal " + artikalNaziv7);
                        } catch (Exception e) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska pri kreiranju artikla.");
                        } finally {
                            if (em.getTransaction().isActive()) em.getTransaction().rollback();
                        }
                        break;

                    case 8: // promena cene
                        String kImeCena8 = txtMsg.getStringProperty("param1");
                        String nazivArtCena8 = txtMsg.getStringProperty("param3");
                        String novaCenaStr8 = txtMsg.getStringProperty("param4");

                        List<Korisnik> korisnikCena8 = em.createQuery(
                            "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :ime", Korisnik.class)
                            .setParameter("ime", kImeCena8)
                            .getResultList();

                        if (korisnikCena8.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Korisnik ne postoji ili pogresna sifra.");
                            break;
                        }

                        List<Artikal> artikalZaCenu8 = em.createQuery(
                            "SELECT a FROM Artikal a WHERE a.naziv = :naziv", Artikal.class)
                            .setParameter("naziv", nazivArtCena8)
                            .getResultList();

                        if (artikalZaCenu8.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Artikal ne postoji.");
                            break;
                        }
                        Artikal aCena8 = artikalZaCenu8.get(0);

                        if (!aCena8.getKorisnikId().getKorisnickoIme().equals(kImeCena8)) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Nemate pravo da menjate cenu tudjeg artikla.");
                            break;
                        }

                        try {
                            em.getTransaction().begin();
                            aCena8.setCena(new java.math.BigDecimal(novaCenaStr8));
                            em.getTransaction().commit();
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Uspesno izmenjena cena artikla " + nazivArtCena8);
                        } catch (Exception e) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska pri izmeni cene.");
                        } finally {
                            if (em.getTransaction().isActive()) em.getTransaction().rollback();
                        }
                        break;

                    case 9: // postavljanje popusta
                        String kImePopust9 = txtMsg.getStringProperty("param1");
                        String nazivArtPopust9 = txtMsg.getStringProperty("param3");
                        String noviPopustStr9 = txtMsg.getStringProperty("param4");

                        List<Korisnik> korisnikPopust9 = em.createQuery(
                            "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :ime", Korisnik.class)
                            .setParameter("ime", kImePopust9)
                            .getResultList();

                        if (korisnikPopust9.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Korisnik ne postoji ili pogresna sifra.");
                            break;
                        }

                        List<Artikal> artikalZaPopust9 = em.createQuery(
                            "SELECT a FROM Artikal a WHERE a.naziv = :naziv", Artikal.class)
                            .setParameter("naziv", nazivArtPopust9)
                            .getResultList();

                        if (artikalZaPopust9.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Artikal ne postoji.");
                            break;
                        }
                        Artikal aPopust9 = artikalZaPopust9.get(0);

                        if (!aPopust9.getKorisnikId().getKorisnickoIme().equals(kImePopust9)) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Nemate pravo da menjate popust za tudji artikal.");
                            break;
                        }

                        try {
                            em.getTransaction().begin();
                            aPopust9.setProcenatPopusta(new java.math.BigDecimal(noviPopustStr9));
                            em.getTransaction().commit();
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Uspesno postavljen popust na " + noviPopustStr9 + "% za artikal " + nazivArtPopust9);
                        } catch (Exception e) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska pri postavljanju popusta.");
                        } finally {
                            if (em.getTransaction().isActive()) em.getTransaction().rollback();
                        }
                        break;

                    case 10: // dodavanje u korpu
                        String kImeKorpa10 = txtMsg.getStringProperty("param1");
                        String nazivArtKorpa10 = txtMsg.getStringProperty("param3");
                        String kolicinaStr10 = txtMsg.getStringProperty("param4");

                        try {
                            int kolicinaZaDodavanje10 = Integer.parseInt(kolicinaStr10);
                            if (kolicinaZaDodavanje10 <= 0) {
                                posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Kolicina mora biti veca od 0.");
                                break;
                            }

                            List<Korisnik> korisnici10 = em.createQuery(
                                "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :ime", Korisnik.class)
                                .setParameter("ime", kImeKorpa10)
                                .getResultList();

                            if (korisnici10.isEmpty()) {
                                posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Korisnik ne postoji ili pogresna sifra.");
                                break;
                            }
                            Korisnik korisnik10 = korisnici10.get(0);

                            List<Artikal> artikli10 = em.createQuery(
                                "SELECT a FROM Artikal a WHERE a.naziv = :naziv", Artikal.class)
                                .setParameter("naziv", nazivArtKorpa10)
                                .getResultList();

                            if (artikli10.isEmpty()) {
                                posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Artikal ne postoji.");
                                break;
                            }
                            Artikal artikal10 = artikli10.get(0);

                            List<Korpa> korpe10 = em.createQuery(
                                "SELECT k FROM Korpa k WHERE k.korisnikId = :korisnik", Korpa.class)
                                .setParameter("korisnik", korisnik10)
                                .getResultList();

                            if (korpe10.isEmpty()) {
                                posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Korisnik nema korpu.");
                                break;
                            }
                            Korpa korpa10 = korpe10.get(0);

                            java.math.BigDecimal popust10 = artikal10.getProcenatPopusta();
                            if (popust10 == null) popust10 = java.math.BigDecimal.ZERO;

                            java.math.BigDecimal cenaSaPopustom10 = artikal10.getCena();
                            if (popust10.compareTo(java.math.BigDecimal.ZERO) > 0) {
                                java.math.BigDecimal faktor10 = java.math.BigDecimal.ONE.subtract(
                                    popust10.divide(new java.math.BigDecimal("100"), 4, java.math.RoundingMode.HALF_UP));
                                cenaSaPopustom10 = artikal10.getCena().multiply(faktor10);
                            }

                            java.math.BigDecimal dodatak10 = cenaSaPopustom10.multiply(new java.math.BigDecimal(kolicinaZaDodavanje10));

                            em.getTransaction().begin();

                            List<Stavkakorpe> postojeceStavke10 = em.createQuery(
                                "SELECT s FROM Stavkakorpe s WHERE s.korpaId = :korpa AND s.artikalId = :artikal", Stavkakorpe.class)
                                .setParameter("korpa", korpa10)
                                .setParameter("artikal", artikal10)
                                .getResultList();

                            if (!postojeceStavke10.isEmpty()) {
                                Stavkakorpe stavka10 = postojeceStavke10.get(0);
                                stavka10.setKolicina(stavka10.getKolicina() + kolicinaZaDodavanje10);
                                stavka10.setCenaStavke(stavka10.getCenaStavke().add(dodatak10));
                            } else {
                                Stavkakorpe novaStavka10 = new Stavkakorpe();
                                novaStavka10.setKorpaId(korpa10);
                                novaStavka10.setArtikalId(artikal10);
                                novaStavka10.setKolicina(kolicinaZaDodavanje10);
                                novaStavka10.setCenaStavke(dodatak10);
                                em.persist(novaStavka10);
                            }
                            korpa10.setUkupnaCena(korpa10.getUkupnaCena().add(dodatak10));
                            em.getTransaction().commit();

                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Uspesno dodat artikal u korpu.");

                        } catch (NumberFormatException e) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Nevalidan format kolicine.");
                        } catch (Exception e) {
                            if (em.getTransaction().isActive()) em.getTransaction().rollback();
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska pri dodavanju u korpu.");
                        }
                        break;

                    case 11: // brisanje iz korpe
                        String kImeObrisi11 = txtMsg.getStringProperty("param1");
                        String nazivArtObrisi11 = txtMsg.getStringProperty("param3");
                        String kolicinaStrBrisanje11 = txtMsg.getStringProperty("param4");

                        try {
                            int kolicinaZaBrisanje11 = Integer.parseInt(kolicinaStrBrisanje11);
                            if (kolicinaZaBrisanje11 <= 0) {
                                posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Kolicina mora biti veca od 0.");
                                break;
                            }

                            List<Korisnik> korisniciBrisanje11 = em.createQuery(
                                "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :ime", Korisnik.class)
                                .setParameter("ime", kImeObrisi11)
                                .getResultList();

                            if (korisniciBrisanje11.isEmpty()) {
                                posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Korisnik ne postoji ili pogresna sifra.");
                                break;
                            }
                            Korisnik korisnikBrisanje11 = korisniciBrisanje11.get(0);

                            List<Artikal> artikliBrisanje11 = em.createQuery(
                                "SELECT a FROM Artikal a WHERE a.naziv = :naziv", Artikal.class)
                                .setParameter("naziv", nazivArtObrisi11)
                                .getResultList();

                            if (artikliBrisanje11.isEmpty()) {
                                posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Artikal ne postoji.");
                                break;
                            }
                            Artikal artikalBr11 = artikliBrisanje11.get(0);

                            List<Korpa> korpeBrisanje11 = em.createQuery(
                                "SELECT k FROM Korpa k WHERE k.korisnikId = :korisnik", Korpa.class)
                                .setParameter("korisnik", korisnikBrisanje11)
                                .getResultList();

                            if (korpeBrisanje11.isEmpty()) {
                                posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Korisnik nema korpu.");
                                break;
                            }
                            Korpa korpaBr11 = korpeBrisanje11.get(0);

                            List<Stavkakorpe> stavke11 = em.createQuery(
                                "SELECT s FROM Stavkakorpe s WHERE s.korpaId = :korpa AND s.artikalId = :artikal", Stavkakorpe.class)
                                .setParameter("korpa", korpaBr11)
                                .setParameter("artikal", artikalBr11)
                                .getResultList();

                            if (stavke11.isEmpty()) {
                                posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Artikal se ne nalazi u korpi.");
                                break;
                            }
                            Stavkakorpe stavka11 = stavke11.get(0);

                            java.math.BigDecimal popustBr11 = artikalBr11.getProcenatPopusta();
                            if (popustBr11 == null) popustBr11 = java.math.BigDecimal.ZERO;

                            java.math.BigDecimal jedinicnaCena11 = artikalBr11.getCena();
                            if (popustBr11.compareTo(java.math.BigDecimal.ZERO) > 0) {
                                java.math.BigDecimal faktor11 = java.math.BigDecimal.ONE.subtract(
                                    popustBr11.divide(new java.math.BigDecimal("100"), 4, java.math.RoundingMode.HALF_UP));
                                jedinicnaCena11 = artikalBr11.getCena().multiply(faktor11);
                            }

                            java.math.BigDecimal staraCenaStavke11 = stavka11.getCenaStavke();

                            em.getTransaction().begin();

                            if (kolicinaZaBrisanje11 >= stavka11.getKolicina()) {
                                korpaBr11.setUkupnaCena(korpaBr11.getUkupnaCena().subtract(staraCenaStavke11));
                                em.remove(stavka11);
                            } else {
                                int novaKolicina11 = stavka11.getKolicina() - kolicinaZaBrisanje11;
                                java.math.BigDecimal novaCenaStavke11 = jedinicnaCena11.multiply(new java.math.BigDecimal(novaKolicina11));
                                stavka11.setKolicina(novaKolicina11);
                                stavka11.setCenaStavke(novaCenaStavke11);
                                korpaBr11.setUkupnaCena(korpaBr11.getUkupnaCena().subtract(staraCenaStavke11).add(novaCenaStavke11));
                            }

                            if (korpaBr11.getUkupnaCena().compareTo(java.math.BigDecimal.ZERO) < 0) {
                                korpaBr11.setUkupnaCena(java.math.BigDecimal.ZERO);
                            }

                            em.getTransaction().commit();
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Uspesno uklonjen artikal iz korpe.");

                        } catch (NumberFormatException e) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Nevalidan format kolicine.");
                        } catch (Exception e) {
                            if (em.getTransaction().isActive()) em.getTransaction().rollback();
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska pri brisanju iz korpe.");
                        }
                        break;

                    case 12: // dodavanje u listu zelja
                        String kImeLista12 = txtMsg.getStringProperty("param1");
                        String nazivArtLista12 = txtMsg.getStringProperty("param3");

                        List<Korisnik> korisniciLZ12 = em.createQuery(
                            "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :ime", Korisnik.class)
                            .setParameter("ime", kImeLista12)
                            .getResultList();

                        if (korisniciLZ12.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Korisnik ne postoji ili pogresna sifra.");
                            break;
                        }
                        Korisnik korisnikLZ12 = korisniciLZ12.get(0);

                        List<Artikal> artikliLZ12 = em.createQuery(
                            "SELECT a FROM Artikal a WHERE a.naziv = :naziv", Artikal.class)
                            .setParameter("naziv", nazivArtLista12)
                            .getResultList();

                        if (artikliLZ12.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Artikal ne postoji.");
                            break;
                        }
                        Artikal artikalLZ12 = artikliLZ12.get(0);

                        List<Listazelja> postojecaZelja12 = em.createQuery(
                            "SELECT l FROM Listazelja l WHERE l.korisnikId = :korisnik AND l.artikalId = :artikal", Listazelja.class)
                            .setParameter("korisnik", korisnikLZ12)
                            .setParameter("artikal", artikalLZ12)
                            .getResultList();

                        if (!postojecaZelja12.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Artikal vec postoji u listi zelja.");
                            break;
                        }

                        try {
                            em.getTransaction().begin();
                            Listazelja novaZelja12 = new Listazelja();
                            novaZelja12.setKorisnikId(korisnikLZ12);
                            novaZelja12.setArtikalId(artikalLZ12);
                            novaZelja12.setDatumDodavanja(new java.util.Date());
                            em.persist(novaZelja12);
                            em.getTransaction().commit();
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Uspesno dodat artikal u listu zelja.");
                        } catch (Exception e) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska pri dodavanju u listu zelja.");
                        } finally {
                            if (em.getTransaction().isActive()) em.getTransaction().rollback();
                        }
                        break;

                    case 13: // brisanje iz liste zelja
                        String kImeBrisZelja13 = txtMsg.getStringProperty("param1");
                        String nazivArtBrisZelja13 = txtMsg.getStringProperty("param3");

                        List<Korisnik> korisniciBLZ13 = em.createQuery(
                            "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :ime", Korisnik.class)
                            .setParameter("ime", kImeBrisZelja13)
                            .getResultList();

                        if (korisniciBLZ13.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Korisnik ne postoji ili pogresna sifra.");
                            break;
                        }
                        Korisnik korisnikBLZ13 = korisniciBLZ13.get(0);

                        List<Artikal> artikliBLZ13 = em.createQuery(
                            "SELECT a FROM Artikal a WHERE a.naziv = :naziv", Artikal.class)
                            .setParameter("naziv", nazivArtBrisZelja13)
                            .getResultList();

                        if (artikliBLZ13.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Artikal ne postoji.");
                            break;
                        }
                        Artikal artikalBLZ13 = artikliBLZ13.get(0);

                        List<Listazelja> zelje13 = em.createQuery(
                            "SELECT l FROM Listazelja l WHERE l.korisnikId = :korisnik AND l.artikalId = :artikal", Listazelja.class)
                            .setParameter("korisnik", korisnikBLZ13)
                            .setParameter("artikal", artikalBLZ13)
                            .getResultList();

                        if (zelje13.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Artikal se ne nalazi u listi zelja.");
                            break;
                        }

                        try {
                            em.getTransaction().begin();
                            em.remove(em.merge(zelje13.get(0)));
                            em.getTransaction().commit();
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Uspesno obrisan artikal iz liste zelja.");
                        } catch (Exception e) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska pri brisanju iz liste zelja.");
                        } finally {
                            if (em.getTransaction().isActive()) em.getTransaction().rollback();
                        }
                        break;

                    case 17: // dohvati sve kategorije
                        String kIme17 = txtMsg.getStringProperty("param1");

                        List<Korisnik> korisnici17 = em.createQuery(
                            "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :ime", Korisnik.class)
                            .setParameter("ime", kIme17)
                            .getResultList();

                        if (korisnici17.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Korisnik ne postoji ili pogresna sifra.");
                            break;
                        }

                        List<Kategorija> sveKategorije17 = em.createQuery(
                            "SELECT k FROM Kategorija k", Kategorija.class).getResultList();
                        StringBuilder sbKat17 = new StringBuilder();

                        for (Kategorija k : sveKategorije17) {
                            sbKat17.append("ID: ").append(k.getKategorijaId())
                                .append(", Naziv: ").append(k.getNaziv())
                                .append(", Nadkategorija: ").append(k.getNadkategorijaId() != null ? k.getNadkategorijaId().getNaziv() : "Nema")
                                .append("\n");
                        }

                        if (sbKat17.length() == 0) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Nema kreiranih kategorija.");
                        } else {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, sbKat17.toString());
                        }
                        break;

                    case 18: // artikli korisnika
                        String kIme18 = txtMsg.getStringProperty("param1");

                        List<Korisnik> korisnici18 = em.createQuery(
                            "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :ime", Korisnik.class)
                            .setParameter("ime", kIme18)
                            .getResultList();

                        if (korisnici18.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Korisnik ne postoji ili pogresna sifra.");
                            break;
                        }
                        Korisnik korisnikPR18 = korisnici18.get(0);

                        List<Artikal> mojiArtikli18 = em.createQuery(
                            "SELECT a FROM Artikal a WHERE a.korisnikId = :korisnik", Artikal.class)
                            .setParameter("korisnik", korisnikPR18)
                            .getResultList();

                        StringBuilder sbMoji18 = new StringBuilder();
                        for (Artikal a : mojiArtikli18) {
                            sbMoji18.append("ID: ").append(a.getArtikalId())
                                .append(", Naziv: ").append(a.getNaziv())
                                .append(", Cena: ").append(a.getCena())
                                .append(", Popust: ").append(a.getProcenatPopusta()).append("%")
                                .append(", Kategorija: ").append(a.getKategorijaId().getNaziv())
                                .append("\n");
                        }

                        if (sbMoji18.length() == 0) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Nemate kreiranih artikala za prodaju.");
                        } else {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, sbMoji18.toString());
                        }
                        break;

                    case 19: // dohvati sadrzaj korpe
                        String kIme19 = txtMsg.getStringProperty("param1");

                        List<Korisnik> korisnici19 = em.createQuery(
                                "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :ime", Korisnik.class)
                                .setParameter("ime", kIme19)
                                .getResultList();

                        if (korisnici19.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Korisnik ne postoji.");
                            break;
                        }
                        Korisnik korisnikK19 = korisnici19.get(0);

                        List<Korpa> korpe19 = em.createQuery(
                                "SELECT k FROM Korpa k WHERE k.korisnikId = :korisnik", Korpa.class)
                                .setParameter("korisnik", korisnikK19)
                                .getResultList();

                        if (korpe19.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Korisnik nema kreiranu korpu.");
                            break;
                        }
                        Korpa korpaK19 = korpe19.get(0);

                        List<Stavkakorpe> stavke19 = em.createQuery(
                                "SELECT s FROM Stavkakorpe s WHERE s.korpaId = :korpa", Stavkakorpe.class)
                                .setParameter("korpa", korpaK19)
                                .getResultList();

                        if (stavke19.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Korpa je prazna.");
                            break;
                        }

                        StringBuilder sbKorpa19 = new StringBuilder();
                        sbKorpa19.append("VASA KORPA\n");
                        for (Stavkakorpe s : stavke19) {
                            sbKorpa19.append("ID:").append(s.getArtikalId().getArtikalId())
                                    .append(", Naziv: ").append(s.getArtikalId().getNaziv())
                                    .append(", Kolicina:").append(s.getKolicina())
                                    .append(", Cena:").append(s.getCenaStavke())
                                    .append("\n");
                        }
                        sbKorpa19.append(" UKUPNA CENA KORPE: ").append(korpaK19.getUkupnaCena());

                        posaljiOdgovor(context, producer, responseQueue, txtMsg, sbKorpa19.toString());
                       
                        break;    
                   
                    case 20: // sadrzaj liste zelja
                        String kIme20 = txtMsg.getStringProperty("param1");

                        List<Korisnik> korisnici20 = em.createQuery(
                            "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :ime", Korisnik.class)
                            .setParameter("ime", kIme20)
                            .getResultList();

                        if (korisnici20.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Greska: Korisnik ne postoji ili pogresna sifra.");
                            break;
                        }
                        Korisnik korisnikL20 = korisnici20.get(0);

                        List<Listazelja> mojeZelje20 = em.createQuery(
                            "SELECT l FROM Listazelja l WHERE l.korisnikId = :korisnik", Listazelja.class)
                            .setParameter("korisnik", korisnikL20)
                            .getResultList();

                        if (mojeZelje20.isEmpty()) {
                            posaljiOdgovor(context, producer, responseQueue, txtMsg, "Vasa lista zelja je prazna.");
                            break;
                        }

                        StringBuilder sbZelje20 = new StringBuilder();
                        sbZelje20.append(" VASA LISTA ZELJA \n");
                        for (Listazelja l : mojeZelje20) {
                            sbZelje20.append("- ").append(l.getArtikalId().getNaziv())
                                .append(" (Cena: ").append(l.getArtikalId().getCena())
                                .append(", Kategorija: ").append(l.getArtikalId().getKategorijaId().getNaziv())
                                .append(")\n");
                        }
                        posaljiOdgovor(context, producer, responseQueue, txtMsg, sbZelje20.toString());
                        break;

                    case 24: // praznjenje korpe nakon uplate
                        String kImePrazni24 = txtMsg.getStringProperty("param1");

                        List<Korisnik> korisniciP24 = em.createQuery(
                            "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :ime", Korisnik.class)
                            .setParameter("ime", kImePrazni24)
                            .getResultList();

                        if (!korisniciP24.isEmpty()) {
                            Korisnik kor24 = korisniciP24.get(0);
                            List<Korpa> korpeP24 = em.createQuery(
                                "SELECT k FROM Korpa k WHERE k.korisnikId = :korisnik", Korpa.class)
                                .setParameter("korisnik", kor24)
                                .getResultList();

                            if (!korpeP24.isEmpty()) {
                                Korpa k24 = korpeP24.get(0);
                                try {
                                    em.getTransaction().begin();
                                    em.createQuery("DELETE FROM Stavkakorpe s WHERE s.korpaId = :korpa")
                                        .setParameter("korpa", k24).executeUpdate();
                                    k24.setUkupnaCena(java.math.BigDecimal.ZERO);
                                    em.getTransaction().commit();
                                } catch (Exception e) {
                                    
                                } finally {
                                    if (em.getTransaction().isActive()) {
                                        em.getTransaction().rollback();
                                    }
                                }
                            }
                        }

                        javax.jms.Destination replyTo24 = txtMsg.getJMSReplyTo();
                        if (replyTo24 != null) {
                            producer.send(replyTo24, context.createTextMessage("Korpa uspesno ispraznjena."));
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