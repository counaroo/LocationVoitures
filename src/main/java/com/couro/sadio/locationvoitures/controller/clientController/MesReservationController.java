package com.couro.sadio.locationvoitures.controller.clientController;

import com.couro.sadio.locationvoitures.interfaces.ControlledScreen; // Nouvelle interface
import com.couro.sadio.locationvoitures.entities.Client;
import com.couro.sadio.locationvoitures.entities.Reservation;
import com.couro.sadio.locationvoitures.entities.StatutReservation;
import com.couro.sadio.locationvoitures.entities.Utilisateur;
import com.couro.sadio.locationvoitures.modele.ReservationModele;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MesReservationController implements Initializable, ControlledScreen {

    // Variables pour stocker l'utilisateur connecté et le modèle
    private Utilisateur utilisateurConnecte;
    private ReservationModele reservationModele;
    private ObservableList<Reservation> reservationsData;

    // Éléments FXML - Labels
    @FXML private Label lblClientNom;
    @FXML private Label lblTotalReservations;
    @FXML private Label lblReservationsActives;
    @FXML private Label lblReservationsAnnulees;
    @FXML private Label lblDetailsReservation;
    @FXML private Label Nom;

    // Éléments FXML - TableView et colonnes
    @FXML private TableView<Reservation> tableReservations;
    @FXML private TableColumn<Reservation, Integer> colId;
    @FXML private TableColumn<Reservation, String> colVehicule;
    @FXML private TableColumn<Reservation, String> colDateReservation;
    @FXML private TableColumn<Reservation, String> colDateDebut;
    @FXML private TableColumn<Reservation, String> colDateFin;
    @FXML private TableColumn<Reservation, Integer> colNbrJours;
    @FXML private TableColumn<Reservation, String> colChauffeur;
    @FXML private TableColumn<Reservation, Double> colMontantTotal;
    @FXML private TableColumn<Reservation, String> colStatut;

    // Éléments FXML - Boutons
    @FXML private Button btnRafraichir;
    @FXML private Button btnVoirDetails;
    @FXML private Button btnAnnuler;
    @FXML private Button btnRetour;

    // Formatter pour les dates
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialiser le modèle
        reservationModele = new ReservationModele();
        reservationsData = FXCollections.observableArrayList();

        // Configurer les colonnes du tableau
        configurerColonnesTableau();

        // Configurer les listeners
        configurerListeners();
    }

    @Override
    public void setUserData(Utilisateur user) {
        this.utilisateurConnecte = user;

        if (user != null) {
            // Mettre à jour les labels avec les informations utilisateur
            lblClientNom.setText(user.getNom() + " " + user.getPrenom());


            // Charger les réservations de l'utilisateur
            chargerReservationsUtilisateur();
        }
    }

    // Reste du code identique...
    // (Toutes les autres méthodes restent inchangées)

    /**
     * Configure les colonnes du tableau
     */
    private void configurerColonnesTableau() {
        // Colonne ID
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        // Colonne Véhicule - affiche marque + modèle
        colVehicule.setCellValueFactory(cellData -> {
            if (cellData.getValue().getVehicule() != null) {
                return new SimpleStringProperty(
                        cellData.getValue().getVehicule().getMarque() + " " +
                                cellData.getValue().getVehicule().getModele()
                );
            }
            return new SimpleStringProperty("N/A");
        });

        // Colonne Date Réservation
        colDateReservation.setCellValueFactory(cellData -> {
            if (cellData.getValue().getDate() != null) {
                return new SimpleStringProperty(
                        cellData.getValue().getDate().format(dateTimeFormatter)
                );
            }
            return new SimpleStringProperty("N/A");
        });

        // Colonne Date Début
        colDateDebut.setCellValueFactory(cellData -> {
            if (cellData.getValue().getDateDebut() != null) {
                return new SimpleStringProperty(
                        cellData.getValue().getDateDebut().format(dateFormatter)
                );
            }
            return new SimpleStringProperty("N/A");
        });

        // Colonne Date Fin
        colDateFin.setCellValueFactory(cellData -> {
            if (cellData.getValue().getDateFin() != null) {
                return new SimpleStringProperty(
                        cellData.getValue().getDateFin().format(dateFormatter)
                );
            }
            return new SimpleStringProperty("N/A");
        });

        // Colonne Nombre de jours
        colNbrJours.setCellValueFactory(new PropertyValueFactory<>("nbrJour"));

        // Colonne Chauffeur
        colChauffeur.setCellValueFactory(cellData -> {
            if (cellData.getValue().isAvecChauffeur() && cellData.getValue().getChauffeur() != null) {
                return new SimpleStringProperty(
                        cellData.getValue().getChauffeur().getNom() + " " +
                                cellData.getValue().getChauffeur().getPrenom()
                );
            } else if (cellData.getValue().isAvecChauffeur()) {
                return new SimpleStringProperty("Avec chauffeur");
            }
            return new SimpleStringProperty("Sans chauffeur");
        });

        // Colonne Montant Total
        colMontantTotal.setCellValueFactory(new PropertyValueFactory<>("montantTotale"));
        colMontantTotal.setCellFactory(column -> new TableCell<Reservation, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f FCFA", item));
                }
            }
        });

        // Colonne Statut
        colStatut.setCellValueFactory(cellData -> {
            StatutReservation statut = cellData.getValue().isStatut();
            return new SimpleStringProperty(statut != null ? statut.getLibelle() : "N/A");
        });

        // Styliser la colonne statut avec des couleurs
        colStatut.setCellFactory(column -> new TableCell<Reservation, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    // Appliquer des couleurs selon le statut
                    switch (item) {
                        case "En attente":
                            setStyle("-fx-text-fill: #f39c12; -fx-font-weight: bold;");
                            break;
                        case "Confirmée":
                            setStyle("-fx-text-fill: #3498db; -fx-font-weight: bold;");
                            break;
                        case "En cours":
                            setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
                            break;
                        case "Terminee":
                            setStyle("-fx-text-fill: #95a5a6; -fx-font-weight: bold;");
                            break;
                        case "Annulee":
                            setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                            break;
                        default:
                            setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");
                    }
                }
            }
        });

        // Lier la liste observable au tableau
        tableReservations.setItems(reservationsData);
    }

    /**
     * Configure les listeners pour les interactions utilisateur
     */
    private void configurerListeners() {
        // Listener pour la sélection dans le tableau
        tableReservations.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    boolean reservationSelectionnee = newValue != null;

                    // Activer/désactiver les boutons selon la sélection
                    btnVoirDetails.setDisable(!reservationSelectionnee);
                    // Seules les réservations EN_ATTENTE et CONFIRMEE peuvent être annulées
                    boolean peutEtreAnnulee = reservationSelectionnee &&
                            (newValue.isStatut() == StatutReservation.EN_ATTENTE ||
                                    newValue.isStatut() == StatutReservation.CONFIRMEE);
                    btnAnnuler.setDisable(!peutEtreAnnulee);

                    // Mettre à jour le label des détails
                    if (reservationSelectionnee) {
                        lblDetailsReservation.setText(
                                String.format("Réservation #%d - %s du %s au %s",
                                        newValue.getId(),
                                        newValue.getVehicule().getMarque() + " " + newValue.getVehicule().getModele(),
                                        newValue.getDateDebut().format(dateFormatter),
                                        newValue.getDateFin().format(dateFormatter)
                                )
                        );
                    } else {
                        lblDetailsReservation.setText("Aucune réservation sélectionnée");
                    }
                }
        );
    }

    /**
     * Charge les réservations de l'utilisateur connecté
     */
    private void chargerReservationsUtilisateur() {
        if (utilisateurConnecte != null && utilisateurConnecte instanceof Client) {
            try {
                Client client = (Client) utilisateurConnecte;
                List<Reservation> reservations = reservationModele.findByClient(client);

                // Mettre à jour la liste observable
                reservationsData.clear();
                reservationsData.addAll(reservations);

                // Mettre à jour les statistiques
                mettreAJourStatistiques(reservations);

            } catch (Exception e) {
                afficherErreur("Erreur lors du chargement des réservations", e.getMessage());
            }
        }
    }

    /**
     * Met à jour les statistiques affichées
     */
    private void mettreAJourStatistiques(List<Reservation> reservations) {
        long totalReservations = reservations.size();

        // Compter les réservations actives (EN_ATTENTE, CONFIRMEE, EN_COURS)
        long reservationsActives = reservations.stream()
                .mapToLong(r -> {
                    StatutReservation statut = r.isStatut();
                    return (statut == StatutReservation.EN_ATTENTE ||
                            statut == StatutReservation.CONFIRMEE ||
                            statut == StatutReservation.EN_COURS) ? 1 : 0;
                })
                .sum();

        // Compter les réservations annulées
        long reservationsAnnulees = reservations.stream()
                .mapToLong(r -> r.isStatut() == StatutReservation.ANNULEE ? 1 : 0)
                .sum();

        lblTotalReservations.setText("Total réservations: " + totalReservations);
        lblReservationsActives.setText("Actives: " + reservationsActives);
        lblReservationsAnnulees.setText("Annulées: " + reservationsAnnulees);
    }

    /**
     * Rafraîchit la liste des réservations
     */
    @FXML
    public void rafraichirListe(ActionEvent event) {
        chargerReservationsUtilisateur();

        // Réinitialiser la sélection
        tableReservations.getSelectionModel().clearSelection();
        lblDetailsReservation.setText("Aucune réservation sélectionnée");

        afficherInformation("Actualisation", "Liste des réservations actualisée avec succès.");
    }

    /**
     * Annule la réservation sélectionnée
     */
    @FXML
    public void annulerReservation(ActionEvent event) {
        Reservation reservationSelectionnee = tableReservations.getSelectionModel().getSelectedItem();

        if (reservationSelectionnee == null) {
            afficherAvertissement("Aucune sélection", "Veuillez sélectionner une réservation à annuler.");
            return;
        }

        StatutReservation statutActuel = reservationSelectionnee.isStatut();

        // Vérifier si la réservation peut être annulée
        if (statutActuel == StatutReservation.ANNULEE) {
            afficherAvertissement("Réservation déjà annulée", "Cette réservation est déjà annulée.");
            return;
        }

        if (statutActuel != StatutReservation.EN_ATTENTE && statutActuel != StatutReservation.CONFIRMEE) {
            afficherAvertissement("Annulation impossible",
                    "Seules les réservations en attente ou confirmées peuvent être annulées.\n" +
                            "Statut actuel: " + statutActuel.getLibelle());
            return;
        }

        // Demander confirmation
        Optional<ButtonType> result = afficherConfirmation(
                "Confirmation d'annulation",
                String.format("Êtes-vous sûr de vouloir annuler la réservation #%d ?\n\n" +
                                "Véhicule: %s\nPériode: %s - %s\nStatut actuel: %s\nMontant: %.2f FCFA",
                        reservationSelectionnee.getId(),
                        reservationSelectionnee.getVehicule().getMarque() + " " + reservationSelectionnee.getVehicule().getModele(),
                        reservationSelectionnee.getDateDebut().format(dateFormatter),
                        reservationSelectionnee.getDateFin().format(dateFormatter),
                        statutActuel.getLibelle(),
                        reservationSelectionnee.getMontantTotale())
        );

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Mettre à jour le statut vers ANNULEE
                reservationSelectionnee.setStatut(StatutReservation.ANNULEE);

                // Sauvegarder en base de données
                reservationModele.update(reservationSelectionnee);

                // Rafraîchir la liste
                rafraichirListe(event);

                afficherInformation("Annulation réussie",
                        "La réservation #" + reservationSelectionnee.getId() + " a été annulée avec succès.");

            } catch (Exception e) {
                afficherErreur("Erreur lors de l'annulation",
                        "Impossible d'annuler la réservation: " + e.getMessage());
            }
        }
    }


    @FXML
    public void voirDetails(ActionEvent event) {
        // TODO: Implémenter la popup de détails
        System.out.println("Fonction voir détails à implémenter");
    }

    // Méthodes utilitaires pour les dialogues

    private void afficherInformation(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherAvertissement(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Optional<ButtonType> afficherConfirmation(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }

    // Getters
    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }
}