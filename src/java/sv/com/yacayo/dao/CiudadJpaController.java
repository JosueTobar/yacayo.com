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
import sv.com.yacayo.entity.Provincia;
import sv.com.yacayo.entity.Direccion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sv.com.yacayo.dao.exceptions.IllegalOrphanException;
import sv.com.yacayo.dao.exceptions.NonexistentEntityException;
import sv.com.yacayo.entity.Ciudad;

/**
 *
 * @author josue.tobarfgkss
 */
public class CiudadJpaController implements Serializable {

    public CiudadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ciudad ciudad) {
        if (ciudad.getDireccionList() == null) {
            ciudad.setDireccionList(new ArrayList<Direccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Provincia idProvincia = ciudad.getIdProvincia();
            if (idProvincia != null) {
                idProvincia = em.getReference(idProvincia.getClass(), idProvincia.getId());
                ciudad.setIdProvincia(idProvincia);
            }
            List<Direccion> attachedDireccionList = new ArrayList<Direccion>();
            for (Direccion direccionListDireccionToAttach : ciudad.getDireccionList()) {
                direccionListDireccionToAttach = em.getReference(direccionListDireccionToAttach.getClass(), direccionListDireccionToAttach.getId());
                attachedDireccionList.add(direccionListDireccionToAttach);
            }
            ciudad.setDireccionList(attachedDireccionList);
            em.persist(ciudad);
            if (idProvincia != null) {
                idProvincia.getCiudadList().add(ciudad);
                idProvincia = em.merge(idProvincia);
            }
            for (Direccion direccionListDireccion : ciudad.getDireccionList()) {
                Ciudad oldIdCiudadOfDireccionListDireccion = direccionListDireccion.getIdCiudad();
                direccionListDireccion.setIdCiudad(ciudad);
                direccionListDireccion = em.merge(direccionListDireccion);
                if (oldIdCiudadOfDireccionListDireccion != null) {
                    oldIdCiudadOfDireccionListDireccion.getDireccionList().remove(direccionListDireccion);
                    oldIdCiudadOfDireccionListDireccion = em.merge(oldIdCiudadOfDireccionListDireccion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ciudad ciudad) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad persistentCiudad = em.find(Ciudad.class, ciudad.getId());
            Provincia idProvinciaOld = persistentCiudad.getIdProvincia();
            Provincia idProvinciaNew = ciudad.getIdProvincia();
            List<Direccion> direccionListOld = persistentCiudad.getDireccionList();
            List<Direccion> direccionListNew = ciudad.getDireccionList();
            List<String> illegalOrphanMessages = null;
            for (Direccion direccionListOldDireccion : direccionListOld) {
                if (!direccionListNew.contains(direccionListOldDireccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Direccion " + direccionListOldDireccion + " since its idCiudad field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idProvinciaNew != null) {
                idProvinciaNew = em.getReference(idProvinciaNew.getClass(), idProvinciaNew.getId());
                ciudad.setIdProvincia(idProvinciaNew);
            }
            List<Direccion> attachedDireccionListNew = new ArrayList<Direccion>();
            for (Direccion direccionListNewDireccionToAttach : direccionListNew) {
                direccionListNewDireccionToAttach = em.getReference(direccionListNewDireccionToAttach.getClass(), direccionListNewDireccionToAttach.getId());
                attachedDireccionListNew.add(direccionListNewDireccionToAttach);
            }
            direccionListNew = attachedDireccionListNew;
            ciudad.setDireccionList(direccionListNew);
            ciudad = em.merge(ciudad);
            if (idProvinciaOld != null && !idProvinciaOld.equals(idProvinciaNew)) {
                idProvinciaOld.getCiudadList().remove(ciudad);
                idProvinciaOld = em.merge(idProvinciaOld);
            }
            if (idProvinciaNew != null && !idProvinciaNew.equals(idProvinciaOld)) {
                idProvinciaNew.getCiudadList().add(ciudad);
                idProvinciaNew = em.merge(idProvinciaNew);
            }
            for (Direccion direccionListNewDireccion : direccionListNew) {
                if (!direccionListOld.contains(direccionListNewDireccion)) {
                    Ciudad oldIdCiudadOfDireccionListNewDireccion = direccionListNewDireccion.getIdCiudad();
                    direccionListNewDireccion.setIdCiudad(ciudad);
                    direccionListNewDireccion = em.merge(direccionListNewDireccion);
                    if (oldIdCiudadOfDireccionListNewDireccion != null && !oldIdCiudadOfDireccionListNewDireccion.equals(ciudad)) {
                        oldIdCiudadOfDireccionListNewDireccion.getDireccionList().remove(direccionListNewDireccion);
                        oldIdCiudadOfDireccionListNewDireccion = em.merge(oldIdCiudadOfDireccionListNewDireccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ciudad.getId();
                if (findCiudad(id) == null) {
                    throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.");
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
            Ciudad ciudad;
            try {
                ciudad = em.getReference(Ciudad.class, id);
                ciudad.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Direccion> direccionListOrphanCheck = ciudad.getDireccionList();
            for (Direccion direccionListOrphanCheckDireccion : direccionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ciudad (" + ciudad + ") cannot be destroyed since the Direccion " + direccionListOrphanCheckDireccion + " in its direccionList field has a non-nullable idCiudad field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Provincia idProvincia = ciudad.getIdProvincia();
            if (idProvincia != null) {
                idProvincia.getCiudadList().remove(ciudad);
                idProvincia = em.merge(idProvincia);
            }
            em.remove(ciudad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ciudad> findCiudadEntities() {
        return findCiudadEntities(true, -1, -1);
    }

    public List<Ciudad> findCiudadEntities(int maxResults, int firstResult) {
        return findCiudadEntities(false, maxResults, firstResult);
    }

    private List<Ciudad> findCiudadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ciudad.class));
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

    public Ciudad findCiudad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ciudad.class, id);
        } finally {
            em.close();
        }
    }

    public int getCiudadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ciudad> rt = cq.from(Ciudad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
