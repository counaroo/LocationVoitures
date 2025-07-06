package com.couro.sadio.locationvoitures.factory;

import com.couro.sadio.locationvoitures.dao.IDao;
import com.couro.sadio.locationvoitures.entities.Administrateur;

public interface AdminFactory {
    IDao<Administrateur> getAdmin(Class<? extends IDao<Administrateur>> daoAdmin);
}
