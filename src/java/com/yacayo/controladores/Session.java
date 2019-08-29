package com.yacayo.controladores;
import com.yacayo.dao.UsuarioJpaController;
import com.yacayo.entidades.Usuario;
import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.persistence.Persistence;

/**  @author josue.tobarfgkss */
@ManagedBean
@SessionScoped
//@RequestScoped

public class Session {
  
    UsuarioJpaController uDao;
    Usuario user;
    public Session() {
        uDao = new UsuarioJpaController(Persistence.createEntityManagerFactory("YacayoPU"));
    }
    
    public String login(String email, String clave){
      
    
         uDao.login(email, clave);
        
    return null;
    }
}
