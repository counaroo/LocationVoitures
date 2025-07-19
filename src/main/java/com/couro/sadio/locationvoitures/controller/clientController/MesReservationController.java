package com.couro.sadio.locationvoitures.controller.clientController;

import com.couro.sadio.locationvoitures.controller.UILoginController;
import com.couro.sadio.locationvoitures.entities.Utilisateur;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MesReservationController implements Initializable, UILoginController.ControlledScreen {

    // Variable pour stocker l'utilisateur connecté
    private Utilisateur utilisateurConnecte;

    @FXML
    private Label Nom;

    @Override
    public void setUserData(Utilisateur user) {
        this.utilisateurConnecte = user;

        // Maintenant vous pouvez utiliser les données utilisateur
        if (user != null) {
            Nom.setText(user.getNom());
            chargerReservationsUtilisateur();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialisation de base
        // Note: l'utilisateur ne sera pas encore disponible ici
        // Il sera disponible après l'appel à setUserData()
    }

    // Méthode pour charger les réservations de l'utilisateur connecté
    private void chargerReservationsUtilisateur() {
        if (utilisateurConnecte != null) {
            // Votre logique pour récupérer et afficher les réservations
            // Par exemple :
            // List<Reservation> reservations = reservationService.getReservationsByUser(utilisateurConnecte.getId());
            // afficherReservations(reservations);
        }
    }

    // Getter pour accéder à l'utilisateur depuis d'autres méthodes
    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }
}