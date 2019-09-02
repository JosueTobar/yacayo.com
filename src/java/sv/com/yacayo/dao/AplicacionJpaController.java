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
import sv.com.yacayo.entity.Publicaciones;
import sv.com.yacayo.entity.Usuario;
import sv.com.yacayo.entity.DetalleAplicacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sv.com.yacayo.dao.exceptions.IllegalOrphanException;
import sv.com.yacayo.dao.exceptions.NonexistentEntityException;
import sv.com.yacayo.dao.exceptions.PreexistingEntityException;
import sv.com.yacayo.entity.Aplicacion;

/**
 *
 * @author josue.tobarfgkss
 */
public class AplicacionJpaController implements Serializable {

    public AplicacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Aplicacion aplicacion) throws PreexistingEntityException, Exception {
        if (aplicacion.getDetalleAplicacionList() == null) {
            aplicacion.setDetalleAplicacionList(new ArrayList<DetalleAplicacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Publicaciones publicacionesId = aplicacion.getPublicacionesId();
            if (publicacionesId != null) {
                publicacionesId = em.getReference(publicacionesId.getClass(), publicacionesId.getId());
                aplicacion.setPublicacionesId(publicacionesId);
            }
            Usuario usuarioId = aplicacion.getUsuarioId();
            if (usuarioId != null) {
                usuarioId = em.getReference(usuarioId.getClass(), usuarioId.getId());
                aplicacion.setUsuarioId(usuarioId);
            }
            List<DetalleAplicacion> attachedDetalleAplicacionList = new ArrayList<DetalleAplicacion>();
            for (DetalleAplicacion detalleAplicacionListDetalleAplicacionToAttach : aplicacion.getDetalleAplicacionList()) {
                detalleAplicacionListDetalleAplicacionToAttach = em.getReference(detalleAplicacionListDetalleAplicacionToAttach.getClass(), detalleAplicacionListDetalleAplicacionToAttach.getId());
                attachedDetalleAplicacionList.add(detalleAplicacionListDetalleAplicacionToAttach);
            }
            aplicacion.setDetalleAplicacionList(attachedDetalleAplicacionList);
            em.persist(aplicacion);
            if (publicacionesId != null) {
                publicacionesId.getAplicacionList().add(aplicacion);
                publicacionesId = em.merge(publicacionesId);
            }
            if (usuarioId != null) {
                usuarioId.getAplicacionList().add(aplicacion);
                usuarioId = em.merge(usuarioId);
            }
            for (DetalleAplicacion detalleAplicacionListDetalleAplicacion : aplicacion.getDetalleAplicacionList()) {
                Aplicacion oldAplicacionIdOfDetalleAplicacionListDetalleAplicacion = detalleAplicacionListDetalleAplicacion.getAplicacionId();
                detalleAplicacionListDetalleAplicacion.setAplicacionId(aplicacion);
                detalleAplicacionListDetalleAplicacion = em.merge(detalleAplicacionListDetalleAplicacion);
                if (oldAplicacionIdOfDetalleAplicacionListDetalleAplicacion != null) {
                    oldAplicacionIdOfDetalleAplicacionListDetalleAplicacion.getDetalleAplicacionList().remove(detalleAplicacionListDetalleAplicacion);
                    oldAplicacionIdOfDetalleAplicacionListDetalleAplicacion = em.merge(oldAplicacionIdOfDetalleAplicacionListDetalleAplicacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAplicacion(aplicacion.getId()) != null) {
                throw new PreexistingEntityException("Aplicacion " + aplicacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Aplicacion aplicacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aplicacion persistentAplicacion = em.find(Aplicacion.class, aplicacion.getId());
            Publicaciones publicacionesIdOld = persistentAplicacion.getPublicacionesId();
            Publicaciones publicacionesIdNew = aplicacion.getPublicacionesId();
            Usuario usuarioIdOld = persistentAplicacion.getUsuarioId();
            Usuario usuarioIdNew = aplicacion.getUsuarioId();
            List<DetalleAplicacion> detalleAplicacionListOld = persistentAplicacion.getDetalleAplicacionList();
            List<DetalleAplicacion> detalleAplicacionListNew = aplicacion.getDetalleAplicacionList();
            List<String> illegalOrphanMessages = null;
            for (DetalleAplicacion detalleAplicacionListOldDetalleAplicacion : detalleAplicacionListOld) {
                if (!detalleAplicacionListNew.contains(detalleAplicacionListOldDetalleAplicacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleAplicacion " + detalleAplicacionListOldDetalleAplicacion + " since its aplicacionId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (publicacionesIdNew != null) {
                publicacionesIdNew = em.getReference(publicacionesIdNew.getClass(), publicacionesIdNew.getId());
                aplicacion.setPublicacionesId(publicacionesIdNew);
            }
            if (usuarioIdNew != null) {
                usuarioIdNew = em.getReference(usuarioIdNew.getClass(), usuarioIdNew.getId());
                aplicacion.setUsuarioId(usuarioIdNew);
            }
            List<DetalleAplicacion> attachedDetalleAplicacionListNew = new ArrayList<DetalleAplicacion>();
            for (DetalleAplicacion detalleAplicacionListNewDetalleAplicacionToAttach : detalleAplicacionListNew) {
                detalleAplicacionListNewDetalleAplicacionToAttach = em.getReference(detalleAplicacionListNewDetalleAplicacionToAttach.getClass(), detalleAplicacionListNewDetalleAplicacionToAttach.getId());
                attachedDetalleAplicacionListNew.add(detalleAplicacionListNewDetalleAplicacionToAttach);
            }
            detalleAplicacionListNew = attachedDetalleAplicacionListNew;
            aplicacion.setDetalleAplicacionList(detalleAplicacionListNew);
            aplicacion = em.merge(aplicacion);
            if (publicacionesIdOld != null && !publicacionesIdOld.equals(publicacionesIdNew)) {
                publicacionesIdOld.getAplicacionList().remove(aplicacion);
                publicacionesIdOld = em.merge(publicacionesIdOld);
            }
            if (publicacionesIdNew != null && !publicacionesIdNew.equals(publicacionesIdOld)) {
                publicacionesIdNew.getAplicacionList().add(aplicacion);
                publicacionesIdNew = em.merge(publicacionesIdNew);
            }
            if (usuarioIdOld != null && !usuarioIdOld.equals(usuarioIdNew)) {
                usuarioIdOld.getAplicacionList().remove(aplicacion);
                usuarioIdOld = em.merge(usuarioIdOld);
            }
            if (usuarioIdNew != null && !usuarioIdNew.equals(usuarioIdOld)) {
                usuarioIdNew.getAplicacionList().add(aplicacion);
                usuarioIdNew = em.merge(usuarioIdNew);
            }
            for (DetalleAplicacion detalleAplicacionListNewDetalleAplicacion : detalleAplicacionListNew) {
                if (!detalleAplicacionListOld.contains(detalleAplicacionListNewDetalleAplicacion)) {
                    Aplicacion oldAplicacionIdOfDetalleAplicacionListNewDetalleAplicacion = detalleAplicacionListNewDetalleAplicacion.getAplicacionId();
                    detalleAplicacionListNewDetalleAplicacion.setAplicacionId(aplicacion);
                    detalleAplicacionListNewDetalleAplicacion = em.merge(detalleAplicacionListNewDetalleAplicacion);
                    if (oldAplicacionIdOfDetalleAplicacionListNewDetalleAplicacion != null && !oldAplicacionIdOfDetalleAplicacionListNewDetalleAplicacion.equals(aplicacion)) {
                        oldAplicacionIdOfDetalleAplicacionListNewDetalleAplicacion.getDetalleAplicacionList().remove(detalleAplicacionListNewDetalleAplicacion);
                        oldAplicacionIdOfDetalleAplicacionListNewDetalleAplicacion = em.merge(oldAplicacionIdOfDetalleAplicacionListNewDetalleAplicacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = aplicacion.getId();
                if (findAplicacion(id) == null) {
                    throw new NonexistentEntityException("The aplicacion with id " + id + " no longer exists.");
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
            Aplicacion aplicacion;
            try {
                aplicacion = em.getReference(Aplicacion.class, id);
                aplicacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aplicacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetalleAplicacion> detalleAplicacionListOrphanCheck = aplicacion.getDetalleAplicacionList();
            for (DetalleAplicacion detalleAplicacionListOrphanCheckDetalleAplicacion : detalleAplicacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Aplicacion (" + aplicacion + ") cannot be destroyed since the DetalleAplicacion " + detalleAplicacionListOrphanCheckDetalleAplicacion + " in its detalleAplicacionList field has a non-nullable aplicacionId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Publicaciones publicacionesId = aplicacion.getPublicacionesId();
            if (publicacionesId != null) {
                publicacionesId.getAplicacionList().remove(aplicacion);
                publicacionesId = em.merge(publicacionesId);
            }
            Usuario usuarioId = aplicacion.getUsuarioId();
            if (usuarioId != null) {
                usuarioId.getAplicacionList().remove(aplicacion);
                usuarioId = em.merge(usuarioId);
            }
            em.remove(aplicacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Aplicacion> findAplicacionEntities() {
        return findAplicacionEntities(true, -1, -1);
    }

    public List<Aplicacion> findAplicacionEntities(int maxResults, int firstResult) {
        return findAplicacionEntities(false, maxResults, firstResult);
    }

    private List<Aplicacion> findAplicacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Aplicacion.class));
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

    public Aplicacion findAplicacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Aplicacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getAplicacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Aplicacion> rt = cq.from(Aplicacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
