/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.yacayo.controler;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import sv.com.yacayo.dao.DireccionJpaController;
import sv.com.yacayo.entity.Direccion;

/**
 *
 * @author david.poncefgkss
 */
@ManagedBean(name = "direccion")
@RequestScoped
public class DireccionControlador {

    private Direccion direccion;
    DireccionJpaController dDAO;

    public DireccionControlador() {
         EntityManagerFactory emf = Persistence.createEntityManagerFactory("YacayoPU");
         dDAO = new DireccionJpaController(emf);
         
         direccion = new Direccion();
    }
    
    public Direccion listD() {
        return dDAO.dtDireccion(SesionUtil.getUserId().getId());
    } 

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
    
    

}
