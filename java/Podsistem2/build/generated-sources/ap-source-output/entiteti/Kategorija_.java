package entiteti;

import entiteti.Artikal;
import entiteti.Kategorija;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2026-06-14T22:51:38")
@StaticMetamodel(Kategorija.class)
public class Kategorija_ { 

    public static volatile ListAttribute<Kategorija, Kategorija> kategorijaList;
    public static volatile SingularAttribute<Kategorija, Integer> kategorijaId;
    public static volatile SingularAttribute<Kategorija, Kategorija> nadkategorijaId;
    public static volatile SingularAttribute<Kategorija, String> naziv;
    public static volatile ListAttribute<Kategorija, Artikal> artikalList;

}