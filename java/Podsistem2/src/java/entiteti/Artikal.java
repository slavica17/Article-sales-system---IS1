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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
    @NamedQuery(name = "Artikal.findByKolicinaNaStanju", query = "SELECT a FROM Artikal a WHERE a.kolicinaNaStanju = :kolicinaNaStanju")})
public class Artikal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "artikalId")
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
    @Column(name = "procenatPopusta")
    private BigDecimal procenatPopusta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kolicinaNaStanju")
    private int kolicinaNaStanju;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artikalId")
    private List<Listazelja> listazeljaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artikalId")
    private List<Stavkakorpe> stavkakorpeList;
    @JoinColumn(name = "kategorijaId", referencedColumnName = "kategorijaId")
    @ManyToOne(optional = false)
    private Kategorija kategorijaId;
    @JoinColumn(name = "korisnikId", referencedColumnName = "korisnikId")
    @ManyToOne(optional = false)
    private Korisnik korisnikId;

    public Artikal() {
    }

    public Artikal(Integer artikalId) {
        this.artikalId = artikalId;
    }

    public Artikal(Integer artikalId, String naziv, BigDecimal cena, BigDecimal procenatPopusta, int kolicinaNaStanju) {
        this.artikalId = artikalId;
        this.naziv = naziv;
        this.cena = cena;
        this.procenatPopusta = procenatPopusta;
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

    public int getKolicinaNaStanju() {
        return kolicinaNaStanju;
    }

    public void setKolicinaNaStanju(int kolicinaNaStanju) {
        this.kolicinaNaStanju = kolicinaNaStanju;
    }

    @XmlTransient
    public List<Listazelja> getListazeljaList() {
        return listazeljaList;
    }

    public void setListazeljaList(List<Listazelja> listazeljaList) {
        this.listazeljaList = listazeljaList;
    }

    @XmlTransient
    public List<Stavkakorpe> getStavkakorpeList() {
        return stavkakorpeList;
    }

    public void setStavkakorpeList(List<Stavkakorpe> stavkakorpeList) {
        this.stavkakorpeList = stavkakorpeList;
    }

    public Kategorija getKategorijaId() {
        return kategorijaId;
    }

    public void setKategorijaId(Kategorija kategorijaId) {
        this.kategorijaId = kategorijaId;
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
