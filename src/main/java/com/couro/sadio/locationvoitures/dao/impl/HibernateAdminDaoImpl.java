package com.couro.sadio.locationvoitures.dao.impl;

import com.couro.sadio.locationvoitures.entities.Administrateur;

public class HibernateAdminDaoImpl extends HibernateObjectDaoImpl<Administrateur>{
    public HibernateAdminDaoImpl(Class<Administrateur> typeObject) {
        super(typeObject);
    }
}
