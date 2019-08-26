
package com.yacayo.controladores;

import com.yacayo.dao.EmpresaJpaController;
import com.yacayo.dao.UsuarioJpaController;
import com.yacayo.entidades.Empresa;
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
        uDAO.create(usuario.getEmail(),usuario.getCalve(),"2","A");
        
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
    
