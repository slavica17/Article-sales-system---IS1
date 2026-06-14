/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ASUS D509D
 */
@Entity
@Table(name = "uloga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Uloga.findAll", query = "SELECT u FROM Uloga u"),
    @NamedQuery(name = "Uloga.findByUlogaId", query = "SELECT u FROM Uloga u WHERE u.ulogaId = :ulogaId"),
    @NamedQuery(name = "Uloga.findByNaziv", query = "SELECT u FROM Uloga u WHERE u.naziv = :naziv")})
public class Uloga implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "uloga_id")
    private Integer ulogaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "naziv")
    private String naziv;

    public Uloga() {
    }

    public Uloga(Integer ulogaId) {
        this.ulogaId = ulogaId;
    }

    public Uloga(Integer ulogaId, String naziv) {
        this.ulogaId = ulogaId;
        this.naziv = naziv;
    }

    public Integer getUlogaId() {
        return ulogaId;
    }

    public void setUlogaId(Integer ulogaId) {
        this.ulogaId = ulogaId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ulogaId != null ? ulogaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Uloga)) {
            return false;
        }
        Uloga other = (Uloga) object;
        if ((this.ulogaId == null && other.ulogaId != null) || (this.ulogaId != null && !this.ulogaId.equals(other.ulogaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Uloga[ ulogaId=" + ulogaId + " ]";
    }
    
}
