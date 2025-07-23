package com.couro.sadio.locationvoitures.controller.clientController;

import com.couro.sadio.locationvoitures.entities.*;
import com.couro.sadio.locationvoitures.interfaces.ControlledScreen;
import com.couro.sadio.locationvoitures.modele.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.util.List;

public class ClientReservationController implements ControlledScreen {

    @FXML private ComboBox<Vehicule> vehiculeCombo;
    @FXML private DatePicker dateDebutPicker;
    @FXML private DatePicker dateFinPicker;
    @FXML private CheckBox avecChauffeurCheck;
    @FXML private ComboBox<Chauffeur> chauffeurCombo;
    @FXML private Label montantVehiculeLabel;
    @FXML private Label montantChauffeurLabel;
    @FXML private Label montantTotalLabel;
    @FXML private Button simulerReservationButton;
    @FXML private Button validerReservationButton;

    private final VehiculeModele vehiculeDao = new VehiculeModele();
    private final ChauffeurModele chauffeurDao = new ChauffeurModele();
    private final ReservationModele reservationDao = new ReservationModele();

    private Utilisateur userActuel;
    private Client clientActuel;

    @FXML
    public void initialize() {
        // Chargement des véhicules disponibles seulement
        List<Vehicule> vehicules = vehiculeDao.lister(); // ou vehiculeDao.lister() si pas de méthode listerDisponibles()
        vehiculeCombo.setItems(FXCollections.observableArrayList(vehicules));

        // Chargement des chauffeurs disponibles seulement
        List<Chauffeur> chauffeurs = chauffeurDao.lister(); // ou chauffeurDao.lister() si pas de méthode listerDisponibles()
        chauffeurCombo.setItems(FXCollections.observableArrayList(chauffeurs));

        // État initial
        chauffeurCombo.setDisable(true);
        montantVehiculeLabel.setText("0 FCFA");
        montantChauffeurLabel.setText("0 FCFA");
        montantTotalLabel.setText("0 FCFA");

        // Activation/désactivation chauffeur
        avecChauffeurCheck.selectedProperty().addListener((obs, oldVal, newVal) -> {
            chauffeurCombo.setDisable(!newVal);
            montantChauffeurLabel.setText(newVal ? "À calculer" : "0 FCFA");
        });

        // NE PAS METTRE LE NOM ICI - clientActuel est encore null
        // Le nom sera mis à jour dans setUserData()
    }

    @FXML
    public void simulerReservation() {
        Vehicule v = vehiculeCombo.getValue();
        Chauffeur c = chauffeurCombo.getValue();
        LocalDateTime debut = dateDebutPicker.getValue() != null ? dateDebutPicker.getValue().atStartOfDay() : null;
        LocalDateTime fin = dateFinPicker.getValue() != null ? dateFinPicker.getValue().atStartOfDay() : null;

        if (v == null || debut == null || fin == null || fin.isBefore(debut)) {
            showAlert("Erreur", "Veuillez remplir correctement tous les champs.");
            return;
        }

        int jours = (int) java.time.temporal.ChronoUnit.DAYS.between(debut, fin);
        if (jours == 0) {
            jours = 1; // Minimum 1 jour pour la location
        }

        double montantVehicule = v.getTarif() * jours;
        double montantChauffeur = (avecChauffeurCheck.isSelected() && c != null) ? c.getTarif() * jours : 0;

        montantVehiculeLabel.setText(montantVehicule + " FCFA");
        montantChauffeurLabel.setText(montantChauffeur + " FCFA");
        montantTotalLabel.setText((montantVehicule + montantChauffeur) + " FCFA");
    }

    @FXML
    public void validerReservation() {
        // Vérifier que le client est bien initialisé
        if (clientActuel == null) {
            showAlert("Erreur", "Erreur d'initialisation du client. Veuillez fermer et rouvrir la fenêtre.");
            return;
        }

        Vehicule v = vehiculeCombo.getValue();
        Chauffeur c = chauffeurCombo.getValue();
        LocalDateTime debut = dateDebutPicker.getValue() != null ? dateDebutPicker.getValue().atStartOfDay() : null;
        LocalDateTime fin = dateFinPicker.getValue() != null ? dateFinPicker.getValue().atStartOfDay() : null;

        // Validation des champs
        if (v == null || debut == null || fin == null || fin.isBefore(debut)) {
            showAlert("Erreur", "Champs invalides. Veuillez vérifier vos dates et votre sélection.");
            return;
        }

        // Vérification si un chauffeur est requis mais non sélectionné
        if (avecChauffeurCheck.isSelected() && c == null) {
            showAlert("Erreur", "Veuillez sélectionner un chauffeur.");
            return;
        }

        // Calcul du nombre de jours et des montants
        int jours = (int) java.time.temporal.ChronoUnit.DAYS.between(debut, fin);
        if (jours == 0) {
            jours = 1; // Minimum 1 jour pour la location
        }

        double montantVehicule = v.getTarif() * jours;
        double montantChauffeur = (avecChauffeurCheck.isSelected() && c != null) ? c.getTarif() * jours : 0;

        // Création de la réservation selon le constructeur approprié
        Reservation reservation;
        if (avecChauffeurCheck.isSelected() && c != null) {
            // Avec chauffeur
            reservation = new Reservation(clientActuel, v, LocalDateTime.now(), StatutReservation.EN_ATTENTE,
                    debut, fin, c, montantVehicule, montantChauffeur);
        } else {
            // Sans chauffeur
            reservation = new Reservation(clientActuel, v, LocalDateTime.now(), StatutReservation.EN_ATTENTE,
                    debut, fin, montantVehicule);
        }

        try {
            // Sauvegarde de la réservation
            reservationDao.create(reservation);

            // Mise à jour de la disponibilité du véhicule
            v.setDispo(false);
            vehiculeDao.update(v);

            // Mise à jour de la disponibilité du chauffeur si nécessaire
            if (avecChauffeurCheck.isSelected() && c != null) {
                c.setDispo(false);
                chauffeurDao.update(c);
            }

            // Création de la facture
            FactureModele factureModele = new FactureModele();
            Facture facture = new Facture(reservation, reservation.getMontantTotale(),
                    reservation.getDate(), StatutFacture.EN_ATTENTE);
            factureModele.create(facture);

            showAlert("Succès", "Votre réservation a été enregistrée avec succès !");
            resetForm();

        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite lors de l'enregistrement : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void resetForm() {
        vehiculeCombo.getSelectionModel().clearSelection();
        dateDebutPicker.setValue(null);
        dateFinPicker.setValue(null);
        avecChauffeurCheck.setSelected(false);
        chauffeurCombo.getSelectionModel().clearSelection();
        montantVehiculeLabel.setText("0 FCFA");
        montantChauffeurLabel.setText("0 FCFA");
        montantTotalLabel.setText("0 FCFA");
    }

    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void setUserData(Utilisateur user) {
        this.userActuel = user;

        // Solution 1 : Cast direct si l'utilisateur connecté EST un client
        if (user instanceof Client) {
            this.clientActuel = (Client) user;
        } else {
            // Solution 2 : Recherche en base si nécessaire
            ClientModele clientModele = new ClientModele();
            this.clientActuel = clientModele.getClientConnecteByUser(user);
        }
    }

    // Méthode utilitaire pour la compatibilité
    public void setClient(Utilisateur user) {
        setUserData(user);
    }
}