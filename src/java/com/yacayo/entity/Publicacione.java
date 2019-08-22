/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yacayo.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
 * @author josue.tobarfgkss
 */
@Entity
@Table(name = "publicacione")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Publicacione.findAll", query = "SELECT p FROM Publicacione p")
    , @NamedQuery(name = "Publicacione.findById", query = "SELECT p FROM Publicacione p WHERE p.id = :id")
    , @NamedQuery(name = "Publicacione.findByTitulo", query = "SELECT p FROM Publicacione p WHERE p.titulo = :titulo")
    , @NamedQuery(name = "Publicacione.findByFchPublicacion", query = "SELECT p FROM Publicacione p WHERE p.fchPublicacion = :fchPublicacion")
    , @NamedQuery(name = "Publicacione.findByFchVencimiento", query = "SELECT p FROM Publicacione p WHERE p.fchVencimiento = :fchVencimiento")
    , @NamedQuery(name = "Publicacione.findByRequerimientos", query = "SELECT p FROM Publicacione p WHERE p.requerimientos = :requerimientos")
    , @NamedQuery(name = "Publicacione.findByEstado", query = "SELECT p FROM Publicacione p WHERE p.estado = :estado")})
public class Publicacione implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TITULO")
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "FCH_PUBLICACION")
    private String fchPublicacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "FCH_VENCIMIENTO")
    private String fchVencimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "REQUERIMIENTOS")
    private String requerimientos;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publicacioneId")
    private List<Rubros> rubrosList;
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

    public Publicacione() {
    }

    public Publicacione(Integer id) {
        this.id = id;
    }

    public Publicacione(Integer id, String titulo, String fchPublicacion, String fchVencimiento, String requerimientos, String descripcion, String estado) {
        this.id = id;
        this.titulo = titulo;
        this.fchPublicacion = fchPublicacion;
        this.fchVencimiento = fchVencimiento;
        this.requerimientos = requerimientos;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFchPublicacion() {
        return fchPublicacion;
    }

    public void setFchPublicacion(String fchPublicacion) {
        this.fchPublicacion = fchPublicacion;
    }

    public String getFchVencimiento() {
        return fchVencimiento;
    }

    public void setFchVencimiento(String fchVencimiento) {
        this.fchVencimiento = fchVencimiento;
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
    public List<Rubros> getRubrosList() {
        return rubrosList;
    }

    public void setRubrosList(List<Rubros> rubrosList) {
        this.rubrosList = rubrosList;
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
        if (!(object instanceof Publicacione)) {
            return false;
        }
        Publicacione other = (Publicacione) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.yacayo.entity.Publicacione[ id=" + id + " ]";
    }
    
}
