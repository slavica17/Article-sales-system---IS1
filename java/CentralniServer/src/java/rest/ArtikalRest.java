/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rest;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author ASUS D509D
 */

@Path("artikal")
public class ArtikalRest {
    
    @EJB
    private jms.jmsUpravljac jmsUpravljac;
    
    //7
    @POST
    @Path("kreiraj")
    public Response kreirajArtikal(
            @QueryParam("korisnik") String korisnik,
            @QueryParam("sifra") String sifra,
            @QueryParam("naziv") String naziv,
            @QueryParam("opis") String opis,
            @QueryParam("cena") String cena,
            @QueryParam("procenatPopusta") String procenatPopusta,
            @QueryParam("kategorija") String kategorija
    ){
        String ishod = jmsUpravljac.posaljiZahtev(2, 7, korisnik, sifra, naziv, opis, cena, procenatPopusta, kategorija);
        return Response.ok(ishod).build();
    }
    
    //8
    @PUT
    @Path("cena")
    public Response promeniCenu(
            @QueryParam("korisnik") String korisnik,
            @QueryParam("sifra") String sifra,
            @QueryParam("nazivArtikla") String nazivArtikla,
            @QueryParam("cena") String cena
    ){
        String ishod = jmsUpravljac.posaljiZahtev(2, 8, korisnik, sifra, nazivArtikla, cena);
        return Response.ok(ishod).build();
    }
 
    //9
    @PUT
    @Path("popust")
    public Response postaviPopust(
            @QueryParam("korisnik") String korisnik,
            @QueryParam("sifra") String sifra,
            @QueryParam("nazivArtikla") String nazivArtikla,
            @QueryParam("popust") String popust
    ){
        String ishod = jmsUpravljac.posaljiZahtev(2, 9, korisnik, sifra, nazivArtikla, popust);
        return Response.ok(ishod).build();
    }
    
    //10
    @POST
    @Path("korpa")
    public Response dodajUKorpu(
            @QueryParam("korisnik") String korisnik,
            @QueryParam("sifra") String sifra,
            @QueryParam("nazivArtikla") String nazivArtikla,
            @QueryParam("kolicina") String kolicina) {
        
        String ishod = jmsUpravljac.posaljiZahtev(2, 10, korisnik, sifra, nazivArtikla, kolicina);
        return Response.ok(ishod).build();
    }
    
    // 11
    @POST
    @Path("korpa/obrisi")
    public Response obrisiIzKorpe(
            @QueryParam("korisnik") String korisnik,
            @QueryParam("sifra") String sifra,
            @QueryParam("nazivArtikla") String nazivArtikla,
            @QueryParam("kolicina") String kolicina) {
        
        String ishod = jmsUpravljac.posaljiZahtev(2, 11, korisnik, sifra, nazivArtikla, kolicina);
        return Response.ok(ishod).build();
    }

    // 12
    @POST
    @Path("listaZelja")
    public Response dodajUListuZelja(
            @QueryParam("korisnik") String korisnik,
            @QueryParam("sifra") String sifra,
            @QueryParam("nazivArtikla") String nazivArtikla) {
        
        String ishod = jmsUpravljac.posaljiZahtev(2, 12, korisnik, sifra, nazivArtikla);
        return Response.ok(ishod).build();
    }

    // 13
    @POST
    @Path("listaZelja/obrisi")
    public Response obrisiIzListeZelja(
            @QueryParam("korisnik") String korisnik,
            @QueryParam("sifra") String sifra,
            @QueryParam("nazivArtikla") String nazivArtikla) {
        
        String ishod = jmsUpravljac.posaljiZahtev(2, 13, korisnik, sifra, nazivArtikla);
        return Response.ok(ishod).build();
    }

    // 18
    @GET
    @Path("moji")
    public Response dohvatiMojeArtikle(
            @QueryParam("korisnik") String korisnik,
            @QueryParam("sifra") String sifra) {
        
        String ishod = jmsUpravljac.posaljiZahtev(2, 18, korisnik, sifra);
        return Response.ok(ishod).build();
    }

    // 19
    @GET
    @Path("korpa/prikaz")
    public Response dohvatiKorpu(
            @QueryParam("korisnik") String korisnik,
            @QueryParam("sifra") String sifra) {
        
        String ishod = jmsUpravljac.posaljiZahtev(2, 19, korisnik, sifra);
        return Response.ok(ishod).build();
    }

    // 20
    @GET
    @Path("listaZelja")
    public Response dohvatiListuZelja(
            @QueryParam("korisnik") String korisnik,
            @QueryParam("sifra") String sifra) {
        
        String ishod = jmsUpravljac.posaljiZahtev(2, 20, korisnik, sifra);
        return Response.ok(ishod).build();
    }
    
}
