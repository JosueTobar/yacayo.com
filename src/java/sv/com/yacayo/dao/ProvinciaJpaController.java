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
import sv.com.yacayo.entity.Pais;
import sv.com.yacayo.entity.Ciudad;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sv.com.yacayo.dao.exceptions.IllegalOrphanException;
import sv.com.yacayo.dao.exceptions.NonexistentEntityException;
import sv.com.yacayo.entity.Provincia;

/**
 *
 * @author josue.tobarfgkss
 */
public class ProvinciaJpaController implements Serializable {

    public ProvinciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Provincia provincia) {
        if (provincia.getCiudadList() == null) {
            provincia.setCiudadList(new ArrayList<Ciudad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais idPais = provincia.getIdPais();
            if (idPais != null) {
                idPais = em.getReference(idPais.getClass(), idPais.getId());
                provincia.setIdPais(idPais);
            }
            List<Ciudad> attachedCiudadList = new ArrayList<Ciudad>();
            for (Ciudad ciudadListCiudadToAttach : provincia.getCiudadList()) {
                ciudadListCiudadToAttach = em.getReference(ciudadListCiudadToAttach.getClass(), ciudadListCiudadToAttach.getId());
                attachedCiudadList.add(ciudadListCiudadToAttach);
            }
            provincia.setCiudadList(attachedCiudadList);
            em.persist(provincia);
            if (idPais != null) {
                idPais.getProvinciaList().add(provincia);
                idPais = em.merge(idPais);
            }
            for (Ciudad ciudadListCiudad : provincia.getCiudadList()) {
                Provincia oldIdProvinciaOfCiudadListCiudad = ciudadListCiudad.getIdProvincia();
                ciudadListCiudad.setIdProvincia(provincia);
                ciudadListCiudad = em.merge(ciudadListCiudad);
                if (oldIdProvinciaOfCiudadListCiudad != null) {
                    oldIdProvinciaOfCiudadListCiudad.getCiudadList().remove(ciudadListCiudad);
                    oldIdProvinciaOfCiudadListCiudad = em.merge(oldIdProvinciaOfCiudadListCiudad);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Provincia provincia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Provincia persistentProvincia = em.find(Provincia.class, provincia.getId());
            Pais idPaisOld = persistentProvincia.getIdPais();
            Pais idPaisNew = provincia.getIdPais();
            List<Ciudad> ciudadListOld = persistentProvincia.getCiudadList();
            List<Ciudad> ciudadListNew = provincia.getCiudadList();
            List<String> illegalOrphanMessages = null;
            for (Ciudad ciudadListOldCiudad : ciudadListOld) {
                if (!ciudadListNew.contains(ciudadListOldCiudad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ciudad " + ciudadListOldCiudad + " since its idProvincia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idPaisNew != null) {
                idPaisNew = em.getReference(idPaisNew.getClass(), idPaisNew.getId());
                provincia.setIdPais(idPaisNew);
            }
            List<Ciudad> attachedCiudadListNew = new ArrayList<Ciudad>();
            for (Ciudad ciudadListNewCiudadToAttach : ciudadListNew) {
                ciudadListNewCiudadToAttach = em.getReference(ciudadListNewCiudadToAttach.getClass(), ciudadListNewCiudadToAttach.getId());
                attachedCiudadListNew.add(ciudadListNewCiudadToAttach);
            }
            ciudadListNew = attachedCiudadListNew;
            provincia.setCiudadList(ciudadListNew);
            provincia = em.merge(provincia);
            if (idPaisOld != null && !idPaisOld.equals(idPaisNew)) {
                idPaisOld.getProvinciaList().remove(provincia);
                idPaisOld = em.merge(idPaisOld);
            }
            if (idPaisNew != null && !idPaisNew.equals(idPaisOld)) {
                idPaisNew.getProvinciaList().add(provincia);
                idPaisNew = em.merge(idPaisNew);
            }
            for (Ciudad ciudadListNewCiudad : ciudadListNew) {
                if (!ciudadListOld.contains(ciudadListNewCiudad)) {
                    Provincia oldIdProvinciaOfCiudadListNewCiudad = ciudadListNewCiudad.getIdProvincia();
                    ciudadListNewCiudad.setIdProvincia(provincia);
                    ciudadListNewCiudad = em.merge(ciudadListNewCiudad);
                    if (oldIdProvinciaOfCiudadListNewCiudad != null && !oldIdProvinciaOfCiudadListNewCiudad.equals(provincia)) {
                        oldIdProvinciaOfCiudadListNewCiudad.getCiudadList().remove(ciudadListNewCiudad);
                        oldIdProvinciaOfCiudadListNewCiudad = em.merge(oldIdProvinciaOfCiudadListNewCiudad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = provincia.getId();
                if (findProvincia(id) == null) {
                    throw new NonexistentEntityException("The provincia with id " + id + " no longer exists.");
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
            Provincia provincia;
            try {
                provincia = em.getReference(Provincia.class, id);
                provincia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The provincia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ciudad> ciudadListOrphanCheck = provincia.getCiudadList();
            for (Ciudad ciudadListOrphanCheckCiudad : ciudadListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Provincia (" + provincia + ") cannot be destroyed since the Ciudad " + ciudadListOrphanCheckCiudad + " in its ciudadList field has a non-nullable idProvincia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pais idPais = provincia.getIdPais();
            if (idPais != null) {
                idPais.getProvinciaList().remove(provincia);
                idPais = em.merge(idPais);
            }
            em.remove(provincia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Provincia> findProvinciaEntities() {
        return findProvinciaEntities(true, -1, -1);
    }

    public List<Provincia> findProvinciaEntities(int maxResults, int firstResult) {
        return findProvinciaEntities(false, maxResults, firstResult);
    }

    private List<Provincia> findProvinciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Provincia.class));
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

    public Provincia findProvincia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Provincia.class, id);
        } finally {
            em.close();
        }
    }

    public int getProvinciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Provincia> rt = cq.from(Provincia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
