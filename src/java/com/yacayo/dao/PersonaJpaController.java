/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yacayo.dao;

import com.yacayo.dao.exceptions.NonexistentEntityException;
import com.yacayo.dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.yacayo.entity.Direccion;
import com.yacayo.entity.Persona;
import com.yacayo.entity.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author josue.tobarfgkss
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Direccion direccionidDireccion = persona.getDireccionidDireccion();
            if (direccionidDireccion != null) {
                direccionidDireccion = em.getReference(direccionidDireccion.getClass(), direccionidDireccion.getId());
                persona.setDireccionidDireccion(direccionidDireccion);
            }
            Usuario usuarioId = persona.getUsuarioId();
            if (usuarioId != null) {
                usuarioId = em.getReference(usuarioId.getClass(), usuarioId.getId());
                persona.setUsuarioId(usuarioId);
            }
            em.persist(persona);
            if (direccionidDireccion != null) {
                direccionidDireccion.getPersonaList().add(persona);
                direccionidDireccion = em.merge(direccionidDireccion);
            }
            if (usuarioId != null) {
                usuarioId.getPersonaList().add(persona);
                usuarioId = em.merge(usuarioId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Persona persistentPersona = em.find(Persona.class, persona.getId());
            Direccion direccionidDireccionOld = persistentPersona.getDireccionidDireccion();
            Direccion direccionidDireccionNew = persona.getDireccionidDireccion();
            Usuario usuarioIdOld = persistentPersona.getUsuarioId();
            Usuario usuarioIdNew = persona.getUsuarioId();
            if (direccionidDireccionNew != null) {
                direccionidDireccionNew = em.getReference(direccionidDireccionNew.getClass(), direccionidDireccionNew.getId());
                persona.setDireccionidDireccion(direccionidDireccionNew);
            }
            if (usuarioIdNew != null) {
                usuarioIdNew = em.getReference(usuarioIdNew.getClass(), usuarioIdNew.getId());
                persona.setUsuarioId(usuarioIdNew);
            }
            persona = em.merge(persona);
            if (direccionidDireccionOld != null && !direccionidDireccionOld.equals(direccionidDireccionNew)) {
                direccionidDireccionOld.getPersonaList().remove(persona);
                direccionidDireccionOld = em.merge(direccionidDireccionOld);
            }
            if (direccionidDireccionNew != null && !direccionidDireccionNew.equals(direccionidDireccionOld)) {
                direccionidDireccionNew.getPersonaList().add(persona);
                direccionidDireccionNew = em.merge(direccionidDireccionNew);
            }
            if (usuarioIdOld != null && !usuarioIdOld.equals(usuarioIdNew)) {
                usuarioIdOld.getPersonaList().remove(persona);
                usuarioIdOld = em.merge(usuarioIdOld);
            }
            if (usuarioIdNew != null && !usuarioIdNew.equals(usuarioIdOld)) {
                usuarioIdNew.getPersonaList().add(persona);
                usuarioIdNew = em.merge(usuarioIdNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = persona.getId();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            Direccion direccionidDireccion = persona.getDireccionidDireccion();
            if (direccionidDireccion != null) {
                direccionidDireccion.getPersonaList().remove(persona);
                direccionidDireccion = em.merge(direccionidDireccion);
            }
            Usuario usuarioId = persona.getUsuarioId();
            if (usuarioId != null) {
                usuarioId.getPersonaList().remove(persona);
                usuarioId = em.merge(usuarioId);
            }
            em.remove(persona);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
