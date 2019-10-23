/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.yacayo.controler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import sv.com.yacayo.dao.AplicacionJpaController;
import sv.com.yacayo.dao.DireccionJpaController;
import sv.com.yacayo.dao.TelefonoJpaController;
import sv.com.yacayo.dao.UsuarioJpaController;
import sv.com.yacayo.entity.Aplicacion;
import sv.com.yacayo.entity.Ciudad;
import sv.com.yacayo.entity.Direccion;
import sv.com.yacayo.entity.Publicaciones;
import sv.com.yacayo.entity.Telefono;
import sv.com.yacayo.entity.TipoUsuario;
import sv.com.yacayo.entity.Usuario;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.faces.context.FacesContext;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;

/**
 *
 * @author Thalia. camposfgkss
 */
@ManagedBean(name = "persona")
@RequestScoped
public class PersonaControlador {

    private Usuario usuario;
    private Direccion direccion;
    private Ciudad ciudad;
    private Telefono telfono;
    private Aplicacion aplicacion;

    private javax.servlet.http.Part file1;
    private javax.servlet.http.Part file2;
    private String message;

    FacesContext context = FacesContext.getCurrentInstance();
    ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
    String path = servletContext.getRealPath("");

    UsuarioJpaController uDAO;
    DireccionJpaController dDAO;
    TelefonoJpaController tDAO;
    AplicacionJpaController aDAO;

    private final Properties properties = new Properties();

    private String password;

    public PersonaControlador() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YacayoPU");
        uDAO = new UsuarioJpaController(emf);
        dDAO = new DireccionJpaController(emf);
        tDAO = new TelefonoJpaController(emf);
        aDAO = new AplicacionJpaController(emf);

        usuario = new Usuario();
        direccion = new Direccion();
        ciudad = new Ciudad();
        telfono = new Telefono();
        aplicacion = new Aplicacion();

    }

    public String ingresar() {
        usuario.setEstado("Activo");
        usuario.setIdTipo(new TipoUsuario(3));
        uDAO.create(usuario);

        usuario = uDAO.ultimo(usuario.getEmail(), usuario.getClave(), usuario.getIdTipo().getId());

        try {
            direccion.setUsuarioId(usuario);
            direccion.setIdCiudad(ciudad);
            dDAO.create(direccion);
            telfono.setIdUsuario(usuario);
            tDAO.create(telfono);

        } catch (Exception e) {
            return "/faces/views/persona/registro?e=1";
        }
        return "/faces/index?faces-redirect=true";
    }

    public String modificar() {
        try {
            for (Telefono t : SesionUtil.getUserId().getTelefonoList()) {
                tDAO.edit(t);
            }
            for (Direccion d : SesionUtil.getUserId().getDireccionList()) {
                dDAO.edit(d);
            }
            uDAO.edit(SesionUtil.getUserId());

            return "aplicar?faces-redirect=true";
        } catch (Exception e) {
            return null;
        }
    }

    public String aplicar(Object[] pu) {
        Properties props = new Properties();

        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        Aplicacion aplic = new Aplicacion();
        aplic.setUsuarioId(SesionUtil.getUserId());
        aplic.setPublicacionesId(new Publicaciones((Integer) pu[0]));

        try {

            aDAO.create(aplic);

            String correoRemitente = "infoyacayo@gmail.com";
            String passwordRemitente = "yacayo123";
            String Asunto = "Nuevo postulante";

            BodyPart mensaje = new MimeBodyPart();
            mensaje.setText("Yacayo.com te informa que tienes una aplicación en la oferta laboral " + pu[5]
                    + "\nDel usuario " + SesionUtil.getUserId().getNombre()
                    + "\nCorreo: " + SesionUtil.getUserId().getEmail());

            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(path +"/resources/cv/CV_" + SesionUtil.getUserId().getNombre() + "_" + SesionUtil.getUserId().getId()+".pdf")));
            adjunto.setFileName("CV_" + SesionUtil.getUserId().getNombre() + "_" + SesionUtil.getUserId().getId()+".pdf");

            MimeMultipart multiparte = new MimeMultipart();
            multiparte.addBodyPart(mensaje);
            multiparte.addBodyPart(adjunto);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correoRemitente));

            String email = pu[2].toString();
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(Asunto);
            message.setContent(multiparte);

            Transport t = session.getTransport("smtp");
            t.connect(correoRemitente, passwordRemitente);
            t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            t.close();

        } catch (Exception e) {
            return "aplicar?e=1";
        }

        return "aplicar?faces-redirect=true";

    }

    public boolean existAplication(Aplicacion a) {
        return !SesionUtil.getUserId().getId().equals(a.getUsuarioId().getId());
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Telefono getTelfono() {
        return telfono;
    }

    public void setTelfono(Telefono telfono) {
        this.telfono = telfono;
    }

    public Aplicacion getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
    }

    public javax.servlet.http.Part getFile1() {
        return file1;
    }

    public void setFile1(javax.servlet.http.Part file1) {
        this.file1 = file1;
    }

    public javax.servlet.http.Part getFile2() {
        return file2;
    }

    public void setFile2(javax.servlet.http.Part file2) {
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
            String nombe = "CV_" + SesionUtil.getUserId().getNombre() + "_" + SesionUtil.getUserId().getId() + ".pdf";
            File outputFile = new File(path + File.separator + "resources" + File.separator + "cv" + File.separator + nombe);

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

    public static String getFileNameFromPart(javax.servlet.http.Part part) {
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
