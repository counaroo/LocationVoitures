package com.couro.sadio.locationvoitures.dao.impl;

import com.couro.sadio.locationvoitures.dao.IDao;
import com.couro.sadio.locationvoitures.data.HibernateConnection;
import com.couro.sadio.locationvoitures.exception.DAOException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public abstract class HibernateObjectDaoImpl<T> implements IDao<T> {
    private Class<T> typeObject;

    public HibernateObjectDaoImpl(Class<T> typeObject) {
        this.typeObject = typeObject;
    }

    @Override
    public void create(T entity) throws DAOException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateConnection.getInstance().getSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            System.out.println("Entité créée avec succès: " + entity.getClass().getSimpleName());
        } catch (Exception e) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackEx) {
                    System.err.println("Erreur lors du rollback: " + rollbackEx.getMessage());
                }
            }
            throw new DAOException("ERROR creating entity: " + e.getMessage());
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception closeEx) {
                    System.err.println("Erreur lors de la fermeture de la session: " + closeEx.getMessage());
                }
            }
        }
    }

    @Override
    public T read(int id) throws DAOException {
        Session session = null;
        try {
            session = HibernateConnection.getInstance().getSession();
            return session.find(typeObject, id);
        } catch (Exception e) {
            throw new DAOException("ERROR reading entity: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void update(T entity) throws DAOException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateConnection.getInstance().getSession();
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DAOException("ERROR updating entity: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<T> list() throws DAOException {
        Session session = null;
        try {
            session = HibernateConnection.getInstance().getSession();
            String hql = "FROM " + typeObject.getName();
            return session.createQuery(hql, typeObject).getResultList();
        } catch (Exception e) {
            throw new DAOException("ERROR listing entities: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateConnection.getInstance().getSession();
            transaction = session.beginTransaction();
            T entity = session.find(typeObject, id);
            if (entity != null) {
                session.remove(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DAOException("ERROR deleting entity: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    // ✅ Ajout : méthode de suppression par instance (en plus de delete(int id))
    public void delete(T entity) {
        if (entity == null) return;

        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateConnection.getInstance().getSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    public void save(T entity) {
        Session session = HibernateConnection.getInstance().getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<T> findAll() throws DAOException {
        return list();
    }
}
