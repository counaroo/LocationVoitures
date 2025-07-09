package com.couro.sadio.locationvoitures.factory;

import com.couro.sadio.locationvoitures.dao.IDao;
import com.couro.sadio.locationvoitures.entities.Utilisateur;
import com.couro.sadio.locationvoitures.entities.Vehicule;

public interface UtilisateurFactory {
    IDao<Utilisateur> getUtilisateur(Class<? extends IDao<Utilisateur>> daoUtilisateur);
}
