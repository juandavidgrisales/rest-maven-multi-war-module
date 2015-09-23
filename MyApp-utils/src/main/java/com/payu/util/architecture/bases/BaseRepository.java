package com.payu.util.architecture.bases;

import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;

import com.payu.util.enums.PersistState;
import com.payu.util.exceptions.InfrastructureException;
import com.payu.util.loggers.Log;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author JuanDavid
 * @param <T>
 */
public class BaseRepository<T extends BaseEntity> implements Serializable {

    private static final long serialVersionUID = -3128060299454853638L;

    /**
     * The Constant DEFAULT_PERSISTENCE_UNIT_NAME.
     */
    private static final String DEFAULT_PERSISTENCE_UNIT_NAME = "krehab-pu";

    /**
     * The persistence_unit.
     */
    private String persistence_unit = null;

    /**
     * The entity manager factory.
     */
    private EntityManagerFactory entityManagerFactory;

    /**
     * The entity manager factory map.
     */
    private static Map<String, EntityManagerFactory> entityManagerFactoryMap = new HashMap<String, EntityManagerFactory>();

    /**
     * The lock1.
     */
    private static Object lock1 = new Object();

    /**
     * Constructor.
     */
    public BaseRepository() {

    }

    /**
     * Constructor.
     *
     * @param persistenceUnitName the persistence unit name
     */
    public BaseRepository(String persistenceUnitName) {
        super();
        synchronized (lock1) {
            this.persistence_unit = persistenceUnitName;
            if (entityManagerFactoryMap.get(persistence_unit) == null) {
                Log.info("didn't found the manager factory for: "
                        + persistenceUnitName + " so create a new one");
                entityManagerFactoryMap.put(persistenceUnitName,
                        Persistence.createEntityManagerFactory(persistenceUnitName));
            }
        }
    }

    /**
     * Gets the entity manager.
     *
     * @return EntityManager
     */
    public EntityManager getEntityManager() {
        // Declaración del objeto EntityManager
        EntityManager entityManager = null;

        // Si la unidad de persistencia no existe
        if (persistence_unit != null) {
            // Se obtiene el entityManagerFactory del Map, el cual si fue inicializado en el Constructor se creo con la unidad
            // de persistencia por defecto y final instanciada en esta clase
            entityManagerFactory = entityManagerFactoryMap.get(persistence_unit);
            if (entityManagerFactory == null) {
                entityManagerFactory = Persistence.createEntityManagerFactory(persistence_unit);
                entityManagerFactoryMap.put(persistence_unit, entityManagerFactory);
            }
            entityManager = entityManagerFactory.createEntityManager();
        } else {
            entityManagerFactory = entityManagerFactoryMap.get(DEFAULT_PERSISTENCE_UNIT_NAME);
            if (entityManagerFactory == null) {
                entityManagerFactory = Persistence.createEntityManagerFactory(DEFAULT_PERSISTENCE_UNIT_NAME);
                entityManagerFactoryMap.put(DEFAULT_PERSISTENCE_UNIT_NAME, entityManagerFactory);
            }
            entityManager = entityManagerFactory.createEntityManager();
        }

        return entityManager;
    }

