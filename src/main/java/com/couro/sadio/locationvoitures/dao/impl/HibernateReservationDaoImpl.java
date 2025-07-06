package com.couro.sadio.locationvoitures.dao.impl;

import com.couro.sadio.locationvoitures.entities.Reservation;

public class HibernateReservationDaoImpl extends HibernateObjectDaoImpl<Reservation>{
    public HibernateReservationDaoImpl(Class<Reservation> typeObject) {
        super(typeObject);
    }
}
