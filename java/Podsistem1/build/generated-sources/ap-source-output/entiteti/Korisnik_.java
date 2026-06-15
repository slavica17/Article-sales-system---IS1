package entiteti;

import entiteti.Grad;
import entiteti.Uloga;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2026-06-16T01:05:05")
@StaticMetamodel(Korisnik.class)
public class Korisnik_ { 

    public static volatile SingularAttribute<Korisnik, String> ime;
    public static volatile SingularAttribute<Korisnik, String> prezime;
    public static volatile ListAttribute<Korisnik, Uloga> ulogaList;
    public static volatile SingularAttribute<Korisnik, String> adresa;
    public static volatile SingularAttribute<Korisnik, String> sifra;
    public static volatile SingularAttribute<Korisnik, BigDecimal> stanjeNovca;
    public static volatile SingularAttribute<Korisnik, String> korisnickoIme;
    public static volatile SingularAttribute<Korisnik, Integer> korisnikId;
    public static volatile SingularAttribute<Korisnik, Grad> gradId;

}