package com.couro.sadio.locationvoitures.controller.adminControllers;

import com.couro.sadio.locationvoitures.dao.impl.*;
import com.couro.sadio.locationvoitures.entities.*;
import com.couro.sadio.locationvoitures.modele.FactureModele;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.beans.property.SimpleIntegerProperty;


public class GestionReservationController {

    @FXML private ComboBox<Client> clientComboBox;
    @FXML private ComboBox<Vehicule> vehiculeComboBox;
    @FXML private ComboBox<Chauffeur> chauffeurComboBox;
    @FXML private ComboBox<StatutReservation> statutComboBox;
    @FXML private DatePicker dateDebutPicker;
    @FXML private DatePicker dateFinPicker;

    @FXML private TableView<Reservation> tableViewReservations;
    @FXML private TableColumn<Reservation, Integer> colId;
    @FXML private TableColumn<Reservation, String> colClient;
    @FXML private TableColumn<Reservation, String> colVehicule;
    @FXML private TableColumn<Reservation, String> colChauffeur;
    @FXML private TableColumn<Reservation, String> colDateDebut;
    @FXML private TableColumn<Reservation, String> colDateFin;
    @FXML private TableColumn<Reservation, String> colStatut;

    private final HibernateReservationDaoImpl reservationDao = new HibernateReservationDaoImpl(Reservation.class);
    private final HibernateClientDaoImpl clientDao = new HibernateClientDaoImpl(Client.class);
    private final HibernateVehiculeDaoImpl vehiculeDao = new HibernateVehiculeDaoImpl(Vehicule.class);
    private final HibernateChauffeurDaoImpl chauffeurDao = new HibernateChauffeurDaoImpl(Chauffeur.class);

    private Reservation reservationSelectionnee;

    @FXML
    public void initialize() {
        chargerComboBox();
        configurerColonnes();
        chargerReservations();
        tableViewReservations.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            reservationSelectionnee = newVal;
            if (newVal != null) afficherDetailsReservation(newVal);
        });
    }

    private void configurerColonnes() {
        colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colClient.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getClient().getNom()));
        colVehicule.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getVehicule().getMarque()));
        colChauffeur.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getChauffeur() != null ? data.getValue().getChauffeur().getNom() : "-"));
        colDateDebut.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDateDebut().toLocalDate().toString()));
        colDateFin.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDateFin().toLocalDate().toString()));
        colStatut.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStatut().getLibelle()));
    }

    private void chargerComboBox() {
        clientComboBox.setItems(FXCollections.observableArrayList(clientDao.findAll()));
        vehiculeComboBox.setItems(FXCollections.observableArrayList(vehiculeDao.findAll()));
        chauffeurComboBox.setItems(FXCollections.observableArrayList(chauffeurDao.findAll()));
        statutComboBox.setItems(FXCollections.observableArrayList(StatutReservation.values()));
    }

    private void chargerReservations() {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList(reservationDao.findAll());
        tableViewReservations.setItems(reservations);
    }

    @FXML
    private void ajouterReservation(ActionEvent event) {
        Client client = clientComboBox.getValue();
        Vehicule vehicule = vehiculeComboBox.getValue();
        Chauffeur chauffeur = chauffeurComboBox.getValue();
        StatutReservation statut = statutComboBox.getValue();
        LocalDate dateDebut = dateDebutPicker.getValue();
        LocalDate dateFin = dateFinPicker.getValue();

        if (client == null || vehicule == null || dateDebut == null || dateFin == null || statut == null) {
            showAlert("Tous les champs obligatoires doivent être remplis.");
            return;
        }

        Reservation nouvelleReservation = new Reservation();
        nouvelleReservation.setClient(client);
        nouvelleReservation.setVehicule(vehicule);
        nouvelleReservation.setDateDebut(dateDebut.atStartOfDay());
        nouvelleReservation.setDateFin(dateFin.atStartOfDay());
        nouvelleReservation.setDate(LocalDateTime.now());
        nouvelleReservation.setStatut(statut);

        if (chauffeur != null) {
            nouvelleReservation.setChauffeur(chauffeur);
            nouvelleReservation.setAvecChauffeur(true);
        }

        reservationDao.save(nouvelleReservation);
        showAlert("Réservation ajoutée avec succès.");
        viderFormulaire(null);
        chargerReservations();

        FactureModele factureModele = new FactureModele();
        Facture facture = new Facture(nouvelleReservation,nouvelleReservation.getMontantTotale(),nouvelleReservation.getDate(),StatutFacture.EN_ATTENTE);
        factureModele.create(facture);
    }

    @FXML
    private void modifierReservation(ActionEvent event) {
        if (reservationSelectionnee == null) {
            showAlert("Veuillez sélectionner une réservation à modifier.");
            return;
        }

        reservationSelectionnee.setClient(clientComboBox.getValue());
        reservationSelectionnee.setVehicule(vehiculeComboBox.getValue());
        reservationSelectionnee.setChauffeur(chauffeurComboBox.getValue());
        reservationSelectionnee.setStatut(statutComboBox.getValue());
        reservationSelectionnee.setDateDebut(dateDebutPicker.getValue().atStartOfDay());
        reservationSelectionnee.setDateFin(dateFinPicker.getValue().atStartOfDay());

        reservationDao.update(reservationSelectionnee);
        showAlert("Réservation modifiée.");
        viderFormulaire(null);
        chargerReservations();
    }

    @FXML
    private void supprimerReservation(ActionEvent event) {
        if (reservationSelectionnee == null) {
            showAlert("Aucune réservation sélectionnée.");
            return;
        }

        reservationDao.delete(reservationSelectionnee);
        showAlert("Réservation supprimée.");
        viderFormulaire(null);
        chargerReservations();
    }

    @FXML
    private void viderFormulaire(ActionEvent event) {
        clientComboBox.getSelectionModel().clearSelection();
        vehiculeComboBox.getSelectionModel().clearSelection();
        chauffeurComboBox.getSelectionModel().clearSelection();
        statutComboBox.getSelectionModel().clearSelection();
        dateDebutPicker.setValue(null);
        dateFinPicker.setValue(null);
        reservationSelectionnee = null;
    }

    private void afficherDetailsReservation(Reservation reservation) {
        clientComboBox.setValue(reservation.getClient());
        vehiculeComboBox.setValue(reservation.getVehicule());
        chauffeurComboBox.setValue(reservation.getChauffeur());
        statutComboBox.setValue(reservation.getStatut());
        dateDebutPicker.setValue(reservation.getDateDebut().toLocalDate());
        dateFinPicker.setValue(reservation.getDateFin().toLocalDate());
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
