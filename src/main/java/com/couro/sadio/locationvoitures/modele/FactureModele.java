package com.couro.sadio.locationvoitures.modele;

import com.couro.sadio.locationvoitures.dao.IDao;
import com.couro.sadio.locationvoitures.dao.impl.HibernateFactureDaoImpl;
import com.couro.sadio.locationvoitures.entities.Facture;
import com.couro.sadio.locationvoitures.exception.DAOException;

import java.util.List;

public class FactureModele extends ObjectModele<Facture>{
    @Override
    protected IDao<Facture> initializeDao() {
        HibernateFactureDaoImpl factureDao = new HibernateFactureDaoImpl(Facture.class);
        return factureDao;
    }

    @Override
    protected String getEntityName(Facture entity) {
        return "";
    }

    @Override
    protected String getEntityTypeName() {
        return "";
    }

    @Override
    protected String getEntityDetails(Facture entity) {
        return "";
    }

    public List<Facture> findFacturesByClient(int id) {
        HibernateFactureDaoImpl hibernateFactureDao = new HibernateFactureDaoImpl(Facture.class);
        try {
            return hibernateFactureDao.findByClient(id);
        } catch (DAOException e) {
            throw new DAOException("Erreur lors de la recherche des factures pour le client " +
                    id + ": " + e.getMessage());
        }
    }
}
