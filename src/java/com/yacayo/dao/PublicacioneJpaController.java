/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yacayo.dao;

import com.yacayo.dao.exceptions.IllegalOrphanException;
import com.yacayo.dao.exceptions.NonexistentEntityException;
import com.yacayo.dao.exceptions.PreexistingEntityException;
import com.yacayo.entidades.Publicacione;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.yacayo.entidades.Usuario;
import com.yacayo.entidades.Rubros;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author david.poncefgkss
 */
public class PublicacioneJpaController implements Serializable {

    public PublicacioneJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Publicacione publicacione) throws PreexistingEntityException, Exception {
        if (publicacione.getRubrosList() == null) {
            publicacione.setRubrosList(new ArrayList<Rubros>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuarioId = publicacione.getUsuarioId();
            if (usuarioId != null) {
                usuarioId = em.getReference(usuarioId.getClass(), usuarioId.getId());
                publicacione.setUsuarioId(usuarioId);
            }
            List<Rubros> attachedRubrosList = new ArrayList<Rubros>();
            for (Rubros rubrosListRubrosToAttach : publicacione.getRubrosList()) {
                rubrosListRubrosToAttach = em.getReference(rubrosListRubrosToAttach.getClass(), rubrosListRubrosToAttach.getId());
                attachedRubrosList.add(rubrosListRubrosToAttach);
            }
            publicacione.setRubrosList(attachedRubrosList);
            em.persist(publicacione);
            if (usuarioId != null) {
                usuarioId.getPublicacioneList().add(publicacione);
                usuarioId = em.merge(usuarioId);
            }
            for (Rubros rubrosListRubros : publicacione.getRubrosList()) {
                Publicacione oldPublicacioneIdOfRubrosListRubros = rubrosListRubros.getPublicacioneId();
                rubrosListRubros.setPublicacioneId(publicacione);
                rubrosListRubros = em.merge(rubrosListRubros);
                if (oldPublicacioneIdOfRubrosListRubros != null) {
                    oldPublicacioneIdOfRubrosListRubros.getRubrosList().remove(rubrosListRubros);
                    oldPublicacioneIdOfRubrosListRubros = em.merge(oldPublicacioneIdOfRubrosListRubros);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPublicacione(publicacione.getId()) != null) {
                throw new PreexistingEntityException("Publicacione " + publicacione + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Publicacione publicacione) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Publicacione persistentPublicacione = em.find(Publicacione.class, publicacione.getId());
            Usuario usuarioIdOld = persistentPublicacione.getUsuarioId();
            Usuario usuarioIdNew = publicacione.getUsuarioId();
            List<Rubros> rubrosListOld = persistentPublicacione.getRubrosList();
            List<Rubros> rubrosListNew = publicacione.getRubrosList();
            List<String> illegalOrphanMessages = null;
            for (Rubros rubrosListOldRubros : rubrosListOld) {
                if (!rubrosListNew.contains(rubrosListOldRubros)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rubros " + rubrosListOldRubros + " since its publicacioneId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioIdNew != null) {
                usuarioIdNew = em.getReference(usuarioIdNew.getClass(), usuarioIdNew.getId());
                publicacione.setUsuarioId(usuarioIdNew);
            }
            List<Rubros> attachedRubrosListNew = new ArrayList<Rubros>();
            for (Rubros rubrosListNewRubrosToAttach : rubrosListNew) {
                rubrosListNewRubrosToAttach = em.getReference(rubrosListNewRubrosToAttach.getClass(), rubrosListNewRubrosToAttach.getId());
                attachedRubrosListNew.add(rubrosListNewRubrosToAttach);
            }
            rubrosListNew = attachedRubrosListNew;
            publicacione.setRubrosList(rubrosListNew);
            publicacione = em.merge(publicacione);
            if (usuarioIdOld != null && !usuarioIdOld.equals(usuarioIdNew)) {
                usuarioIdOld.getPublicacioneList().remove(publicacione);
                usuarioIdOld = em.merge(usuarioIdOld);
            }
            if (usuarioIdNew != null && !usuarioIdNew.equals(usuarioIdOld)) {
                usuarioIdNew.getPublicacioneList().add(publicacione);
                usuarioIdNew = em.merge(usuarioIdNew);
            }
            for (Rubros rubrosListNewRubros : rubrosListNew) {
                if (!rubrosListOld.contains(rubrosListNewRubros)) {
                    Publicacione oldPublicacioneIdOfRubrosListNewRubros = rubrosListNewRubros.getPublicacioneId();
                    rubrosListNewRubros.setPublicacioneId(publicacione);
                    rubrosListNewRubros = em.merge(rubrosListNewRubros);
                    if (oldPublicacioneIdOfRubrosListNewRubros != null && !oldPublicacioneIdOfRubrosListNewRubros.equals(publicacione)) {
                        oldPublicacioneIdOfRubrosListNewRubros.getRubrosList().remove(rubrosListNewRubros);
                        oldPublicacioneIdOfRubrosListNewRubros = em.merge(oldPublicacioneIdOfRubrosListNewRubros);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = publicacione.getId();
                if (findPublicacione(id) == null) {
                    throw new NonexistentEntityException("The publicacione with id " + id + " no longer exists.");
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
            Publicacione publicacione;
            try {
                publicacione = em.getReference(Publicacione.class, id);
                publicacione.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The publicacione with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Rubros> rubrosListOrphanCheck = publicacione.getRubrosList();
            for (Rubros rubrosListOrphanCheckRubros : rubrosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Publicacione (" + publicacione + ") cannot be destroyed since the Rubros " + rubrosListOrphanCheckRubros + " in its rubrosList field has a non-nullable publicacioneId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuarioId = publicacione.getUsuarioId();
            if (usuarioId != null) {
                usuarioId.getPublicacioneList().remove(publicacione);
                usuarioId = em.merge(usuarioId);
            }
            em.remove(publicacione);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Publicacione> findPublicacioneEntities() {
        return findPublicacioneEntities(true, -1, -1);
    }

    public List<Publicacione> findPublicacioneEntities(int maxResults, int firstResult) {
        return findPublicacioneEntities(false, maxResults, firstResult);
    }

    private List<Publicacione> findPublicacioneEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Publicacione.class));
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

    public Publicacione findPublicacione(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Publicacione.class, id);
        } finally {
            em.close();
        }
    }

    public int getPublicacioneCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Publicacione> rt = cq.from(Publicacione.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
