/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author ASUS D509D
 */
@Embeddable
public class KorisnikUlogaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "korisnik_id")
    private int korisnikId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "uloga_id")
    private int ulogaId;

    public KorisnikUlogaPK() {
    }

    public KorisnikUlogaPK(int korisnikId, int ulogaId) {
        this.korisnikId = korisnikId;
        this.ulogaId = ulogaId;
    }

    public int getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId = korisnikId;
    }

    public int getUlogaId() {
        return ulogaId;
    }

    public void setUlogaId(int ulogaId) {
        this.ulogaId = ulogaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) korisnikId;
        hash += (int) ulogaId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KorisnikUlogaPK)) {
            return false;
        }
        KorisnikUlogaPK other = (KorisnikUlogaPK) object;
        if (this.korisnikId != other.korisnikId) {
            return false;
        }
        if (this.ulogaId != other.ulogaId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.KorisnikUlogaPK[ korisnikId=" + korisnikId + ", ulogaId=" + ulogaId + " ]";
    }
    
}
