package com.yacayo.controladores;

import com.yacayo.dao.UsuarioJpaController;
import com.yacayo.entidades.Usuario;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.persistence.Persistence;

/** @author josue.tobarfgkss */
@ManagedBean(name = "sesion")
@RequestScoped
@SessionScoped
public class SesionControldor {

    public SesionControldor() {
        uDao = new UsuarioJpaController(Persistence.createEntityManagerFactory("YacayoPU"));
    }
    UsuarioJpaController uDao;
    Usuario user;
       
    public String login(){
        uDao.login(user.getEmail(), user.getClave()); 
    return null;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    
}
