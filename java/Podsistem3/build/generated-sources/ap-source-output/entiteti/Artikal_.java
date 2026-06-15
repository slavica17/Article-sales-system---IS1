package entiteti;

import entiteti.Korisnik;
import entiteti.Stavka;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2026-06-16T01:05:41")
@StaticMetamodel(Artikal.class)
public class Artikal_ { 

    public static volatile SingularAttribute<Artikal, String> naziv;
    public static volatile ListAttribute<Artikal, Stavka> stavkaList;
    public static volatile SingularAttribute<Artikal, Integer> artikalId;
    public static volatile SingularAttribute<Artikal, Korisnik> korisnik;

}