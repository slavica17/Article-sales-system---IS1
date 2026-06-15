package entiteti;

import entiteti.Artikal;
import entiteti.Narudzbina;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2026-06-16T01:05:41")
@StaticMetamodel(Stavka.class)
public class Stavka_ { 

    public static volatile SingularAttribute<Stavka, BigDecimal> jedinicnaCena;
    public static volatile SingularAttribute<Stavka, Integer> stavkaId;
    public static volatile SingularAttribute<Stavka, Integer> kolicina;
    public static volatile SingularAttribute<Stavka, Narudzbina> narudzbinaId;
    public static volatile SingularAttribute<Stavka, Artikal> artikalId;

}