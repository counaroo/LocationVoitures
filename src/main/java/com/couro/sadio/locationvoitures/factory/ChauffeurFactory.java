package com.couro.sadio.locationvoitures.factory;

import com.couro.sadio.locationvoitures.dao.IDao;
import com.couro.sadio.locationvoitures.entities.Chauffeur;

public interface ChauffeurFactory {
    IDao<Chauffeur> getChauffeur(Class<? extends IDao<Chauffeur>> daoChauffeur);
}
