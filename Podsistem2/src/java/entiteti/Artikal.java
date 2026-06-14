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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ASUS D509D
 */
@Entity
@Table(name = "artikal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Artikal.findAll", query = "SELECT a FROM Artikal a"),
    @NamedQuery(name = "Artikal.findByArtikalId", query = "SELECT a FROM Artikal a WHERE a.artikalId = :artikalId"),
    @NamedQuery(name = "Artikal.findByNaziv", query = "SELECT a FROM Artikal a WHERE a.naziv = :naziv"),
    @NamedQuery(name = "Artikal.findByOpis", query = "SELECT a FROM Artikal a WHERE a.opis = :opis"),
    @NamedQuery(name = "Artikal.findByCena", query = "SELECT a FROM Artikal a WHERE a.cena = :cena"),
    @NamedQuery(name = "Artikal.findByProcenatPopusta", query = "SELECT a FROM Artikal a WHERE a.procenatPopusta = :procenatPopusta"),
    @NamedQuery(name = "Artikal.findByKategorijaId", query = "SELECT a FROM Artikal a WHERE a.kategorijaId = :kategorijaId"),
    @NamedQuery(name = "Artikal.findByKorisnikId", query = "SELECT a FROM Artikal a WHERE a.korisnikId = :korisnikId"),
    @NamedQuery(name = "Artikal.findByKolicinaNaStanju", query = "SELECT a FROM Artikal a WHERE a.kolicinaNaStanju = :kolicinaNaStanju")})
public class Artikal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "artikal_id")
    private Integer artikalId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "naziv")
    private String naziv;
    @Size(max = 1000)
    @Column(name = "opis")
    private String opis;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private BigDecimal cena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "procenat_popusta")
    private BigDecimal procenatPopusta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kategorija_id")
    private int kategorijaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "korisnik_id")
    private int korisnikId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kolicina_na_stanju")
    private int kolicinaNaStanju;

    public Artikal() {
    }

    public Artikal(Integer artikalId) {
        this.artikalId = artikalId;
    }

    public Artikal(Integer artikalId, String naziv, BigDecimal cena, BigDecimal procenatPopusta, int kategorijaId, int korisnikId, int kolicinaNaStanju) {
        this.artikalId = artikalId;
        this.naziv = naziv;
        this.cena = cena;
        this.procenatPopusta = procenatPopusta;
        this.kategorijaId = kategorijaId;
        this.korisnikId = korisnikId;
        this.kolicinaNaStanju = kolicinaNaStanju;
    }

    public Integer getArtikalId() {
        return artikalId;
    }

    public void setArtikalId(Integer artikalId) {
        this.artikalId = artikalId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    public BigDecimal getProcenatPopusta() {
        return procenatPopusta;
    }

    public void setProcenatPopusta(BigDecimal procenatPopusta) {
        this.procenatPopusta = procenatPopusta;
    }

    public int getKategorijaId() {
        return kategorijaId;
    }

    public void setKategorijaId(int kategorijaId) {
        this.kategorijaId = kategorijaId;
    }

    public int getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId = korisnikId;
    }

    public int getKolicinaNaStanju() {
        return kolicinaNaStanju;
    }

    public void setKolicinaNaStanju(int kolicinaNaStanju) {
        this.kolicinaNaStanju = kolicinaNaStanju;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (artikalId != null ? artikalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artikal)) {
            return false;
        }
        Artikal other = (Artikal) object;
        if ((this.artikalId == null && other.artikalId != null) || (this.artikalId != null && !this.artikalId.equals(other.artikalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Artikal[ artikalId=" + artikalId + " ]";
    }
    
}
