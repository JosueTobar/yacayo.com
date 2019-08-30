package com.yacayo.controladores;

import com.yacayo.dao.UsuarioJpaController;
import com.yacayo.entidades.Usuario;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Persistence;
import java.io.Serializable;
import com.yacayo.controladores.SessionUtils;

import javax.servlet.http.HttpSession;

/**
 * @author josue.tobarfgkss
 */
@ManagedBean(name = "sesion")

@SessionScoped
public class SesionControldor implements Serializable {
    UsuarioJpaController uDao;
    Usuario user;
    public SesionControldor() {
        uDao = new UsuarioJpaController(Persistence.createEntityManagerFactory("YacayoPU"));
        user = new Usuario();
    }
   

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String logear(){
    return "empresa";
    }
    public String login() {
        user = uDao.login(user.getEmail(), user.getClave());
        if (user != null) {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("user", user);
            return "empresa";
        } else {
            System.err.println("Usuario nulo");
            return "login";
        }

    }

    //logout event, cerrar sesion
    public String cerrar() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login";
    }
}
