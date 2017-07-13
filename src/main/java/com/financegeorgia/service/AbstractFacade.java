/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.financegeorgia.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Viplav
 */

//** DO NOT create table relationships as it generates entites with all child tables and forces nested json

public class AbstractFacade<T> {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("tommy_tommy_war_1.0PU");
    
    private EntityManager em;
    
    private Class<T> entityClass;
    
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }            
       
    public T create(T entity) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(entity);
            em.flush();
            em.getTransaction().commit();            
            return entity;
        } finally {
            em.close();
        }
    }
    
    public T edit(T entity) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            T t = em.merge(entity); 
            em.getTransaction().commit();
            return t;
        } finally {
            em.close();
        }
    }
    
    //changed this to update deletedInd instead of actual deletion in extending classes
//    public void remove(T entity) {
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();                   
//            em.remove(em.merge(entity));
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//    }    
    
    public List<T> findByAllByField(String queryName, String field, Object value) {
        return getEntityManager().createNamedQuery(queryName, entityClass).setParameter(field, "%" + value + "%").getResultList();
    }
    
    public List<T> findByField(String queryName, String field, Object value) {
        return getEntityManager().createNamedQuery(queryName, entityClass).setParameter(field, value).getResultList();
    }
    
    public List<T> findBy2Fields(String queryName, String field, String value, String field2, String value2) {
        Query q = getEntityManager().createNamedQuery(queryName, entityClass);
        q.setParameter(field, value);
        q.setParameter(field2, value2);
        return q.getResultList();
    }
    
    public List<T> findBy2Fields(String queryName, String field, String value, String field2, Integer value2) {
        Query q = getEntityManager().createNamedQuery(queryName, entityClass);
        q.setParameter(field, value);
        q.setParameter(field2, value2);
        return q.getResultList();
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
