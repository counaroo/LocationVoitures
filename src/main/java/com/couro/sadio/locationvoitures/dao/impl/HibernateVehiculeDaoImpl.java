package com.couro.sadio.locationvoitures.dao.impl;

import com.couro.sadio.locationvoitures.entities.Vehicule;

public class HibernateVehiculeDaoImpl extends HibernateObjectDaoImpl<Vehicule>{
    public HibernateVehiculeDaoImpl(Class<Vehicule> typeObject) {
        super(typeObject);
    }
}
