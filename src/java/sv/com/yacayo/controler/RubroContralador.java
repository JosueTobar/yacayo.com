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
import sv.com.yacayo.dao.RubrosJpaController;
import sv.com.yacayo.entity.Rubros;

/**
 *
 * @author david.poncefgkss
 */
@ManagedBean(name = "rubro")
@RequestScoped
public class RubroContralador {

    Rubros rubros;
    
    RubrosJpaController rDAO;

    public RubroContralador() {
        
        rDAO= new RubrosJpaController(Persistence.createEntityManagerFactory("YacayoPU"));
        rubros = new Rubros();
        
    }
    
    public List<Rubros> listR(){
        return rDAO.findRubrosEntities();
    }
    
}
