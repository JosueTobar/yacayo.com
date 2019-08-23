/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yacayo.dao;

import com.yacayo.dao.exceptions.NonexistentEntityException;
import com.yacayo.dao.exceptions.PreexistingEntityException;
import com.yacayo.dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.yacayo.entity.Direccion;
import com.yacayo.entity.Empresa;
import com.yacayo.entity.Usuario;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author josue.tobarfgkss
 */
@Named
@RequestScoped
public class EmpresaDAO implements Serializable {
    @Resource
    private UserTransaction utx;
    @PersistenceContext
    private EntityManager em;
    
    public EmpresaDAO() { }
    
    /*public EmpresaDAO(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }*/

    public void create(Empresa empresa) throws PreexistingEntityException, RollbackFailureException, Exception {
        //EntityManager em = null;
        try {
            utx.begin();
            //em = getEntityManager();
            Direccion direccionidDireccion = empresa.getDireccionidDireccion();
            if (direccionidDireccion != null) {
                direccionidDireccion = em.getReference(direccionidDireccion.getClass(), direccionidDireccion.getId());
                empresa.setDireccionidDireccion(direccionidDireccion);
            }
            Usuario usuarioId = empresa.getUsuarioId();
            if (usuarioId != null) {
                usuarioId = em.getReference(usuarioId.getClass(), usuarioId.getId());
                empresa.setUsuarioId(usuarioId);
            }
            em.persist(empresa);
            if (direccionidDireccion != null) {
                direccionidDireccion.getEmpresaList().add(empresa);
                direccionidDireccion = em.merge(direccionidDireccion);
            }
            if (usuarioId != null) {
                usuarioId.getEmpresaList().add(empresa);
                usuarioId = em.merge(usuarioId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEmpresa(empresa.getId()) != null) {
                throw new PreexistingEntityException("Empresa " + empresa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empresa empresa) throws NonexistentEntityException, RollbackFailureException, Exception {
        //EntityManager em = null;
        try {
            utx.begin();
            //em = getEntityManager();
            Empresa persistentEmpresa = em.find(Empresa.class, empresa.getId());
            Direccion direccionidDireccionOld = persistentEmpresa.getDireccionidDireccion();
            Direccion direccionidDireccionNew = empresa.getDireccionidDireccion();
            Usuario usuarioIdOld = persistentEmpresa.getUsuarioId();
            Usuario usuarioIdNew = empresa.getUsuarioId();
            if (direccionidDireccionNew != null) {
                direccionidDireccionNew = em.getReference(direccionidDireccionNew.getClass(), direccionidDireccionNew.getId());
                empresa.setDireccionidDireccion(direccionidDireccionNew);
            }
            if (usuarioIdNew != null) {
                usuarioIdNew = em.getReference(usuarioIdNew.getClass(), usuarioIdNew.getId());
                empresa.setUsuarioId(usuarioIdNew);
            }
            empresa = em.merge(empresa);
            if (direccionidDireccionOld != null && !direccionidDireccionOld.equals(direccionidDireccionNew)) {
                direccionidDireccionOld.getEmpresaList().remove(empresa);
                direccionidDireccionOld = em.merge(direccionidDireccionOld);
            }
            if (direccionidDireccionNew != null && !direccionidDireccionNew.equals(direccionidDireccionOld)) {
                direccionidDireccionNew.getEmpresaList().add(empresa);
                direccionidDireccionNew = em.merge(direccionidDireccionNew);
            }
            if (usuarioIdOld != null && !usuarioIdOld.equals(usuarioIdNew)) {
                usuarioIdOld.getEmpresaList().remove(empresa);
                usuarioIdOld = em.merge(usuarioIdOld);
            }
            if (usuarioIdNew != null && !usuarioIdNew.equals(usuarioIdOld)) {
                usuarioIdNew.getEmpresaList().add(empresa);
                usuarioIdNew = em.merge(usuarioIdNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empresa.getId();
                if (findEmpresa(id) == null) {
                    throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        //EntityManager em = null;
        try {
            utx.begin();
            //em = getEntityManager();
            Empresa empresa;
            try {
                empresa = em.getReference(Empresa.class, id);
                empresa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.", enfe);
            }
            Direccion direccionidDireccion = empresa.getDireccionidDireccion();
            if (direccionidDireccion != null) {
                direccionidDireccion.getEmpresaList().remove(empresa);
                direccionidDireccion = em.merge(direccionidDireccion);
            }
            Usuario usuarioId = empresa.getUsuarioId();
            if (usuarioId != null) {
                usuarioId.getEmpresaList().remove(empresa);
                usuarioId = em.merge(usuarioId);
            }
            em.remove(empresa);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empresa> findEmpresaEntities() {
        return findEmpresaEntities(true, -1, -1);
    }

    public List<Empresa> findEmpresaEntities(int maxResults, int firstResult) {
        return findEmpresaEntities(false, maxResults, firstResult);
    }

    private List<Empresa> findEmpresaEntities(boolean all, int maxResults, int firstResult) {
        //EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empresa.class));
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

    public Empresa findEmpresa(Integer id) {
        //EntityManager em = getEntityManager();
        try {
            return em.find(Empresa.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpresaCount() {
        //EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empresa> rt = cq.from(Empresa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
