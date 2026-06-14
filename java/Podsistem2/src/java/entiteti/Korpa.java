/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ASUS D509D
 */
@Entity
@Table(name = "korpa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korpa.findAll", query = "SELECT k FROM Korpa k"),
    @NamedQuery(name = "Korpa.findByKorpaId", query = "SELECT k FROM Korpa k WHERE k.korpaId = :korpaId"),
    @NamedQuery(name = "Korpa.findByUkupnaCena", query = "SELECT k FROM Korpa k WHERE k.ukupnaCena = :ukupnaCena")})
public class Korpa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "korpaId")
    private Integer korpaId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "ukupnaCena")
    private BigDecimal ukupnaCena;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "korpaId")
    private List<Stavkakorpe> stavkakorpeList;
    @JoinColumn(name = "korisnikId", referencedColumnName = "korisnikId")
    @OneToOne(optional = false)
    private Korisnik korisnikId;

    public Korpa() {
    }

    public Korpa(Integer korpaId) {
        this.korpaId = korpaId;
    }

    public Korpa(Integer korpaId, BigDecimal ukupnaCena) {
        this.korpaId = korpaId;
        this.ukupnaCena = ukupnaCena;
    }

    public Integer getKorpaId() {
        return korpaId;
    }

    public void setKorpaId(Integer korpaId) {
        this.korpaId = korpaId;
    }

    public BigDecimal getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(BigDecimal ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    @XmlTransient
    public List<Stavkakorpe> getStavkakorpeList() {
        return stavkakorpeList;
    }

    public void setStavkakorpeList(List<Stavkakorpe> stavkakorpeList) {
        this.stavkakorpeList = stavkakorpeList;
    }

    public Korisnik getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Korisnik korisnikId) {
        this.korisnikId = korisnikId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (korpaId != null ? korpaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korpa)) {
            return false;
        }
        Korpa other = (Korpa) object;
        if ((this.korpaId == null && other.korpaId != null) || (this.korpaId != null && !this.korpaId.equals(other.korpaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Korpa[ korpaId=" + korpaId + " ]";
    }
    
}
