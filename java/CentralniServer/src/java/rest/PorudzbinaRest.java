package rest;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("porudzbina")
public class PorudzbinaRest {

    @EJB
    private jms.jmsUpravljac jmsUpravljac;

    // 14
    @POST
    @Path("plati")
    public Response plati(
            @QueryParam("korisnik") String korisnik,
            @QueryParam("sifra") String sifra,
            @QueryParam("adresaDostave") String adresaDostave,
            @QueryParam("gradDostave") String gradDostave) {
        
        String stringIdKorisnika = String.valueOf(Math.abs(korisnik.hashCode() % 10000));
        
        String ishod = jmsUpravljac.posaljiZahtev(3, 14, korisnik, stringIdKorisnika, adresaDostave, gradDostave);
        
        return Response.ok(ishod).build();
    }

    // 21
    @GET
    @Path("moje")
    public Response dohvatiMojeNarudzbine(
            @QueryParam("korisnik") String korisnik,
            @QueryParam("sifra") String sifra) {
        
        String ishod = jmsUpravljac.posaljiZahtev(3, 21, korisnik, sifra);
        return Response.ok(ishod).build();
    }

    // 22
    @GET
    public Response dohvatiSveNarudzbine(
            @QueryParam("korisnik") String korisnik,
            @QueryParam("sifra") String sifra) {
        
        String ishod = jmsUpravljac.posaljiZahtev(3, 22, korisnik, sifra);
        return Response.ok(ishod).build();
    }

    // 23
    @GET
    @Path("transakcije")
    public Response dohvatiSveTransakcije(
            @QueryParam("korisnik") String korisnik,
            @QueryParam("sifra") String sifra) {
        
        String ishod = jmsUpravljac.posaljiZahtev(3, 23, korisnik, sifra);
        return Response.ok(ishod).build();
    }
}