package entiteti;

import entiteti.Artikal;
import entiteti.Narudzbina;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2026-06-16T01:05:41")
@StaticMetamodel(Korisnik.class)
public class Korisnik_ { 

    public static volatile ListAttribute<Korisnik, Narudzbina> narudzbinaList;
    public static volatile SingularAttribute<Korisnik, String> korisnickoIme;
    public static volatile SingularAttribute<Korisnik, Integer> korisnikId;
    public static volatile ListAttribute<Korisnik, Artikal> artikalList;

}