package sv.com.yacayo.controler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import sv.com.yacayo.dao.CiudadJpaController;
import sv.com.yacayo.dao.DireccionJpaController;
import sv.com.yacayo.dao.TelefonoJpaController;
import sv.com.yacayo.dao.UsuarioJpaController;
import sv.com.yacayo.entity.Ciudad;
import sv.com.yacayo.entity.Direccion;
import sv.com.yacayo.entity.Telefono;
import sv.com.yacayo.entity.TipoUsuario;
import sv.com.yacayo.entity.Usuario;

/**
 *
 * @author david.poncefgkss
 */
@ManagedBean
@RequestScoped
public class EmpresaControlador {

    private Usuario user;
    private Direccion direccion;
    private Ciudad ciudad;
    private Telefono telefono;

    UsuarioJpaController uDAO;
    DireccionJpaController dDAO;
    CiudadJpaController cDAO;
    TelefonoJpaController tDAO;

    public EmpresaControlador() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YacayoPU");
        uDAO = new UsuarioJpaController(emf);
        dDAO = new DireccionJpaController(emf);
        cDAO = new CiudadJpaController(emf);
        tDAO = new TelefonoJpaController(emf);

        user = new Usuario();
        direccion = new Direccion();
        ciudad = new Ciudad();
        telefono = new Telefono();
    }

    public String insertar() {
        user.setEstado("Activo");
        user.setIdTipo(new TipoUsuario(2));

        uDAO.create(user);

        user = uDAO.ultimo(user.getEmail(), user.getClave(), user.getIdTipo().getId());
        try {
            telefono.setIdUsuario(user);
            tDAO.create(telefono);
            direccion.setUsuarioId(user);
            direccion.setIdCiudad(ciudad);
            dDAO.create(direccion);
        } catch (Exception e) {
            return "/faces/views/empresa/registro?e=1";
        }
        return "/faces/index?faces-redirect=true";
    }

    public String modificar() {
        try {
            for (Direccion d : SesionUtil.getUserId().getDireccionList()) {
                dDAO.edit(d);
            }

            uDAO.edit(SesionUtil.getUserId());

            return "agregar?faces-redirect=true";
        } catch (Exception e) {
            return null;
        }
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Telefono getTelefono() {
        return telefono;
    }

    public void setTelefono(Telefono telefono) {
        this.telefono = telefono;
    }

    private Part file1;
    private Part file2;
    private String message;
    
    FacesContext context = FacesContext.getCurrentInstance();
    ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
    private String path = servletContext.getRealPath("");

    public Part getFile1() {
        return file1;
    }
    
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFile1(Part file1) {
        this.file1 = file1;
    }

    public Part getFile2() {
        return file2;
    }

    public void setFile2(Part file2) {
        this.file2 = file2;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String uploadFile() throws IOException {

        InputStream inputStream = null;
        OutputStream outputStream = null;
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
        path = servletContext.getRealPath("");

        boolean file1Success = false;
        if (file1.getSize() > 0) {
            String fileName = getFileNameFromPart(file1);
            Integer id = SesionUtil.getUserId().getId();
            String nombe = "documento_"+ id +".png";
            File outputFile = new File(path + File.separator + "resources" + File.separator + "documentos" + File.separator + nombe);
            inputStream = file1.getInputStream();
            outputStream = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            
            
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            file1Success = true;
        }

        if (file1Success) {
            System.out.println("File uploaded to : " + path);

            setMessage("File successfully uploaded to " + path);
        } else {
            setMessage("Error, select atleast one file!");
        }

        return null;
    }

    public static String getFileNameFromPart(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : partHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                String fileName = content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
                return fileName;
            }
        }
        return null;
    }
}
