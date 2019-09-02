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
import sv.com.yacayo.entity.TipoDocumento;
import sv.com.yacayo.entity.DetalleAplicacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sv.com.yacayo.dao.exceptions.IllegalOrphanException;
import sv.com.yacayo.dao.exceptions.NonexistentEntityException;
import sv.com.yacayo.entity.Documento;

/**
 *
 * @author josue.tobarfgkss
 */
public class DocumentoJpaController implements Serializable {

    public DocumentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Documento documento) {
        if (documento.getDetalleAplicacionList() == null) {
            documento.setDetalleAplicacionList(new ArrayList<DetalleAplicacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idUsuario = documento.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getId());
                documento.setIdUsuario(idUsuario);
            }
            TipoDocumento idDocumento = documento.getIdDocumento();
            if (idDocumento != null) {
                idDocumento = em.getReference(idDocumento.getClass(), idDocumento.getId());
                documento.setIdDocumento(idDocumento);
            }
            List<DetalleAplicacion> attachedDetalleAplicacionList = new ArrayList<DetalleAplicacion>();
            for (DetalleAplicacion detalleAplicacionListDetalleAplicacionToAttach : documento.getDetalleAplicacionList()) {
                detalleAplicacionListDetalleAplicacionToAttach = em.getReference(detalleAplicacionListDetalleAplicacionToAttach.getClass(), detalleAplicacionListDetalleAplicacionToAttach.getId());
                attachedDetalleAplicacionList.add(detalleAplicacionListDetalleAplicacionToAttach);
            }
            documento.setDetalleAplicacionList(attachedDetalleAplicacionList);
            em.persist(documento);
            if (idUsuario != null) {
                idUsuario.getDocumentoList().add(documento);
                idUsuario = em.merge(idUsuario);
            }
            if (idDocumento != null) {
                idDocumento.getDocumentoList().add(documento);
                idDocumento = em.merge(idDocumento);
            }
            for (DetalleAplicacion detalleAplicacionListDetalleAplicacion : documento.getDetalleAplicacionList()) {
                Documento oldDocumentoIdOfDetalleAplicacionListDetalleAplicacion = detalleAplicacionListDetalleAplicacion.getDocumentoId();
                detalleAplicacionListDetalleAplicacion.setDocumentoId(documento);
                detalleAplicacionListDetalleAplicacion = em.merge(detalleAplicacionListDetalleAplicacion);
                if (oldDocumentoIdOfDetalleAplicacionListDetalleAplicacion != null) {
                    oldDocumentoIdOfDetalleAplicacionListDetalleAplicacion.getDetalleAplicacionList().remove(detalleAplicacionListDetalleAplicacion);
                    oldDocumentoIdOfDetalleAplicacionListDetalleAplicacion = em.merge(oldDocumentoIdOfDetalleAplicacionListDetalleAplicacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Documento documento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Documento persistentDocumento = em.find(Documento.class, documento.getId());
            Usuario idUsuarioOld = persistentDocumento.getIdUsuario();
            Usuario idUsuarioNew = documento.getIdUsuario();
            TipoDocumento idDocumentoOld = persistentDocumento.getIdDocumento();
            TipoDocumento idDocumentoNew = documento.getIdDocumento();
            List<DetalleAplicacion> detalleAplicacionListOld = persistentDocumento.getDetalleAplicacionList();
            List<DetalleAplicacion> detalleAplicacionListNew = documento.getDetalleAplicacionList();
            List<String> illegalOrphanMessages = null;
            for (DetalleAplicacion detalleAplicacionListOldDetalleAplicacion : detalleAplicacionListOld) {
                if (!detalleAplicacionListNew.contains(detalleAplicacionListOldDetalleAplicacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleAplicacion " + detalleAplicacionListOldDetalleAplicacion + " since its documentoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getId());
                documento.setIdUsuario(idUsuarioNew);
            }
            if (idDocumentoNew != null) {
                idDocumentoNew = em.getReference(idDocumentoNew.getClass(), idDocumentoNew.getId());
                documento.setIdDocumento(idDocumentoNew);
            }
            List<DetalleAplicacion> attachedDetalleAplicacionListNew = new ArrayList<DetalleAplicacion>();
            for (DetalleAplicacion detalleAplicacionListNewDetalleAplicacionToAttach : detalleAplicacionListNew) {
                detalleAplicacionListNewDetalleAplicacionToAttach = em.getReference(detalleAplicacionListNewDetalleAplicacionToAttach.getClass(), detalleAplicacionListNewDetalleAplicacionToAttach.getId());
                attachedDetalleAplicacionListNew.add(detalleAplicacionListNewDetalleAplicacionToAttach);
            }
            detalleAplicacionListNew = attachedDetalleAplicacionListNew;
            documento.setDetalleAplicacionList(detalleAplicacionListNew);
            documento = em.merge(documento);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getDocumentoList().remove(documento);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getDocumentoList().add(documento);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            if (idDocumentoOld != null && !idDocumentoOld.equals(idDocumentoNew)) {
                idDocumentoOld.getDocumentoList().remove(documento);
                idDocumentoOld = em.merge(idDocumentoOld);
            }
            if (idDocumentoNew != null && !idDocumentoNew.equals(idDocumentoOld)) {
                idDocumentoNew.getDocumentoList().add(documento);
                idDocumentoNew = em.merge(idDocumentoNew);
            }
            for (DetalleAplicacion detalleAplicacionListNewDetalleAplicacion : detalleAplicacionListNew) {
                if (!detalleAplicacionListOld.contains(detalleAplicacionListNewDetalleAplicacion)) {
                    Documento oldDocumentoIdOfDetalleAplicacionListNewDetalleAplicacion = detalleAplicacionListNewDetalleAplicacion.getDocumentoId();
                    detalleAplicacionListNewDetalleAplicacion.setDocumentoId(documento);
                    detalleAplicacionListNewDetalleAplicacion = em.merge(detalleAplicacionListNewDetalleAplicacion);
                    if (oldDocumentoIdOfDetalleAplicacionListNewDetalleAplicacion != null && !oldDocumentoIdOfDetalleAplicacionListNewDetalleAplicacion.equals(documento)) {
                        oldDocumentoIdOfDetalleAplicacionListNewDetalleAplicacion.getDetalleAplicacionList().remove(detalleAplicacionListNewDetalleAplicacion);
                        oldDocumentoIdOfDetalleAplicacionListNewDetalleAplicacion = em.merge(oldDocumentoIdOfDetalleAplicacionListNewDetalleAplicacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = documento.getId();
                if (findDocumento(id) == null) {
                    throw new NonexistentEntityException("The documento with id " + id + " no longer exists.");
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
            Documento documento;
            try {
                documento = em.getReference(Documento.class, id);
                documento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The documento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetalleAplicacion> detalleAplicacionListOrphanCheck = documento.getDetalleAplicacionList();
            for (DetalleAplicacion detalleAplicacionListOrphanCheckDetalleAplicacion : detalleAplicacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Documento (" + documento + ") cannot be destroyed since the DetalleAplicacion " + detalleAplicacionListOrphanCheckDetalleAplicacion + " in its detalleAplicacionList field has a non-nullable documentoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario idUsuario = documento.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getDocumentoList().remove(documento);
                idUsuario = em.merge(idUsuario);
            }
            TipoDocumento idDocumento = documento.getIdDocumento();
            if (idDocumento != null) {
                idDocumento.getDocumentoList().remove(documento);
                idDocumento = em.merge(idDocumento);
            }
            em.remove(documento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Documento> findDocumentoEntities() {
        return findDocumentoEntities(true, -1, -1);
    }

    public List<Documento> findDocumentoEntities(int maxResults, int firstResult) {
        return findDocumentoEntities(false, maxResults, firstResult);
    }

    private List<Documento> findDocumentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Documento.class));
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

    public Documento findDocumento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Documento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocumentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Documento> rt = cq.from(Documento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
