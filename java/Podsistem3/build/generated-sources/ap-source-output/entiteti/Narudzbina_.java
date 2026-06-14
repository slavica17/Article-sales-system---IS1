package entiteti;

import entiteti.Korisnik;
import entiteti.Stavka;
import entiteti.Transakcija;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2026-06-14T22:45:28")
@StaticMetamodel(Narudzbina.class)
public class Narudzbina_ { 

    public static volatile SingularAttribute<Narudzbina, BigDecimal> ukupnaCena;
    public static volatile SingularAttribute<Narudzbina, Date> vremeKreiranja;
    public static volatile SingularAttribute<Narudzbina, Transakcija> transakcija;
    public static volatile SingularAttribute<Narudzbina, String> adresaDostave;
    public static volatile ListAttribute<Narudzbina, Stavka> stavkaList;
    public static volatile SingularAttribute<Narudzbina, Integer> narudzbinaId;
    public static volatile SingularAttribute<Narudzbina, Korisnik> kupacId;
    public static volatile SingularAttribute<Narudzbina, String> gradDostave;

}