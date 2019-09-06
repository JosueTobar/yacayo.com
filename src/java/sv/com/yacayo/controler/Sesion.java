package sv.com.yacayo.controler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Persistence;
import java.io.Serializable;
import javax.servlet.http.HttpSession;
import sv.com.yacayo.dao.UsuarioJpaController;
import sv.com.yacayo.entity.Usuario;

/**  @author josue.tobarfgkss */
@ManagedBean(name = "sesion")

@SessionScoped
public class Sesion implements Serializable {
    UsuarioJpaController uDao;
    Usuario user;
    public Sesion() {
        uDao = new UsuarioJpaController(Persistence.createEntityManagerFactory("YacayoPU"));
        user = new Usuario();
    }
   

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

   
    public String login() {
        user = uDao.login(user.getEmail(), user.getClave());
        if (user != null) {
            HttpSession session = SesionUtil.getSession();
            session.setAttribute("user", user);
            return "empresa/agregar";
        } else {
            System.err.println("Usuario nulo");
            return "login";
        }

    }

    //logout event, cerrar sesion
    public String cerrar() {
        HttpSession session = SesionUtil.getSession();
        session.invalidate();
        return "login";
    }
}