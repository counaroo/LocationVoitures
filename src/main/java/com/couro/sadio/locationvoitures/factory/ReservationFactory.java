package com.couro.sadio.locationvoitures.factory;

import com.couro.sadio.locationvoitures.dao.IDao;
import com.couro.sadio.locationvoitures.entities.Reservation;

public interface ReservationFactory {
    IDao<Reservation> getReservation(Class<? extends IDao<Reservation>> daoReservation);
}
