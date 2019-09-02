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
import sv.com.yacayo.entity.Aplicacion;
import sv.com.yacayo.entity.DetalleAplicacion;
import sv.com.yacayo.entity.Documento;

/**
 *
 * @author josue.tobarfgkss
 */
public class DetalleAplicacionJpaController implements Serializable {

    public DetalleAplicacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleAplicacion detalleAplicacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aplicacion aplicacionId = detalleAplicacion.getAplicacionId();
            if (aplicacionId != null) {
                aplicacionId = em.getReference(aplicacionId.getClass(), aplicacionId.getId());
                detalleAplicacion.setAplicacionId(aplicacionId);
            }
            Documento documentoId = detalleAplicacion.getDocumentoId();
            if (documentoId != null) {
                documentoId = em.getReference(documentoId.getClass(), documentoId.getId());
                detalleAplicacion.setDocumentoId(documentoId);
            }
            em.persist(detalleAplicacion);
            if (aplicacionId != null) {
                aplicacionId.getDetalleAplicacionList().add(detalleAplicacion);
                aplicacionId = em.merge(aplicacionId);
            }
            if (documentoId != null) {
                documentoId.getDetalleAplicacionList().add(detalleAplicacion);
                documentoId = em.merge(documentoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleAplicacion detalleAplicacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleAplicacion persistentDetalleAplicacion = em.find(DetalleAplicacion.class, detalleAplicacion.getId());
            Aplicacion aplicacionIdOld = persistentDetalleAplicacion.getAplicacionId();
            Aplicacion aplicacionIdNew = detalleAplicacion.getAplicacionId();
            Documento documentoIdOld = persistentDetalleAplicacion.getDocumentoId();
            Documento documentoIdNew = detalleAplicacion.getDocumentoId();
            if (aplicacionIdNew != null) {
                aplicacionIdNew = em.getReference(aplicacionIdNew.getClass(), aplicacionIdNew.getId());
                detalleAplicacion.setAplicacionId(aplicacionIdNew);
            }
            if (documentoIdNew != null) {
                documentoIdNew = em.getReference(documentoIdNew.getClass(), documentoIdNew.getId());
                detalleAplicacion.setDocumentoId(documentoIdNew);
            }
            detalleAplicacion = em.merge(detalleAplicacion);
            if (aplicacionIdOld != null && !aplicacionIdOld.equals(aplicacionIdNew)) {
                aplicacionIdOld.getDetalleAplicacionList().remove(detalleAplicacion);
                aplicacionIdOld = em.merge(aplicacionIdOld);
            }
            if (aplicacionIdNew != null && !aplicacionIdNew.equals(aplicacionIdOld)) {
                aplicacionIdNew.getDetalleAplicacionList().add(detalleAplicacion);
                aplicacionIdNew = em.merge(aplicacionIdNew);
            }
            if (documentoIdOld != null && !documentoIdOld.equals(documentoIdNew)) {
                documentoIdOld.getDetalleAplicacionList().remove(detalleAplicacion);
                documentoIdOld = em.merge(documentoIdOld);
            }
            if (documentoIdNew != null && !documentoIdNew.equals(documentoIdOld)) {
                documentoIdNew.getDetalleAplicacionList().add(detalleAplicacion);
                documentoIdNew = em.merge(documentoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleAplicacion.getId();
                if (findDetalleAplicacion(id) == null) {
                    throw new NonexistentEntityException("The detalleAplicacion with id " + id + " no longer exists.");
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
            DetalleAplicacion detalleAplicacion;
            try {
                detalleAplicacion = em.getReference(DetalleAplicacion.class, id);
                detalleAplicacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleAplicacion with id " + id + " no longer exists.", enfe);
            }
            Aplicacion aplicacionId = detalleAplicacion.getAplicacionId();
            if (aplicacionId != null) {
                aplicacionId.getDetalleAplicacionList().remove(detalleAplicacion);
                aplicacionId = em.merge(aplicacionId);
            }
            Documento documentoId = detalleAplicacion.getDocumentoId();
            if (documentoId != null) {
                documentoId.getDetalleAplicacionList().remove(detalleAplicacion);
                documentoId = em.merge(documentoId);
            }
            em.remove(detalleAplicacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleAplicacion> findDetalleAplicacionEntities() {
        return findDetalleAplicacionEntities(true, -1, -1);
    }

    public List<DetalleAplicacion> findDetalleAplicacionEntities(int maxResults, int firstResult) {
        return findDetalleAplicacionEntities(false, maxResults, firstResult);
    }

    private List<DetalleAplicacion> findDetalleAplicacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleAplicacion.class));
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

    public DetalleAplicacion findDetalleAplicacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleAplicacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleAplicacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleAplicacion> rt = cq.from(DetalleAplicacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
