package entiteti;

import entiteti.Artikal;
import entiteti.Korpa;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2026-06-14T22:51:38")
@StaticMetamodel(Stavkakorpe.class)
public class Stavkakorpe_ { 

    public static volatile SingularAttribute<Stavkakorpe, BigDecimal> cenaStavke;
    public static volatile SingularAttribute<Stavkakorpe, Integer> stavkaKorpeId;
    public static volatile SingularAttribute<Stavkakorpe, Korpa> korpaId;
    public static volatile SingularAttribute<Stavkakorpe, Integer> kolicina;
    public static volatile SingularAttribute<Stavkakorpe, Artikal> artikalId;

}