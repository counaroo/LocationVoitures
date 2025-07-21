package com.couro.sadio.locationvoitures.controller.clientControllers;

import com.couro.sadio.locationvoitures.dao.impl.HibernateChauffeurDaoImpl;
import com.couro.sadio.locationvoitures.dao.impl.HibernateReservationDaoImpl;
import com.couro.sadio.locationvoitures.dao.impl.HibernateVehiculeDaoImpl;
import com.couro.sadio.locationvoitures.entities.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.util.List;

public class ClientReservationController {

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

    private final HibernateVehiculeDaoImpl vehiculeDao = new HibernateVehiculeDaoImpl(Vehicule.class);
    private final HibernateChauffeurDaoImpl chauffeurDao = new HibernateChauffeurDaoImpl(Chauffeur.class);
    private final HibernateReservationDaoImpl reservationDao = new HibernateReservationDaoImpl(Reservation.class);

    private Client clientActuel;

    public void setClient(Client client) {
        this.clientActuel = client;
    }

    @FXML
    public void initialize() {
        // Chargement des véhicules
        List<Vehicule> vehicules = vehiculeDao.findAll();
        vehiculeCombo.setItems(FXCollections.observableArrayList(vehicules));

        // Chargement des chauffeurs disponibles
        List<Chauffeur> chauffeurs = chauffeurDao.findAllDispos();
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
        double montantVehicule = v.getPrixParJour() * jours;
        double montantChauffeur = (avecChauffeurCheck.isSelected() && c != null) ? c.getPrixParJour() * jours : 0;

        montantVehiculeLabel.setText(montantVehicule + " FCFA");
        montantChauffeurLabel.setText(montantChauffeur + " FCFA");
        montantTotalLabel.setText((montantVehicule + montantChauffeur) + " FCFA");
    }

    @FXML
    public void validerReservation() {
        Vehicule v = vehiculeCombo.getValue();
        Chauffeur c = chauffeurCombo.getValue();
        LocalDateTime debut = dateDebutPicker.getValue() != null ? dateDebutPicker.getValue().atStartOfDay() : null;
        LocalDateTime fin = dateFinPicker.getValue() != null ? dateFinPicker.getValue().atStartOfDay() : null;

        if (v == null || debut == null || fin == null || fin.isBefore(debut)) {
            showAlert("Erreur", "Champs invalides. Veuillez vérifier vos dates et votre sélection.");
            return;
        }

        int jours = (int) java.time.temporal.ChronoUnit.DAYS.between(debut, fin);
        double montantVehicule = v.getPrixParJour() * jours;
        double montantChauffeur = (avecChauffeurCheck.isSelected() && c != null) ? c.getPrixParJour() * jours : 0;

        Reservation reservation = avecChauffeurCheck.isSelected()
                ? new Reservation(clientActuel, v, LocalDateTime.now(), StatutReservation.EN_ATTENTE, debut, fin, c, montantVehicule, montantChauffeur)
                : new Reservation(clientActuel, v, LocalDateTime.now(), StatutReservation.EN_ATTENTE, debut, fin, montantVehicule);

        reservationDao.save(reservation);

        showAlert("Succès", "Votre réservation a été enregistrée avec succès !");
        resetForm();
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
}
