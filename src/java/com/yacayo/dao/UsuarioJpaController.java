/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yacayo.dao;

import com.yacayo.dao.exceptions.IllegalOrphanException;
import com.yacayo.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.yacayo.entidades.TipoUsuario;
import com.yacayo.entidades.Publicacione;
import java.util.ArrayList;
import java.util.List;
import com.yacayo.entidades.Persona;
import com.yacayo.entidades.Documento;
import com.yacayo.entidades.Empresa;
import com.yacayo.entidades.Usuario;
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
        if (usuario.getPublicacioneList() == null) {
            usuario.setPublicacioneList(new ArrayList<Publicacione>());
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
            TipoUsuario tipoUsuario = usuario.getTipoUsuario();
            if (tipoUsuario != null) {
                tipoUsuario = em.getReference(tipoUsuario.getClass(), tipoUsuario.getId());
                usuario.setTipoUsuario(tipoUsuario);
            }
            List<Publicacione> attachedPublicacioneList = new ArrayList<Publicacione>();
            for (Publicacione publicacioneListPublicacioneToAttach : usuario.getPublicacioneList()) {
                publicacioneListPublicacioneToAttach = em.getReference(publicacioneListPublicacioneToAttach.getClass(), publicacioneListPublicacioneToAttach.getId());
                attachedPublicacioneList.add(publicacioneListPublicacioneToAttach);
            }
            usuario.setPublicacioneList(attachedPublicacioneList);
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
            if (tipoUsuario != null) {
                tipoUsuario.getUsuarioList().add(usuario);
                tipoUsuario = em.merge(tipoUsuario);
            }
            for (Publicacione publicacioneListPublicacione : usuario.getPublicacioneList()) {
                Usuario oldUsuarioIdOfPublicacioneListPublicacione = publicacioneListPublicacione.getUsuarioId();
                publicacioneListPublicacione.setUsuarioId(usuario);
                publicacioneListPublicacione = em.merge(publicacioneListPublicacione);
                if (oldUsuarioIdOfPublicacioneListPublicacione != null) {
                    oldUsuarioIdOfPublicacioneListPublicacione.getPublicacioneList().remove(publicacioneListPublicacione);
                    oldUsuarioIdOfPublicacioneListPublicacione = em.merge(oldUsuarioIdOfPublicacioneListPublicacione);
                }
            }
            for (Persona personaListPersona : usuario.getPersonaList()) {
                Usuario oldUsuarioIdOfPersonaListPersona = personaListPersona.getUsuarioId();
                personaListPersona.setUsuarioId(usuario);
                personaListPersona = em.merge(personaListPersona);
                if (oldUsuarioIdOfPersonaListPersona != null) {
                    oldUsuarioIdOfPersonaListPersona.getPersonaList().remove(personaListPersona);
                    oldUsuarioIdOfPersonaListPersona = em.merge(oldUsuarioIdOfPersonaListPersona);
                }
            }
            for (Documento documentoListDocumento : usuario.getDocumentoList()) {
                Usuario oldUsuarioIdOfDocumentoListDocumento = documentoListDocumento.getUsuarioId();
                documentoListDocumento.setUsuarioId(usuario);
                documentoListDocumento = em.merge(documentoListDocumento);
                if (oldUsuarioIdOfDocumentoListDocumento != null) {
                    oldUsuarioIdOfDocumentoListDocumento.getDocumentoList().remove(documentoListDocumento);
                    oldUsuarioIdOfDocumentoListDocumento = em.merge(oldUsuarioIdOfDocumentoListDocumento);
                }
            }
            for (Empresa empresaListEmpresa : usuario.getEmpresaList()) {
                Usuario oldUsuarioIdOfEmpresaListEmpresa = empresaListEmpresa.getUsuarioId();
                empresaListEmpresa.setUsuarioId(usuario);
                empresaListEmpresa = em.merge(empresaListEmpresa);
                if (oldUsuarioIdOfEmpresaListEmpresa != null) {
                    oldUsuarioIdOfEmpresaListEmpresa.getEmpresaList().remove(empresaListEmpresa);
                    oldUsuarioIdOfEmpresaListEmpresa = em.merge(oldUsuarioIdOfEmpresaListEmpresa);
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
            TipoUsuario tipoUsuarioOld = persistentUsuario.getTipoUsuario();
            TipoUsuario tipoUsuarioNew = usuario.getTipoUsuario();
            List<Publicacione> publicacioneListOld = persistentUsuario.getPublicacioneList();
            List<Publicacione> publicacioneListNew = usuario.getPublicacioneList();
            List<Persona> personaListOld = persistentUsuario.getPersonaList();
            List<Persona> personaListNew = usuario.getPersonaList();
            List<Documento> documentoListOld = persistentUsuario.getDocumentoList();
            List<Documento> documentoListNew = usuario.getDocumentoList();
            List<Empresa> empresaListOld = persistentUsuario.getEmpresaList();
            List<Empresa> empresaListNew = usuario.getEmpresaList();
            List<String> illegalOrphanMessages = null;
            for (Publicacione publicacioneListOldPublicacione : publicacioneListOld) {
                if (!publicacioneListNew.contains(publicacioneListOldPublicacione)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Publicacione " + publicacioneListOldPublicacione + " since its usuarioId field is not nullable.");
                }
            }
            for (Persona personaListOldPersona : personaListOld) {
                if (!personaListNew.contains(personaListOldPersona)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Persona " + personaListOldPersona + " since its usuarioId field is not nullable.");
                }
            }
            for (Documento documentoListOldDocumento : documentoListOld) {
                if (!documentoListNew.contains(documentoListOldDocumento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Documento " + documentoListOldDocumento + " since its usuarioId field is not nullable.");
                }
            }
            for (Empresa empresaListOldEmpresa : empresaListOld) {
                if (!empresaListNew.contains(empresaListOldEmpresa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empresa " + empresaListOldEmpresa + " since its usuarioId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tipoUsuarioNew != null) {
                tipoUsuarioNew = em.getReference(tipoUsuarioNew.getClass(), tipoUsuarioNew.getId());
                usuario.setTipoUsuario(tipoUsuarioNew);
            }
            List<Publicacione> attachedPublicacioneListNew = new ArrayList<Publicacione>();
            for (Publicacione publicacioneListNewPublicacioneToAttach : publicacioneListNew) {
                publicacioneListNewPublicacioneToAttach = em.getReference(publicacioneListNewPublicacioneToAttach.getClass(), publicacioneListNewPublicacioneToAttach.getId());
                attachedPublicacioneListNew.add(publicacioneListNewPublicacioneToAttach);
            }
            publicacioneListNew = attachedPublicacioneListNew;
            usuario.setPublicacioneList(publicacioneListNew);
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
            if (tipoUsuarioOld != null && !tipoUsuarioOld.equals(tipoUsuarioNew)) {
                tipoUsuarioOld.getUsuarioList().remove(usuario);
                tipoUsuarioOld = em.merge(tipoUsuarioOld);
            }
            if (tipoUsuarioNew != null && !tipoUsuarioNew.equals(tipoUsuarioOld)) {
                tipoUsuarioNew.getUsuarioList().add(usuario);
                tipoUsuarioNew = em.merge(tipoUsuarioNew);
            }
            for (Publicacione publicacioneListNewPublicacione : publicacioneListNew) {
                if (!publicacioneListOld.contains(publicacioneListNewPublicacione)) {
                    Usuario oldUsuarioIdOfPublicacioneListNewPublicacione = publicacioneListNewPublicacione.getUsuarioId();
                    publicacioneListNewPublicacione.setUsuarioId(usuario);
                    publicacioneListNewPublicacione = em.merge(publicacioneListNewPublicacione);
                    if (oldUsuarioIdOfPublicacioneListNewPublicacione != null && !oldUsuarioIdOfPublicacioneListNewPublicacione.equals(usuario)) {
                        oldUsuarioIdOfPublicacioneListNewPublicacione.getPublicacioneList().remove(publicacioneListNewPublicacione);
                        oldUsuarioIdOfPublicacioneListNewPublicacione = em.merge(oldUsuarioIdOfPublicacioneListNewPublicacione);
                    }
                }
            }
            for (Persona personaListNewPersona : personaListNew) {
                if (!personaListOld.contains(personaListNewPersona)) {
                    Usuario oldUsuarioIdOfPersonaListNewPersona = personaListNewPersona.getUsuarioId();
                    personaListNewPersona.setUsuarioId(usuario);
                    personaListNewPersona = em.merge(personaListNewPersona);
                    if (oldUsuarioIdOfPersonaListNewPersona != null && !oldUsuarioIdOfPersonaListNewPersona.equals(usuario)) {
                        oldUsuarioIdOfPersonaListNewPersona.getPersonaList().remove(personaListNewPersona);
                        oldUsuarioIdOfPersonaListNewPersona = em.merge(oldUsuarioIdOfPersonaListNewPersona);
                    }
                }
            }
            for (Documento documentoListNewDocumento : documentoListNew) {
                if (!documentoListOld.contains(documentoListNewDocumento)) {
                    Usuario oldUsuarioIdOfDocumentoListNewDocumento = documentoListNewDocumento.getUsuarioId();
                    documentoListNewDocumento.setUsuarioId(usuario);
                    documentoListNewDocumento = em.merge(documentoListNewDocumento);
                    if (oldUsuarioIdOfDocumentoListNewDocumento != null && !oldUsuarioIdOfDocumentoListNewDocumento.equals(usuario)) {
                        oldUsuarioIdOfDocumentoListNewDocumento.getDocumentoList().remove(documentoListNewDocumento);
                        oldUsuarioIdOfDocumentoListNewDocumento = em.merge(oldUsuarioIdOfDocumentoListNewDocumento);
                    }
                }
            }
            for (Empresa empresaListNewEmpresa : empresaListNew) {
                if (!empresaListOld.contains(empresaListNewEmpresa)) {
                    Usuario oldUsuarioIdOfEmpresaListNewEmpresa = empresaListNewEmpresa.getUsuarioId();
                    empresaListNewEmpresa.setUsuarioId(usuario);
                    empresaListNewEmpresa = em.merge(empresaListNewEmpresa);
                    if (oldUsuarioIdOfEmpresaListNewEmpresa != null && !oldUsuarioIdOfEmpresaListNewEmpresa.equals(usuario)) {
                        oldUsuarioIdOfEmpresaListNewEmpresa.getEmpresaList().remove(empresaListNewEmpresa);
                        oldUsuarioIdOfEmpresaListNewEmpresa = em.merge(oldUsuarioIdOfEmpresaListNewEmpresa);
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
            List<Publicacione> publicacioneListOrphanCheck = usuario.getPublicacioneList();
            for (Publicacione publicacioneListOrphanCheckPublicacione : publicacioneListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Publicacione " + publicacioneListOrphanCheckPublicacione + " in its publicacioneList field has a non-nullable usuarioId field.");
            }
            List<Persona> personaListOrphanCheck = usuario.getPersonaList();
            for (Persona personaListOrphanCheckPersona : personaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Persona " + personaListOrphanCheckPersona + " in its personaList field has a non-nullable usuarioId field.");
            }
            List<Documento> documentoListOrphanCheck = usuario.getDocumentoList();
            for (Documento documentoListOrphanCheckDocumento : documentoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Documento " + documentoListOrphanCheckDocumento + " in its documentoList field has a non-nullable usuarioId field.");
            }
            List<Empresa> empresaListOrphanCheck = usuario.getEmpresaList();
            for (Empresa empresaListOrphanCheckEmpresa : empresaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Empresa " + empresaListOrphanCheckEmpresa + " in its empresaList field has a non-nullable usuarioId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoUsuario tipoUsuario = usuario.getTipoUsuario();
            if (tipoUsuario != null) {
                tipoUsuario.getUsuarioList().remove(usuario);
                tipoUsuario = em.merge(tipoUsuario);
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
    
}
