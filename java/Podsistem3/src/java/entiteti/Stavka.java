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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "stavka")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stavka.findAll", query = "SELECT s FROM Stavka s"),
    @NamedQuery(name = "Stavka.findByStavkaId", query = "SELECT s FROM Stavka s WHERE s.stavkaId = :stavkaId"),
    @NamedQuery(name = "Stavka.findByKolicina", query = "SELECT s FROM Stavka s WHERE s.kolicina = :kolicina"),
    @NamedQuery(name = "Stavka.findByJedinicnaCena", query = "SELECT s FROM Stavka s WHERE s.jedinicnaCena = :jedinicnaCena")})
public class Stavka implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "stavkaId")
    private Integer stavkaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kolicina")
    private int kolicina;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "jedinicnaCena")
    private BigDecimal jedinicnaCena;
    @JoinColumn(name = "artikalId", referencedColumnName = "artikalId")
    @ManyToOne(optional = false)
    private Artikal artikalId;
    @JoinColumn(name = "narudzbinaId", referencedColumnName = "narudzbinaId")
    @ManyToOne(optional = false)
    private Narudzbina narudzbinaId;

    public Stavka() {
    }

    public Stavka(Integer stavkaId) {
        this.stavkaId = stavkaId;
    }

    public Stavka(Integer stavkaId, int kolicina, BigDecimal jedinicnaCena) {
        this.stavkaId = stavkaId;
        this.kolicina = kolicina;
        this.jedinicnaCena = jedinicnaCena;
    }

    public Integer getStavkaId() {
        return stavkaId;
    }

    public void setStavkaId(Integer stavkaId) {
        this.stavkaId = stavkaId;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public BigDecimal getJedinicnaCena() {
        return jedinicnaCena;
    }

    public void setJedinicnaCena(BigDecimal jedinicnaCena) {
        this.jedinicnaCena = jedinicnaCena;
    }

    public Artikal getArtikalId() {
        return artikalId;
    }

    public void setArtikalId(Artikal artikalId) {
        this.artikalId = artikalId;
    }

    public Narudzbina getNarudzbinaId() {
        return narudzbinaId;
    }

    public void setNarudzbinaId(Narudzbina narudzbinaId) {
        this.narudzbinaId = narudzbinaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stavkaId != null ? stavkaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stavka)) {
            return false;
        }
        Stavka other = (Stavka) object;
        if ((this.stavkaId == null && other.stavkaId != null) || (this.stavkaId != null && !this.stavkaId.equals(other.stavkaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Stavka[ stavkaId=" + stavkaId + " ]";
    }
    
}
