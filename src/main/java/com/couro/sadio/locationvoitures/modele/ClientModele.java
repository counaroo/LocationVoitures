package com.couro.sadio.locationvoitures.modele;

import com.couro.sadio.locationvoitures.dao.IDao;
import com.couro.sadio.locationvoitures.dao.impl.HibernateClientDaoImpl;
import com.couro.sadio.locationvoitures.entities.Client;
import com.couro.sadio.locationvoitures.entities.Utilisateur;
import com.couro.sadio.locationvoitures.exception.DAOException;
import com.couro.sadio.locationvoitures.factory.ClientFactory;
import com.couro.sadio.locationvoitures.factory.ConcreteFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ClientModele extends ObjectModele<Client>{
    HibernateClientDaoImpl dao = new HibernateClientDaoImpl(Client.class);

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

    public List<Client> rechercherParNom(String nom) {
        try {
            List<Client> tousLesClients = dao.list();
            return tousLesClients.stream()
                    .filter(client -> client.getNom().toLowerCase().contains(nom.toLowerCase()) ||
                            client.getPrenom().toLowerCase().contains(nom.toLowerCase()))
                    .collect(Collectors.toList());
        } catch (DAOException e) {
            System.err.println("Erreur lors de la recherche par nom : " + e.getMessage());
            return null;
        }
    }

    /**
     * Recherche un client par email
     */
    public Client rechercherParEmail(String email) {
        try {
            List<Client> tousLesClients = dao.list();
            return tousLesClients.stream()
                    .filter(client -> client.getEmail().equalsIgnoreCase(email))
                    .findFirst()
                    .orElse(null);
        } catch (DAOException e) {
            System.err.println("Erreur lors de la recherche par email : " + e.getMessage());
            return null;
        }
    }

    /**
     * Recherche des clients par points de fidélité minimum
     */
    public List<Client> rechercherParPointsFidelite(int pointsMin) {
        try {
            List<Client> tousLesClients = dao.list();
            return tousLesClients.stream()
                    .filter(client -> client.getPointsFidelite() >= pointsMin)
                    .collect(Collectors.toList());
        } catch (DAOException e) {
            System.err.println("Erreur lors de la recherche par points de fidélité : " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtient les clients les plus fidèles (par points de fidélité)
     */
    public List<Client> getTopClientsFideles(int limite) {
        try {
            List<Client> tousLesClients = dao.list();
            return tousLesClients.stream()
                    .sorted((c1, c2) -> Integer.compare(c2.getPointsFidelite(), c1.getPointsFidelite()))
                    .limit(limite)
                    .collect(Collectors.toList());
        } catch (DAOException e) {
            System.err.println("Erreur lors de la récupération des top clients : " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtient les clients les plus actifs (par nombre de réservations)
     */
    public List<Client> getTopClientsActifs(int limite) {
        try {
            List<Client> tousLesClients = dao.list();
            return tousLesClients.stream()
                    .sorted((c1, c2) -> Integer.compare(c2.getReservations().size(), c1.getReservations().size()))
                    .limit(limite)
                    .collect(Collectors.toList());
        } catch (DAOException e) {
            System.err.println("Erreur lors de la récupération des clients actifs : " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtient les clients inactifs (sans réservations)
     */
    public List<Client> getClientsInactifs() {
        try {
            List<Client> tousLesClients = dao.list();
            return tousLesClients.stream()
                    .filter(client -> client.getReservations().isEmpty())
                    .collect(Collectors.toList());
        } catch (DAOException e) {
            System.err.println("Erreur lors de la récupération des clients inactifs : " + e.getMessage());
            return null;
        }
    }

    public Client getClientConnecteByUser(Utilisateur user){
        try {
            ClientModele clientModele = new ClientModele();
            Client client ;
            client = clientModele.read(user.getId());
            return client;
        } catch (DAOException e) {
            System.err.println("Erreur lors de la récupération du client connecté: " + e.getMessage());
            return null;
        }
    }
}
