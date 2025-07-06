package com.couro.sadio.locationvoitures.modele;


import com.couro.sadio.locationvoitures.dao.IDao;
import com.couro.sadio.locationvoitures.dao.impl.HibernateChauffeurDaoImpl;
import com.couro.sadio.locationvoitures.entities.Chauffeur;
import com.couro.sadio.locationvoitures.factory.ChauffeurFactory;
import com.couro.sadio.locationvoitures.factory.ConcreteFactory;


public class ChauffeurModele extends ObjectModele<Chauffeur>{

    @Override
    protected IDao<Chauffeur> initializeDao() {
        return ConcreteFactory.getFactory(ChauffeurFactory.class)
                .getChauffeur(HibernateChauffeurDaoImpl.class);
    }

    @Override
    protected String getEntityName(Chauffeur entity) {
        return entity.getPrenom() + " " + entity.getNom();
    }

    @Override
    protected String getEntityTypeName() {
        return "Chauffeur";
    }

    @Override
    protected String getEntityDetails(Chauffeur entity) {
        return entity.getId() + " " + entity.getPrenom() + " " + entity.getNom() ;
    }
}
