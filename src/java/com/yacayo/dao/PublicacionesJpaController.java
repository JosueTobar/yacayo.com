/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yacayo.dao;

import com.yacayo.entidades.Publicaciones;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.yacayo.entidades.Usuario;
import com.yacayo.entidades.Rubros;
import com.yacayo.dao.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author david.poncefgkss
 */
public class PublicacionesJpaController implements Serializable {

    public PublicacionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Publicaciones publicaciones) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idUsuario = publicaciones.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getId());
                publicaciones.setIdUsuario(idUsuario);
            }
            Rubros idRubro = publicaciones.getIdRubro();
            if (idRubro != null) {
                idRubro = em.getReference(idRubro.getClass(), idRubro.getId());
                publicaciones.setIdRubro(idRubro);
            }
            em.persist(publicaciones);
            if (idUsuario != null) {
                idUsuario.getPublicacionesList().add(publicaciones);
                idUsuario = em.merge(idUsuario);
            }
            if (idRubro != null) {
                idRubro.getPublicacionesList().add(publicaciones);
                idRubro = em.merge(idRubro);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Publicaciones publicaciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Publicaciones persistentPublicaciones = em.find(Publicaciones.class, publicaciones.getId());
            Usuario idUsuarioOld = persistentPublicaciones.getIdUsuario();
            Usuario idUsuarioNew = publicaciones.getIdUsuario();
            Rubros idRubroOld = persistentPublicaciones.getIdRubro();
            Rubros idRubroNew = publicaciones.getIdRubro();
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getId());
                publicaciones.setIdUsuario(idUsuarioNew);
            }
            if (idRubroNew != null) {
                idRubroNew = em.getReference(idRubroNew.getClass(), idRubroNew.getId());
                publicaciones.setIdRubro(idRubroNew);
            }
            publicaciones = em.merge(publicaciones);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getPublicacionesList().remove(publicaciones);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getPublicacionesList().add(publicaciones);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            if (idRubroOld != null && !idRubroOld.equals(idRubroNew)) {
                idRubroOld.getPublicacionesList().remove(publicaciones);
                idRubroOld = em.merge(idRubroOld);
            }
            if (idRubroNew != null && !idRubroNew.equals(idRubroOld)) {
                idRubroNew.getPublicacionesList().add(publicaciones);
                idRubroNew = em.merge(idRubroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = publicaciones.getId();
                if (findPublicaciones(id) == null) {
                    throw new NonexistentEntityException("The publicaciones with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Publicaciones publicaciones;
            try {
                publicaciones = em.getReference(Publicaciones.class, id);
                publicaciones.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The publicaciones with id " + id + " no longer exists.", enfe);
            }
            Usuario idUsuario = publicaciones.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getPublicacionesList().remove(publicaciones);
                idUsuario = em.merge(idUsuario);
            }
            Rubros idRubro = publicaciones.getIdRubro();
            if (idRubro != null) {
                idRubro.getPublicacionesList().remove(publicaciones);
                idRubro = em.merge(idRubro);
            }
            em.remove(publicaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Publicaciones> findPublicacionesEntities() {
        return findPublicacionesEntities(true, -1, -1);
    }

    public List<Publicaciones> findPublicacionesEntities(int maxResults, int firstResult) {
        return findPublicacionesEntities(false, maxResults, firstResult);
    }

    private List<Publicaciones> findPublicacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Publicaciones.class));
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

    public Publicaciones findPublicaciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Publicaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getPublicacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Publicaciones> rt = cq.from(Publicaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
