/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.yacayo.entity;

import com.sun.istack.internal.NotNull;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author josue.tobarfgkss
 */
@Entity
@Table(name = "detalle_aplicacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleAplicacion.findAll", query = "SELECT d FROM DetalleAplicacion d"),
    @NamedQuery(name = "DetalleAplicacion.findById", query = "SELECT d FROM DetalleAplicacion d WHERE d.id = :id"),
    @NamedQuery(name = "DetalleAplicacion.findByPretencion", query = "SELECT d FROM DetalleAplicacion d WHERE d.pretencion = :pretencion")})
public class DetalleAplicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pretencion")
    @NotNull
    private BigDecimal pretencion;
    @JoinColumn(name = "aplicacion_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Aplicacion aplicacionId;
    @JoinColumn(name = "documento_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Documento documentoId;

    public DetalleAplicacion() {
    }

    public DetalleAplicacion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPretencion() {
        return pretencion;
    }

    public void setPretencion(BigDecimal pretencion) {
        this.pretencion = pretencion;
    }

    public Aplicacion getAplicacionId() {
        return aplicacionId;
    }

    public void setAplicacionId(Aplicacion aplicacionId) {
        this.aplicacionId = aplicacionId;
    }

    public Documento getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(Documento documentoId) {
        this.documentoId = documentoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleAplicacion)) {
            return false;
        }
        DetalleAplicacion other = (DetalleAplicacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.yacayo.entity.DetalleAplicacion[ id=" + id + " ]";
    }
    
}
