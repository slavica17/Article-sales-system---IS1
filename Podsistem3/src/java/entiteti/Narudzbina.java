/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

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
    @NamedQuery(name = "Narudzbina.findByKupacId", query = "SELECT n FROM Narudzbina n WHERE n.kupacId = :kupacId"),
    @NamedQuery(name = "Narudzbina.findByUkupnaCena", query = "SELECT n FROM Narudzbina n WHERE n.ukupnaCena = :ukupnaCena"),
    @NamedQuery(name = "Narudzbina.findByVremeKreiranja", query = "SELECT n FROM Narudzbina n WHERE n.vremeKreiranja = :vremeKreiranja"),
    @NamedQuery(name = "Narudzbina.findByAdresaDostave", query = "SELECT n FROM Narudzbina n WHERE n.adresaDostave = :adresaDostave"),
    @NamedQuery(name = "Narudzbina.findByGrad", query = "SELECT n FROM Narudzbina n WHERE n.grad = :grad"),
    @NamedQuery(name = "Narudzbina.findByStatus", query = "SELECT n FROM Narudzbina n WHERE n.status = :status")})
public class Narudzbina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "narudzbina_id")
    private Integer narudzbinaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kupac_id")
    private int kupacId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "ukupna_cena")
    private BigDecimal ukupnaCena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vreme_kreiranja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vremeKreiranja;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "adresa_dostave")
    private String adresaDostave;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "grad")
    private String grad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "status")
    private String status;

    public Narudzbina() {
    }

    public Narudzbina(Integer narudzbinaId) {
        this.narudzbinaId = narudzbinaId;
    }

    public Narudzbina(Integer narudzbinaId, int kupacId, BigDecimal ukupnaCena, Date vremeKreiranja, String adresaDostave, String grad, String status) {
        this.narudzbinaId = narudzbinaId;
        this.kupacId = kupacId;
        this.ukupnaCena = ukupnaCena;
        this.vremeKreiranja = vremeKreiranja;
        this.adresaDostave = adresaDostave;
        this.grad = grad;
        this.status = status;
    }

    public Integer getNarudzbinaId() {
        return narudzbinaId;
    }

    public void setNarudzbinaId(Integer narudzbinaId) {
        this.narudzbinaId = narudzbinaId;
    }

    public int getKupacId() {
        return kupacId;
    }

    public void setKupacId(int kupacId) {
        this.kupacId = kupacId;
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

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
