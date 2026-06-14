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
@Table(name = "transakcija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transakcija.findAll", query = "SELECT t FROM Transakcija t"),
    @NamedQuery(name = "Transakcija.findByTransakcijaId", query = "SELECT t FROM Transakcija t WHERE t.transakcijaId = :transakcijaId"),
    @NamedQuery(name = "Transakcija.findByNarudzbinaId", query = "SELECT t FROM Transakcija t WHERE t.narudzbinaId = :narudzbinaId"),
    @NamedQuery(name = "Transakcija.findBySuma", query = "SELECT t FROM Transakcija t WHERE t.suma = :suma"),
    @NamedQuery(name = "Transakcija.findByVremePlacanja", query = "SELECT t FROM Transakcija t WHERE t.vremePlacanja = :vremePlacanja"),
    @NamedQuery(name = "Transakcija.findByTipPlacanja", query = "SELECT t FROM Transakcija t WHERE t.tipPlacanja = :tipPlacanja"),
    @NamedQuery(name = "Transakcija.findByStatusPlacanja", query = "SELECT t FROM Transakcija t WHERE t.statusPlacanja = :statusPlacanja")})
public class Transakcija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "transakcija_id")
    private Integer transakcijaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "narudzbina_id")
    private int narudzbinaId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "suma")
    private BigDecimal suma;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vreme_placanja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vremePlacanja;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "tip_placanja")
    private String tipPlacanja;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "status_placanja")
    private String statusPlacanja;

    public Transakcija() {
    }

    public Transakcija(Integer transakcijaId) {
        this.transakcijaId = transakcijaId;
    }

    public Transakcija(Integer transakcijaId, int narudzbinaId, BigDecimal suma, Date vremePlacanja, String tipPlacanja, String statusPlacanja) {
        this.transakcijaId = transakcijaId;
        this.narudzbinaId = narudzbinaId;
        this.suma = suma;
        this.vremePlacanja = vremePlacanja;
        this.tipPlacanja = tipPlacanja;
        this.statusPlacanja = statusPlacanja;
    }

    public Integer getTransakcijaId() {
        return transakcijaId;
    }

    public void setTransakcijaId(Integer transakcijaId) {
        this.transakcijaId = transakcijaId;
    }

    public int getNarudzbinaId() {
        return narudzbinaId;
    }

    public void setNarudzbinaId(int narudzbinaId) {
        this.narudzbinaId = narudzbinaId;
    }

    public BigDecimal getSuma() {
        return suma;
    }

    public void setSuma(BigDecimal suma) {
        this.suma = suma;
    }

    public Date getVremePlacanja() {
        return vremePlacanja;
    }

    public void setVremePlacanja(Date vremePlacanja) {
        this.vremePlacanja = vremePlacanja;
    }

    public String getTipPlacanja() {
        return tipPlacanja;
    }

    public void setTipPlacanja(String tipPlacanja) {
        this.tipPlacanja = tipPlacanja;
    }

    public String getStatusPlacanja() {
        return statusPlacanja;
    }

    public void setStatusPlacanja(String statusPlacanja) {
        this.statusPlacanja = statusPlacanja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transakcijaId != null ? transakcijaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transakcija)) {
            return false;
        }
        Transakcija other = (Transakcija) object;
        if ((this.transakcijaId == null && other.transakcijaId != null) || (this.transakcijaId != null && !this.transakcijaId.equals(other.transakcijaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Transakcija[ transakcijaId=" + transakcijaId + " ]";
    }
    
}
