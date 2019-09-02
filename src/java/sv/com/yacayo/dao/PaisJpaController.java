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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sv.com.yacayo.dao.exceptions.IllegalOrphanException;
import sv.com.yacayo.dao.exceptions.NonexistentEntityException;
import sv.com.yacayo.entity.Pais;

/**
 *
 * @author josue.tobarfgkss
 */
public class PaisJpaController implements Serializable {

    public PaisJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pais pais) {
        if (pais.getProvinciaList() == null) {
            pais.setProvinciaList(new ArrayList<Provincia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Provincia> attachedProvinciaList = new ArrayList<Provincia>();
            for (Provincia provinciaListProvinciaToAttach : pais.getProvinciaList()) {
                provinciaListProvinciaToAttach = em.getReference(provinciaListProvinciaToAttach.getClass(), provinciaListProvinciaToAttach.getId());
                attachedProvinciaList.add(provinciaListProvinciaToAttach);
            }
            pais.setProvinciaList(attachedProvinciaList);
            em.persist(pais);
            for (Provincia provinciaListProvincia : pais.getProvinciaList()) {
                Pais oldIdPaisOfProvinciaListProvincia = provinciaListProvincia.getIdPais();
                provinciaListProvincia.setIdPais(pais);
                provinciaListProvincia = em.merge(provinciaListProvincia);
                if (oldIdPaisOfProvinciaListProvincia != null) {
                    oldIdPaisOfProvinciaListProvincia.getProvinciaList().remove(provinciaListProvincia);
                    oldIdPaisOfProvinciaListProvincia = em.merge(oldIdPaisOfProvinciaListProvincia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pais pais) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais persistentPais = em.find(Pais.class, pais.getId());
            List<Provincia> provinciaListOld = persistentPais.getProvinciaList();
            List<Provincia> provinciaListNew = pais.getProvinciaList();
            List<String> illegalOrphanMessages = null;
            for (Provincia provinciaListOldProvincia : provinciaListOld) {
                if (!provinciaListNew.contains(provinciaListOldProvincia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Provincia " + provinciaListOldProvincia + " since its idPais field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Provincia> attachedProvinciaListNew = new ArrayList<Provincia>();
            for (Provincia provinciaListNewProvinciaToAttach : provinciaListNew) {
                provinciaListNewProvinciaToAttach = em.getReference(provinciaListNewProvinciaToAttach.getClass(), provinciaListNewProvinciaToAttach.getId());
                attachedProvinciaListNew.add(provinciaListNewProvinciaToAttach);
            }
            provinciaListNew = attachedProvinciaListNew;
            pais.setProvinciaList(provinciaListNew);
            pais = em.merge(pais);
            for (Provincia provinciaListNewProvincia : provinciaListNew) {
                if (!provinciaListOld.contains(provinciaListNewProvincia)) {
                    Pais oldIdPaisOfProvinciaListNewProvincia = provinciaListNewProvincia.getIdPais();
                    provinciaListNewProvincia.setIdPais(pais);
                    provinciaListNewProvincia = em.merge(provinciaListNewProvincia);
                    if (oldIdPaisOfProvinciaListNewProvincia != null && !oldIdPaisOfProvinciaListNewProvincia.equals(pais)) {
                        oldIdPaisOfProvinciaListNewProvincia.getProvinciaList().remove(provinciaListNewProvincia);
                        oldIdPaisOfProvinciaListNewProvincia = em.merge(oldIdPaisOfProvinciaListNewProvincia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pais.getId();
                if (findPais(id) == null) {
                    throw new NonexistentEntityException("The pais with id " + id + " no longer exists.");
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
            Pais pais;
            try {
                pais = em.getReference(Pais.class, id);
                pais.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pais with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Provincia> provinciaListOrphanCheck = pais.getProvinciaList();
            for (Provincia provinciaListOrphanCheckProvincia : provinciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pais (" + pais + ") cannot be destroyed since the Provincia " + provinciaListOrphanCheckProvincia + " in its provinciaList field has a non-nullable idPais field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pais);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pais> findPaisEntities() {
        return findPaisEntities(true, -1, -1);
    }

    public List<Pais> findPaisEntities(int maxResults, int firstResult) {
        return findPaisEntities(false, maxResults, firstResult);
    }

    private List<Pais> findPaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pais.class));
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

    public Pais findPais(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pais.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pais> rt = cq.from(Pais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
