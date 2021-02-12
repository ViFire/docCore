package cleversoft.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.validation.*;
import java.util.*;

public abstract class GenericDAO<T> {

    private Class<T> clazz;
    public EntityManager em;
    private CriteriaBuilder cb = null;

    public GenericDAO(Class<T> class1) {
        this.clazz = class1;
        this.em = HibernatePersistenceUtil.getEntityManager();
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public void beginTransaction() {
        if(!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    public void closeTransaction() {
        if(em.getTransaction().isActive()) {
            em.getTransaction().commit();
            em.close();
        }
    }

    public void create(T entity) throws PersistenceException,ConstraintViolationException {
        try {
            beginTransaction();
            em.persist(entity);
        } catch (ConstraintViolationException e) {
            em.getTransaction().rollback();
            throw e;
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    public T find(int id) throws PersistenceException {
        return em.find(this.clazz, id);
    }

    public T find(String name, String columnName) throws PersistenceException {
        Map<String, String> attributes = new HashMap<>();
        attributes.put(columnName, name);
        return findEntityByAttributes(attributes);
    }

    public List<T> findAll() throws PersistenceException {
        return em.createQuery("from "+this.clazz.getName()).getResultList();
    }

    public void update(T entity) throws PersistenceException {
        em.merge(entity);
    }

    public void delete(T entity) throws PersistenceException {
        em.remove(entity);
    }

    /* attribute equals table name */
    public T findEntityByAttributes(Map<String, String> attributes) {
        List<T> results = findEntitiesByAttributes(attributes);

        if(results != null) {
            return results.get(0);
        }
        return null;
    }

    public List<T> findEntitiesByAttributes(Map<String, String> attributes) {
        List<T> results;

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(this.clazz);
        Root<T> root = criteria.from(this.clazz);

        //https://vladmihalcea.com/query-entity-type-jpa-criteria-api/
        List<Predicate> predicates = new ArrayList<Predicate>();
        for(String s : attributes.keySet()) {
            if(root.get(s) != null){
                predicates.add(em.getCriteriaBuilder().equal((Expression) root.get(s), attributes.get(s) ));
            }
        }

        criteria.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<T> q = em.createQuery(criteria);

        results = q.getResultList();

        if(results.size() > 0) {
            return results;
        } else {
            return null;
        }
    }

    public Set<ConstraintViolation<T>> isValid(T entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(entity);
    }
}