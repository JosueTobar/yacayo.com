/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.yacayo.entity;

import java.io.Serializable;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author josue.tobarfgkss
 */
@Entity
@Table(name = "publicaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Publicaciones.findAll", query = "SELECT p FROM Publicaciones p"),
    @NamedQuery(name = "Publicaciones.findById", query = "SELECT p FROM Publicaciones p WHERE p.id = :id"),
    @NamedQuery(name = "Publicaciones.findByVacantes", query = "SELECT p FROM Publicaciones p WHERE p.vacantes = :vacantes"),
    @NamedQuery(name = "Publicaciones.findByTitulo", query = "SELECT p FROM Publicaciones p WHERE p.titulo = :titulo"),
    @NamedQuery(name = "Publicaciones.findByFechaPublicacion", query = "SELECT p FROM Publicaciones p WHERE p.fechaPublicacion = :fechaPublicacion"),
    @NamedQuery(name = "Publicaciones.findByFechaVencimiento", query = "SELECT p FROM Publicaciones p WHERE p.fechaVencimiento = :fechaVencimiento"),
    @NamedQuery(name = "Publicaciones.findByEstado", query = "SELECT p FROM Publicaciones p WHERE p.estado = :estado"),
    @NamedQuery(name = "Publicaciones.findIdUsuario", query = "SELECT p FROM Publicaciones p WHERE p.idUsuario.id = :id ORDER BY p.fechaPublicacion DESC")})
public class Publicaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "vacantes")
    private Integer vacantes;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "fecha_publicacion")
    @Temporal(TemporalType.DATE)
    private Date fechaPublicacion;
    @Basic(optional = false)
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    @Basic(optional = false)
    @Lob
    @Column(name = "requerimientos")
    private String requerimientos;
    @Basic(optional = false)
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "estado")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publicacionesId")
    private List<Aplicacion> aplicacionList;
    @JoinColumn(name = "idUsuario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario idUsuario;
    @JoinColumn(name = "idRubro", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rubros idRubro;

    public Publicaciones() {
    }

    public Publicaciones(Integer id) {
        this.id = id;
    }

    public Publicaciones(Integer id, String titulo, Date fechaPublicacion, Date fechaVencimiento, String requerimientos, String descripcion) {
        this.id = id;
        this.titulo = titulo;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaVencimiento = fechaVencimiento;
        this.requerimientos = requerimientos;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVacantes() {
        return vacantes;
    }

    public void setVacantes(Integer vacantes) {
        this.vacantes = vacantes;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getRequerimientos() {
        return requerimientos;
    }

    public void setRequerimientos(String requerimientos) {
        this.requerimientos = requerimientos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Aplicacion> getAplicacionList() {
        return aplicacionList;
    }

    public void setAplicacionList(List<Aplicacion> aplicacionList) {
        this.aplicacionList = aplicacionList;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Rubros getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(Rubros idRubro) {
        this.idRubro = idRubro;
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
        if (!(object instanceof Publicaciones)) {
            return false;
        }
        Publicaciones other = (Publicaciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.yacayo.entity.Publicaciones[ id=" + id + " ]";
    }

}
