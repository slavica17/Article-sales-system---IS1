/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ASUS D509D
 */
@Entity
@Table(name = "listazelja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Listazelja.findAll", query = "SELECT l FROM Listazelja l"),
    @NamedQuery(name = "Listazelja.findByListaZeljaId", query = "SELECT l FROM Listazelja l WHERE l.listaZeljaId = :listaZeljaId"),
    @NamedQuery(name = "Listazelja.findByKorisnikId", query = "SELECT l FROM Listazelja l WHERE l.korisnikId = :korisnikId"),
    @NamedQuery(name = "Listazelja.findByArtikalId", query = "SELECT l FROM Listazelja l WHERE l.artikalId = :artikalId"),
    @NamedQuery(name = "Listazelja.findByDatumDodavanja", query = "SELECT l FROM Listazelja l WHERE l.datumDodavanja = :datumDodavanja")})
public class Listazelja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lista_zelja_id")
    private Integer listaZeljaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "korisnik_id")
    private int korisnikId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "artikal_id")
    private int artikalId;
    @Column(name = "datum_dodavanja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumDodavanja;

    public Listazelja() {
    }

    public Listazelja(Integer listaZeljaId) {
        this.listaZeljaId = listaZeljaId;
    }

    public Listazelja(Integer listaZeljaId, int korisnikId, int artikalId) {
        this.listaZeljaId = listaZeljaId;
        this.korisnikId = korisnikId;
        this.artikalId = artikalId;
    }

    public Integer getListaZeljaId() {
        return listaZeljaId;
    }

    public void setListaZeljaId(Integer listaZeljaId) {
        this.listaZeljaId = listaZeljaId;
    }

    public int getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId = korisnikId;
    }

    public int getArtikalId() {
        return artikalId;
    }

    public void setArtikalId(int artikalId) {
        this.artikalId = artikalId;
    }

    public Date getDatumDodavanja() {
        return datumDodavanja;
    }

    public void setDatumDodavanja(Date datumDodavanja) {
        this.datumDodavanja = datumDodavanja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (listaZeljaId != null ? listaZeljaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Listazelja)) {
            return false;
        }
        Listazelja other = (Listazelja) object;
        if ((this.listaZeljaId == null && other.listaZeljaId != null) || (this.listaZeljaId != null && !this.listaZeljaId.equals(other.listaZeljaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Listazelja[ listaZeljaId=" + listaZeljaId + " ]";
    }
    
}
