package com.yacayo.controladores;

import com.yacayo.dao.DireccionJpaController;
import com.yacayo.dao.EmpresaJpaController;
import com.yacayo.dao.PersonaJpaController;
import com.yacayo.dao.UsuarioJpaController;
import com.yacayo.entidades.Ciudad;
import com.yacayo.entidades.Direccion;
import com.yacayo.entidades.Persona;
import com.yacayo.entidades.TipoUsuario;
import com.yacayo.entidades.Usuario;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.Persistence;

/**
 *
 * @author david.poncefgkss
 */
@ManagedBean(name = "persona")
@RequestScoped
public class PersonaControlador {

    private Persona persona;
    private Usuario usuario;
    private Direccion direccion;
    private Ciudad ciudad;
    
    PersonaJpaController pDAO;
    UsuarioJpaController uDAO;
    DireccionJpaController dDAO;
    
    public PersonaControlador() {
        pDAO = new PersonaJpaController(Persistence.createEntityManagerFactory("YacayoPU"));
        uDAO = new UsuarioJpaController(Persistence.createEntityManagerFactory("YacayoPU"));
        dDAO = new DireccionJpaController(Persistence.createEntityManagerFactory("YacayoPU"));
        
        persona = new Persona();
        usuario = new Usuario();
        direccion = new Direccion();
        ciudad = new Ciudad();
    }
    
    public String insertar(){
        usuario.setEstado("A");
        usuario.setIdTipo(new TipoUsuario(3));
        
        direccion.setIdCiudad(ciudad);
        uDAO.create(usuario);
        dDAO.create(direccion);
        
        usuario= uDAO.ultimo(usuario.getEmail(), usuario.getClave(), usuario.getIdTipo().getId());
        direccion = dDAO.ultima(direccion.getDescripcion(), direccion.getIdCiudad().getId());
        
        persona.setIdUsuario(usuario);
        persona.setIdDireccion(direccion);
        
        try {
            pDAO.create(persona);
        } catch (Exception e) {
            return "/sesion?m=1";
        }
        return "login";
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
