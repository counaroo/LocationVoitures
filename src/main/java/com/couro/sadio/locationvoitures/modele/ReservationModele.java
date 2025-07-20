package com.couro.sadio.locationvoitures.modele;

import com.couro.sadio.locationvoitures.dao.IDao;
import com.couro.sadio.locationvoitures.dao.impl.HibernateEmployeeDaoImpl;
import com.couro.sadio.locationvoitures.dao.impl.HibernateReservationDaoImpl;
import com.couro.sadio.locationvoitures.entities.Client;
import com.couro.sadio.locationvoitures.entities.Reservation;
import com.couro.sadio.locationvoitures.exception.DAOException;
import com.couro.sadio.locationvoitures.factory.ConcreteFactory;
import com.couro.sadio.locationvoitures.factory.EmployeFactory;
import com.couro.sadio.locationvoitures.factory.ReservationFactory;

import java.util.ArrayList;
import java.util.List;

public class ReservationModele extends ObjectModele<Reservation>{
    @Override
    protected IDao<Reservation> initializeDao() {
        return ConcreteFactory.getFactory(ReservationFactory.class)
                .getReservation(HibernateReservationDaoImpl.class);
    }

    @Override
    protected String getEntityName(Reservation entity) {
        return "";
    }

    @Override
    protected String getEntityTypeName() {
        return "Reservation";
    }

    @Override
    protected String getEntityDetails(Reservation entity) {
        return "";
    }

    public List<Reservation> findByClient(Client clientConnecte) throws DAOException {
        HibernateReservationDaoImpl reservationDao = new HibernateReservationDaoImpl(Reservation.class);
        try {
            return reservationDao.findByClient(clientConnecte);
        } catch (DAOException e) {
            throw new DAOException("Erreur lors de la recherche des réservations pour le client " +
                    clientConnecte.getId() + ": " + e.getMessage());
        }
    }
}