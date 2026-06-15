package entiteti;

import entiteti.Korisnik;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2026-06-16T01:05:05")
@StaticMetamodel(Uloga.class)
public class Uloga_ { 

    public static volatile SingularAttribute<Uloga, String> naziv;
    public static volatile SingularAttribute<Uloga, Integer> ulogaId;
    public static volatile ListAttribute<Uloga, Korisnik> korisnikList;

}