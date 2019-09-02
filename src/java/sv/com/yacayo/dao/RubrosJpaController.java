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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sv.com.yacayo.dao.exceptions.IllegalOrphanException;
import sv.com.yacayo.dao.exceptions.NonexistentEntityException;
import sv.com.yacayo.entity.Rubros;

/**
 *
 * @author josue.tobarfgkss
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
        if (rubros.getPublicacionesList() == null) {
            rubros.setPublicacionesList(new ArrayList<Publicaciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Publicaciones> attachedPublicacionesList = new ArrayList<Publicaciones>();
            for (Publicaciones publicacionesListPublicacionesToAttach : rubros.getPublicacionesList()) {
                publicacionesListPublicacionesToAttach = em.getReference(publicacionesListPublicacionesToAttach.getClass(), publicacionesListPublicacionesToAttach.getId());
                attachedPublicacionesList.add(publicacionesListPublicacionesToAttach);
            }
            rubros.setPublicacionesList(attachedPublicacionesList);
            em.persist(rubros);
            for (Publicaciones publicacionesListPublicaciones : rubros.getPublicacionesList()) {
                Rubros oldIdRubroOfPublicacionesListPublicaciones = publicacionesListPublicaciones.getIdRubro();
                publicacionesListPublicaciones.setIdRubro(rubros);
                publicacionesListPublicaciones = em.merge(publicacionesListPublicaciones);
                if (oldIdRubroOfPublicacionesListPublicaciones != null) {
                    oldIdRubroOfPublicacionesListPublicaciones.getPublicacionesList().remove(publicacionesListPublicaciones);
                    oldIdRubroOfPublicacionesListPublicaciones = em.merge(oldIdRubroOfPublicacionesListPublicaciones);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rubros rubros) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rubros persistentRubros = em.find(Rubros.class, rubros.getId());
            List<Publicaciones> publicacionesListOld = persistentRubros.getPublicacionesList();
            List<Publicaciones> publicacionesListNew = rubros.getPublicacionesList();
            List<String> illegalOrphanMessages = null;
            for (Publicaciones publicacionesListOldPublicaciones : publicacionesListOld) {
                if (!publicacionesListNew.contains(publicacionesListOldPublicaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Publicaciones " + publicacionesListOldPublicaciones + " since its idRubro field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Publicaciones> attachedPublicacionesListNew = new ArrayList<Publicaciones>();
            for (Publicaciones publicacionesListNewPublicacionesToAttach : publicacionesListNew) {
                publicacionesListNewPublicacionesToAttach = em.getReference(publicacionesListNewPublicacionesToAttach.getClass(), publicacionesListNewPublicacionesToAttach.getId());
                attachedPublicacionesListNew.add(publicacionesListNewPublicacionesToAttach);
            }
            publicacionesListNew = attachedPublicacionesListNew;
            rubros.setPublicacionesList(publicacionesListNew);
            rubros = em.merge(rubros);
            for (Publicaciones publicacionesListNewPublicaciones : publicacionesListNew) {
                if (!publicacionesListOld.contains(publicacionesListNewPublicaciones)) {
                    Rubros oldIdRubroOfPublicacionesListNewPublicaciones = publicacionesListNewPublicaciones.getIdRubro();
                    publicacionesListNewPublicaciones.setIdRubro(rubros);
                    publicacionesListNewPublicaciones = em.merge(publicacionesListNewPublicaciones);
                    if (oldIdRubroOfPublicacionesListNewPublicaciones != null && !oldIdRubroOfPublicacionesListNewPublicaciones.equals(rubros)) {
                        oldIdRubroOfPublicacionesListNewPublicaciones.getPublicacionesList().remove(publicacionesListNewPublicaciones);
                        oldIdRubroOfPublicacionesListNewPublicaciones = em.merge(oldIdRubroOfPublicacionesListNewPublicaciones);
                    }
                }
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            List<Publicaciones> publicacionesListOrphanCheck = rubros.getPublicacionesList();
            for (Publicaciones publicacionesListOrphanCheckPublicaciones : publicacionesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rubros (" + rubros + ") cannot be destroyed since the Publicaciones " + publicacionesListOrphanCheckPublicaciones + " in its publicacionesList field has a non-nullable idRubro field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
