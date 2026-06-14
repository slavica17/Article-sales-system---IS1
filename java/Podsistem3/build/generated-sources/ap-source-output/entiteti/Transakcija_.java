package entiteti;

import entiteti.Narudzbina;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2026-06-14T22:45:28")
@StaticMetamodel(Transakcija.class)
public class Transakcija_ { 

    public static volatile SingularAttribute<Transakcija, BigDecimal> suma;
    public static volatile SingularAttribute<Transakcija, Date> vremePlacanja;
    public static volatile SingularAttribute<Transakcija, Narudzbina> narudzbinaId;
    public static volatile SingularAttribute<Transakcija, Integer> transakcijaId;

}