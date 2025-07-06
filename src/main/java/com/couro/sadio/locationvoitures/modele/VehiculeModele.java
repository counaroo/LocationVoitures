package com.couro.sadio.locationvoitures.modele;

import com.couro.sadio.locationvoitures.dao.IDao;
import com.couro.sadio.locationvoitures.dao.impl.HibernateEmployeeDaoImpl;
import com.couro.sadio.locationvoitures.dao.impl.HibernateVehiculeDaoImpl;
import com.couro.sadio.locationvoitures.entities.Vehicule;
import com.couro.sadio.locationvoitures.factory.ConcreteFactory;
import com.couro.sadio.locationvoitures.factory.EmployeFactory;
import com.couro.sadio.locationvoitures.factory.VehiculeFactory;

public class VehiculeModele extends ObjectModele<Vehicule>{
    @Override
    protected IDao<Vehicule> initializeDao() {
        return ConcreteFactory.getFactory(VehiculeFactory.class)
                .getVehicule(HibernateVehiculeDaoImpl.class);
    }

    @Override
    protected String getEntityName(Vehicule entity) {
        return entity.getMarque()+" "+entity.getModele();
    }

    @Override
    protected String getEntityTypeName() {
        return "vehicule";
    }

    @Override
    protected String getEntityDetails(Vehicule entity) {
        return entity.getId()+" "+ entity.getMarque()+" "+entity.getModele();
    }
}
