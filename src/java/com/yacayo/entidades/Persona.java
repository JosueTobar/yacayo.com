/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yacayo.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author david.poncefgkss
 */
@Entity
@Table(name = "persona")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p"),
    @NamedQuery(name = "Persona.findById", query = "SELECT p FROM Persona p WHERE p.id = :id"),
    @NamedQuery(name = "Persona.findByNombres", query = "SELECT p FROM Persona p WHERE p.nombres = :nombres"),
    @NamedQuery(name = "Persona.findByApellidos", query = "SELECT p FROM Persona p WHERE p.apellidos = :apellidos"),
    @NamedQuery(name = "Persona.findByTelefono1", query = "SELECT p FROM Persona p WHERE p.telefono1 = :telefono1"),
    @NamedQuery(name = "Persona.findByTelefono2", query = "SELECT p FROM Persona p WHERE p.telefono2 = :telefono2"),
    @NamedQuery(name = "Persona.findByFchNacimiento", query = "SELECT p FROM Persona p WHERE p.fchNacimiento = :fchNacimiento"),
    @NamedQuery(name = "Persona.findByPretencion", query = "SELECT p FROM Persona p WHERE p.pretencion = :pretencion")})
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOMBRES")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "TELEFONO1")
    private String telefono1;
    @Basic(optional = false)
    @Column(name = "TELEFONO2")
    private String telefono2;
    @Basic(optional = false)
    @Column(name = "FCH_NACIMIENTO")
    @Temporal(TemporalType.DATE)
    private Date fchNacimiento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "PRETENCION")
    private BigDecimal pretencion;
    @JoinColumn(name = "Direccion_idDireccion", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Direccion direccionidDireccion;
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

    public Persona() {
    }

    public Persona(Integer id) {
        this.id = id;
    }

    public Persona(Integer id, String nombres, String apellidos, String telefono1, String telefono2, Date fchNacimiento, BigDecimal pretencion) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.fchNacimiento = fchNacimiento;
        this.pretencion = pretencion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public Date getFchNacimiento() {
        return fchNacimiento;
    }

    public void setFchNacimiento(Date fchNacimiento) {
        this.fchNacimiento = fchNacimiento;
    }

    public BigDecimal getPretencion() {
        return pretencion;
    }

    public void setPretencion(BigDecimal pretencion) {
        this.pretencion = pretencion;
    }

    public Direccion getDireccionidDireccion() {
        return direccionidDireccion;
    }

    public void setDireccionidDireccion(Direccion direccionidDireccion) {
        this.direccionidDireccion = direccionidDireccion;
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
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.yacayo.entidades.Persona[ id=" + id + " ]";
    }
    
}
