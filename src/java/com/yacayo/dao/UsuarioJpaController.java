/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yacayo.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.yacayo.entidades.TipoUsuario;
import com.yacayo.entidades.Publicaciones;
import java.util.ArrayList;
import java.util.List;
import com.yacayo.entidades.Persona;
import com.yacayo.entidades.Documento;
import com.yacayo.entidades.Empresa;
import com.yacayo.entidades.Usuario;
import com.yacayo.dao.exceptions.IllegalOrphanException;
import com.yacayo.dao.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author david.poncefgkss
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
        if (usuario.getPublicacionesList() == null) {
            usuario.setPublicacionesList(new ArrayList<Publicaciones>());
        }
        if (usuario.getPersonaList() == null) {
            usuario.setPersonaList(new ArrayList<Persona>());
        }
        if (usuario.getDocumentoList() == null) {
            usuario.setDocumentoList(new ArrayList<Documento>());
        }
        if (usuario.getEmpresaList() == null) {
            usuario.setEmpresaList(new ArrayList<Empresa>());
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
            List<Publicaciones> attachedPublicacionesList = new ArrayList<Publicaciones>();
            for (Publicaciones publicacionesListPublicacionesToAttach : usuario.getPublicacionesList()) {
                publicacionesListPublicacionesToAttach = em.getReference(publicacionesListPublicacionesToAttach.getClass(), publicacionesListPublicacionesToAttach.getId());
                attachedPublicacionesList.add(publicacionesListPublicacionesToAttach);
            }
            usuario.setPublicacionesList(attachedPublicacionesList);
            List<Persona> attachedPersonaList = new ArrayList<Persona>();
            for (Persona personaListPersonaToAttach : usuario.getPersonaList()) {
                personaListPersonaToAttach = em.getReference(personaListPersonaToAttach.getClass(), personaListPersonaToAttach.getId());
                attachedPersonaList.add(personaListPersonaToAttach);
            }
            usuario.setPersonaList(attachedPersonaList);
            List<Documento> attachedDocumentoList = new ArrayList<Documento>();
            for (Documento documentoListDocumentoToAttach : usuario.getDocumentoList()) {
                documentoListDocumentoToAttach = em.getReference(documentoListDocumentoToAttach.getClass(), documentoListDocumentoToAttach.getId());
                attachedDocumentoList.add(documentoListDocumentoToAttach);
            }
            usuario.setDocumentoList(attachedDocumentoList);
            List<Empresa> attachedEmpresaList = new ArrayList<Empresa>();
            for (Empresa empresaListEmpresaToAttach : usuario.getEmpresaList()) {
                empresaListEmpresaToAttach = em.getReference(empresaListEmpresaToAttach.getClass(), empresaListEmpresaToAttach.getId());
                attachedEmpresaList.add(empresaListEmpresaToAttach);
            }
            usuario.setEmpresaList(attachedEmpresaList);
            em.persist(usuario);
            if (idTipo != null) {
                idTipo.getUsuarioList().add(usuario);
                idTipo = em.merge(idTipo);
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
            for (Persona personaListPersona : usuario.getPersonaList()) {
                Usuario oldIdUsuarioOfPersonaListPersona = personaListPersona.getIdUsuario();
                personaListPersona.setIdUsuario(usuario);
                personaListPersona = em.merge(personaListPersona);
                if (oldIdUsuarioOfPersonaListPersona != null) {
                    oldIdUsuarioOfPersonaListPersona.getPersonaList().remove(personaListPersona);
                    oldIdUsuarioOfPersonaListPersona = em.merge(oldIdUsuarioOfPersonaListPersona);
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
            for (Empresa empresaListEmpresa : usuario.getEmpresaList()) {
                Usuario oldIdUsuarioOfEmpresaListEmpresa = empresaListEmpresa.getIdUsuario();
                empresaListEmpresa.setIdUsuario(usuario);
                empresaListEmpresa = em.merge(empresaListEmpresa);
                if (oldIdUsuarioOfEmpresaListEmpresa != null) {
                    oldIdUsuarioOfEmpresaListEmpresa.getEmpresaList().remove(empresaListEmpresa);
                    oldIdUsuarioOfEmpresaListEmpresa = em.merge(oldIdUsuarioOfEmpresaListEmpresa);
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
            List<Publicaciones> publicacionesListOld = persistentUsuario.getPublicacionesList();
            List<Publicaciones> publicacionesListNew = usuario.getPublicacionesList();
            List<Persona> personaListOld = persistentUsuario.getPersonaList();
            List<Persona> personaListNew = usuario.getPersonaList();
            List<Documento> documentoListOld = persistentUsuario.getDocumentoList();
            List<Documento> documentoListNew = usuario.getDocumentoList();
            List<Empresa> empresaListOld = persistentUsuario.getEmpresaList();
            List<Empresa> empresaListNew = usuario.getEmpresaList();
            List<String> illegalOrphanMessages = null;
            for (Publicaciones publicacionesListOldPublicaciones : publicacionesListOld) {
                if (!publicacionesListNew.contains(publicacionesListOldPublicaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Publicaciones " + publicacionesListOldPublicaciones + " since its idUsuario field is not nullable.");
                }
            }
            for (Persona personaListOldPersona : personaListOld) {
                if (!personaListNew.contains(personaListOldPersona)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Persona " + personaListOldPersona + " since its idUsuario field is not nullable.");
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
            for (Empresa empresaListOldEmpresa : empresaListOld) {
                if (!empresaListNew.contains(empresaListOldEmpresa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empresa " + empresaListOldEmpresa + " since its idUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getId());
                usuario.setIdTipo(idTipoNew);
            }
            List<Publicaciones> attachedPublicacionesListNew = new ArrayList<Publicaciones>();
            for (Publicaciones publicacionesListNewPublicacionesToAttach : publicacionesListNew) {
                publicacionesListNewPublicacionesToAttach = em.getReference(publicacionesListNewPublicacionesToAttach.getClass(), publicacionesListNewPublicacionesToAttach.getId());
                attachedPublicacionesListNew.add(publicacionesListNewPublicacionesToAttach);
            }
            publicacionesListNew = attachedPublicacionesListNew;
            usuario.setPublicacionesList(publicacionesListNew);
            List<Persona> attachedPersonaListNew = new ArrayList<Persona>();
            for (Persona personaListNewPersonaToAttach : personaListNew) {
                personaListNewPersonaToAttach = em.getReference(personaListNewPersonaToAttach.getClass(), personaListNewPersonaToAttach.getId());
                attachedPersonaListNew.add(personaListNewPersonaToAttach);
            }
            personaListNew = attachedPersonaListNew;
            usuario.setPersonaList(personaListNew);
            List<Documento> attachedDocumentoListNew = new ArrayList<Documento>();
            for (Documento documentoListNewDocumentoToAttach : documentoListNew) {
                documentoListNewDocumentoToAttach = em.getReference(documentoListNewDocumentoToAttach.getClass(), documentoListNewDocumentoToAttach.getId());
                attachedDocumentoListNew.add(documentoListNewDocumentoToAttach);
            }
            documentoListNew = attachedDocumentoListNew;
            usuario.setDocumentoList(documentoListNew);
            List<Empresa> attachedEmpresaListNew = new ArrayList<Empresa>();
            for (Empresa empresaListNewEmpresaToAttach : empresaListNew) {
                empresaListNewEmpresaToAttach = em.getReference(empresaListNewEmpresaToAttach.getClass(), empresaListNewEmpresaToAttach.getId());
                attachedEmpresaListNew.add(empresaListNewEmpresaToAttach);
            }
            empresaListNew = attachedEmpresaListNew;
            usuario.setEmpresaList(empresaListNew);
            usuario = em.merge(usuario);
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getUsuarioList().remove(usuario);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getUsuarioList().add(usuario);
                idTipoNew = em.merge(idTipoNew);
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
            for (Persona personaListNewPersona : personaListNew) {
                if (!personaListOld.contains(personaListNewPersona)) {
                    Usuario oldIdUsuarioOfPersonaListNewPersona = personaListNewPersona.getIdUsuario();
                    personaListNewPersona.setIdUsuario(usuario);
                    personaListNewPersona = em.merge(personaListNewPersona);
                    if (oldIdUsuarioOfPersonaListNewPersona != null && !oldIdUsuarioOfPersonaListNewPersona.equals(usuario)) {
                        oldIdUsuarioOfPersonaListNewPersona.getPersonaList().remove(personaListNewPersona);
                        oldIdUsuarioOfPersonaListNewPersona = em.merge(oldIdUsuarioOfPersonaListNewPersona);
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
            for (Empresa empresaListNewEmpresa : empresaListNew) {
                if (!empresaListOld.contains(empresaListNewEmpresa)) {
                    Usuario oldIdUsuarioOfEmpresaListNewEmpresa = empresaListNewEmpresa.getIdUsuario();
                    empresaListNewEmpresa.setIdUsuario(usuario);
                    empresaListNewEmpresa = em.merge(empresaListNewEmpresa);
                    if (oldIdUsuarioOfEmpresaListNewEmpresa != null && !oldIdUsuarioOfEmpresaListNewEmpresa.equals(usuario)) {
                        oldIdUsuarioOfEmpresaListNewEmpresa.getEmpresaList().remove(empresaListNewEmpresa);
                        oldIdUsuarioOfEmpresaListNewEmpresa = em.merge(oldIdUsuarioOfEmpresaListNewEmpresa);
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
            List<Publicaciones> publicacionesListOrphanCheck = usuario.getPublicacionesList();
            for (Publicaciones publicacionesListOrphanCheckPublicaciones : publicacionesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Publicaciones " + publicacionesListOrphanCheckPublicaciones + " in its publicacionesList field has a non-nullable idUsuario field.");
            }
            List<Persona> personaListOrphanCheck = usuario.getPersonaList();
            for (Persona personaListOrphanCheckPersona : personaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Persona " + personaListOrphanCheckPersona + " in its personaList field has a non-nullable idUsuario field.");
            }
            List<Documento> documentoListOrphanCheck = usuario.getDocumentoList();
            for (Documento documentoListOrphanCheckDocumento : documentoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Documento " + documentoListOrphanCheckDocumento + " in its documentoList field has a non-nullable idUsuario field.");
            }
            List<Empresa> empresaListOrphanCheck = usuario.getEmpresaList();
            for (Empresa empresaListOrphanCheckEmpresa : empresaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Empresa " + empresaListOrphanCheckEmpresa + " in its empresaList field has a non-nullable idUsuario field.");
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

    public Usuario ultimo(String email, String calve, Integer tipoUsuario) {
        EntityManager em = getEntityManager();
        try {
            return (Usuario) em.createNamedQuery("Usuario.ultimoid", Usuario.class).setParameter("email", email).setParameter("calve", calve).setParameter("tipoUsuario", tipoUsuario).getSingleResult();
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

}