    /**
     * Gets the entity manager.
     *
     * @param persistenceUnitName the persistence unit name
     * @return Entity Manager
     */
    public EntityManager getEntityManager(String persistenceUnitName) {

        EntityManager entityManager = null;

        if (persistenceUnitName != null) {
            entityManagerFactory = entityManagerFactoryMap.get(persistenceUnitName);
            if (entityManagerFactory == null) {
                entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
                entityManagerFactoryMap.put(persistence_unit, entityManagerFactory);
            }
            entityManager = entityManagerFactory.createEntityManager();
        } else {
            entityManagerFactory = entityManagerFactoryMap.get(DEFAULT_PERSISTENCE_UNIT_NAME);
            if (entityManagerFactory == null) {
                entityManagerFactory = Persistence.createEntityManagerFactory(DEFAULT_PERSISTENCE_UNIT_NAME);
                entityManagerFactoryMap.put(DEFAULT_PERSISTENCE_UNIT_NAME, entityManagerFactory);
            }
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

    /**
     * Gets the connection.
     *
     * @return the connection
     * @throws InfrastructureException the persistence exception
     */
    public Connection getConnection() throws InfrastructureException {
        return this.getConnection(this.getEntityManager());
    }

    /**
     * Gets the connection.
     *
     * @param entityManager the entity manager
     * @return the connection
     * @throws InfrastructureException the persistence exception
     */
    public Connection getConnection(EntityManager entityManager) throws InfrastructureException {
        Connection con = null;
        try {
            Session session = (Session) entityManager.getDelegate();
            SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();
            ConnectionProvider cp = sfi.getConnectionProvider();
            con = cp.getConnection();
        } catch (SQLException sqle) {
            new InfrastructureException("BaseRepository.getConnection: SQLException: " + sqle.getMessage(), sqle);
        } catch (Exception e) {
            new InfrastructureException("BaseRepository.getConnection: Exception: " + e.getMessage(), e);
        }
        return con;

    }

    /**
     * Close db resources.
     *
     * @param rs the rs
     * @param st the st
     * @param con the con
     */
    public void closeDBResources(ResultSet rs, Statement st, Connection con) {
        // Close result set
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                //getLogger().warn(e.getMessage());
            }
        }
        // Close statement
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                //getLogger().warn(e.getMessage());
            }
        }
        // Close DB connection
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                //getLogger().warn(e.getMessage());
            }
        }
    }

    /**
     * Read entity.
     *
     * @param entity the entity
     * @param id the id
     * @return the t
     * @throws InfrastructureException the jPA exception
     */
    public BaseEntity readEntity(BaseEntity entity, Object id) throws InfrastructureException {
        EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            return entityManager.find((Class<T>) entity.getClass(), id);
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
            throw new InfrastructureException("BaseRepository.readEntity: IllegalStateException: " + ise.getMessage(), ise);
        } catch (TransactionRequiredException te) {
            te.printStackTrace();
            throw new InfrastructureException("BaseRepository.readEntity: TransactionRequiredException: " + te.getMessage(), te);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InfrastructureException("BaseRepository.readEntity: Exception: " + e.getMessage(), e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
                entityManager = null;
            }
        }
    }

    /**
     * Delete entity.
     *
     * @param entity the entity
     * @param id the id
     * @throws InfrastructureException the jPA exception
     */
    public String deleteEntity(BaseEntity entity, Object id) throws InfrastructureException {
        String deleteState = PersistState.Failed.name();
        EntityManager entityManager = getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            entityManager.remove(entityManager.find((Class<T>) entity.getClass(), id));
            entityManager.flush();
            entityTransaction.commit();
            deleteState = PersistState.Succes.name();
        } catch (IllegalStateException ise) {
            entityTransaction.rollback();
            ise.printStackTrace();
            deleteState = ise.getMessage();
            throw new InfrastructureException("BaseRepository.deleteEntity: IllegalStateException: " + ise.getMessage(), ise);
        } catch (TransactionRequiredException te) {
            entityTransaction.rollback();
            te.printStackTrace();
            deleteState = te.getMessage();
            throw new InfrastructureException("BaseRepository.deleteEntity: TransactionRequiredException: " + te.getMessage(), te);
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
            deleteState = e.getMessage();
            throw new InfrastructureException("BaseRepository.deleteEntity: Exception: " + e.getMessage(), e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
                entityManager = null;
            }
            return deleteState;
        }
    }

    /**
     * Creates the entity.
     *
     * @param entity the entity
     * @throws InfrastructureException the jPA exception
     */
    public String createEntity(BaseEntity entity) throws InfrastructureException {
        String creationState = PersistState.Failed.name();
        EntityManager entityManager = getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            entityManager.persist(entity);
            entityManager.flush();
            entityTransaction.commit();
            creationState = PersistState.Succes.name();
        } catch (EntityExistsException ee) {
            entityTransaction.rollback();
            ee.printStackTrace();
            creationState = ee.getMessage();
            throw new InfrastructureException("BaseRepositor.createEntity: EntityExistsException: " + ee.getMessage(), ee);
        } catch (IllegalStateException ise) {
            entityTransaction.rollback();
            ise.printStackTrace();
            creationState = ise.getMessage();
            throw new InfrastructureException("BaseRepositor.createEntity: IllegalStateException: " + ise.getMessage(), ise);
        } catch (TransactionRequiredException te) {
            entityTransaction.rollback();
            te.printStackTrace();
            creationState = te.getMessage();
            throw new InfrastructureException("BaseRepositor.createEntity: TransactionRequiredException: " + te.getMessage(), te);
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
            creationState = e.getMessage();
            throw new InfrastructureException("BaseRepositor.createEntity: Exception: " + e.getMessage(), e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
                entityManager = null;
            }
            return creationState;
        }
    }

    /**
     * Update entity.
     *
     * @param entity the entity
     * @return the t
     * @throws InfrastructureException the jPA exception
     */
    public BaseEntity updateEntity(BaseEntity entity) throws InfrastructureException {
        EntityManager entityManager = getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            entity = entityManager.merge(entity);
            entityManager.flush();
            entityTransaction.commit();
            return entity;
        } catch (IllegalStateException ise) {
            entityTransaction.rollback();
            ise.printStackTrace();
            throw new InfrastructureException("BaseRepository.updateEntity: IllegalStateException: " + ise.getMessage(), ise);
        } catch (TransactionRequiredException te) {
            entityTransaction.rollback();
            te.printStackTrace();
            throw new InfrastructureException("BaseRepository.updateEntity: TransactionRequiredException: " + te.getMessage(), te);
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
            throw new InfrastructureException("BaseRepository.updateEntity: Exception: " + e.getMessage(), e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
                entityManager = null;
            }
        }

    }

    /**
     * Read dataModels list.
     *
     * @param entity the entity
     * @return List<T>
     * @throws InfrastructureException the jPA exception
     */
    public Collection<T> readEntitiesList(BaseEntity entity) throws InfrastructureException {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery("SELECT x from " + entity.getClass().getSimpleName() + " x ").getResultList();
        } catch (NoResultException nre) {
            return null;
        } catch (IllegalStateException ise) {
            throw new InfrastructureException("BaseRepository.readListEntities: IllegalStateException: " + ise.getMessage(), ise);
        } catch (Exception e) {
            throw new InfrastructureException("BaseRepository.readListEntities: Exception: " + e.getMessage(), e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
                entityManager = null;
            }
        }
    }

    /**
     * Read enable dataModels list.
     *
     * @param entity the entity
     * @return the collection
     * @throws InfrastructureException the jPA exception
     */
    public Collection<T> readEnableEntitiesList(BaseEntity entity) throws InfrastructureException {
        String query = "SELECT x from " + entity.getClass().getSimpleName() + " x";
        EntityManager entityManager = getEntityManager();
        try {
            if (entity instanceof BaseEntity) {
                query += " WHERE x.statusId = 1";
            }
            return entityManager.createQuery(query).getResultList();
        } catch (IllegalStateException ise) {
            throw new InfrastructureException("BaseRepositor.readEnableEntitiesList: IllegalStateException: " + ise.getMessage(), ise);
        } catch (Exception e) {
            throw new InfrastructureException("BaseRepository.readEnableEntitiesList: Exception: " + e.getMessage(), e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
                entityManager = null;
            }
        }
    }

    /**
     * Gets the last id.
     *
     * @param nombreEntidad the nombre entidad
     * @param nombreCampo the nombre campo
     * @return the last id
     * @throws InfrastructureException the jPA exception
     */
    public long getLastId(String nombreEntidad, String nombreCampo) throws InfrastructureException {
        EntityManager entityManager = getEntityManager();
        try {
            long maxId = 0;
            Object o = entityManager.createQuery("SELECT MAX (x." + nombreCampo + ") from " + nombreEntidad + " x").getSingleResult();
            if (o != null) {
                maxId = (Long) o;
            }
            return maxId;
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
            throw new InfrastructureException("BaseRepositor.getLastId: IllegalStateException: " + ise.getMessage(), ise);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InfrastructureException("BaseRepository.getLastId: Exception: " + e.getMessage(), e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
                entityManager = null;
            }
        }
    }

    /**
     * Gets the next id sequence.
     *
     * @param sequencename the sequencename
     * @return the next id sequence
     * @throws InfrastructureException the jPA exception
     */
    public BigDecimal getNextIdSequence(String sequencename) throws InfrastructureException {
        EntityManager entityManager = getEntityManager();
        try {
            BigDecimal currVal = null;
            Object o = entityManager.createNativeQuery("select last_number from user_sequences where sequence_name='" + sequencename + "'").getSingleResult();
            if (o != null) {
                currVal = (BigDecimal) o;
            }
            return currVal;
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
            throw new InfrastructureException("BaseRepository.getNextIdSequence: IllegalStateException: " + ise.getMessage(), ise);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InfrastructureException("BaseRepository.getNextIdSequence: Exception: " + e.getMessage(), e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
                entityManager = null;
            }
        }
    }

    /**
     * Dispose.
     */
    public void dispose() {
        for (Map.Entry<String, EntityManagerFactory> entry : entityManagerFactoryMap.entrySet()) {
            EntityManagerFactory value = (EntityManagerFactory) entry.getValue();
            value.close();
        }
    }

//    public String createEntity(BaseEntity entity) throws InfrastructureException {
//        return null;
//    }
//
//    public BaseEntity readEntity(BaseEntity entity, Object id) throws InfrastructureException {
//        return null;
//    }
//
//    public BaseEntity updateEntity(BaseEntity entity) throws InfrastructureException {
//        return null;
//    }
//
//    public String deleteEntity(BaseEntity entity, Object id) throws InfrastructureException {
//        return null;
//    }
//
//    public Collection readEntitiesList(BaseEntity entity) throws InfrastructureException {
//        return null;
//    }
//
//    public Collection readEnableEntitiesList(BaseEntity entity) throws InfrastructureException {
//        return null;
//    }
}

