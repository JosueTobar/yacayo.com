
package com.yacayo.controladores;

import com.yacayo.dao.EmpresaJpaController;
import com.yacayo.dao.UsuarioJpaController;
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
@ManagedBean(name = "empresa")
@RequestScoped
public class EmpresaControlador {

    private Empresa empresa;
    private Usuario usuario;
    
    private EmpresaJpaController eDAO;
    private UsuarioJpaController uDAO;
    
    public EmpresaControlador() {
        eDAO = new EmpresaJpaController(Persistence.createEntityManagerFactory("YacayoPU"));
        uDAO = new UsuarioJpaController(Persistence.createEntityManagerFactory("YacayoPU"));
        empresa = new Empresa();
        usuario = new Usuario();
    }
    
    public String insertar(){
        usuario.setEstado("A");
        usuario.setTipoUsuario(new TipoUsuario(2));
        
        uDAO.create(usuario);
        
        usuario = uDAO.ultimo(usuario.getEmail(), usuario.getCalve(), usuario.getTipoUsuario().getId());
        
        
        
        empresa.setUsuarioId(usuario);
        try {
            empresa.setIdDireccion(new Direccion(1));
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
    
    
}
    
