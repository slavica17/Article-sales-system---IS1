package entiteti;

import entiteti.Korisnik;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2026-06-14T22:20:56")
@StaticMetamodel(Grad.class)
public class Grad_ { 

    public static volatile SingularAttribute<Grad, String> naziv;
    public static volatile SingularAttribute<Grad, Integer> gradId;
    public static volatile ListAttribute<Grad, Korisnik> korisnikList;

}