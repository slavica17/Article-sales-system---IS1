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
@Table(name = "stavkakorpe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stavkakorpe.findAll", query = "SELECT s FROM Stavkakorpe s"),
    @NamedQuery(name = "Stavkakorpe.findByStavkaKorpeId", query = "SELECT s FROM Stavkakorpe s WHERE s.stavkaKorpeId = :stavkaKorpeId"),
    @NamedQuery(name = "Stavkakorpe.findByKolicina", query = "SELECT s FROM Stavkakorpe s WHERE s.kolicina = :kolicina"),
    @NamedQuery(name = "Stavkakorpe.findByCenaStavke", query = "SELECT s FROM Stavkakorpe s WHERE s.cenaStavke = :cenaStavke")})
public class Stavkakorpe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "stavkaKorpeId")
    private Integer stavkaKorpeId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kolicina")
    private int kolicina;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "cenaStavke")
    private BigDecimal cenaStavke;
    @JoinColumn(name = "artikalId", referencedColumnName = "artikalId")
    @ManyToOne(optional = false)
    private Artikal artikalId;
    @JoinColumn(name = "korpaId", referencedColumnName = "korpaId")
    @ManyToOne(optional = false)
    private Korpa korpaId;

    public Stavkakorpe() {
    }

    public Stavkakorpe(Integer stavkaKorpeId) {
        this.stavkaKorpeId = stavkaKorpeId;
    }

    public Stavkakorpe(Integer stavkaKorpeId, int kolicina, BigDecimal cenaStavke) {
        this.stavkaKorpeId = stavkaKorpeId;
        this.kolicina = kolicina;
        this.cenaStavke = cenaStavke;
    }

    public Integer getStavkaKorpeId() {
        return stavkaKorpeId;
    }

    public void setStavkaKorpeId(Integer stavkaKorpeId) {
        this.stavkaKorpeId = stavkaKorpeId;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public BigDecimal getCenaStavke() {
        return cenaStavke;
    }

    public void setCenaStavke(BigDecimal cenaStavke) {
        this.cenaStavke = cenaStavke;
    }

    public Artikal getArtikalId() {
        return artikalId;
    }

    public void setArtikalId(Artikal artikalId) {
        this.artikalId = artikalId;
    }

    public Korpa getKorpaId() {
        return korpaId;
    }

    public void setKorpaId(Korpa korpaId) {
        this.korpaId = korpaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stavkaKorpeId != null ? stavkaKorpeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stavkakorpe)) {
            return false;
        }
        Stavkakorpe other = (Stavkakorpe) object;
        if ((this.stavkaKorpeId == null && other.stavkaKorpeId != null) || (this.stavkaKorpeId != null && !this.stavkaKorpeId.equals(other.stavkaKorpeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Stavkakorpe[ stavkaKorpeId=" + stavkaKorpeId + " ]";
    }
    
}
