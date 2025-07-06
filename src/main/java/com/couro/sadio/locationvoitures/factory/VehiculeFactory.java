package com.couro.sadio.locationvoitures.factory;

import com.couro.sadio.locationvoitures.dao.IDao;
import com.couro.sadio.locationvoitures.entities.Vehicule;

public interface VehiculeFactory {
    IDao<Vehicule> getVehicule(Class<? extends IDao<Vehicule>> daoVehicule);
}
