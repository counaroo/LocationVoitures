package com.couro.sadio.locationvoitures.dao.impl;

import com.couro.sadio.locationvoitures.entities.Chauffeur;

public class HibernateChauffeurDaoImpl extends HibernateObjectDaoImpl<Chauffeur>{

    public HibernateChauffeurDaoImpl(Class<Chauffeur> typeObject) {
        super(typeObject);
    }
}
