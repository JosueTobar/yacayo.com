/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yacayo.dao;

import com.yacayo.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.yacayo.entidades.Publicacione;
import com.yacayo.entidades.Rubros;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author david.poncefgkss
 */
public class RubrosJpaController implements Serializable {

    public RubrosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rubros rubros) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Publicacione publicacioneId = rubros.getPublicacioneId();
            if (publicacioneId != null) {
                publicacioneId = em.getReference(publicacioneId.getClass(), publicacioneId.getId());
                rubros.setPublicacioneId(publicacioneId);
            }
            em.persist(rubros);
            if (publicacioneId != null) {
                publicacioneId.getRubrosList().add(rubros);
                publicacioneId = em.merge(publicacioneId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rubros rubros) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rubros persistentRubros = em.find(Rubros.class, rubros.getId());
            Publicacione publicacioneIdOld = persistentRubros.getPublicacioneId();
            Publicacione publicacioneIdNew = rubros.getPublicacioneId();
            if (publicacioneIdNew != null) {
                publicacioneIdNew = em.getReference(publicacioneIdNew.getClass(), publicacioneIdNew.getId());
                rubros.setPublicacioneId(publicacioneIdNew);
            }
            rubros = em.merge(rubros);
            if (publicacioneIdOld != null && !publicacioneIdOld.equals(publicacioneIdNew)) {
                publicacioneIdOld.getRubrosList().remove(rubros);
                publicacioneIdOld = em.merge(publicacioneIdOld);
            }
            if (publicacioneIdNew != null && !publicacioneIdNew.equals(publicacioneIdOld)) {
                publicacioneIdNew.getRubrosList().add(rubros);
                publicacioneIdNew = em.merge(publicacioneIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rubros.getId();
                if (findRubros(id) == null) {
                    throw new NonexistentEntityException("The rubros with id " + id + " no longer exists.");
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
            Rubros rubros;
            try {
                rubros = em.getReference(Rubros.class, id);
                rubros.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rubros with id " + id + " no longer exists.", enfe);
            }
            Publicacione publicacioneId = rubros.getPublicacioneId();
            if (publicacioneId != null) {
                publicacioneId.getRubrosList().remove(rubros);
                publicacioneId = em.merge(publicacioneId);
            }
            em.remove(rubros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rubros> findRubrosEntities() {
        return findRubrosEntities(true, -1, -1);
    }

    public List<Rubros> findRubrosEntities(int maxResults, int firstResult) {
        return findRubrosEntities(false, maxResults, firstResult);
    }

    private List<Rubros> findRubrosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rubros.class));
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

    public Rubros findRubros(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rubros.class, id);
        } finally {
            em.close();
        }
    }

    public int getRubrosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rubros> rt = cq.from(Rubros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
