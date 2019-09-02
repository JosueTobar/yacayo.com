package sv.com.yacayo.controler;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import sv.com.yacayo.dao.DireccionJpaController;
import sv.com.yacayo.dao.UsuarioJpaController;
import sv.com.yacayo.entity.Ciudad;
import sv.com.yacayo.entity.Direccion;
import sv.com.yacayo.entity.Usuario;

/**
 *
 * @author david.poncefgkss
 */
@ManagedBean
@RequestScoped
public class Empresa {

    Usuario user;
    Direccion direccion;
    Ciudad ciudad;
    
    UsuarioJpaController uDAO;
    DireccionJpaController dDAO;
    
    public Empresa() {
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("YacayoPU");
        uDAO = new UsuarioJpaController(emf);
        dDAO = new DireccionJpaController(emf);
        
        user = new Usuario();
        direccion = new Direccion();
        ciudad = new Ciudad();
    }
    
    
}
