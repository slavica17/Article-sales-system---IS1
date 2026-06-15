package entiteti;

import entiteti.Artikal;
import entiteti.Korisnik;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2026-06-16T01:05:27")
@StaticMetamodel(Listazelja.class)
public class Listazelja_ { 

    public static volatile SingularAttribute<Listazelja, Date> datumDodavanja;
    public static volatile SingularAttribute<Listazelja, Korisnik> korisnikId;
    public static volatile SingularAttribute<Listazelja, Artikal> artikalId;
    public static volatile SingularAttribute<Listazelja, Integer> listaZeljaId;

}