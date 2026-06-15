package entiteti;

import entiteti.Korisnik;
import entiteti.Stavkakorpe;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2026-06-16T01:05:27")
@StaticMetamodel(Korpa.class)
public class Korpa_ { 

    public static volatile ListAttribute<Korpa, Stavkakorpe> stavkakorpeList;
    public static volatile SingularAttribute<Korpa, BigDecimal> ukupnaCena;
    public static volatile SingularAttribute<Korpa, Integer> korpaId;
    public static volatile SingularAttribute<Korpa, Korisnik> korisnikId;

}