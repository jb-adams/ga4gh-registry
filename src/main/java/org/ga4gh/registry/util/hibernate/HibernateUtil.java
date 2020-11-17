package org.ga4gh.registry.util.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import javax.persistence.PersistenceException;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.model.RegistryEntity;
import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.model.StandardCategory;
import org.ga4gh.registry.model.ReleaseStatus;
import org.ga4gh.registry.model.Service;
import org.ga4gh.registry.model.StandardVersion;
import org.ga4gh.registry.model.WorkStream;

/** Provides database connectivity via Hibernate API
 * 
 * @author GA4GH Technical Team
 */
public class HibernateUtil {

    @Autowired
    private HibernateConfig hibernateConfig;

    private boolean configured;
    private SessionFactory sessionFactory;

    /** Instantiate a HibernateUtil
     */
    public HibernateUtil() {
        configured = false;
    }

    /** Build and configure the session factory, which yields database 
     * sessions that can query the database
     */
    @PostConstruct
    public void buildSessionFactory() {
        try {
            SessionFactory sessionFactory = 
                new Configuration()
                .setProperties(hibernateConfig.getAllProperties())
                .addAnnotatedClass(Standard.class)
                .addAnnotatedClass(StandardCategory.class)
                .addAnnotatedClass(StandardVersion.class)
                .addAnnotatedClass(ReleaseStatus.class)
                .addAnnotatedClass(Organization.class)
                .addAnnotatedClass(Implementation.class)
                .addAnnotatedClass(Service.class)
                .addAnnotatedClass(WorkStream.class)
                .buildSessionFactory();
            setSessionFactory(sessionFactory);
            setConfigured(true);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /* API Methods */

    /** Generic POST request functionality, creates/registers a new entity in
     * the database
     * 
     * @param entityClass The class of the object to be saved (proxy for database entity/table)
     * @param newObject The object to be created
     * @throws HibernateException Thrown when the object could not be saved for any reason
     */
    public void createEntityObject(Class<? extends RegistryModel> entityClass, Object newObject) throws HibernateException {
        Session session = newTransaction();
        try {
            session.saveOrUpdate(newObject);
            endTransaction(session);
        } catch (PersistenceException e) {
            throw new HibernateException(e.getMessage());
        } catch (Exception e) {
            throw new HibernateException(e.getMessage());
        } finally {
            endTransaction(session);
        }
    }

    /** Retrieves a single entity object from the database. Generally corresponds
     * to the SHOW REST API endpoint 
     * 
     * @param entityClass The class of the object to be retrieved
     * @param id The id of the object to be retrieved
     * @return The database entity object
     * @throws HibernateException Thrown when an error is encountered during retrieval
     */
    public RegistryEntity readEntityObject(Class<? extends RegistryEntity> entityClass, String id) throws HibernateException {
        Session session = newTransaction();
        RegistryEntity object = null;
        try {
            object = (RegistryEntity) session.get(entityClass, id);
            if (object != null) {
                object.lazyLoad();
            }
            endTransaction(session);
        } catch (PersistenceException e) {
            throw new HibernateException(e.getMessage());
        } catch (Exception e) {
            throw new HibernateException(e.getMessage());
        } finally {
            endTransaction(session);
        }
        return object;
    }

    /** Updates a single existing entity object in the database with new properties.
     * Generally corresponds to the UPDATE REST API endpoint and the PUT HTTP request
     * 
     * @param entityClass The class of the object to be updated
     * @param oldId The object's original id as it exists in the db before modification
     * @param newId The object's new id, which may or may not differ from the original
     * @param newObject The object containing new properties to update the old object with
     * @throws HibernateException Thrown when the object could not be updated for any reason
     */
    public void updateEntityObject(Class<? extends RegistryEntity> entityClass, String oldId, String newId, RegistryEntity newObject) throws HibernateException {
        Session session = newTransaction();
        try {
            // update the object at the existing id
            newObject.setId(oldId);
            session.update(newObject);
            endTransaction(session);
            // update the object's id via manual query
            if (!newId.equals(oldId)) {
                session = newTransaction();
                String updateId =
                "UPDATE " + newObject.getClass().getName()
                + " set id='" + newId + "'"
                + " WHERE id='" + oldId + "'";
                session.createQuery(updateId).executeUpdate();
                endTransaction(session);
            }
        } catch (PersistenceException e) {
            throw new HibernateException(e.getMessage());
        } catch (Exception e) {
            throw new HibernateException(e.getMessage());
        } finally {
            endTransaction(session);
        }
    }

    /** Deletes a single existing entity object in the database. Generally corresponds
     * to the DESTRY REST API endpoint and the DELETE HTTP request
     * 
     * @param entityClass The class of the object to be deleted
     * @param id The id of the object to be deleted
     * @throws HibernateException Thrown when the object could not be deleted for any reason
     */
    public void deleteEntityObject(Class<? extends RegistryModel> entityClass, String id) throws HibernateException {
        Session session = newTransaction();
        RegistryModel object = null;
        try {
            object = session.get(entityClass, id);
            session.delete(object);
            endTransaction(session);
        } catch (PersistenceException e) {
            throw new HibernateException(e.getMessage());
        } catch (Exception e) {
            throw new HibernateException(e.getMessage());
        } finally {
            endTransaction(session);
        }
    }

    /* Private Convenience Methods */

    /** Retrieves a new session transaction, which will not conflict with 
     * any other concurrent db transactions
     * 
     * @return a new database session transaction
     */
    private Session newTransaction() {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        return session;
    }

    /** End/close a transaction
     * 
     * @param session The session containing the transaction to be closed
     */
    private void endTransaction(Session session) {
        if (session.getTransaction().isActive()) {
            session.getTransaction().commit();
            session.close();
        }
    }

    /** Shuts down the session factory at end of program lifecycle
     */
    public void shutdown() {
        getSessionFactory().close();
    }

    /* Setters and Getters */

    private void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private void setConfigured(boolean configured) {
        this.configured = configured;
    }

    public boolean getConfigured() {
        return configured;
    }
}
