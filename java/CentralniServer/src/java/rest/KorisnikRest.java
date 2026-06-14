/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rest;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import jms.jmsUpravljac;

/**
 *
 * @author ASUS D509D
 */

@Path("korisnik")
public class KorisnikRest {
    
    @EJB
    private jmsUpravljac jmsUpravljac;
    
    //1
    @POST
    @Path("/proveri")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response proveriKorisnika(
            @QueryParam("korisnik") String korisnik,
            @QueryParam("sifra") String sifra){
        String ishod = jmsUpravljac.posaljiZahtev(1,1, korisnik, sifra);
        return Response.ok(ishod).build();
    }
    
    //3
    @POST
    public Response kreirajKorisnika(
            @QueryParam("adminKorisnik") String adminKorisnik,
            @QueryParam("adminSifra") String adminSifra,
            @QueryParam("korisnickoIme") String korisnickoIme,
            @QueryParam("sifra") String sifra,
            @QueryParam("ime") String ime,
            @QueryParam("prezime") String prezime,
            @QueryParam("adresa") String adresa,
            @QueryParam("nazivGrada") String nazivGrada
    ){
      String ishod = jmsUpravljac.posaljiZahtev(1, 3, adminKorisnik, adminSifra, korisnickoIme, sifra, ime, prezime, adresa, nazivGrada );
      
        if (ishod != null && !ishod.contains("Greska")) {
            jmsUpravljac.posaljiZahtev(3, 3, korisnickoIme);
        }
      
      return Response.ok(ishod).build();
        
    }
    
    //4
    @POST
    @Path("novac")
    public Response dodajNovac(
        @QueryParam("adminKorisnik") String adminKorisnik,
        @QueryParam("adminSifra") String adminSifra,
        @QueryParam("zaKorisnika") String zaKorisnika,
        @QueryParam("iznos") String iznos
    ){
        String ishod = jmsUpravljac.posaljiZahtev(1, 4, adminKorisnik, adminSifra, zaKorisnika, iznos);
        return Response.ok(ishod).build();          
    }
    
    //5
    @PUT
    @Path("adresa")
    public Response promeniAdresu(
            @QueryParam("adminKorisnik") String adminKorisnik,
            @QueryParam("adminSifra") String adminSifra,
            @QueryParam("zaKorisnika") String zaKorisnika,
            @QueryParam("novaAdresa") String novaAdresa,
            @QueryParam("noviGrad") String noviGrad
    ){
        String ishod = jmsUpravljac.posaljiZahtev(1, 5, adminKorisnik, adminSifra, zaKorisnika, novaAdresa, noviGrad);
        return Response.ok(ishod).build();
    }
    
    //16
    @GET
    public Response dohvatiKorisnike(
            @QueryParam("adminKorisnik") String adminKorisnik,
            @QueryParam("adminSifra") String adminSifra
    ){
        String ishod = jmsUpravljac.posaljiZahtev(1, 16, adminKorisnik, adminSifra);
        return Response.ok(ishod).build();
    }
    
}
