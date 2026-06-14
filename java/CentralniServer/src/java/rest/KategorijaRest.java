/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rest;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import jms.jmsUpravljac;

/**
 *
 * @author ASUS D509D
 */

@Path("kategorija")
public class KategorijaRest {
    @EJB
    private jmsUpravljac jmsUpravljac;
    
    //6
    @POST
    public Response kreirajKategoriju(
            @QueryParam("korisnik") String korisnik,
            @QueryParam("sifra") String sifra,
            @QueryParam("naziv") String naziv, 
            @QueryParam("nadKategorija") String nadKategorija
    ){
        String ishod = jmsUpravljac.posaljiZahtev(2, 6, korisnik, sifra, naziv, nadKategorija);
        return Response.ok(ishod).build();
    }
 
    //17
    @GET
    public Response dohvatiKategorije(
            @QueryParam("korisnik") String korisnik,
            @QueryParam("sifra") String sifra
    ){
        String ishod = jmsUpravljac.posaljiZahtev(2, 17, korisnik, sifra);
        return Response.ok(ishod).build();
    }
            
    
}
