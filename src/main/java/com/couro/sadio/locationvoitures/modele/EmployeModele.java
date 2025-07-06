package com.couro.sadio.locationvoitures.modele;

import com.couro.sadio.locationvoitures.dao.IDao;
import com.couro.sadio.locationvoitures.dao.impl.HibernateClientDaoImpl;
import com.couro.sadio.locationvoitures.dao.impl.HibernateEmployeeDaoImpl;
import com.couro.sadio.locationvoitures.entities.Employe;
import com.couro.sadio.locationvoitures.factory.ClientFactory;
import com.couro.sadio.locationvoitures.factory.ConcreteFactory;
import com.couro.sadio.locationvoitures.factory.EmployeFactory;

public class EmployeModele extends ObjectModele<Employe>{
    @Override
    protected IDao<Employe> initializeDao() {
        return ConcreteFactory.getFactory(EmployeFactory.class)
                .getEmploye(HibernateEmployeeDaoImpl.class);
    }

    @Override
    protected String getEntityName(Employe entity) {
        return entity.getPrenom()+" "+entity.getNom();
    }

    @Override
    protected String getEntityTypeName() {
        return "Employe";
    }

    @Override
    protected String getEntityDetails(Employe entity) {
        return entity.getId() + " " + entity.getPrenom() + " " + entity.getNom();
    }
}
