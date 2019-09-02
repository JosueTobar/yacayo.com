/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.yacayo.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import sv.com.yacayo.dao.exceptions.NonexistentEntityException;
import sv.com.yacayo.entity.Email;
import sv.com.yacayo.entity.Usuario;

/**
 *
 * @author josue.tobarfgkss
 */
public class EmailJpaController implements Serializable {

    public EmailJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Email email) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idUsuario = email.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getId());
                email.setIdUsuario(idUsuario);
            }
            em.persist(email);
            if (idUsuario != null) {
                idUsuario.getEmailList().add(email);
                idUsuario = em.merge(idUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Email email) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Email persistentEmail = em.find(Email.class, email.getId());
            Usuario idUsuarioOld = persistentEmail.getIdUsuario();
            Usuario idUsuarioNew = email.getIdUsuario();
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getId());
                email.setIdUsuario(idUsuarioNew);
            }
            email = em.merge(email);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getEmailList().remove(email);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getEmailList().add(email);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = email.getId();
                if (findEmail(id) == null) {
                    throw new NonexistentEntityException("The email with id " + id + " no longer exists.");
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
            Email email;
            try {
                email = em.getReference(Email.class, id);
                email.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The email with id " + id + " no longer exists.", enfe);
            }
            Usuario idUsuario = email.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getEmailList().remove(email);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(email);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Email> findEmailEntities() {
        return findEmailEntities(true, -1, -1);
    }

    public List<Email> findEmailEntities(int maxResults, int firstResult) {
        return findEmailEntities(false, maxResults, firstResult);
    }

    private List<Email> findEmailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Email.class));
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

    public Email findEmail(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Email.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Email> rt = cq.from(Email.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
