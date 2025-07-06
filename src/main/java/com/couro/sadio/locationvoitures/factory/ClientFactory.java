package com.couro.sadio.locationvoitures.factory;

import com.couro.sadio.locationvoitures.dao.IDao;
import com.couro.sadio.locationvoitures.entities.Administrateur;
import com.couro.sadio.locationvoitures.entities.Client;

public interface ClientFactory {
    IDao<Client> getClient(Class<? extends IDao<Client>> daoClient);
}
