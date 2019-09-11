package sv.com.yacayo.controler;

import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import sv.com.yacayo.dao.CiudadJpaController;
import sv.com.yacayo.dao.DireccionJpaController;
import sv.com.yacayo.dao.TelefonoJpaController;
import sv.com.yacayo.dao.UsuarioJpaController;
import sv.com.yacayo.entity.Ciudad;
import sv.com.yacayo.entity.Direccion;
import sv.com.yacayo.entity.Telefono;
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
    private Telefono telefono;
    
    UsuarioJpaController uDAO;
    DireccionJpaController dDAO;
    CiudadJpaController cDAO;
    TelefonoJpaController tDAO;
    
    public EmpresaControlador() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YacayoPU");
        uDAO = new UsuarioJpaController(emf);
        dDAO = new DireccionJpaController(emf);
        cDAO = new CiudadJpaController(emf);
        tDAO = new TelefonoJpaController(emf);
        
        user = new Usuario();
        direccion = new Direccion();
        ciudad = new Ciudad();
        telefono = new Telefono();
    }
    
    public String insertar() {
        user.setEstado("Activo");
        user.setIdTipo(new TipoUsuario(2));
        
        uDAO.create(user);
        
        user = uDAO.ultimo(user.getEmail(), user.getClave(), user.getIdTipo().getId());        
        try {
            telefono.setIdUsuario(user);
            tDAO.create(telefono);
            direccion.setUsuarioId(user);
            direccion.setIdCiudad(ciudad);
            dDAO.create(direccion);
        } catch (Exception e) {
            return "/faces/views/empresa/registro?e=1";
        }
         return "/faces/index?faces-redirect=true";
    }
    
    public String editar(Usuario u) {
        Map<String, Object> datos = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        datos.put("dt", u);
        return "perfil";
    }
    
    public String modificar() {
        try {
            for (Direccion d : SesionUtil.getUserId().getDireccionList()) {
                dDAO.edit(d);
            }
            
            uDAO.edit(SesionUtil.getUserId());
            
            return "agregar";
        } catch (Exception e) {
            return null;
        }
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
    
    public Telefono getTelefono() {
        return telefono;
    }
    
    public void setTelefono(Telefono telefono) {
        this.telefono = telefono;
    }
    
}
