package com.couro.sadio.locationvoitures.dao.impl;

import com.couro.sadio.locationvoitures.entities.Employe;

public class HibernateEmployeeDaoImpl extends HibernateObjectDaoImpl<Employe>{
    public HibernateEmployeeDaoImpl(Class<Employe> typeObject) {
        super(typeObject);
    }
}
