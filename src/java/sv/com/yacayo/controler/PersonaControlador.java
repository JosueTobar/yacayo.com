/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.yacayo.controler;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
 * @author Thalia. camposfgkss
 */
@ManagedBean(name = "persona")
@RequestScoped
public class PersonaControlador {
  private Usuario usuario;
  private Direccion direccion;
  private Ciudad ciudad;
  private String nombre;
  private String apellido;
    
    UsuarioJpaController uDAO;
    DireccionJpaController dDAO;
    
  
    
    public PersonaControlador() {
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("YacayoPU");
       uDAO=new UsuarioJpaController(emf);
       dDAO=new DireccionJpaController(emf);
       
       usuario=new Usuario();
       direccion= new Direccion();
       ciudad= new Ciudad();
        
    }
    public String ingresar(){
        usuario.setEstado("Activo");
        usuario.setIdTipo(new TipoUsuario(2));
        usuario.setNombre(apellido + ", " + nombre);
        uDAO.create(usuario);
        
        usuario=uDAO.ultimo(usuario.getEmail(), usuario.getClave(),usuario.getIdTipo().getId());
        
        try{
            direccion.setUsuarioId(usuario);
            direccion.setIdCiudad(ciudad);
            dDAO.create(direccion);
        }catch (Exception e){
            return "persona?e=1";
        }
        return "login";
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    
}
