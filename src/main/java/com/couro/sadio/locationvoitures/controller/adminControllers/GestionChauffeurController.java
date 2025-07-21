package com.couro.sadio.locationvoitures.controller.adminControllers;

import com.couro.sadio.locationvoitures.dao.impl.HibernateChauffeurDaoImpl;
import com.couro.sadio.locationvoitures.entities.Chauffeur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class GestionChauffeurController {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField telephoneField;
    @FXML private CheckBox disponibiliteCheckBox;
    @FXML private Label lblStatut;
    @FXML private Label lblDerniereMaj;
    @FXML private TextField txtRecherche;

    @FXML private TableView<Chauffeur> tableViewChauffeurs;
    @FXML private TableColumn<Chauffeur, Integer> colId;
    @FXML private TableColumn<Chauffeur, String> colNom;
    @FXML private TableColumn<Chauffeur, String> colPrenom;
    @FXML private TableColumn<Chauffeur, String> colTelephone;
    @FXML private TableColumn<Chauffeur, Boolean> colDisponible;

    private final HibernateChauffeurDaoImpl chauffeurDao = new HibernateChauffeurDaoImpl(Chauffeur.class);
    private Chauffeur chauffeurSelectionne;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colDisponible.setCellValueFactory(new PropertyValueFactory<>("dispo"));

        tableViewChauffeurs.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            chauffeurSelectionne = newVal;
            if (newVal != null) afficherDetails(newVal);
        });

        chargerChauffeurs();
    }

    private void chargerChauffeurs() {
        List<Chauffeur> chauffeurs = chauffeurDao.findAll();
        tableViewChauffeurs.setItems(FXCollections.observableArrayList(chauffeurs));
        lblStatut.setText("Liste chargée.");
        lblDerniereMaj.setText("Dernière mise à jour: " + LocalDateTime.now().withNano(0));
    }

    private void afficherDetails(Chauffeur chauffeur) {
        nomField.setText(chauffeur.getNom());
        prenomField.setText(chauffeur.getPrenom());
        telephoneField.setText(String.valueOf(chauffeur.getTelephone()));
        disponibiliteCheckBox.setSelected(chauffeur.isDispo());
    }

    @FXML
    private void ajouterChauffeur(ActionEvent event) {
        try {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            int telephone = Integer.parseInt(telephoneField.getText());
            boolean dispo = disponibiliteCheckBox.isSelected();

            Chauffeur chauffeur = new Chauffeur(nom, prenom, telephone, dispo);
            chauffeurDao.save(chauffeur);
            chargerChauffeurs();
            clearForm();
            lblStatut.setText("Chauffeur ajouté.");
        } catch (NumberFormatException e) {
            showAlert("Erreur: numéro de téléphone invalide.");
        }
    }

    @FXML
    private void modifierChauffeur(ActionEvent event) {
        if (chauffeurSelectionne == null) {
            showAlert("Veuillez sélectionner un chauffeur à modifier.");
            return;
        }
        try {
            chauffeurSelectionne.setNom(nomField.getText());
            chauffeurSelectionne.setPrenom(prenomField.getText());
            chauffeurSelectionne.setTelephone(Integer.parseInt(telephoneField.getText()));
            chauffeurSelectionne.setDispo(disponibiliteCheckBox.isSelected());

            chauffeurDao.update(chauffeurSelectionne);
            chargerChauffeurs();
            clearForm();
            lblStatut.setText("Chauffeur modifié.");
        } catch (NumberFormatException e) {
            showAlert("Erreur: numéro de téléphone invalide.");
        }
    }

    @FXML
    private void supprimerChauffeur(ActionEvent event) {
        if (chauffeurSelectionne == null) {
            showAlert("Veuillez sélectionner un chauffeur à supprimer.");
            return;
        }

        chauffeurDao.delete(chauffeurSelectionne);
        chargerChauffeurs();
        clearForm();
        lblStatut.setText("Chauffeur supprimé.");
    }

    @FXML
    private void actualiserListe(ActionEvent event) {
        chargerChauffeurs();
    }

    @FXML
    private void rechercherChauffeur(ActionEvent event) {
        String keyword = txtRecherche.getText().toLowerCase();
        List<Chauffeur> tous = chauffeurDao.findAll();
        List<Chauffeur> filtrés = tous.stream().filter(c ->
                c.getNom().toLowerCase().contains(keyword) ||
                        c.getPrenom().toLowerCase().contains(keyword)
        ).collect(Collectors.toList());
        tableViewChauffeurs.setItems(FXCollections.observableArrayList(filtrés));
        lblStatut.setText("Résultat de la recherche: " + filtrés.size() + " trouvé(s).");
    }

    @FXML
    private void resetFilters(ActionEvent event) {
        txtRecherche.clear();
        chargerChauffeurs();
    }

    private void clearForm() {
        nomField.clear();
        prenomField.clear();
        telephoneField.clear();
        disponibiliteCheckBox.setSelected(false);
        chauffeurSelectionne = null;
        tableViewChauffeurs.getSelectionModel().clearSelection();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
