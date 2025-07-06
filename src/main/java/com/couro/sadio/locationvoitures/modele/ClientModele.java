package com.couro.sadio.locationvoitures.modele;

import com.couro.sadio.locationvoitures.dao.IDao;
import com.couro.sadio.locationvoitures.dao.impl.HibernateClientDaoImpl;
import com.couro.sadio.locationvoitures.entities.Client;
import com.couro.sadio.locationvoitures.factory.ClientFactory;
import com.couro.sadio.locationvoitures.factory.ConcreteFactory;

public class ClientModele extends ObjectModele<Client>{
    @Override
    protected IDao<Client> initializeDao() {
        return ConcreteFactory.getFactory(ClientFactory.class)
                .getClient(HibernateClientDaoImpl.class);
    }

    @Override
    protected String getEntityName(Client entity) {
        return entity.getPrenom()+" "+entity.getNom();
    }

    @Override
    protected String getEntityTypeName() {
        return "Client";
    }

    @Override
    protected String getEntityDetails(Client entity) {
        return entity.getId() + " " + entity.getPrenom() + " " + entity.getNom() ;
    }
}
