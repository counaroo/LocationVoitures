package com.couro.sadio.locationvoitures.dao.impl;

import com.couro.sadio.locationvoitures.data.HibernateConnection;
import com.couro.sadio.locationvoitures.entities.Employe;
import com.couro.sadio.locationvoitures.entities.Facture;
import com.couro.sadio.locationvoitures.entities.Reservation;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateFactureDaoImpl extends HibernateObjectDaoImpl<Facture>{
    public HibernateFactureDaoImpl(Class<Facture> typeObject) {
        super(typeObject);
    }

    public List<Facture> findByClient(int id) {
        Session session = HibernateConnection.getInstance().getSession();
        try {
            String hql = "FROM factures f WHERE f.reservation.client.id = :clientId ORDER BY f.dateFacture DESC";
            Query<Facture> query = session.createQuery(hql, Facture.class);
            query.setParameter("clientId", id);

            List<Facture> result = query.getResultList();
            System.out.println("Nombre de factures trouvées pour le client ID " + id + ": " + result.size());
            return result;

        } catch (Exception e) {
            System.err.println("Erreur dans findByClient pour client ID: " + id);
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération des factures par client: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

}
