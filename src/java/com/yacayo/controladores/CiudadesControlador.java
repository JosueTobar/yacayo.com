/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yacayo.controladores;

import com.yacayo.dao.CiudadJpaController;
import com.yacayo.entidades.Ciudad;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.Persistence;

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
