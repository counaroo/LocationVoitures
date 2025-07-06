package com.couro.sadio.locationvoitures.factory;

import com.couro.sadio.locationvoitures.dao.IDao;
import com.couro.sadio.locationvoitures.entities.Employe;

public interface EmployeFactory {
    IDao<Employe> getEmploye(Class<? extends IDao<Employe>> daoEmploye);
}
