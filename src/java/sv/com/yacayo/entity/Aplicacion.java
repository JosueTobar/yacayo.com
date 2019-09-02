/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.yacayo.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author josue.tobarfgkss
 */
@Entity
@Table(name = "aplicacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aplicacion.findAll", query = "SELECT a FROM Aplicacion a"),
    @NamedQuery(name = "Aplicacion.findById", query = "SELECT a FROM Aplicacion a WHERE a.id = :id")})
public class Aplicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aplicacionId")
    private List<DetalleAplicacion> detalleAplicacionList;
    @JoinColumn(name = "publicaciones_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Publicaciones publicacionesId;
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

    public Aplicacion() {
    }

    public Aplicacion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public List<DetalleAplicacion> getDetalleAplicacionList() {
        return detalleAplicacionList;
    }

    public void setDetalleAplicacionList(List<DetalleAplicacion> detalleAplicacionList) {
        this.detalleAplicacionList = detalleAplicacionList;
    }

    public Publicaciones getPublicacionesId() {
        return publicacionesId;
    }

    public void setPublicacionesId(Publicaciones publicacionesId) {
        this.publicacionesId = publicacionesId;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
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
        if (!(object instanceof Aplicacion)) {
            return false;
        }
        Aplicacion other = (Aplicacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.yacayo.entity.Aplicacion[ id=" + id + " ]";
    }
    
}
