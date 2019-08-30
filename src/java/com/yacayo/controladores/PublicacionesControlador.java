package com.yacayo.controladores;

import com.yacayo.dao.PublicacionesJpaController;
import com.yacayo.entidades.Publicaciones;
import com.yacayo.entidades.Rubros;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.Persistence;

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
        publicaciones.setIdUsuario(SessionUtils.getUserId());
        publicaciones.setEstado("A");
        publicaciones.setIdRubro(rubros);
        publicaciones.setFechaPublicacion(new Date());
        
        pDAO.create(publicaciones);
        
        return "publicacion";
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
