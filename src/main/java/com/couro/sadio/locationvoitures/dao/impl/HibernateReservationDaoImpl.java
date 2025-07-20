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
        if (client == null) {
            throw new DAOException("Client null");
        }

        Session session = HibernateConnection.getInstance().getSession();
        try {
            String hql = "FROM reservations r WHERE r.client.id = :clientId ORDER BY r.dateDebut DESC";
            Query<Reservation> query = session.createQuery(hql, Reservation.class);
            query.setParameter("clientId", client.getId());

            List<Reservation> results = query.getResultList();
            System.out.println("Nombre de réservations trouvées pour le client " + client.getId() + ": " + results.size());
            return results;

        } catch (Exception e) {
            System.err.println("Erreur dans findByClient pour client ID: " + client.getId());
            e.printStackTrace();
            throw new DAOException("Erreur lors de la récupération des réservations par client: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
