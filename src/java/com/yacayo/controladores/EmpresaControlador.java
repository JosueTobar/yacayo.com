/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yacayo.controladores;

import com.yacayo.dao.DireccionJpaController;
import com.yacayo.dao.EmpresaJpaController;
import com.yacayo.dao.UsuarioJpaController;
import com.yacayo.entidades.Ciudad;
import com.yacayo.entidades.Direccion;
import com.yacayo.entidades.Empresa;
import com.yacayo.entidades.TipoUsuario;
import com.yacayo.entidades.Usuario;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.Persistence;

/**
 *
 * @author david.poncefgkss
 */
@ManagedBean(name = "empresa1")
@RequestScoped
public class EmpresaControlador {

    private Empresa empresa;
    private Usuario usuario;
    private Direccion direccion;
    private Ciudad ciudad;

    private EmpresaJpaController eDAO;
    private UsuarioJpaController uDAO;
    private DireccionJpaController dDAO;

    public EmpresaControlador() {
        eDAO = new EmpresaJpaController(Persistence.createEntityManagerFactory("YacayoPU"));
        uDAO = new UsuarioJpaController(Persistence.createEntityManagerFactory("YacayoPU"));
        dDAO = new DireccionJpaController(Persistence.createEntityManagerFactory("YacayoPU"));

        empresa = new Empresa();
        usuario = new Usuario();
        direccion = new Direccion();
        ciudad = new Ciudad();
    }

    public String insertar() {
        usuario.setEstado("A");
        usuario.setIdTipo(new TipoUsuario(2));

        direccion.setIdCiudad(ciudad);
        uDAO.create(usuario);
        dDAO.create(direccion);

        usuario = uDAO.ultimo(usuario.getEmail(), usuario.getClave(), usuario.getIdTipo().getId());
        direccion = dDAO.ultima(direccion.getDescripcion(), direccion.getIdCiudad().getId());

        empresa.setIdUsuario(usuario);
        empresa.setIdDireccion(direccion);

        try {
            eDAO.create(empresa);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return "login";
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EmpresaJpaController geteDAO() {
        return eDAO;
    }

    public void seteDAO(EmpresaJpaController eDAO) {
        this.eDAO = eDAO;
    }

    public UsuarioJpaController getuDAO() {
        return uDAO;
    }

    public void setuDAO(UsuarioJpaController uDAO) {
        this.uDAO = uDAO;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public DireccionJpaController getdDAO() {
        return dDAO;
    }

    public void setdDAO(DireccionJpaController dDAO) {
        this.dDAO = dDAO;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

}
