/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.yacayo.controler;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.Persistence;
import sv.com.yacayo.dao.CiudadJpaController;
import sv.com.yacayo.entity.Ciudad;

/**
 *
 * @author david.poncefgkss
 */
@ManagedBean(name = "ciudades")
@RequestScoped
public class CiudadesControlador {

    Ciudad ciudades;
    CiudadJpaController cDAO;

    public CiudadesControlador() {
        cDAO = new CiudadJpaController(Persistence.createEntityManagerFactory("YacayoPU"));
        ciudades = new Ciudad();
    }

    public List<Ciudad> listC() {
        return cDAO.findCiudadEntities();
    }

}


