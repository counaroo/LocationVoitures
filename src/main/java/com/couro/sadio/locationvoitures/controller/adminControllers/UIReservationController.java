package com.couro.sadio.locationvoitures.controller.adminControllers;

import com.couro.sadio.locationvoitures.dao.impl.HibernateChauffeurDaoImpl;
import com.couro.sadio.locationvoitures.dao.impl.HibernateClientDaoImpl;
import com.couro.sadio.locationvoitures.dao.impl.HibernateReservationDaoImpl;
import com.couro.sadio.locationvoitures.dao.impl.HibernateVehiculeDaoImpl;
import com.couro.sadio.locationvoitures.entities.Chauffeur;
import com.couro.sadio.locationvoitures.entities.Client;
import com.couro.sadio.locationvoitures.entities.Reservation;
import com.couro.sadio.locationvoitures.entities.Vehicule;
import com.couro.sadio.locationvoitures.entities.StatutReservation;
import com.couro.sadio.locationvoitures.exception.DAOException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UIReservationController {

    @FXML
    private ComboBox<Client> clientComboBox;

    @FXML
    private ComboBox<Vehicule> vehiculeComboBox;

    @FXML
    private ComboBox<Chauffeur> chauffeurComboBox;

    @FXML
    private ComboBox<StatutReservation> statutComboBox;

    @FXML
    private DatePicker dateDebutPicker;

    @FXML
    private DatePicker dateFinPicker;

    private final HibernateClientDaoImpl clientDao = new HibernateClientDaoImpl(Client.class);
    private final HibernateVehiculeDaoImpl vehiculeDao = new HibernateVehiculeDaoImpl(Vehicule.class);
    private final HibernateChauffeurDaoImpl chauffeurDao = new HibernateChauffeurDaoImpl(Chauffeur.class);
    private final HibernateReservationDaoImpl reservationDao = new HibernateReservationDaoImpl(Reservation.class);

    @FXML
    public void initialize() {
        try {
            clientComboBox.setItems(FXCollections.observableArrayList(clientDao.findAll()));
            vehiculeComboBox.setItems(FXCollections.observableArrayList(vehiculeDao.findAll()));
            chauffeurComboBox.setItems(FXCollections.observableArrayList(chauffeurDao.findAll()));
        } catch (DAOException e) {
            showAlert("Erreur lors du chargement des données : " + e.getMessage());
            e.printStackTrace();
        }

        statutComboBox.setItems(FXCollections.observableArrayList(StatutReservation.values()));

        // Customisation de l'affichage des enums avec .getLibelle()
        statutComboBox.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(StatutReservation item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getLibelle());
            }
        });

        statutComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(StatutReservation item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getLibelle());
            }
        });
    }

    @FXML
    private void validerReservation() {
        Client client = clientComboBox.getValue();
        Vehicule vehicule = vehiculeComboBox.getValue();
        Chauffeur chauffeur = chauffeurComboBox.getValue();
        LocalDate debut = dateDebutPicker.getValue();
        LocalDate fin = dateFinPicker.getValue();
        StatutReservation statut = statutComboBox.getValue();

        if (client == null || vehicule == null || debut == null || fin == null || statut == null) {
            showAlert("Tous les champs obligatoires doivent être remplis !");
            return;
        }

        LocalDateTime dateDebut = debut.atStartOfDay();
        LocalDateTime dateFin = fin.atStartOfDay();

        Reservation r = new Reservation();
        r.setClient(client);
        r.setVehicule(vehicule);
        r.setChauffeur(chauffeur);
        r.setDateDebut(dateDebut);
        r.setDateFin(dateFin);
        r.setStatut(statut);

        try {
            reservationDao.create(r);
            showAlert("Réservation enregistrée avec succès !");
        } catch (DAOException e) {
            showAlert("Erreur lors de la sauvegarde : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
