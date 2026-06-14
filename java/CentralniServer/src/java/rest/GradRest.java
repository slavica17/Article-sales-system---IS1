package rest;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import jms.jmsUpravljac;

@Path("grad")
public class GradRest {

    @EJB
    private jmsUpravljac jmsUpravljac;

    //2 posaljem naziv i koji korisnik kreira-jer treba provera da li je admin
   @POST
public Response kreirajGrad(
        @QueryParam("korisnik") String korisnik, 
        @QueryParam("sifra") String sifra, 
        @QueryParam("naziv") String naziv) {
    
    String ishod = jmsUpravljac.posaljiZahtev(1, 2, korisnik, sifra, naziv);
    return Response.ok(ishod).build();
}

//15
    @GET
    public Response dohvatiSveGradove(
            @QueryParam("korisnik") String korisnik,
            @QueryParam("sifra") String sifra) {
        String ishod = jmsUpravljac.posaljiZahtev(1, 15, korisnik, sifra);
        return Response.ok(ishod).build();
    }
}