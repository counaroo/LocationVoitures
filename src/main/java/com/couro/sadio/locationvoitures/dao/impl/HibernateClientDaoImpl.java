package com.couro.sadio.locationvoitures.dao.impl;

import com.couro.sadio.locationvoitures.entities.Client;

public class HibernateClientDaoImpl extends HibernateObjectDaoImpl<Client>{
    public HibernateClientDaoImpl(Class<Client> typeObject) {
        super(typeObject);
    }
}
