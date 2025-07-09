package com.couro.sadio.locationvoitures.modele;

import com.couro.sadio.locationvoitures.dao.IDao;
import com.couro.sadio.locationvoitures.dao.impl.HibernateUserDaoImpl;
import com.couro.sadio.locationvoitures.entities.Utilisateur;
import com.couro.sadio.locationvoitures.factory.ConcreteFactory;
import com.couro.sadio.locationvoitures.factory.UtilisateurFactory;

public class UserModele extends ObjectModele<Utilisateur>{
    @Override
    protected IDao<Utilisateur> initializeDao() {
        return ConcreteFactory.getFactory(UtilisateurFactory.class)
                .getUtilisateur(HibernateUserDaoImpl.class);
    }

    @Override
    protected String getEntityName(Utilisateur entity) {
        return entity.getPrenom() + " " + entity.getNom();
    }

    @Override
    protected String getEntityTypeName() {
        return "utilisateurs";
    }

    @Override
    protected String getEntityDetails(Utilisateur entity) {
        return entity.getId() + " " + entity.getPrenom() + " " + entity.getNom() ;
    }

    public Utilisateur authenticate(String login, String password){
        HibernateUserDaoImpl userDao = new HibernateUserDaoImpl();
        Utilisateur user = userDao.findByLoginAndPassword(login, password);
        if(user != null) {
            return user;
        }

        return null;
    }
    
}
