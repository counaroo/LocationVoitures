package com.couro.sadio.locationvoitures.controller.clientController;

import com.couro.sadio.locationvoitures.dao.impl.HibernateFactureDaoImpl;
import com.couro.sadio.locationvoitures.entities.*;
import com.couro.sadio.locationvoitures.interfaces.ControlledScreen;
import com.couro.sadio.locationvoitures.modele.ClientModele;
import com.couro.sadio.locationvoitures.modele.FactureModele;
import com.couro.sadio.locationvoitures.modele.ReservationModele;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MesFacturesController implements ControlledScreen {

    @FXML private TableView<Facture> factureTable;
    @FXML private TableColumn<Facture, Integer> colId;
    @FXML private TableColumn<Facture, Double> colMontant;
    @FXML private TableColumn<Facture, Integer> colReservation;
    @FXML private TableColumn<Facture, String> colStatut;
    @FXML private TableColumn<Facture, String> colDate;

    private final FactureModele factureDao = new FactureModele();

    private Utilisateur clientActuel;

    private Facture factureSelectionne;

    // Méthode pour définir le client actuel
    public void setClient(Client client) {
        this.clientActuel = client;
        chargerFacturesClient();
    }

    @FXML
    public void initialize() {
        // Configuration des colonnes du TableView
        configureColumns();

        // Chargement initial (sera mis à jour quand le client sera défini)
        chargerFacturesClient();
        factureTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            factureSelectionne = newVal;
        });
    }

    private void configureColumns() {
        // Colonne ID Facture
        colId.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getId()).asObject());

        // Colonne Montant
        colMontant.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getMontant()).asObject());

        // Format du montant avec FCFA
        colMontant.setCellFactory(column -> new TableCell<Facture, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.0f FCFA", item));
                }
            }
        });

        // Colonne ID Réservation
        colReservation.setCellValueFactory(cellData -> {
            Reservation reservation = cellData.getValue().getReservation();
            return new SimpleIntegerProperty(reservation != null ? reservation.getId() : 0).asObject();
        });

        // Colonne Statut de la réservation (pas de la facture)
        colStatut.setCellValueFactory(cellData -> {
            Reservation reservation = cellData.getValue().getReservation();
            String statut = reservation != null && reservation.getStatut() != null
                    ? reservation.getStatut().toString()
                    : "INCONNU";
            return new SimpleStringProperty(statut);
        });

        colDate.setCellValueFactory(cellData -> {
            LocalDateTime dateFacture = cellData.getValue().getDateFacture();
            if (dateFacture != null) {
                // Format de la date : dd/MM/yyyy HH:mm
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                return new SimpleStringProperty(dateFacture.format(formatter));
            }
            return new SimpleStringProperty("N/A");
        });
    }

    private void chargerFactures() {
        try {
            List<Facture> factures = factureDao.lister();
            ObservableList<Facture> factureList = FXCollections.observableArrayList(factures);
            factureTable.setItems(factureList);
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors du chargement des factures : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void chargerFacturesClient() {
        if (clientActuel != null) {
            try {
                // Récupération des factures du client spécifique
                List<Facture> facturesClient = factureDao.findFacturesByClient(clientActuel.getId());
                ObservableList<Facture> factureList = FXCollections.observableArrayList(facturesClient);
                factureTable.setItems(factureList);
            } catch (Exception e) {
                showAlert("Erreur", "Erreur lors du chargement de vos factures : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Méthode utilitaire pour afficher les alertes
    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour actualiser les données
    @FXML
    public void actualiserFactures() {
        if (clientActuel != null) {
            chargerFacturesClient();
        } else {
            chargerFactures();
        }
    }

    @Override
    public void setUserData(Utilisateur user) {

        if (user != null) {
            // ✅ Récupérer directement le client correspondant
            ClientModele clientModele = new ClientModele();
            this.clientActuel = clientModele.getClientConnecteByUser(user);

            // Charger les factures du client
            chargerFacturesClient();
        }
    }

    public void payerFacture(ActionEvent event) {
        if (factureSelectionne == null) {
            showAlert("Veuillez sélectionner une facture à payer.");
            return;
        }

        ClientModele clientModele = new ClientModele();
        Client client = clientModele.getClientConnecteByUser(clientActuel);

        if (client.getPortefeuille() < factureSelectionne.getMontant()) {
            showAlert("Vous n'avez pas le solde nécessaire");
        } else {
            try {
                // Mise à jour du statut de la facture
                factureSelectionne.setStatutFacture(StatutFacture.PAYEE);

                // Sauvegarder la facture mise à jour
                FactureModele factureModele = new FactureModele();
                factureModele.update(factureSelectionne);

                // Déduire le montant du portefeuille du client
                client.setPortefeuille(client.getPortefeuille() - factureSelectionne.getMontant());
                clientModele.update(client);

                // Mettre à jour le statut de la réservation si elle existe
                if (factureSelectionne.getReservation() != null) {
                    ReservationModele reservationModele = new ReservationModele();
                    Reservation reservation = reservationModele.read(factureSelectionne.getReservation().getId());
                    reservation.setStatut(StatutReservation.CONFIRMEE);
                    reservationModele.update(reservation); // ⚠️ AJOUT IMPORTANT : sauvegarder la réservation
                }

                // Actualiser l'affichage
                chargerFacturesClient();

                showAlert("Paiement effectué avec succès !");

            } catch (Exception e) {
                showAlert("Erreur lors du paiement : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void annulerFacture(ActionEvent event) {
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}