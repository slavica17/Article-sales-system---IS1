/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.klijent;

import java.util.Scanner;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Main {

    private static String ulogovanKorisnik = null;
    private static String ulogovanaSifra = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Dobrodosli u sistem za prodaju artikala!");

        while (true) {
            if (ulogovanKorisnik == null) {
                prikaziMeniPrijava();
            } else {
                prikaziMeniKorisnik();
            }
        }
    }

    private static void prikaziMeniPrijava() {
        System.out.println("\n  MENI:  ");
        System.out.println("1. Prijava");
        System.out.println("0. Izlaz");
        System.out.print("Izbor: ");

        String izbor = scanner.nextLine();
        switch (izbor) {
            case "1":
                prijava();
                break;
            case "0":
                System.out.println("Dovidjenja!");
                System.exit(0);
                break;
            default:
                System.out.println("Nepoznata opcija.");
        }
    }

    private static void prikaziMeniKorisnik() {
        System.out.println("\n  MENI - Ulogovani: " + ulogovanKorisnik + "  ");
        System.out.println("1. Dohvati kategorije");
        System.out.println("2. Kreiraj artikal");
        System.out.println("3. Promeni cenu artikla");
        System.out.println("4. Postavi popust za artikal");
        System.out.println("5. Dodaj artikal u korpu");
        System.out.println("6. Obrisi artikal iz korpe");
        System.out.println("7. Pregledaj korpu");
        System.out.println("8. Dodaj artikal u listu zelja");
        System.out.println("9. Obrisi artikal iz liste zelja");
        System.out.println("10. Pregledaj listu zelja");
        System.out.println("11. Moji artikli");
        System.out.println("12. Plati");
        System.out.println("13. Moje narudzbine");
        System.out.println("14. Sve narudzbine");
        System.out.println("15. Sve transakcije");
        System.out.println("16. Svi korisnici (admin)");
        System.out.println("17. Svi gradovi (admin)");
        System.out.println("18. Kreiraj grad (admin)");
        System.out.println("19. Kreiraj korisnika (admin)");
        System.out.println("20. Dodaj novac korisniku (admin)");
        System.out.println("21. Promeni adresu korisnika (admin)");
        System.out.println("22. Kreiraj kategoriju ");
        System.out.println("0. Odjava");
        System.out.print("Izbor: ");

        String izbor = scanner.nextLine();
        switch (izbor) {
            case "1":
                dohvatiKategorije();
                break;
            case "2":
                kreirajArtikal();
                break;
            case "3":
                promeniCenu();
                break;
            case "4":
                postaviPopust();
                break;
            case "5":
                dodajUKorpu();
                break;
            case "6":
                obrisiIzKorpe();
                break;
            case "7":
                pregledajKorpu();
                break;
            case "8":
                dodajUListuZelja();
                break;
            case "9":
                obrisiIzListeZelja();
                break;
            case "10":
                pregledajListuZelja();
                break;
            case "11":
                mojiArtikli();
                break;
            case "12":
                plati();
                break;
            case "13":
                mojeNarudzbine();
                break;
            case "14":
                sveNarudzbine();
                break;
            case "15":
                sveTransakcije();
                break;
            case "16":
                sviKorisnici();
                break;
            case "17":
                sviGradovi();
                break;
            case "18":
                kreirajGrad();
                break;
            case "19":
                kreirajKorisnika();
                break;
            case "20":
                dodajNovac();
                break;
            case "21":
                promeniAdresu();
                break;
            case "22":
                kreirajKategoriju();
                break;
            case "0":
                odjava();
                break;
            default:
                System.out.println("Nepoznata opcija.");
        }
    }

    private static void prijava() {
        System.out.print("Korisnicko ime: ");
        String kIme = scanner.nextLine();
        System.out.print("Sifra: ");
        String sifra = scanner.nextLine();

        //String rezultat = HttpKlijent.post("/resources/korisnik/proveri?korisnik=" + kIme + "&sifra=" + sifra);
        String rezultat = HttpKlijent.post("/korisnik/proveri?korisnik=" + kIme + "&sifra=" + sifra);

        if (rezultat.contains("OK")) {
            ulogovanKorisnik = kIme;
            ulogovanaSifra = sifra;
            System.out.println("Uspesna prijava!");
        } else {
            System.out.println("Pogresno korisnicko ime ili sifra.");
        }
    }

    private static void odjava() {
        ulogovanKorisnik = null;
        ulogovanaSifra = null;
        System.out.println("Uspesno ste se odjavili.");
    }

    private static void dohvatiKategorije() {
        String rezultat = HttpKlijent.get("/kategorija?korisnik=" + ulogovanKorisnik + "&sifra=" + ulogovanaSifra);
        System.out.println(rezultat);
    }

    private static void kreirajArtikal() {
        System.out.print("Naziv artikla: ");
        String naziv = scanner.nextLine();
        System.out.print("Opis: ");
        String opis = scanner.nextLine();
        System.out.print("Cena: ");
        String cena = scanner.nextLine();
        System.out.print("Procenat popusta (0 ako nema): ");
        String popust = scanner.nextLine();
        System.out.print("Naziv kategorije: ");
        String kategorija = scanner.nextLine();

        try {
            String rezultat = HttpKlijent.post("/artikal/kreiraj?korisnik=" + URLEncoder.encode(ulogovanKorisnik, StandardCharsets.UTF_8.toString())
                    + "&sifra=" + URLEncoder.encode(ulogovanaSifra, StandardCharsets.UTF_8.toString())
                    + "&naziv=" + URLEncoder.encode(naziv, StandardCharsets.UTF_8.toString())
                    + "&opis=" + URLEncoder.encode(opis, StandardCharsets.UTF_8.toString())
                    + "&cena=" + URLEncoder.encode(cena, StandardCharsets.UTF_8.toString())
                    + "&procenatPopusta=" + URLEncoder.encode(popust, StandardCharsets.UTF_8.toString())
                    + "&kategorija=" + URLEncoder.encode(kategorija, StandardCharsets.UTF_8.toString()));

            System.out.println(rezultat);
        } catch (Exception e) {
            System.out.println("Greska pri slanju zahteva: " + e.getMessage());
        }
    }

    private static void promeniCenu() {
        System.out.print("Naziv artikla: ");
        String naziv = scanner.nextLine();
        System.out.print("Nova cena: ");
        String cena = scanner.nextLine();

        try {
            String encodedNaziv = URLEncoder.encode(naziv, StandardCharsets.UTF_8.toString());

            String rezultat = HttpKlijent.put("/artikal/cena?korisnik=" + URLEncoder.encode(ulogovanKorisnik, StandardCharsets.UTF_8.toString())
                    + "&sifra=" + URLEncoder.encode(ulogovanaSifra, StandardCharsets.UTF_8.toString())
                    + "&nazivArtikla=" + encodedNaziv
                    + "&cena=" + cena);

            System.out.println(rezultat);
        } catch (Exception e) {
            System.out.println("Greska pri promene cene! ");
        }
    }

    private static void postaviPopust() {
        System.out.print("Naziv artikla: ");
        String naziv = scanner.nextLine();
        System.out.print("Procenat popusta: ");
        String popust = scanner.nextLine();

        try {
            String encodedNaziv = URLEncoder.encode(naziv, StandardCharsets.UTF_8.toString());

            String rezultat = HttpKlijent.put("/artikal/popust?korisnik=" + URLEncoder.encode(ulogovanKorisnik, StandardCharsets.UTF_8.toString())
                    + "&sifra=" + URLEncoder.encode(ulogovanaSifra, StandardCharsets.UTF_8.toString())
                    + "&nazivArtikla=" + encodedNaziv
                    + "&popust=" + popust);

            System.out.println(rezultat);
        } catch (Exception e) {
            System.out.println("Greska pri postavljanju popusta: " + e.getMessage());
        }
    }

    private static void dodajUKorpu() {
        System.out.print("Naziv artikla: ");
        String naziv = scanner.nextLine();
        System.out.print("Kolicina: ");
        String kolicina = scanner.nextLine();

        try {
            String encodedNaziv = URLEncoder.encode(naziv, StandardCharsets.UTF_8.toString());

            String rezultat = HttpKlijent.post("/artikal/korpa?korisnik=" + URLEncoder.encode(ulogovanKorisnik, StandardCharsets.UTF_8.toString())
                    + "&sifra=" + URLEncoder.encode(ulogovanaSifra, StandardCharsets.UTF_8.toString())
                    + "&nazivArtikla=" + encodedNaziv
                    + "&kolicina=" + kolicina);

            System.out.println(rezultat);
        } catch (Exception e) {
            System.out.println("Greska pri dodavanju u korpu: " + e.getMessage());
        }
    }

    private static void obrisiIzKorpe() {
        System.out.print("Naziv artikla: ");
        String naziv = scanner.nextLine();
        System.out.print("Kolicina: ");
        String kolicina = scanner.nextLine();

        try {
            String encodedNaziv = URLEncoder.encode(naziv, StandardCharsets.UTF_8.toString());

            String rezultat = HttpKlijent.post("/artikal/korpa/obrisi?korisnik=" + URLEncoder.encode(ulogovanKorisnik, StandardCharsets.UTF_8.toString())
                    + "&sifra=" + URLEncoder.encode(ulogovanaSifra, StandardCharsets.UTF_8.toString())
                    + "&nazivArtikla=" + encodedNaziv
                    + "&kolicina=" + kolicina);

            System.out.println(rezultat);
        } catch (Exception e) {
            System.out.println("Greska pri brisanju iz korpe: " + e.getMessage());
        }
    }

    private static void pregledajKorpu() {
        String rezultat = HttpKlijent.get("/artikal/korpa/prikaz?korisnik=" + ulogovanKorisnik
                + "&sifra=" + ulogovanaSifra);
        System.out.println(rezultat);
    }

    private static void dodajUListuZelja() {
        System.out.print("Naziv artikla: ");
        String naziv = scanner.nextLine();

        try {
            String encodedNaziv = URLEncoder.encode(naziv, StandardCharsets.UTF_8.toString());

            String rezultat = HttpKlijent.post("/artikal/listaZelja?korisnik=" + URLEncoder.encode(ulogovanKorisnik, StandardCharsets.UTF_8.toString())
                    + "&sifra=" + URLEncoder.encode(ulogovanaSifra, StandardCharsets.UTF_8.toString())
                    + "&nazivArtikla=" + encodedNaziv);

            System.out.println(rezultat);
        } catch (Exception e) {
            System.out.println("Greska pri dodavanju u listu zelja: " + e.getMessage());
        }
    }

    private static void obrisiIzListeZelja() {
        System.out.print("Naziv artikla: ");
        String naziv = scanner.nextLine();

        try {
            String encodedNaziv = URLEncoder.encode(naziv, StandardCharsets.UTF_8.toString());

            String rezultat = HttpKlijent.post("/artikal/listaZelja/obrisi?korisnik=" + URLEncoder.encode(ulogovanKorisnik, StandardCharsets.UTF_8.toString())
                    + "&sifra=" + URLEncoder.encode(ulogovanaSifra, StandardCharsets.UTF_8.toString())
                    + "&nazivArtikla=" + encodedNaziv);

            System.out.println(rezultat);
        } catch (Exception e) {
            System.out.println("Greska pri birsanju iz liste zelja: " + e.getMessage());
        }
    }

    private static void pregledajListuZelja() {
        String rezultat = HttpKlijent.get("/artikal/listaZelja?korisnik=" + ulogovanKorisnik
                + "&sifra=" + ulogovanaSifra);
        System.out.println(rezultat);
    }

    private static void mojiArtikli() {
        String rezultat = HttpKlijent.get("/artikal/moji?korisnik=" + ulogovanKorisnik
                + "&sifra=" + ulogovanaSifra);
        System.out.println(rezultat);
    }

    private static void plati() {
        System.out.print("Adresa dostave: ");
        String adresa = scanner.nextLine();
        System.out.print("Grad dostave: ");
        String grad = scanner.nextLine();

        try {
            String encodedAdresa = URLEncoder.encode(adresa, StandardCharsets.UTF_8.toString());
            String encodedGrad = URLEncoder.encode(grad, StandardCharsets.UTF_8.toString());

            String rezultat = HttpKlijent.post("/porudzbina/plati?korisnik=" + URLEncoder.encode(ulogovanKorisnik, StandardCharsets.UTF_8.toString())
                    + "&sifra=" + URLEncoder.encode(ulogovanaSifra, StandardCharsets.UTF_8.toString())
                    + "&adresaDostave=" + encodedAdresa
                    + "&gradDostave=" + encodedGrad);

            System.out.println(rezultat);
        } catch (Exception e) {
            System.out.println("Greska pri placanju: " + e.getMessage());
        }
    }

    private static void mojeNarudzbine() {
        String rezultat = HttpKlijent.get("/porudzbina/moje?korisnik=" + ulogovanKorisnik
                + "&sifra=" + ulogovanaSifra);
        System.out.println(rezultat);
    }

    private static void sveNarudzbine() {
        String rezultat = HttpKlijent.get("/porudzbina?korisnik=" + ulogovanKorisnik
                + "&sifra=" + ulogovanaSifra);
        System.out.println(rezultat);
    }

    private static void sveTransakcije() {
        String rezultat = HttpKlijent.get("/porudzbina/transakcije?korisnik=" + ulogovanKorisnik
                + "&sifra=" + ulogovanaSifra);
        System.out.println(rezultat);
    }

    private static void sviKorisnici() {
        String rezultat = HttpKlijent.get("/korisnik?adminKorisnik=" + ulogovanKorisnik
                + "&adminSifra=" + ulogovanaSifra);
        System.out.println(rezultat);
    }

    private static void sviGradovi() {
        String rezultat = HttpKlijent.get("/grad?korisnik=" + ulogovanKorisnik
                + "&sifra=" + ulogovanaSifra);
        System.out.println(rezultat);
    }

    private static void kreirajGrad() {
        System.out.print("Naziv grada: ");
        String naziv = scanner.nextLine();

        try {
            String encodedNaziv = URLEncoder.encode(naziv, StandardCharsets.UTF_8.toString());

            String rezultat = HttpKlijent.post("/grad?korisnik=" + URLEncoder.encode(ulogovanKorisnik, StandardCharsets.UTF_8.toString())
                    + "&sifra=" + URLEncoder.encode(ulogovanaSifra, StandardCharsets.UTF_8.toString())
                    + "&naziv=" + encodedNaziv);

            System.out.println(rezultat);
        } catch (Exception e) {
            System.out.println("Greska pri kreiranju grada: " + e.getMessage());
        }
    }

    private static void kreirajKorisnika() {
        System.out.print("Korisnicko ime novog korisnika: ");
        String kIme = scanner.nextLine();
        System.out.print("Sifra: ");
        String sifra = scanner.nextLine();
        System.out.print("Ime: ");
        String ime = scanner.nextLine();
        System.out.print("Prezime: ");
        String prezime = scanner.nextLine();
        System.out.print("Adresa: ");
        String adresa = scanner.nextLine();
        System.out.print("Naziv grada: ");
        String grad = scanner.nextLine();

        String rezultat = HttpKlijent.post("/korisnik?adminKorisnik=" + ulogovanKorisnik
                + "&adminSifra=" + ulogovanaSifra
                + "&korisnickoIme=" + kIme
                + "&sifra=" + sifra
                + "&ime=" + ime
                + "&prezime=" + prezime
                + "&adresa=" + adresa
                + "&nazivGrada=" + grad);
        System.out.println(rezultat);
    }

    private static void dodajNovac() {
        System.out.print("Korisnicko ime korisnika: ");
        String kIme = scanner.nextLine();
        System.out.print("Iznos: ");
        String iznos = scanner.nextLine();

        String rezultat = HttpKlijent.post("/korisnik/novac?adminKorisnik=" + ulogovanKorisnik
                + "&adminSifra=" + ulogovanaSifra
                + "&zaKorisnika=" + kIme
                + "&iznos=" + iznos);
        System.out.println(rezultat);
    }

    private static void promeniAdresu() {
        System.out.print("Korisnicko ime korisnika: ");
        String kIme = scanner.nextLine();
        System.out.print("Nova adresa: ");
        String adresa = scanner.nextLine();
        System.out.print("Novi grad: ");
        String grad = scanner.nextLine();

        try {
            String encodedKorisnik = URLEncoder.encode(ulogovanKorisnik, StandardCharsets.UTF_8.toString());
            String encodedSifra = URLEncoder.encode(ulogovanaSifra, StandardCharsets.UTF_8.toString());
            String encodedKime = URLEncoder.encode(kIme, StandardCharsets.UTF_8.toString());
            String encodedAdresa = URLEncoder.encode(adresa, StandardCharsets.UTF_8.toString());
            String encodedGrad = URLEncoder.encode(grad, StandardCharsets.UTF_8.toString());

            String rezultat = HttpKlijent.put("/korisnik/adresa?adminKorisnik=" + encodedKorisnik
                    + "&adminSifra=" + encodedSifra
                    + "&zaKorisnika=" + encodedKime
                    + "&novaAdresa=" + encodedAdresa
                    + "&noviGrad=" + encodedGrad);

            System.out.println(rezultat);
        } catch (Exception e) {
            System.out.println("Greska pri promeni adrese: " + e.getMessage());
        }
    }

    private static void kreirajKategoriju() {
        System.out.print("Naziv kategorije: ");
        String naziv = scanner.nextLine();
        System.out.print("Naziv nadkategorije (Enter ako nema): ");
        String nadKat = scanner.nextLine();

        try {
            String encodedNaziv = URLEncoder.encode(naziv, StandardCharsets.UTF_8.toString());
            String encodedNadKat = URLEncoder.encode(nadKat, StandardCharsets.UTF_8.toString());

            String rezultat = HttpKlijent.post("/kategorija?korisnik=" + URLEncoder.encode(ulogovanKorisnik, StandardCharsets.UTF_8.toString())
                    + "&sifra=" + URLEncoder.encode(ulogovanaSifra, StandardCharsets.UTF_8.toString())
                    + "&naziv=" + encodedNaziv
                    + "&nadKategorija=" + encodedNadKat);

            System.out.println(rezultat);
        } catch (Exception e) {
            System.out.println("Greska pri kreiranju kategorije: " + e.getMessage());
        }
    }

}
