package sv.com.yacayo.controler;

import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import sv.com.yacayo.dao.DireccionJpaController;
import sv.com.yacayo.dao.UsuarioJpaController;
import sv.com.yacayo.entity.Ciudad;
import sv.com.yacayo.entity.Direccion;
import sv.com.yacayo.entity.TipoUsuario;
import sv.com.yacayo.entity.Usuario;

/**
 *
 * @author david.poncefgkss
 */
@ManagedBean
@RequestScoped
public class EmpresaControlador {

    private Usuario user;
    private Direccion direccion;
    private Ciudad ciudad;

    UsuarioJpaController uDAO;
    DireccionJpaController dDAO;

    public EmpresaControlador() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YacayoPU");
        uDAO = new UsuarioJpaController(emf);
        dDAO = new DireccionJpaController(emf);

        user = new Usuario();
        direccion = new Direccion();
        ciudad = new Ciudad();
    }

    public String insertar() {
        user.setEstado("Activo");
        user.setIdTipo(new TipoUsuario(2));

        uDAO.create(user);

        user = uDAO.ultimo(user.getEmail(), user.getClave(), user.getIdTipo().getId()); 
        try {
            direccion.setUsuarioId(user);
            direccion.setIdCiudad(ciudad);
            dDAO.create(direccion);
        } catch (Exception e) {
            return "registro?e=1";
        }
        return "login";
    }
    
    public String editar(Usuario u){
        Map<String, Object> datos= FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        datos.put("dt", u);
        return "perfil";
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
