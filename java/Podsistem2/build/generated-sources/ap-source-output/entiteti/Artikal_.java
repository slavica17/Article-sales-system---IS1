package entiteti;

import entiteti.Kategorija;
import entiteti.Korisnik;
import entiteti.Listazelja;
import entiteti.Stavkakorpe;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2026-06-14T22:51:38")
@StaticMetamodel(Artikal.class)
public class Artikal_ { 

    public static volatile ListAttribute<Artikal, Listazelja> listazeljaList;
    public static volatile ListAttribute<Artikal, Stavkakorpe> stavkakorpeList;
    public static volatile SingularAttribute<Artikal, Kategorija> kategorijaId;
    public static volatile SingularAttribute<Artikal, Integer> kolicinaNaStanju;
    public static volatile SingularAttribute<Artikal, String> naziv;
    public static volatile SingularAttribute<Artikal, BigDecimal> procenatPopusta;
    public static volatile SingularAttribute<Artikal, BigDecimal> cena;
    public static volatile SingularAttribute<Artikal, Korisnik> korisnikId;
    public static volatile SingularAttribute<Artikal, Integer> artikalId;
    public static volatile SingularAttribute<Artikal, String> opis;

}