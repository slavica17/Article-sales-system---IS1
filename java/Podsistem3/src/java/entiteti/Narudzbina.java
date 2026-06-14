/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ASUS D509D
 */
@Entity
@Table(name = "narudzbina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Narudzbina.findAll", query = "SELECT n FROM Narudzbina n"),
    @NamedQuery(name = "Narudzbina.findByNarudzbinaId", query = "SELECT n FROM Narudzbina n WHERE n.narudzbinaId = :narudzbinaId"),
    @NamedQuery(name = "Narudzbina.findByUkupnaCena", query = "SELECT n FROM Narudzbina n WHERE n.ukupnaCena = :ukupnaCena"),
    @NamedQuery(name = "Narudzbina.findByVremeKreiranja", query = "SELECT n FROM Narudzbina n WHERE n.vremeKreiranja = :vremeKreiranja"),
    @NamedQuery(name = "Narudzbina.findByAdresaDostave", query = "SELECT n FROM Narudzbina n WHERE n.adresaDostave = :adresaDostave"),
    @NamedQuery(name = "Narudzbina.findByGradDostave", query = "SELECT n FROM Narudzbina n WHERE n.gradDostave = :gradDostave")})
public class Narudzbina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "narudzbinaId")
    private Integer narudzbinaId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "ukupnaCena")
    private BigDecimal ukupnaCena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vremeKreiranja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vremeKreiranja;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "adresaDostave")
    private String adresaDostave;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "gradDostave")
    private String gradDostave;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "narudzbinaId")
    private List<Stavka> stavkaList;
    @JoinColumn(name = "kupacId", referencedColumnName = "korisnikId")
    @ManyToOne(optional = false)
    private Korisnik kupacId;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "narudzbinaId")
    private Transakcija transakcija;

    public Narudzbina() {
    }

    public Narudzbina(Integer narudzbinaId) {
        this.narudzbinaId = narudzbinaId;
    }

    public Narudzbina(Integer narudzbinaId, BigDecimal ukupnaCena, Date vremeKreiranja, String adresaDostave, String gradDostave) {
        this.narudzbinaId = narudzbinaId;
        this.ukupnaCena = ukupnaCena;
        this.vremeKreiranja = vremeKreiranja;
        this.adresaDostave = adresaDostave;
        this.gradDostave = gradDostave;
    }

    public Integer getNarudzbinaId() {
        return narudzbinaId;
    }

    public void setNarudzbinaId(Integer narudzbinaId) {
        this.narudzbinaId = narudzbinaId;
    }

    public BigDecimal getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(BigDecimal ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public Date getVremeKreiranja() {
        return vremeKreiranja;
    }

    public void setVremeKreiranja(Date vremeKreiranja) {
        this.vremeKreiranja = vremeKreiranja;
    }

    public String getAdresaDostave() {
        return adresaDostave;
    }

    public void setAdresaDostave(String adresaDostave) {
        this.adresaDostave = adresaDostave;
    }

    public String getGradDostave() {
        return gradDostave;
    }

    public void setGradDostave(String gradDostave) {
        this.gradDostave = gradDostave;
    }

    @XmlTransient
    public List<Stavka> getStavkaList() {
        return stavkaList;
    }

    public void setStavkaList(List<Stavka> stavkaList) {
        this.stavkaList = stavkaList;
    }

    public Korisnik getKupacId() {
        return kupacId;
    }

    public void setKupacId(Korisnik kupacId) {
        this.kupacId = kupacId;
    }

    public Transakcija getTransakcija() {
        return transakcija;
    }

    public void setTransakcija(Transakcija transakcija) {
        this.transakcija = transakcija;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (narudzbinaId != null ? narudzbinaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Narudzbina)) {
            return false;
        }
        Narudzbina other = (Narudzbina) object;
        if ((this.narudzbinaId == null && other.narudzbinaId != null) || (this.narudzbinaId != null && !this.narudzbinaId.equals(other.narudzbinaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Narudzbina[ narudzbinaId=" + narudzbinaId + " ]";
    }
    
}
