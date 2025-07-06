package com.couro.sadio.locationvoitures.modele;

import com.couro.sadio.locationvoitures.dao.IDao;
import com.couro.sadio.locationvoitures.dao.impl.HibernateAdminDaoImpl;
import com.couro.sadio.locationvoitures.entities.Administrateur;
import com.couro.sadio.locationvoitures.factory.AdminFactory;
import com.couro.sadio.locationvoitures.factory.ConcreteFactory;

public class AdminModele extends ObjectModele<Administrateur> {

    @Override
    protected IDao<Administrateur> initializeDao() {
        return ConcreteFactory.getFactory(AdminFactory.class)
                .getAdmin(HibernateAdminDaoImpl.class);
    }

    @Override
    protected String getEntityName(Administrateur entity) {
        // Retourne le nom complet de l'administrateur
        // Utilisé dans les messages comme "Admin trouvé : Jean Dupont"
        return entity.getPrenom() + " " + entity.getNom();
    }

    @Override
    protected String getEntityTypeName() {
        // Retourne le nom du type d'entité
        // Utilisé dans les messages comme "Administrateur créé avec succès"
        return "Administrateur";
    }

    @Override
    protected String getEntityDetails(Administrateur entity) {
        // Retourne les détails complets pour l'affichage dans les listes
        // Utilisé dans lister() pour afficher "1 Jean Dupont (admin@email.com)"
        return entity.getId() + " " + entity.getPrenom() + " " + entity.getNom() ;
    }
}