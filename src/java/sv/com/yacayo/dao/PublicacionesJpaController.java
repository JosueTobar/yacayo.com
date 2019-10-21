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
import sv.com.yacayo.entity.Usuario;
import sv.com.yacayo.entity.Rubros;
import sv.com.yacayo.entity.Aplicacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sv.com.yacayo.dao.exceptions.IllegalOrphanException;
import sv.com.yacayo.dao.exceptions.NonexistentEntityException;
import sv.com.yacayo.entity.Publicaciones;

/**
 *
 * @author josue.tobarfgkss
 */
public class PublicacionesJpaController implements Serializable {

    public PublicacionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Publicaciones publicaciones) {
        if (publicaciones.getAplicacionList() == null) {
            publicaciones.setAplicacionList(new ArrayList<Aplicacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idUsuario = publicaciones.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getId());
                publicaciones.setIdUsuario(idUsuario);
            }
            Rubros idRubro = publicaciones.getIdRubro();
            if (idRubro != null) {
                idRubro = em.getReference(idRubro.getClass(), idRubro.getId());
                publicaciones.setIdRubro(idRubro);
            }
            List<Aplicacion> attachedAplicacionList = new ArrayList<Aplicacion>();
            for (Aplicacion aplicacionListAplicacionToAttach : publicaciones.getAplicacionList()) {
                aplicacionListAplicacionToAttach = em.getReference(aplicacionListAplicacionToAttach.getClass(), aplicacionListAplicacionToAttach.getId());
                attachedAplicacionList.add(aplicacionListAplicacionToAttach);
            }
            publicaciones.setAplicacionList(attachedAplicacionList);
            em.persist(publicaciones);
            if (idUsuario != null) {
                idUsuario.getPublicacionesList().add(publicaciones);
                idUsuario = em.merge(idUsuario);
            }
            if (idRubro != null) {
                idRubro.getPublicacionesList().add(publicaciones);
                idRubro = em.merge(idRubro);
            }
            for (Aplicacion aplicacionListAplicacion : publicaciones.getAplicacionList()) {
                Publicaciones oldPublicacionesIdOfAplicacionListAplicacion = aplicacionListAplicacion.getPublicacionesId();
                aplicacionListAplicacion.setPublicacionesId(publicaciones);
                aplicacionListAplicacion = em.merge(aplicacionListAplicacion);
                if (oldPublicacionesIdOfAplicacionListAplicacion != null) {
                    oldPublicacionesIdOfAplicacionListAplicacion.getAplicacionList().remove(aplicacionListAplicacion);
                    oldPublicacionesIdOfAplicacionListAplicacion = em.merge(oldPublicacionesIdOfAplicacionListAplicacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Publicaciones publicaciones) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Publicaciones persistentPublicaciones = em.find(Publicaciones.class, publicaciones.getId());
            Usuario idUsuarioOld = persistentPublicaciones.getIdUsuario();
            Usuario idUsuarioNew = publicaciones.getIdUsuario();
            Rubros idRubroOld = persistentPublicaciones.getIdRubro();
            Rubros idRubroNew = publicaciones.getIdRubro();
            List<Aplicacion> aplicacionListOld = persistentPublicaciones.getAplicacionList();
            List<Aplicacion> aplicacionListNew = publicaciones.getAplicacionList();
            List<String> illegalOrphanMessages = null;
            for (Aplicacion aplicacionListOldAplicacion : aplicacionListOld) {
                if (!aplicacionListNew.contains(aplicacionListOldAplicacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Aplicacion " + aplicacionListOldAplicacion + " since its publicacionesId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getId());
                publicaciones.setIdUsuario(idUsuarioNew);
            }
            if (idRubroNew != null) {
                idRubroNew = em.getReference(idRubroNew.getClass(), idRubroNew.getId());
                publicaciones.setIdRubro(idRubroNew);
            }
            List<Aplicacion> attachedAplicacionListNew = new ArrayList<Aplicacion>();
            for (Aplicacion aplicacionListNewAplicacionToAttach : aplicacionListNew) {
                aplicacionListNewAplicacionToAttach = em.getReference(aplicacionListNewAplicacionToAttach.getClass(), aplicacionListNewAplicacionToAttach.getId());
                attachedAplicacionListNew.add(aplicacionListNewAplicacionToAttach);
            }
            aplicacionListNew = attachedAplicacionListNew;
            publicaciones.setAplicacionList(aplicacionListNew);
            publicaciones = em.merge(publicaciones);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getPublicacionesList().remove(publicaciones);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getPublicacionesList().add(publicaciones);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            if (idRubroOld != null && !idRubroOld.equals(idRubroNew)) {
                idRubroOld.getPublicacionesList().remove(publicaciones);
                idRubroOld = em.merge(idRubroOld);
            }
            if (idRubroNew != null && !idRubroNew.equals(idRubroOld)) {
                idRubroNew.getPublicacionesList().add(publicaciones);
                idRubroNew = em.merge(idRubroNew);
            }
            for (Aplicacion aplicacionListNewAplicacion : aplicacionListNew) {
                if (!aplicacionListOld.contains(aplicacionListNewAplicacion)) {
                    Publicaciones oldPublicacionesIdOfAplicacionListNewAplicacion = aplicacionListNewAplicacion.getPublicacionesId();
                    aplicacionListNewAplicacion.setPublicacionesId(publicaciones);
                    aplicacionListNewAplicacion = em.merge(aplicacionListNewAplicacion);
                    if (oldPublicacionesIdOfAplicacionListNewAplicacion != null && !oldPublicacionesIdOfAplicacionListNewAplicacion.equals(publicaciones)) {
                        oldPublicacionesIdOfAplicacionListNewAplicacion.getAplicacionList().remove(aplicacionListNewAplicacion);
                        oldPublicacionesIdOfAplicacionListNewAplicacion = em.merge(oldPublicacionesIdOfAplicacionListNewAplicacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = publicaciones.getId();
                if (findPublicaciones(id) == null) {
                    throw new NonexistentEntityException("The publicaciones with id " + id + " no longer exists.");
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
            Publicaciones publicaciones;
            try {
                publicaciones = em.getReference(Publicaciones.class, id);
                publicaciones.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The publicaciones with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Aplicacion> aplicacionListOrphanCheck = publicaciones.getAplicacionList();
            for (Aplicacion aplicacionListOrphanCheckAplicacion : aplicacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Publicaciones (" + publicaciones + ") cannot be destroyed since the Aplicacion " + aplicacionListOrphanCheckAplicacion + " in its aplicacionList field has a non-nullable publicacionesId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario idUsuario = publicaciones.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getPublicacionesList().remove(publicaciones);
                idUsuario = em.merge(idUsuario);
            }
            Rubros idRubro = publicaciones.getIdRubro();
            if (idRubro != null) {
                idRubro.getPublicacionesList().remove(publicaciones);
                idRubro = em.merge(idRubro);
            }
            em.remove(publicaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Publicaciones> findPublicacionesEntities() {
        return findPublicacionesEntities(true, -1, -1);
    }

    public List<Publicaciones> findPublicacionesEntities(int maxResults, int firstResult) {
        return findPublicacionesEntities(false, maxResults, firstResult);
    }

    private List<Publicaciones> findPublicacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Publicaciones.class));
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

    public Publicaciones findPublicaciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Publicaciones.class, id);
        } finally {
            em.close();
        }
    }

    public List<Publicaciones> listarP(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("Publicaciones.findIdUsuario", Publicaciones.class).setParameter("id", id).getResultList();
        } finally {
            em.close();
        }
    }

    public int getPublicacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Publicaciones> rt = cq.from(Publicaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public List<Publicaciones> obtenerPorIdRubroPublicaciones (Integer idRubro) {
        EntityManager em  = getEntityManager();
        try {
        List<Publicaciones> lista = em.createQuery("Publicaciones.publiByRubro", Publicaciones.class).setParameter("idRubro", idRubro).getResultList();
        return lista; 
        } finally {
        em.close();
        }
    }

    public List<Object[]> obtener(Integer id) {
        EntityManager em = getEntityManager();

        List<Object[]> listado = em.createNativeQuery("SELECT p.id, u.nombre, u.email, r.descripcion rubro, p.vacantes, p.titulo, p.fecha_vencimiento, p.requerimientos, p.descripcion, u.id \n"
                + "from publicaciones p \n"
                + "INNER JOIN usuario u ON p.idUsuario = u.id\n"
                + "INNER JOIN rubros r ON p.idRubro = r.id\n"
                + "WHERE p.id NOT IN (SELECT a.publicaciones_id FROM aplicacion a WHERE a.usuario_id = "+id+")").getResultList();

        em.close();

        return listado;
    }
}
