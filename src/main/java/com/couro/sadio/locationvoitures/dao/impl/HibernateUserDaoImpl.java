package com.couro.sadio.locationvoitures.dao.impl;

import com.couro.sadio.locationvoitures.dao.IDao;
import com.couro.sadio.locationvoitures.data.HibernateConnection;
import com.couro.sadio.locationvoitures.entities.Utilisateur;
import com.couro.sadio.locationvoitures.exception.DAOException;

import org.hibernate.Session;
import org.hibernate.query.Query;

public class HibernateUserDaoImpl extends HibernateObjectDaoImpl<Utilisateur>{

    public HibernateUserDaoImpl(Class typeObject) {
        super(typeObject);
    }

    public HibernateUserDaoImpl() {
        super(Utilisateur.class);
    }

    public Utilisateur findByLoginAndPassword(String login, String password) throws DAOException {
        Session session = null;
        try {
            session = HibernateConnection.getInstance().getSession();
            String hql = " FROM Utilisateur WHERE login = :login AND password = :password";
            Query<Utilisateur> query = session.createQuery(hql, Utilisateur.class);
            query.setParameter("login", login);
            query.setParameter("password", password);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new DAOException("ERROR finding user by login and password: " + e.getMessage());
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
}
