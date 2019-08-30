package com.yacayo.controladores;

import com.yacayo.dao.RubrosJpaController;
import com.yacayo.entidades.Rubros;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.Persistence;

/**
 *
 * @author david.poncefgkss
 */
@ManagedBean(name = "rubro")
@RequestScoped
public class RubroControlador {

    Rubros rubros;
    
    RubrosJpaController rDAO;
    
    public RubroControlador() {
        
        rDAO = new RubrosJpaController(Persistence.createEntityManagerFactory("YacayoPU"));
        rubros = new Rubros();
        
    }
    
    public List<Rubros> listR(){
        return rDAO.findRubrosEntities();
    }
}
