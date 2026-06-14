/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;

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
    @NamedQuery(name = "Korpa.findByKorisnikId", query = "SELECT k FROM Korpa k WHERE k.korisnikId = :korisnikId"),
    @NamedQuery(name = "Korpa.findByArtikalId", query = "SELECT k FROM Korpa k WHERE k.artikalId = :artikalId"),
    @NamedQuery(name = "Korpa.findByKolicina", query = "SELECT k FROM Korpa k WHERE k.kolicina = :kolicina"),
    @NamedQuery(name = "Korpa.findByUkupnaCena", query = "SELECT k FROM Korpa k WHERE k.ukupnaCena = :ukupnaCena")})
public class Korpa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "korpa_id")
    private Integer korpaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "korisnik_id")
    private int korisnikId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "artikal_id")
    private int artikalId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kolicina")
    private int kolicina;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "ukupna_cena")
    private BigDecimal ukupnaCena;

    public Korpa() {
    }

    public Korpa(Integer korpaId) {
        this.korpaId = korpaId;
    }

    public Korpa(Integer korpaId, int korisnikId, int artikalId, int kolicina, BigDecimal ukupnaCena) {
        this.korpaId = korpaId;
        this.korisnikId = korisnikId;
        this.artikalId = artikalId;
        this.kolicina = kolicina;
        this.ukupnaCena = ukupnaCena;
    }

    public Integer getKorpaId() {
        return korpaId;
    }

    public void setKorpaId(Integer korpaId) {
        this.korpaId = korpaId;
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

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public BigDecimal getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(BigDecimal ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
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
