/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.yacayo.controler;

import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.Persistence;
import sv.com.yacayo.dao.PublicacionesJpaController;
import sv.com.yacayo.entity.Publicaciones;
import sv.com.yacayo.entity.Rubros;

/**
 *
 * @author david.poncefgkss
 */
@ManagedBean(name = "publicaciones")
@RequestScoped
public class PublicacionesControlador {

    private Publicaciones publicaciones;
    private Rubros rubros;
    
    PublicacionesJpaController pDAO;
    
    public PublicacionesControlador() {
        pDAO = new PublicacionesJpaController(Persistence.createEntityManagerFactory("YacayoPU"));
        publicaciones = new Publicaciones();
        rubros = new Rubros();
        
    }
    
    public String insertar(){
        publicaciones.setIdUsuario(SesionUtil.getUserId());
        publicaciones.setEstado("Activo");
        publicaciones.setIdRubro(rubros);
        publicaciones.setFechaPublicacion(new Date());
        
        pDAO.create(publicaciones);
        return "publicacion?faces-redirect=true";
    }
    
    public String eliminar(Publicaciones pu){
        try {
            pDAO.destroy(pu.getId());
            return "publicacion?faces-redirect=true";
        } catch (Exception e) {
            return null;
        }
    }

    public List<Publicaciones> listP(){
        return pDAO.listarP(SesionUtil.getUserId().getId());
    }
    
    public Publicaciones getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(Publicaciones publicaciones) {
        this.publicaciones = publicaciones;
    }

    public Rubros getRubros() {
        return rubros;
    }

    public void setRubros(Rubros rubros) {
        this.rubros = rubros;
    }
    
    
}
