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

    private Usuario user;
    private Direccion direccion;
    private Ciudad ciudad;
    
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
    
    public String insertar(){
        user.setEstado("A");
        
        direccion.setIdCiudad(ciudad);
        dDAO.create(direccion);
        
        direccion=dDAO.ultima(direccion.getDescripcion(), direccion.getIdCiudad().getId());
        
        try {
            uDAO.create(user);
        } catch (Exception e) {
            return "empresa?e=1";
        }
        
        return "login";
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
    
    
}
