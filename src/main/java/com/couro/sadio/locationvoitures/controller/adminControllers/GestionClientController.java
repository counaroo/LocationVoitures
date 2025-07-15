package com.couro.sadio.locationvoitures.controller.adminControllers;

import com.couro.sadio.locationvoitures.entities.Client;
import com.couro.sadio.locationvoitures.modele.ClientModele;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GestionClientController implements Initializable {

    // Éléments de recherche
    @FXML
    private TextField txtRecherche;
    @FXML
    private Button btnRechercher;
    @FXML
    private Button btnReset;

    // Boutons d'actions
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnActualiser;

    // Tableau des clients
    @FXML
    private TableView<Client> tableClients;
    @FXML
    private TableColumn<Client, Integer> colId;
    @FXML
    private TableColumn<Client, String> colNom;
    @FXML
    private TableColumn<Client, String> colPrenom;
    @FXML
    private TableColumn<Client, String> colEmail;
    @FXML
    private TableColumn<Client, Integer> colTelephone;
    @FXML
    private TableColumn<Client, String> colAdresse;

    // Labels de statistiques
    @FXML
    private Label lblTotalClients;
    @FXML
    private Label lblClientsActifs;
    @FXML
    private Label lblPointsMoyens;
    @FXML
    private Label lblPointsTotal;

    // Actions rapides
    @FXML
    private Button btnTopClients;
    @FXML
    private Button btnGererFidelite;

    // Barre de statut
    @FXML private Label lblStatut;
    @FXML private Label lblDerniereMaj;

    // Modèle et données
    private ClientModele clientModele;
    private ObservableList<Client> clientsData;
    private FilteredList<Client> filteredClients;
    private SortedList<Client> sortedClients;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientModele = new ClientModele();
        clientsData = FXCollections.observableArrayList();

        initialiserTableau();
        initialiserFiltres();
        initialiserEcouteurs();
        chargerDonnees();
        mettreAJourStatut("Interface initialisée");
    }

    /**
     * Initialise les colonnes du tableau
     */
    private void initialiserTableau() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));

        // Configuration de l'alignement des colonnes
        colId.setStyle("-fx-alignment: CENTER;");
        colTelephone.setStyle("-fx-alignment: CENTER;");

        // Permettre la sélection multiple
        tableClients.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * Initialise les filtres de recherche
     */
    private void initialiserFiltres() {
        filteredClients = new FilteredList<>(clientsData, p -> true);
        sortedClients = new SortedList<>(filteredClients);
        sortedClients.comparatorProperty().bind(tableClients.comparatorProperty());
        tableClients.setItems(sortedClients);
    }

    /**
     * Initialise les écouteurs d'événements
     */
    private void initialiserEcouteurs() {
        // Écouteur de sélection dans le tableau
        tableClients.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean clientSelectionne = newSelection != null;
            btnModifier.setDisable(!clientSelectionne);
            btnSupprimer.setDisable(!clientSelectionne);
        });

        // Écouteur pour la recherche en temps réel
        txtRecherche.textProperty().addListener((obs, oldValue, newValue) -> appliquerFiltres());
    }

    /**
     * Charge les données depuis la base
     */
    private void chargerDonnees() {
        try {
            List<Client> clients = clientModele.lister();
            if (clients != null) {
                clientsData.clear();
                clientsData.addAll(clients);
                mettreAJourStatistiques();
                mettreAJourStatut("Données chargées avec succès - " + clients.size() + " clients");
            }
        } catch (Exception e) {
            mettreAJourStatut("Erreur lors du chargement des données");
            //afficherAlerte("Erreur", "Impossible de charger les données: " + e.getMessage());
        }
        mettreAJourDerniereMaj();
    }

    /**
     * Applique les filtres de recherche
     */
    private void appliquerFiltres() {
        filteredClients.setPredicate(client -> {
            String rechercheText = txtRecherche.getText().toLowerCase().trim();
            if (rechercheText.isEmpty()) {
                return true;
            }

            String nomComplet = (client.getNom() + " " + client.getPrenom()).toLowerCase();
            return nomComplet.contains(rechercheText);
        });

        mettreAJourStatut("Filtres appliqués - " + filteredClients.size() + " clients affichés");
    }

    /**
     * Met à jour les statistiques affichées
     */
    private void mettreAJourStatistiques() {
        int totalClients = clientsData.size();
        int clientsActifs = (int) clientsData.stream()
                .filter(c -> c.getReservations() != null && !c.getReservations().isEmpty())
                .count();
        int pointsTotal = clientsData.stream().mapToInt(Client::getPointsFidelite).sum();
        int pointsMoyens = totalClients > 0 ? pointsTotal / totalClients : 0;

        lblTotalClients.setText("Total clients: " + totalClients);
        lblClientsActifs.setText("Clients actifs: " + clientsActifs);
        lblPointsMoyens.setText("Points moyens: " + pointsMoyens);
        lblPointsTotal.setText("Points total: " + pointsTotal);
    }

    /**
     * Met à jour le statut
     */
    private void mettreAJourStatut(String message) {
        lblStatut.setText(message);
    }

    /**
     * Met à jour l'heure de dernière mise à jour
     */
    private void mettreAJourDerniereMaj() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        lblDerniereMaj.setText("Dernière mise à jour: " + now.format(formatter));
    }

    // ==================== ACTIONS DU CONTRÔLEUR ====================

    @FXML
    private void rechercherClient() {
        appliquerFiltres();
    }

    @FXML
    private void resetFilters() {
        txtRecherche.clear();
        mettreAJourStatut("Filtres réinitialisés");
    }

    @FXML
    private void ajouterClient() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/couro/sadio/view/AdminWindows/FormClient.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Ajout Client");
            FormClientController controller = loader.getController();
            controller.setTitleForm("Formulaire d'ajout");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Actualiser la liste après fermeture (au cas où un client aurait été ajouté)
            chargerDonnees();
            mettreAJourStatut("Fenêtre d'ajout de client fermée");

        } catch (Exception e) {
            showError("Erreur lors du chargement de l'interface: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de connexion");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void modifierClient() {
        Client clientSelectionne = tableClients.getSelectionModel().getSelectedItem();
        if (clientSelectionne != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/couro/sadio/view/AdminWindows/FormClient.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Modifier Client");

                // Récupération du contrôleur et configuration
                FormClientController controller = loader.getController();
                controller.setTitleForm("Formulaire de modification");
                controller.setClientAModifier(clientSelectionne); // Passer le client à modifier

                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();

                // Actualiser la liste après fermeture
                chargerDonnees();
                mettreAJourStatut("Client modifié avec succès");

            } catch (Exception e) {
                showError("Erreur lors du chargement de l'interface: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            afficherInfo("Information", "Veuillez sélectionner un client à modifier");
        }
    }

    @FXML
    private void supprimerClient() {
        Client clientSelectionne = tableClients.getSelectionModel().getSelectedItem();
        if (clientSelectionne != null) {
            Optional<ButtonType> result = afficherConfirmation(
                    "Confirmer la suppression",
                    "Êtes-vous sûr de vouloir supprimer le client " +
                            clientSelectionne.getNom() + " " + clientSelectionne.getPrenom() + " ?",
                    "Cette action est irréversible."
            );

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    clientModele.delete(clientSelectionne.getId());
                    clientsData.remove(clientSelectionne);
                    mettreAJourStatistiques();
                    mettreAJourStatut("Client supprimé avec succès");
                } catch (Exception e) {
                    afficherAlerte("Erreur", "Impossible de supprimer le client: " + e.getMessage());
                }
            }
        }
    }

    @FXML
    private void actualiserListe() {
        chargerDonnees();
    }

    @FXML
    private void afficherTopClients() {
        try {
            List<Client> topClients = clientModele.getTopClientsFideles(10);
            if (topClients != null && !topClients.isEmpty()) {
                StringBuilder message = new StringBuilder("Top 10 clients par points de fidélité:\n\n");
                for (int i = 0; i < topClients.size(); i++) {
                    Client client = topClients.get(i);
                    message.append(String.format("%d. %s %s - %d points\n",
                            i + 1, client.getNom(), client.getPrenom(), client.getPointsFidelite()));
                }
                afficherInfo("Top Clients", message.toString());
            } else {
                afficherInfo("Information", "Aucun client trouvé");
            }
        } catch (Exception e) {
            afficherAlerte("Erreur", "Impossible de récupérer les top clients: " + e.getMessage());
        }
        mettreAJourStatut("Top clients affichés");
    }

    @FXML
    private void gererPointsFidelite() {
        Client clientSelectionne = tableClients.getSelectionModel().getSelectedItem();
        if (clientSelectionne != null) {
            // TODO: Ouvrir une interface pour gérer les points de fidélité
            mettreAJourStatut("Fonction de gestion des points de fidélité à implémenter");
            afficherInfo("Information", "Fonctionnalité de gestion des points de fidélité à implémenter\n" +
                    "Client: " + clientSelectionne.getNom() + " " + clientSelectionne.getPrenom() + "\n" +
                    "Points actuels: " + clientSelectionne.getPointsFidelite());
        } else {
            afficherInfo("Information", "Veuillez sélectionner un client pour gérer ses points de fidélité");
        }
    }

    // ==================== MÉTHODES UTILITAIRES ====================

    /**
     * Affiche une alerte d'erreur
     */
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Affiche une alerte de confirmation
     */
    private Optional<ButtonType> afficherConfirmation(String titre, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titre);
        alert.setHeaderText(header);
        alert.setContentText(message);
        return alert.showAndWait();
    }

    /**
     * Affiche une alerte d'information
     */
    private void afficherInfo(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}