package com.couro.sadio.locationvoitures.modele;

import com.couro.sadio.locationvoitures.dao.IDao;
import com.couro.sadio.locationvoitures.dao.impl.HibernateEmployeeDaoImpl;
import com.couro.sadio.locationvoitures.entities.Employe;
import com.couro.sadio.locationvoitures.exception.DAOException;
import com.couro.sadio.locationvoitures.factory.ConcreteFactory;
import com.couro.sadio.locationvoitures.factory.EmployeFactory;

import java.util.List;

public class EmployeModele extends ObjectModele<Employe> {

    @Override
    protected IDao<Employe> initializeDao() {
        return ConcreteFactory.getFactory(EmployeFactory.class)
                .getEmploye(HibernateEmployeeDaoImpl.class);
    }

    @Override
    protected String getEntityName(Employe entity) {
        return entity.getPrenom() + " " + entity.getNom();
    }

    @Override
    protected String getEntityTypeName() {
        return "Employe";
    }

    @Override
    protected String getEntityDetails(Employe entity) {
        return entity.getId() + " " + entity.getPrenom() + " " + entity.getNom();
    }

    /**
     * Vérifie si un login existe déjà dans la base de données
     * @param login Le login à vérifier
     * @return true si le login existe, false sinon
     */
    public boolean loginExiste(String login) {
        if (login == null || login.trim().isEmpty()) {
            return false;
        }

        try {
            // Récupérer tous les employés
            List<Employe> employes = dao.list();

            // Vérifier si le login existe (en ignorant la casse)
            return employes.stream()
                    .anyMatch(employe -> employe.getLogin() != null &&
                            employe.getLogin().equalsIgnoreCase(login.trim()));

        } catch (DAOException e) {
            System.err.println("Erreur lors de la vérification du login : " + e.getMessage());
            return false; // En cas d'erreur, on considère que le login n'existe pas
        }
    }

    /**
     * Obtient le prochain numéro d'employé disponible
     * @return Le prochain numéro d'employé (séquentiel)
     */
    public int obtenirProchainNumeroEmploye() {
        try {
            // Récupérer tous les employés
            List<Employe> employes = dao.list();

            if (employes.isEmpty()) {
                return 1; // Premier employé
            }

            // Trouver le numéro le plus élevé en extrayant la partie numérique
            int maxNumero = 0;

            for (Employe employe : employes) {
                String numeroEmploye = employe.getNumeroEmploye();
                if (numeroEmploye != null && !numeroEmploye.isEmpty()) {
                    try {
                        // Extraire la partie numérique (ex: "EMP0001" -> "0001" -> 1)
                        String partieNumerique = numeroEmploye.replaceAll("[^0-9]", "");
                        if (!partieNumerique.isEmpty()) {
                            int numero = Integer.parseInt(partieNumerique);
                            maxNumero = Math.max(maxNumero, numero);
                        }
                    } catch (NumberFormatException e) {
                        // Ignorer les numéros mal formatés
                        System.err.println("Numéro d'employé mal formaté : " + numeroEmploye);
                    }
                }
            }

            return maxNumero + 1;

        } catch (DAOException e) {
            System.err.println("Erreur lors de l'obtention du prochain numéro d'employé : " + e.getMessage());
            return 1; // Valeur par défaut en cas d'erreur
        }
    }

    /**
     * Méthode utilitaire pour rechercher un employé par son login
     * @param login Le login à rechercher
     * @return L'employé trouvé ou null
     */
    public Employe rechercherParLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            return null;
        }

        try {
            List<Employe> employes = dao.list();
            return employes.stream()
                    .filter(employe -> employe.getLogin() != null &&
                            employe.getLogin().equalsIgnoreCase(login.trim()))
                    .findFirst()
                    .orElse(null);

        } catch (DAOException e) {
            System.err.println("Erreur lors de la recherche par login : " + e.getMessage());
            return null;
        }
    }

    /**
     * Méthode utilitaire pour rechercher des employés par nom ou prénom
     * @param terme Le terme de recherche
     * @return La liste des employés correspondants
     */
    public List<Employe> rechercherParNomOuPrenom(String terme) {
        if (terme == null || terme.trim().isEmpty()) {
            return lister();
        }

        try {
            List<Employe> employes = dao.list();
            return employes.stream()
                    .filter(employe ->
                            (employe.getNom() != null && employe.getNom().toLowerCase().contains(terme.toLowerCase())) ||
                                    (employe.getPrenom() != null && employe.getPrenom().toLowerCase().contains(terme.toLowerCase())) ||
                                    (employe.getNumeroEmploye() != null && employe.getNumeroEmploye().toLowerCase().contains(terme.toLowerCase()))
                    )
                    .toList();

        } catch (DAOException e) {
            System.err.println("Erreur lors de la recherche : " + e.getMessage());
            return List.of();
        }
    }
}