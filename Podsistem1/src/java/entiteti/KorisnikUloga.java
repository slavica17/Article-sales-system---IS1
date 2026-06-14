/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ASUS D509D
 */
@Entity
@Table(name = "korisnik_uloga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KorisnikUloga.findAll", query = "SELECT k FROM KorisnikUloga k"),
    @NamedQuery(name = "KorisnikUloga.findByKorisnikId", query = "SELECT k FROM KorisnikUloga k WHERE k.korisnikUlogaPK.korisnikId = :korisnikId"),
    @NamedQuery(name = "KorisnikUloga.findByUlogaId", query = "SELECT k FROM KorisnikUloga k WHERE k.korisnikUlogaPK.ulogaId = :ulogaId")})
public class KorisnikUloga implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KorisnikUlogaPK korisnikUlogaPK;

    public KorisnikUloga() {
    }

    public KorisnikUloga(KorisnikUlogaPK korisnikUlogaPK) {
        this.korisnikUlogaPK = korisnikUlogaPK;
    }

    public KorisnikUloga(int korisnikId, int ulogaId) {
        this.korisnikUlogaPK = new KorisnikUlogaPK(korisnikId, ulogaId);
    }

    public KorisnikUlogaPK getKorisnikUlogaPK() {
        return korisnikUlogaPK;
    }

    public void setKorisnikUlogaPK(KorisnikUlogaPK korisnikUlogaPK) {
        this.korisnikUlogaPK = korisnikUlogaPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (korisnikUlogaPK != null ? korisnikUlogaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KorisnikUloga)) {
            return false;
        }
        KorisnikUloga other = (KorisnikUloga) object;
        if ((this.korisnikUlogaPK == null && other.korisnikUlogaPK != null) || (this.korisnikUlogaPK != null && !this.korisnikUlogaPK.equals(other.korisnikUlogaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.KorisnikUloga[ korisnikUlogaPK=" + korisnikUlogaPK + " ]";
    }
    
}
