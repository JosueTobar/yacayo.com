/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yacayo.dao;

import com.yacayo.dao.exceptions.IllegalOrphanException;
import com.yacayo.dao.exceptions.NonexistentEntityException;
import com.yacayo.dao.exceptions.PreexistingEntityException;
import com.yacayo.dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.yacayo.entity.Ciudad;
import com.yacayo.entity.Direccion;
import com.yacayo.entity.Persona;
import java.util.ArrayList;
import java.util.List;
import com.yacayo.entity.Empresa;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author josue.tobarfgkss
 */
public class DireccionJpaController implements Serializable {

    public DireccionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Direccion direccion) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (direccion.getPersonaList() == null) {
            direccion.setPersonaList(new ArrayList<Persona>());
        }
        if (direccion.getEmpresaList() == null) {
            direccion.setEmpresaList(new ArrayList<Empresa>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Ciudad municipioId = direccion.getMunicipioId();
            if (municipioId != null) {
                municipioId = em.getReference(municipioId.getClass(), municipioId.getId());
                direccion.setMunicipioId(municipioId);
            }
            List<Persona> attachedPersonaList = new ArrayList<Persona>();
            for (Persona personaListPersonaToAttach : direccion.getPersonaList()) {
                personaListPersonaToAttach = em.getReference(personaListPersonaToAttach.getClass(), personaListPersonaToAttach.getId());
                attachedPersonaList.add(personaListPersonaToAttach);
            }
            direccion.setPersonaList(attachedPersonaList);
            List<Empresa> attachedEmpresaList = new ArrayList<Empresa>();
            for (Empresa empresaListEmpresaToAttach : direccion.getEmpresaList()) {
                empresaListEmpresaToAttach = em.getReference(empresaListEmpresaToAttach.getClass(), empresaListEmpresaToAttach.getId());
                attachedEmpresaList.add(empresaListEmpresaToAttach);
            }
            direccion.setEmpresaList(attachedEmpresaList);
            em.persist(direccion);
            if (municipioId != null) {
                municipioId.getDireccionList().add(direccion);
                municipioId = em.merge(municipioId);
            }
            for (Persona personaListPersona : direccion.getPersonaList()) {
                Direccion oldDireccionidDireccionOfPersonaListPersona = personaListPersona.getDireccionidDireccion();
                personaListPersona.setDireccionidDireccion(direccion);
                personaListPersona = em.merge(personaListPersona);
                if (oldDireccionidDireccionOfPersonaListPersona != null) {
                    oldDireccionidDireccionOfPersonaListPersona.getPersonaList().remove(personaListPersona);
                    oldDireccionidDireccionOfPersonaListPersona = em.merge(oldDireccionidDireccionOfPersonaListPersona);
                }
            }
            for (Empresa empresaListEmpresa : direccion.getEmpresaList()) {
                Direccion oldDireccionidDireccionOfEmpresaListEmpresa = empresaListEmpresa.getDireccionidDireccion();
                empresaListEmpresa.setDireccionidDireccion(direccion);
                empresaListEmpresa = em.merge(empresaListEmpresa);
                if (oldDireccionidDireccionOfEmpresaListEmpresa != null) {
                    oldDireccionidDireccionOfEmpresaListEmpresa.getEmpresaList().remove(empresaListEmpresa);
                    oldDireccionidDireccionOfEmpresaListEmpresa = em.merge(oldDireccionidDireccionOfEmpresaListEmpresa);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findDireccion(direccion.getId()) != null) {
                throw new PreexistingEntityException("Direccion " + direccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Direccion direccion) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Direccion persistentDireccion = em.find(Direccion.class, direccion.getId());
            Ciudad municipioIdOld = persistentDireccion.getMunicipioId();
            Ciudad municipioIdNew = direccion.getMunicipioId();
            List<Persona> personaListOld = persistentDireccion.getPersonaList();
            List<Persona> personaListNew = direccion.getPersonaList();
            List<Empresa> empresaListOld = persistentDireccion.getEmpresaList();
            List<Empresa> empresaListNew = direccion.getEmpresaList();
            List<String> illegalOrphanMessages = null;
            for (Persona personaListOldPersona : personaListOld) {
                if (!personaListNew.contains(personaListOldPersona)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Persona " + personaListOldPersona + " since its direccionidDireccion field is not nullable.");
                }
            }
            for (Empresa empresaListOldEmpresa : empresaListOld) {
                if (!empresaListNew.contains(empresaListOldEmpresa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empresa " + empresaListOldEmpresa + " since its direccionidDireccion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (municipioIdNew != null) {
                municipioIdNew = em.getReference(municipioIdNew.getClass(), municipioIdNew.getId());
                direccion.setMunicipioId(municipioIdNew);
            }
            List<Persona> attachedPersonaListNew = new ArrayList<Persona>();
            for (Persona personaListNewPersonaToAttach : personaListNew) {
                personaListNewPersonaToAttach = em.getReference(personaListNewPersonaToAttach.getClass(), personaListNewPersonaToAttach.getId());
                attachedPersonaListNew.add(personaListNewPersonaToAttach);
            }
            personaListNew = attachedPersonaListNew;
            direccion.setPersonaList(personaListNew);
            List<Empresa> attachedEmpresaListNew = new ArrayList<Empresa>();
            for (Empresa empresaListNewEmpresaToAttach : empresaListNew) {
                empresaListNewEmpresaToAttach = em.getReference(empresaListNewEmpresaToAttach.getClass(), empresaListNewEmpresaToAttach.getId());
                attachedEmpresaListNew.add(empresaListNewEmpresaToAttach);
            }
            empresaListNew = attachedEmpresaListNew;
            direccion.setEmpresaList(empresaListNew);
            direccion = em.merge(direccion);
            if (municipioIdOld != null && !municipioIdOld.equals(municipioIdNew)) {
                municipioIdOld.getDireccionList().remove(direccion);
                municipioIdOld = em.merge(municipioIdOld);
            }
            if (municipioIdNew != null && !municipioIdNew.equals(municipioIdOld)) {
                municipioIdNew.getDireccionList().add(direccion);
                municipioIdNew = em.merge(municipioIdNew);
            }
            for (Persona personaListNewPersona : personaListNew) {
                if (!personaListOld.contains(personaListNewPersona)) {
                    Direccion oldDireccionidDireccionOfPersonaListNewPersona = personaListNewPersona.getDireccionidDireccion();
                    personaListNewPersona.setDireccionidDireccion(direccion);
                    personaListNewPersona = em.merge(personaListNewPersona);
                    if (oldDireccionidDireccionOfPersonaListNewPersona != null && !oldDireccionidDireccionOfPersonaListNewPersona.equals(direccion)) {
                        oldDireccionidDireccionOfPersonaListNewPersona.getPersonaList().remove(personaListNewPersona);
                        oldDireccionidDireccionOfPersonaListNewPersona = em.merge(oldDireccionidDireccionOfPersonaListNewPersona);
                    }
                }
            }
            for (Empresa empresaListNewEmpresa : empresaListNew) {
                if (!empresaListOld.contains(empresaListNewEmpresa)) {
                    Direccion oldDireccionidDireccionOfEmpresaListNewEmpresa = empresaListNewEmpresa.getDireccionidDireccion();
                    empresaListNewEmpresa.setDireccionidDireccion(direccion);
                    empresaListNewEmpresa = em.merge(empresaListNewEmpresa);
                    if (oldDireccionidDireccionOfEmpresaListNewEmpresa != null && !oldDireccionidDireccionOfEmpresaListNewEmpresa.equals(direccion)) {
                        oldDireccionidDireccionOfEmpresaListNewEmpresa.getEmpresaList().remove(empresaListNewEmpresa);
                        oldDireccionidDireccionOfEmpresaListNewEmpresa = em.merge(oldDireccionidDireccionOfEmpresaListNewEmpresa);
                    }
                }
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
                Integer id = direccion.getId();
                if (findDireccion(id) == null) {
                    throw new NonexistentEntityException("The direccion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Direccion direccion;
            try {
                direccion = em.getReference(Direccion.class, id);
                direccion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The direccion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Persona> personaListOrphanCheck = direccion.getPersonaList();
            for (Persona personaListOrphanCheckPersona : personaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Direccion (" + direccion + ") cannot be destroyed since the Persona " + personaListOrphanCheckPersona + " in its personaList field has a non-nullable direccionidDireccion field.");
            }
            List<Empresa> empresaListOrphanCheck = direccion.getEmpresaList();
            for (Empresa empresaListOrphanCheckEmpresa : empresaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Direccion (" + direccion + ") cannot be destroyed since the Empresa " + empresaListOrphanCheckEmpresa + " in its empresaList field has a non-nullable direccionidDireccion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ciudad municipioId = direccion.getMunicipioId();
            if (municipioId != null) {
                municipioId.getDireccionList().remove(direccion);
                municipioId = em.merge(municipioId);
            }
            em.remove(direccion);
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

    public List<Direccion> findDireccionEntities() {
        return findDireccionEntities(true, -1, -1);
    }

    public List<Direccion> findDireccionEntities(int maxResults, int firstResult) {
        return findDireccionEntities(false, maxResults, firstResult);
    }

    private List<Direccion> findDireccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Direccion.class));
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

    public Direccion findDireccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Direccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDireccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Direccion> rt = cq.from(Direccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
