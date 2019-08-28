/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yacayo.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.yacayo.entidades.Ciudad;
import com.yacayo.entidades.Direccion;
import com.yacayo.entidades.Persona;
import java.util.ArrayList;
import java.util.List;
import com.yacayo.entidades.Empresa;
import com.yacayo.dao.exceptions.IllegalOrphanException;
import com.yacayo.dao.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author david.poncefgkss
 */
public class DireccionJpaController implements Serializable {

    public DireccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Direccion direccion) {
        if (direccion.getPersonaList() == null) {
            direccion.setPersonaList(new ArrayList<Persona>());
        }
        if (direccion.getEmpresaList() == null) {
            direccion.setEmpresaList(new ArrayList<Empresa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad idCiudad = direccion.getIdCiudad();
            if (idCiudad != null) {
                idCiudad = em.getReference(idCiudad.getClass(), idCiudad.getId());
                direccion.setIdCiudad(idCiudad);
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
            if (idCiudad != null) {
                idCiudad.getDireccionList().add(direccion);
                idCiudad = em.merge(idCiudad);
            }
            for (Persona personaListPersona : direccion.getPersonaList()) {
                Direccion oldIdDireccionOfPersonaListPersona = personaListPersona.getIdDireccion();
                personaListPersona.setIdDireccion(direccion);
                personaListPersona = em.merge(personaListPersona);
                if (oldIdDireccionOfPersonaListPersona != null) {
                    oldIdDireccionOfPersonaListPersona.getPersonaList().remove(personaListPersona);
                    oldIdDireccionOfPersonaListPersona = em.merge(oldIdDireccionOfPersonaListPersona);
                }
            }
            for (Empresa empresaListEmpresa : direccion.getEmpresaList()) {
                Direccion oldIdDireccionOfEmpresaListEmpresa = empresaListEmpresa.getIdDireccion();
                empresaListEmpresa.setIdDireccion(direccion);
                empresaListEmpresa = em.merge(empresaListEmpresa);
                if (oldIdDireccionOfEmpresaListEmpresa != null) {
                    oldIdDireccionOfEmpresaListEmpresa.getEmpresaList().remove(empresaListEmpresa);
                    oldIdDireccionOfEmpresaListEmpresa = em.merge(oldIdDireccionOfEmpresaListEmpresa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Direccion direccion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Direccion persistentDireccion = em.find(Direccion.class, direccion.getId());
            Ciudad idCiudadOld = persistentDireccion.getIdCiudad();
            Ciudad idCiudadNew = direccion.getIdCiudad();
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
                    illegalOrphanMessages.add("You must retain Persona " + personaListOldPersona + " since its idDireccion field is not nullable.");
                }
            }
            for (Empresa empresaListOldEmpresa : empresaListOld) {
                if (!empresaListNew.contains(empresaListOldEmpresa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empresa " + empresaListOldEmpresa + " since its idDireccion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idCiudadNew != null) {
                idCiudadNew = em.getReference(idCiudadNew.getClass(), idCiudadNew.getId());
                direccion.setIdCiudad(idCiudadNew);
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
            if (idCiudadOld != null && !idCiudadOld.equals(idCiudadNew)) {
                idCiudadOld.getDireccionList().remove(direccion);
                idCiudadOld = em.merge(idCiudadOld);
            }
            if (idCiudadNew != null && !idCiudadNew.equals(idCiudadOld)) {
                idCiudadNew.getDireccionList().add(direccion);
                idCiudadNew = em.merge(idCiudadNew);
            }
            for (Persona personaListNewPersona : personaListNew) {
                if (!personaListOld.contains(personaListNewPersona)) {
                    Direccion oldIdDireccionOfPersonaListNewPersona = personaListNewPersona.getIdDireccion();
                    personaListNewPersona.setIdDireccion(direccion);
                    personaListNewPersona = em.merge(personaListNewPersona);
                    if (oldIdDireccionOfPersonaListNewPersona != null && !oldIdDireccionOfPersonaListNewPersona.equals(direccion)) {
                        oldIdDireccionOfPersonaListNewPersona.getPersonaList().remove(personaListNewPersona);
                        oldIdDireccionOfPersonaListNewPersona = em.merge(oldIdDireccionOfPersonaListNewPersona);
                    }
                }
            }
            for (Empresa empresaListNewEmpresa : empresaListNew) {
                if (!empresaListOld.contains(empresaListNewEmpresa)) {
                    Direccion oldIdDireccionOfEmpresaListNewEmpresa = empresaListNewEmpresa.getIdDireccion();
                    empresaListNewEmpresa.setIdDireccion(direccion);
                    empresaListNewEmpresa = em.merge(empresaListNewEmpresa);
                    if (oldIdDireccionOfEmpresaListNewEmpresa != null && !oldIdDireccionOfEmpresaListNewEmpresa.equals(direccion)) {
                        oldIdDireccionOfEmpresaListNewEmpresa.getEmpresaList().remove(empresaListNewEmpresa);
                        oldIdDireccionOfEmpresaListNewEmpresa = em.merge(oldIdDireccionOfEmpresaListNewEmpresa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
                illegalOrphanMessages.add("This Direccion (" + direccion + ") cannot be destroyed since the Persona " + personaListOrphanCheckPersona + " in its personaList field has a non-nullable idDireccion field.");
            }
            List<Empresa> empresaListOrphanCheck = direccion.getEmpresaList();
            for (Empresa empresaListOrphanCheckEmpresa : empresaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Direccion (" + direccion + ") cannot be destroyed since the Empresa " + empresaListOrphanCheckEmpresa + " in its empresaList field has a non-nullable idDireccion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ciudad idCiudad = direccion.getIdCiudad();
            if (idCiudad != null) {
                idCiudad.getDireccionList().remove(direccion);
                idCiudad = em.merge(idCiudad);
            }
            em.remove(direccion);
            em.getTransaction().commit();
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
