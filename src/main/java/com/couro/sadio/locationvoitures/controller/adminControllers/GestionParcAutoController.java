package com.couro.sadio.locationvoitures.controller.adminControllers;

import com.couro.sadio.locationvoitures.entities.Vehicule;
import com.couro.sadio.locationvoitures.modele.VehiculeModele;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GestionParcAutoController implements Initializable {

    // Éléments du formulaire
    @FXML private TextField marqueField;
    @FXML private TextField modeleField;
    @FXML private TextField tarifField;
    @FXML private TextField immatriculationField;
    @FXML private CheckBox dispoCheckBox;

    // Boutons d'action
    @FXML private Button ajouterBtn;
    @FXML private Button modifierBtn;
    @FXML private Button annulerBtn;
    @FXML private Button supprimerBtn;
    @FXML private Button modifierSelectionBtn;
    @FXML private Button rechercherBtn;
    @FXML private Button actualiserBtn;

    // Recherche
    @FXML private TextField rechercheField;

    // Tableau
    @FXML private TableView<Vehicule> vehiculesTable;
    @FXML private TableColumn<Vehicule, Integer> idColumn;
    @FXML private TableColumn<Vehicule, String> marqueColumn;
    @FXML private TableColumn<Vehicule, String> modeleColumn;
    @FXML private TableColumn<Vehicule, String> tarifColumn;
    @FXML private TableColumn<Vehicule, String> immatriculationColumn;
    @FXML private TableColumn<Vehicule, Boolean> dispoColumn;

    // Status
    @FXML private Label statusLabel;

    // Données
    private VehiculeModele vehiculeModele = new VehiculeModele();
    private ObservableList<Vehicule> vehiculesList = FXCollections.observableArrayList();
    private FilteredList<Vehicule> filteredData;
    private Vehicule vehiculeEnModification = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTable();
        setupSearch();
        chargerVehicules();
        configurerBoutons();
    }

    /**
     * Configuration du tableau
     */
    private void setupTable() {
        // Configuration des colonnes
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        marqueColumn.setCellValueFactory(new PropertyValueFactory<>("marque"));
        modeleColumn.setCellValueFactory(new PropertyValueFactory<>("modele"));
        tarifColumn.setCellValueFactory(new PropertyValueFactory<>("tarif"));
        immatriculationColumn.setCellValueFactory(new PropertyValueFactory<>("immatriculation"));
        dispoColumn.setCellValueFactory(new PropertyValueFactory<>("dispo"));

        // Personnalisation de la colonne disponibilité
        dispoColumn.setCellFactory(column -> new TableCell<Vehicule, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Disponible" : "Non disponible");
                    setTextFill(item ? Color.GREEN : Color.RED);
                }
            }
        });

        // Liaison des données
        vehiculesTable.setItems(vehiculesList);

        // Gestion de la sélection
        vehiculesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    boolean hasSelection = newValue != null;
                    supprimerBtn.setDisable(!hasSelection);
                    modifierSelectionBtn.setDisable(!hasSelection);
                }
        );
    }

    /**
     * Configuration de la recherche
     */
    private void setupSearch() {
        filteredData = new FilteredList<>(vehiculesList, p -> true);

        // Listener pour la recherche en temps réel
        rechercheField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(vehicule -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return vehicule.getMarque().toLowerCase().contains(lowerCaseFilter) ||
                        vehicule.getModele().toLowerCase().contains(lowerCaseFilter) ||
                        vehicule.getImmatriculation().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<Vehicule> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(vehiculesTable.comparatorProperty());
        vehiculesTable.setItems(sortedData);
    }

    /**
     * Configuration des boutons
     */
    private void configurerBoutons() {
        supprimerBtn.setDisable(true);
        modifierSelectionBtn.setDisable(true);
        modifierBtn.setDisable(true);
    }

    /**
     * Charger les véhicules depuis la base de données
     */
    private void chargerVehicules() {
        try {
            // TODO: Remplacer par l'appel à votre service/DAO
            List<Vehicule> vehicules = obtenirVehiculesDepuisDB();
            vehiculesList.clear();
            vehiculesList.addAll(vehicules);
            updateStatus("Véhicules chargés avec succès", false);
        } catch (Exception e) {
            updateStatus("Erreur lors du chargement des véhicules: " + e.getMessage(), true);
        }
    }

    /**
     * Ajouter un nouveau véhicule
     */
    @FXML
    private void ajouterVehicule() {
        if (!validerFormulaire()) return;

        try {
            Vehicule nouveauVehicule = new Vehicule(
                    marqueField.getText().trim(),
                    modeleField.getText().trim(),
                    Integer.parseInt(tarifField.getText().trim()),
                    dispoCheckBox.isSelected(),
                    immatriculationField.getText().trim()
            );

            vehiculeModele.create(nouveauVehicule);

            vehiculesList.add(nouveauVehicule);
            viderFormulaire();
            updateStatus("Véhicule ajouté avec succès", false);

        } catch (Exception e) {
            updateStatus("Erreur lors de l'ajout: " + e.getMessage(), true);
        }
    }

    /**
     * Modifier un véhicule existant
     */
    @FXML
    private void modifierVehicule() {
        if (vehiculeEnModification == null || !validerFormulaire()) return;

        try {
            vehiculeEnModification.setMarque(marqueField.getText().trim());
            vehiculeEnModification.setModele(modeleField.getText().trim());
            vehiculeEnModification.setTarif(Integer.parseInt(tarifField.getText().trim()));
            vehiculeEnModification.setDispo(dispoCheckBox.isSelected());
            vehiculeEnModification.setImmatriculation(immatriculationField.getText().trim());

            vehiculeModele.update(vehiculeEnModification);

            vehiculesTable.refresh();
            viderFormulaire();
            vehiculeEnModification = null;
            configurerBoutons();

            updateStatus("Véhicule modifié avec succès", false);

        } catch (Exception e) {
            updateStatus("Erreur lors de la modification: " + e.getMessage(), true);
        }
    }

    /**
     * Supprimer un véhicule
     */
    @FXML
    private void supprimerVehicule() {
        Vehicule vehiculeSelectionne = vehiculesTable.getSelectionModel().getSelectedItem();
        if (vehiculeSelectionne == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer le véhicule");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce véhicule?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    vehiculeModele.delete(vehiculeSelectionne.getId());

                    vehiculesList.remove(vehiculeSelectionne);
                    updateStatus("Véhicule supprimé avec succès", false);

                } catch (Exception e) {
                    updateStatus("Erreur lors de la suppression: " + e.getMessage(), true);
                }
            }
        });
    }

    /**
     * Charger les données du véhicule sélectionné dans le formulaire
     */
    @FXML
    private void modifierSelection() {
        Vehicule vehiculeSelectionne = vehiculesTable.getSelectionModel().getSelectedItem();
        if (vehiculeSelectionne == null) return;

        vehiculeEnModification = vehiculeSelectionne;

        marqueField.setText(vehiculeSelectionne.getMarque());
        modeleField.setText(vehiculeSelectionne.getModele());
        tarifField.setText(String.valueOf(vehiculeSelectionne.getTarif()));
        immatriculationField.setText(vehiculeSelectionne.getImmatriculation());
        dispoCheckBox.setSelected(vehiculeSelectionne.getDispo());

        ajouterBtn.setDisable(true);
        modifierBtn.setDisable(false);

        updateStatus("Véhicule chargé pour modification", false);
    }

    /**
     * Rechercher des véhicules
     */
    @FXML
    private void rechercherVehicule() {
        // La recherche se fait automatiquement via le listener
        updateStatus("Recherche effectuée", false);
    }

    /**
     * Actualiser la liste
     */
    @FXML
    private void actualiserListe() {
        chargerVehicules();
        rechercheField.clear();
    }

    /**
     * Annuler l'action en cours
     */
    @FXML
    private void annulerAction() {
        viderFormulaire();
        vehiculeEnModification = null;
        configurerBoutons();
        updateStatus("Action annulée", false);
    }

    /**
     * Valider le formulaire
     */
    private boolean validerFormulaire() {
        if (marqueField.getText().trim().isEmpty()) {
            updateStatus("Veuillez saisir la marque", true);
            marqueField.requestFocus();
            return false;
        }

        if (modeleField.getText().trim().isEmpty()) {
            updateStatus("Veuillez saisir le modèle", true);
            modeleField.requestFocus();
            return false;
        }

        if (tarifField.getText().trim().isEmpty()) {
            updateStatus("Veuillez saisir le tarif", true);
            tarifField.requestFocus();
            return false;
        }

        if (immatriculationField.getText().trim().isEmpty()) {
            updateStatus("Veuillez saisir l'immatriculation", true);
            immatriculationField.requestFocus();
            return false;
        }

        // Vérifier si l'immatriculation existe déjà
        String immatriculation = immatriculationField.getText().trim();
        boolean existe = vehiculesList.stream()
                .anyMatch(v -> v.getImmatriculation().equals(immatriculation) &&
                        (vehiculeEnModification == null || v.getId() != vehiculeEnModification.getId()));

        if (existe) {
            updateStatus("Cette immatriculation existe déjà", true);
            immatriculationField.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Vider le formulaire
     */
    private void viderFormulaire() {
        marqueField.clear();
        modeleField.clear();
        tarifField.clear();
        immatriculationField.clear();
        dispoCheckBox.setSelected(true);

        ajouterBtn.setDisable(false);
        modifierBtn.setDisable(true);
    }

    /**
     * Mettre à jour le statut
     */
    private void updateStatus(String message, boolean isError) {
        statusLabel.setText(message);
        statusLabel.setTextFill(isError ? Color.RED : Color.GREEN);
    }

    private List<Vehicule> obtenirVehiculesDepuisDB() {
        // Exemple de données fictives - remplacer par votre DAO
        List<Vehicule> vehicules = new ArrayList<>();
        vehicules = vehiculeModele.lister();
        return vehicules;
    }

}