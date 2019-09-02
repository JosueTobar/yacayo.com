/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.yacayo.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import sv.com.yacayo.entity.TipoUsuario;
import sv.com.yacayo.entity.Direccion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sv.com.yacayo.dao.exceptions.IllegalOrphanException;
import sv.com.yacayo.dao.exceptions.NonexistentEntityException;
import sv.com.yacayo.entity.Aplicacion;
import sv.com.yacayo.entity.Documento;
import sv.com.yacayo.entity.Publicaciones;
import sv.com.yacayo.entity.Telefono;
import sv.com.yacayo.entity.Email;
import sv.com.yacayo.entity.Usuario;

/**
 *
 * @author josue.tobarfgkss
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getDireccionList() == null) {
            usuario.setDireccionList(new ArrayList<Direccion>());
        }
        if (usuario.getAplicacionList() == null) {
            usuario.setAplicacionList(new ArrayList<Aplicacion>());
        }
        if (usuario.getDocumentoList() == null) {
            usuario.setDocumentoList(new ArrayList<Documento>());
        }
        if (usuario.getPublicacionesList() == null) {
            usuario.setPublicacionesList(new ArrayList<Publicaciones>());
        }
        if (usuario.getTelefonoList() == null) {
            usuario.setTelefonoList(new ArrayList<Telefono>());
        }
        if (usuario.getEmailList() == null) {
            usuario.setEmailList(new ArrayList<Email>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoUsuario idTipo = usuario.getIdTipo();
            if (idTipo != null) {
                idTipo = em.getReference(idTipo.getClass(), idTipo.getId());
                usuario.setIdTipo(idTipo);
            }
            List<Direccion> attachedDireccionList = new ArrayList<Direccion>();
            for (Direccion direccionListDireccionToAttach : usuario.getDireccionList()) {
                direccionListDireccionToAttach = em.getReference(direccionListDireccionToAttach.getClass(), direccionListDireccionToAttach.getId());
                attachedDireccionList.add(direccionListDireccionToAttach);
            }
            usuario.setDireccionList(attachedDireccionList);
            List<Aplicacion> attachedAplicacionList = new ArrayList<Aplicacion>();
            for (Aplicacion aplicacionListAplicacionToAttach : usuario.getAplicacionList()) {
                aplicacionListAplicacionToAttach = em.getReference(aplicacionListAplicacionToAttach.getClass(), aplicacionListAplicacionToAttach.getId());
                attachedAplicacionList.add(aplicacionListAplicacionToAttach);
            }
            usuario.setAplicacionList(attachedAplicacionList);
            List<Documento> attachedDocumentoList = new ArrayList<Documento>();
            for (Documento documentoListDocumentoToAttach : usuario.getDocumentoList()) {
                documentoListDocumentoToAttach = em.getReference(documentoListDocumentoToAttach.getClass(), documentoListDocumentoToAttach.getId());
                attachedDocumentoList.add(documentoListDocumentoToAttach);
            }
            usuario.setDocumentoList(attachedDocumentoList);
            List<Publicaciones> attachedPublicacionesList = new ArrayList<Publicaciones>();
            for (Publicaciones publicacionesListPublicacionesToAttach : usuario.getPublicacionesList()) {
                publicacionesListPublicacionesToAttach = em.getReference(publicacionesListPublicacionesToAttach.getClass(), publicacionesListPublicacionesToAttach.getId());
                attachedPublicacionesList.add(publicacionesListPublicacionesToAttach);
            }
            usuario.setPublicacionesList(attachedPublicacionesList);
            List<Telefono> attachedTelefonoList = new ArrayList<Telefono>();
            for (Telefono telefonoListTelefonoToAttach : usuario.getTelefonoList()) {
                telefonoListTelefonoToAttach = em.getReference(telefonoListTelefonoToAttach.getClass(), telefonoListTelefonoToAttach.getId());
                attachedTelefonoList.add(telefonoListTelefonoToAttach);
            }
            usuario.setTelefonoList(attachedTelefonoList);
            List<Email> attachedEmailList = new ArrayList<Email>();
            for (Email emailListEmailToAttach : usuario.getEmailList()) {
                emailListEmailToAttach = em.getReference(emailListEmailToAttach.getClass(), emailListEmailToAttach.getId());
                attachedEmailList.add(emailListEmailToAttach);
            }
            usuario.setEmailList(attachedEmailList);
            em.persist(usuario);
            if (idTipo != null) {
                idTipo.getUsuarioList().add(usuario);
                idTipo = em.merge(idTipo);
            }
            for (Direccion direccionListDireccion : usuario.getDireccionList()) {
                Usuario oldUsuarioIdOfDireccionListDireccion = direccionListDireccion.getUsuarioId();
                direccionListDireccion.setUsuarioId(usuario);
                direccionListDireccion = em.merge(direccionListDireccion);
                if (oldUsuarioIdOfDireccionListDireccion != null) {
                    oldUsuarioIdOfDireccionListDireccion.getDireccionList().remove(direccionListDireccion);
                    oldUsuarioIdOfDireccionListDireccion = em.merge(oldUsuarioIdOfDireccionListDireccion);
                }
            }
            for (Aplicacion aplicacionListAplicacion : usuario.getAplicacionList()) {
                Usuario oldUsuarioIdOfAplicacionListAplicacion = aplicacionListAplicacion.getUsuarioId();
                aplicacionListAplicacion.setUsuarioId(usuario);
                aplicacionListAplicacion = em.merge(aplicacionListAplicacion);
                if (oldUsuarioIdOfAplicacionListAplicacion != null) {
                    oldUsuarioIdOfAplicacionListAplicacion.getAplicacionList().remove(aplicacionListAplicacion);
                    oldUsuarioIdOfAplicacionListAplicacion = em.merge(oldUsuarioIdOfAplicacionListAplicacion);
                }
            }
            for (Documento documentoListDocumento : usuario.getDocumentoList()) {
                Usuario oldIdUsuarioOfDocumentoListDocumento = documentoListDocumento.getIdUsuario();
                documentoListDocumento.setIdUsuario(usuario);
                documentoListDocumento = em.merge(documentoListDocumento);
                if (oldIdUsuarioOfDocumentoListDocumento != null) {
                    oldIdUsuarioOfDocumentoListDocumento.getDocumentoList().remove(documentoListDocumento);
                    oldIdUsuarioOfDocumentoListDocumento = em.merge(oldIdUsuarioOfDocumentoListDocumento);
                }
            }
            for (Publicaciones publicacionesListPublicaciones : usuario.getPublicacionesList()) {
                Usuario oldIdUsuarioOfPublicacionesListPublicaciones = publicacionesListPublicaciones.getIdUsuario();
                publicacionesListPublicaciones.setIdUsuario(usuario);
                publicacionesListPublicaciones = em.merge(publicacionesListPublicaciones);
                if (oldIdUsuarioOfPublicacionesListPublicaciones != null) {
                    oldIdUsuarioOfPublicacionesListPublicaciones.getPublicacionesList().remove(publicacionesListPublicaciones);
                    oldIdUsuarioOfPublicacionesListPublicaciones = em.merge(oldIdUsuarioOfPublicacionesListPublicaciones);
                }
            }
            for (Telefono telefonoListTelefono : usuario.getTelefonoList()) {
                Usuario oldIdUsuarioOfTelefonoListTelefono = telefonoListTelefono.getIdUsuario();
                telefonoListTelefono.setIdUsuario(usuario);
                telefonoListTelefono = em.merge(telefonoListTelefono);
                if (oldIdUsuarioOfTelefonoListTelefono != null) {
                    oldIdUsuarioOfTelefonoListTelefono.getTelefonoList().remove(telefonoListTelefono);
                    oldIdUsuarioOfTelefonoListTelefono = em.merge(oldIdUsuarioOfTelefonoListTelefono);
                }
            }
            for (Email emailListEmail : usuario.getEmailList()) {
                Usuario oldIdUsuarioOfEmailListEmail = emailListEmail.getIdUsuario();
                emailListEmail.setIdUsuario(usuario);
                emailListEmail = em.merge(emailListEmail);
                if (oldIdUsuarioOfEmailListEmail != null) {
                    oldIdUsuarioOfEmailListEmail.getEmailList().remove(emailListEmail);
                    oldIdUsuarioOfEmailListEmail = em.merge(oldIdUsuarioOfEmailListEmail);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            TipoUsuario idTipoOld = persistentUsuario.getIdTipo();
            TipoUsuario idTipoNew = usuario.getIdTipo();
            List<Direccion> direccionListOld = persistentUsuario.getDireccionList();
            List<Direccion> direccionListNew = usuario.getDireccionList();
            List<Aplicacion> aplicacionListOld = persistentUsuario.getAplicacionList();
            List<Aplicacion> aplicacionListNew = usuario.getAplicacionList();
            List<Documento> documentoListOld = persistentUsuario.getDocumentoList();
            List<Documento> documentoListNew = usuario.getDocumentoList();
            List<Publicaciones> publicacionesListOld = persistentUsuario.getPublicacionesList();
            List<Publicaciones> publicacionesListNew = usuario.getPublicacionesList();
            List<Telefono> telefonoListOld = persistentUsuario.getTelefonoList();
            List<Telefono> telefonoListNew = usuario.getTelefonoList();
            List<Email> emailListOld = persistentUsuario.getEmailList();
            List<Email> emailListNew = usuario.getEmailList();
            List<String> illegalOrphanMessages = null;
            for (Direccion direccionListOldDireccion : direccionListOld) {
                if (!direccionListNew.contains(direccionListOldDireccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Direccion " + direccionListOldDireccion + " since its usuarioId field is not nullable.");
                }
            }
            for (Aplicacion aplicacionListOldAplicacion : aplicacionListOld) {
                if (!aplicacionListNew.contains(aplicacionListOldAplicacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Aplicacion " + aplicacionListOldAplicacion + " since its usuarioId field is not nullable.");
                }
            }
            for (Documento documentoListOldDocumento : documentoListOld) {
                if (!documentoListNew.contains(documentoListOldDocumento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Documento " + documentoListOldDocumento + " since its idUsuario field is not nullable.");
                }
            }
            for (Publicaciones publicacionesListOldPublicaciones : publicacionesListOld) {
                if (!publicacionesListNew.contains(publicacionesListOldPublicaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Publicaciones " + publicacionesListOldPublicaciones + " since its idUsuario field is not nullable.");
                }
            }
            for (Telefono telefonoListOldTelefono : telefonoListOld) {
                if (!telefonoListNew.contains(telefonoListOldTelefono)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Telefono " + telefonoListOldTelefono + " since its idUsuario field is not nullable.");
                }
            }
            for (Email emailListOldEmail : emailListOld) {
                if (!emailListNew.contains(emailListOldEmail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Email " + emailListOldEmail + " since its idUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getId());
                usuario.setIdTipo(idTipoNew);
            }
            List<Direccion> attachedDireccionListNew = new ArrayList<Direccion>();
            for (Direccion direccionListNewDireccionToAttach : direccionListNew) {
                direccionListNewDireccionToAttach = em.getReference(direccionListNewDireccionToAttach.getClass(), direccionListNewDireccionToAttach.getId());
                attachedDireccionListNew.add(direccionListNewDireccionToAttach);
            }
            direccionListNew = attachedDireccionListNew;
            usuario.setDireccionList(direccionListNew);
            List<Aplicacion> attachedAplicacionListNew = new ArrayList<Aplicacion>();
            for (Aplicacion aplicacionListNewAplicacionToAttach : aplicacionListNew) {
                aplicacionListNewAplicacionToAttach = em.getReference(aplicacionListNewAplicacionToAttach.getClass(), aplicacionListNewAplicacionToAttach.getId());
                attachedAplicacionListNew.add(aplicacionListNewAplicacionToAttach);
            }
            aplicacionListNew = attachedAplicacionListNew;
            usuario.setAplicacionList(aplicacionListNew);
            List<Documento> attachedDocumentoListNew = new ArrayList<Documento>();
            for (Documento documentoListNewDocumentoToAttach : documentoListNew) {
                documentoListNewDocumentoToAttach = em.getReference(documentoListNewDocumentoToAttach.getClass(), documentoListNewDocumentoToAttach.getId());
                attachedDocumentoListNew.add(documentoListNewDocumentoToAttach);
            }
            documentoListNew = attachedDocumentoListNew;
            usuario.setDocumentoList(documentoListNew);
            List<Publicaciones> attachedPublicacionesListNew = new ArrayList<Publicaciones>();
            for (Publicaciones publicacionesListNewPublicacionesToAttach : publicacionesListNew) {
                publicacionesListNewPublicacionesToAttach = em.getReference(publicacionesListNewPublicacionesToAttach.getClass(), publicacionesListNewPublicacionesToAttach.getId());
                attachedPublicacionesListNew.add(publicacionesListNewPublicacionesToAttach);
            }
            publicacionesListNew = attachedPublicacionesListNew;
            usuario.setPublicacionesList(publicacionesListNew);
            List<Telefono> attachedTelefonoListNew = new ArrayList<Telefono>();
            for (Telefono telefonoListNewTelefonoToAttach : telefonoListNew) {
                telefonoListNewTelefonoToAttach = em.getReference(telefonoListNewTelefonoToAttach.getClass(), telefonoListNewTelefonoToAttach.getId());
                attachedTelefonoListNew.add(telefonoListNewTelefonoToAttach);
            }
            telefonoListNew = attachedTelefonoListNew;
            usuario.setTelefonoList(telefonoListNew);
            List<Email> attachedEmailListNew = new ArrayList<Email>();
            for (Email emailListNewEmailToAttach : emailListNew) {
                emailListNewEmailToAttach = em.getReference(emailListNewEmailToAttach.getClass(), emailListNewEmailToAttach.getId());
                attachedEmailListNew.add(emailListNewEmailToAttach);
            }
            emailListNew = attachedEmailListNew;
            usuario.setEmailList(emailListNew);
            usuario = em.merge(usuario);
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getUsuarioList().remove(usuario);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getUsuarioList().add(usuario);
                idTipoNew = em.merge(idTipoNew);
            }
            for (Direccion direccionListNewDireccion : direccionListNew) {
                if (!direccionListOld.contains(direccionListNewDireccion)) {
                    Usuario oldUsuarioIdOfDireccionListNewDireccion = direccionListNewDireccion.getUsuarioId();
                    direccionListNewDireccion.setUsuarioId(usuario);
                    direccionListNewDireccion = em.merge(direccionListNewDireccion);
                    if (oldUsuarioIdOfDireccionListNewDireccion != null && !oldUsuarioIdOfDireccionListNewDireccion.equals(usuario)) {
                        oldUsuarioIdOfDireccionListNewDireccion.getDireccionList().remove(direccionListNewDireccion);
                        oldUsuarioIdOfDireccionListNewDireccion = em.merge(oldUsuarioIdOfDireccionListNewDireccion);
                    }
                }
            }
            for (Aplicacion aplicacionListNewAplicacion : aplicacionListNew) {
                if (!aplicacionListOld.contains(aplicacionListNewAplicacion)) {
                    Usuario oldUsuarioIdOfAplicacionListNewAplicacion = aplicacionListNewAplicacion.getUsuarioId();
                    aplicacionListNewAplicacion.setUsuarioId(usuario);
                    aplicacionListNewAplicacion = em.merge(aplicacionListNewAplicacion);
                    if (oldUsuarioIdOfAplicacionListNewAplicacion != null && !oldUsuarioIdOfAplicacionListNewAplicacion.equals(usuario)) {
                        oldUsuarioIdOfAplicacionListNewAplicacion.getAplicacionList().remove(aplicacionListNewAplicacion);
                        oldUsuarioIdOfAplicacionListNewAplicacion = em.merge(oldUsuarioIdOfAplicacionListNewAplicacion);
                    }
                }
            }
            for (Documento documentoListNewDocumento : documentoListNew) {
                if (!documentoListOld.contains(documentoListNewDocumento)) {
                    Usuario oldIdUsuarioOfDocumentoListNewDocumento = documentoListNewDocumento.getIdUsuario();
                    documentoListNewDocumento.setIdUsuario(usuario);
                    documentoListNewDocumento = em.merge(documentoListNewDocumento);
                    if (oldIdUsuarioOfDocumentoListNewDocumento != null && !oldIdUsuarioOfDocumentoListNewDocumento.equals(usuario)) {
                        oldIdUsuarioOfDocumentoListNewDocumento.getDocumentoList().remove(documentoListNewDocumento);
                        oldIdUsuarioOfDocumentoListNewDocumento = em.merge(oldIdUsuarioOfDocumentoListNewDocumento);
                    }
                }
            }
            for (Publicaciones publicacionesListNewPublicaciones : publicacionesListNew) {
                if (!publicacionesListOld.contains(publicacionesListNewPublicaciones)) {
                    Usuario oldIdUsuarioOfPublicacionesListNewPublicaciones = publicacionesListNewPublicaciones.getIdUsuario();
                    publicacionesListNewPublicaciones.setIdUsuario(usuario);
                    publicacionesListNewPublicaciones = em.merge(publicacionesListNewPublicaciones);
                    if (oldIdUsuarioOfPublicacionesListNewPublicaciones != null && !oldIdUsuarioOfPublicacionesListNewPublicaciones.equals(usuario)) {
                        oldIdUsuarioOfPublicacionesListNewPublicaciones.getPublicacionesList().remove(publicacionesListNewPublicaciones);
                        oldIdUsuarioOfPublicacionesListNewPublicaciones = em.merge(oldIdUsuarioOfPublicacionesListNewPublicaciones);
                    }
                }
            }
            for (Telefono telefonoListNewTelefono : telefonoListNew) {
                if (!telefonoListOld.contains(telefonoListNewTelefono)) {
                    Usuario oldIdUsuarioOfTelefonoListNewTelefono = telefonoListNewTelefono.getIdUsuario();
                    telefonoListNewTelefono.setIdUsuario(usuario);
                    telefonoListNewTelefono = em.merge(telefonoListNewTelefono);
                    if (oldIdUsuarioOfTelefonoListNewTelefono != null && !oldIdUsuarioOfTelefonoListNewTelefono.equals(usuario)) {
                        oldIdUsuarioOfTelefonoListNewTelefono.getTelefonoList().remove(telefonoListNewTelefono);
                        oldIdUsuarioOfTelefonoListNewTelefono = em.merge(oldIdUsuarioOfTelefonoListNewTelefono);
                    }
                }
            }
            for (Email emailListNewEmail : emailListNew) {
                if (!emailListOld.contains(emailListNewEmail)) {
                    Usuario oldIdUsuarioOfEmailListNewEmail = emailListNewEmail.getIdUsuario();
                    emailListNewEmail.setIdUsuario(usuario);
                    emailListNewEmail = em.merge(emailListNewEmail);
                    if (oldIdUsuarioOfEmailListNewEmail != null && !oldIdUsuarioOfEmailListNewEmail.equals(usuario)) {
                        oldIdUsuarioOfEmailListNewEmail.getEmailList().remove(emailListNewEmail);
                        oldIdUsuarioOfEmailListNewEmail = em.merge(oldIdUsuarioOfEmailListNewEmail);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Direccion> direccionListOrphanCheck = usuario.getDireccionList();
            for (Direccion direccionListOrphanCheckDireccion : direccionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Direccion " + direccionListOrphanCheckDireccion + " in its direccionList field has a non-nullable usuarioId field.");
            }
            List<Aplicacion> aplicacionListOrphanCheck = usuario.getAplicacionList();
            for (Aplicacion aplicacionListOrphanCheckAplicacion : aplicacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Aplicacion " + aplicacionListOrphanCheckAplicacion + " in its aplicacionList field has a non-nullable usuarioId field.");
            }
            List<Documento> documentoListOrphanCheck = usuario.getDocumentoList();
            for (Documento documentoListOrphanCheckDocumento : documentoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Documento " + documentoListOrphanCheckDocumento + " in its documentoList field has a non-nullable idUsuario field.");
            }
            List<Publicaciones> publicacionesListOrphanCheck = usuario.getPublicacionesList();
            for (Publicaciones publicacionesListOrphanCheckPublicaciones : publicacionesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Publicaciones " + publicacionesListOrphanCheckPublicaciones + " in its publicacionesList field has a non-nullable idUsuario field.");
            }
            List<Telefono> telefonoListOrphanCheck = usuario.getTelefonoList();
            for (Telefono telefonoListOrphanCheckTelefono : telefonoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Telefono " + telefonoListOrphanCheckTelefono + " in its telefonoList field has a non-nullable idUsuario field.");
            }
            List<Email> emailListOrphanCheck = usuario.getEmailList();
            for (Email emailListOrphanCheckEmail : emailListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Email " + emailListOrphanCheckEmail + " in its emailList field has a non-nullable idUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoUsuario idTipo = usuario.getIdTipo();
            if (idTipo != null) {
                idTipo.getUsuarioList().remove(usuario);
                idTipo = em.merge(idTipo);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    //Función login..
     public Usuario login(String email, String calve) {
        EntityManager em = getEntityManager();
        try {
            return (Usuario) em.createNamedQuery("Usuario.validar", Usuario.class).setParameter("email", email).setParameter("clave", calve).getSingleResult();
        } finally {
            em.close();
        }
    }

    
}
