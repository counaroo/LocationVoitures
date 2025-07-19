package com.couro.sadio.locationvoitures.dao.impl;

import com.couro.sadio.locationvoitures.data.HibernateConnection;
import com.couro.sadio.locationvoitures.entities.Client;
import com.couro.sadio.locationvoitures.entities.Reservation;
import com.couro.sadio.locationvoitures.exception.DAOException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateReservationDaoImpl extends HibernateObjectDaoImpl<Reservation>{
    public HibernateReservationDaoImpl(Class<Reservation> typeObject) {
        super(typeObject);
    }

    public List<Reservation> findByClient(Client client) throws DAOException {
        Session session = HibernateConnection.getInstance().getSession();
        try {
            String hql = "FROM Reservation r WHERE r.client = :client ORDER BY r.dateDebut DESC";
            Query<Reservation> query = session.createQuery(hql, Reservation.class);
            query.setParameter("client", client);
            return query.getResultList();
        } catch (Exception e) {
            throw new DAOException("Erreur lors de la récupération des réservations par client");
        } finally {
            session.close();
        }
    }
}
