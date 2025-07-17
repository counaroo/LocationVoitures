package com.couro.sadio.locationvoitures.controller.adminControllers;

import com.couro.sadio.locationvoitures.entities.Employe;
import com.couro.sadio.locationvoitures.entities.Role;
import com.couro.sadio.locationvoitures.modele.EmployeModele;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class GestionEmployeController implements Initializable {

    // Champs du formulaire
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField telephoneField;
    @FXML private ComboBox<Role> roleComboBox;
    @FXML private TextField loginField;
    @FXML private PasswordField motDePasseField;
    @FXML private TextField numeroEmployeField;

    // Boutons
    @FXML private Button ajouterBtn;
    @FXML private Button modifierBtn;
    @FXML private Button supprimerBtn;
    @FXML private Button viderBtn;
    @FXML private Button actualiserBtn;

    // Recherche
    @FXML private TextField rechercheField;

    // Tableau
    @FXML private TableView<Employe> tableViewEmployes;
    @FXML private TableColumn<Employe, Integer> colId;
    @FXML private TableColumn<Employe, String> colNumeroEmploye;
    @FXML private TableColumn<Employe, String> colNom;
    @FXML private TableColumn<Employe, String> colPrenom;
    @FXML private TableColumn<Employe, Integer> colTelephone;
    @FXML private TableColumn<Employe, Role> colRole;
    @FXML private TableColumn<Employe, String> colLogin;

    // Messages
    @FXML private Label messageLabel;

    // Service
    private EmployeModele employeModele;

    // Liste des employés
    private ObservableList<Employe> listeEmployes;
    private ObservableList<Employe> listeEmployesFiltree;

    // Employé sélectionné
    private Employe employeSelectionne;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialisation du service
        employeModele = new EmployeModele();

        // Initialisation des listes
        listeEmployes = FXCollections.observableArrayList();
        listeEmployesFiltree = FXCollections.observableArrayList();

        // Configuration des colonnes du tableau
        configurerTableau();

        // Remplir la ComboBox des rôles
        remplirComboBoxRoles();

        // Charger les données initiales
        chargerEmployes();

        // Configurer les événements
        configurerEvenements();

        // Désactiver le bouton modifier au début
        modifierBtn.setDisable(true);
        supprimerBtn.setDisable(true);

        // Générer automatiquement le numéro d'employé
        genererNumeroEmploye();
    }

    private void configurerTableau() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNumeroEmploye.setCellValueFactory(new PropertyValueFactory<>("numeroEmploye"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));

        // Lier la liste filtrée au tableau
        tableViewEmployes.setItems(listeEmployesFiltree);
    }

    private void remplirComboBoxRoles() {
        roleComboBox.getItems().addAll(Role.values());
        roleComboBox.setValue(Role.EMPLOYE); // Valeur par défaut
    }

    private void configurerEvenements() {
        // Événement de sélection dans le tableau
        tableViewEmployes.setOnMouseClicked(this::selectionnerEmploye);

        // Événement de recherche
        rechercheField.setOnKeyReleased(this::rechercherEmploye);
    }

    private void chargerEmployes() {
        try {
            List<Employe> employes = employeModele.lister();
            listeEmployes.clear();
            listeEmployes.addAll(employes);

            listeEmployesFiltree.clear();
            listeEmployesFiltree.addAll(employes);

            afficherMessage("Employés chargés avec succès.", "success");
        } catch (Exception e) {
            afficherMessage("Erreur lors du chargement des employés: " + e.getMessage(), "error");
        }
    }

    @FXML
    private void ajouterEmploye() {
        try {
            if (!validerFormulaire()) {
                return;
            }

            // Vérifier si le login existe déjà
            if (employeModele.loginExiste(loginField.getText())) {
                afficherMessage("Ce login existe déjà.", "error");
                return;
            }

            // Créer le nouvel employé
            Employe nouvelEmploye = new Employe(
                    nomField.getText().trim(),
                    prenomField.getText().trim(),
                    Integer.parseInt(telephoneField.getText().trim()),
                    roleComboBox.getValue(),
                    loginField.getText().trim(),
                    motDePasseField.getText(),
                    numeroEmployeField.getText().trim()
            );

            // Sauvegarder
            employeModele.create(nouvelEmploye);

            // Actualiser la liste
            chargerEmployes();

            // Vider le formulaire
            viderFormulaire();

            // Générer un nouveau numéro d'employé
            genererNumeroEmploye();

            afficherMessage("Employé ajouté avec succès.", "success");

        } catch (NumberFormatException e) {
            afficherMessage("Le numéro de téléphone doit être un nombre.", "error");
        } catch (Exception e) {
            afficherMessage("Erreur lors de l'ajout: " + e.getMessage(), "error");
        }
    }

    @FXML
    private void modifierEmploye() {
        try {
            if (employeSelectionne == null) {
                afficherMessage("Veuillez sélectionner un employé à modifier.", "error");
                return;
            }

            if (!validerFormulaire()) {
                return;
            }

            // Vérifier si le login existe déjà (sauf pour l'employé actuel)
            if (!loginField.getText().equals(employeSelectionne.getLogin()) &&
                    employeModele.loginExiste(loginField.getText())) {
                afficherMessage("Ce login existe déjà.", "error");
                return;
            }

            // Mettre à jour l'employé
            employeSelectionne.setNom(nomField.getText().trim());
            employeSelectionne.setPrenom(prenomField.getText().trim());
            employeSelectionne.setTelephone(Integer.parseInt(telephoneField.getText().trim()));
            employeSelectionne.setRole(roleComboBox.getValue());
            employeSelectionne.setLogin(loginField.getText().trim());

            // Mettre à jour le mot de passe seulement s'il a été modifié
            if (!motDePasseField.getText().isEmpty()) {
                employeSelectionne.setMotDePasse(motDePasseField.getText());
            }

            employeSelectionne.setNumeroEmploye(numeroEmployeField.getText().trim());

            // Sauvegarder
            employeModele.update(employeSelectionne);

            // Actualiser la liste
            chargerEmployes();

            // Vider le formulaire
            viderFormulaire();

            afficherMessage("Employé modifié avec succès.", "success");

        } catch (NumberFormatException e) {
            afficherMessage("Le numéro de téléphone doit être un nombre.", "error");
        } catch (Exception e) {
            afficherMessage("Erreur lors de la modification: " + e.getMessage(), "error");
        }
    }

    @FXML
    private void supprimerEmploye() {
        try {
            if (employeSelectionne == null) {
                afficherMessage("Veuillez sélectionner un employé à supprimer.", "error");
                return;
            }

            // Confirmation de suppression
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmer la suppression");
            confirmation.setHeaderText("Supprimer l'employé");
            confirmation.setContentText("Êtes-vous sûr de vouloir supprimer cet employé ?");

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Supprimer
                employeModele.delete(employeSelectionne.getId());

                // Actualiser la liste
                chargerEmployes();

                // Vider le formulaire
                viderFormulaire();

                afficherMessage("Employé supprimé avec succès.", "success");
            }

        } catch (Exception e) {
            afficherMessage("Erreur lors de la suppression: " + e.getMessage(), "error");
        }
    }

    @FXML
    private void viderFormulaire() {
        nomField.clear();
        prenomField.clear();
        telephoneField.clear();
        roleComboBox.setValue(Role.EMPLOYE);
        loginField.clear();
        motDePasseField.clear();
        numeroEmployeField.clear();

        employeSelectionne = null;

        // Réactiver le bouton ajouter et désactiver modifier/supprimer
        ajouterBtn.setDisable(false);
        modifierBtn.setDisable(true);
        supprimerBtn.setDisable(true);

        // Générer un nouveau numéro d'employé
        genererNumeroEmploye();

        // Désélectionner dans le tableau
        tableViewEmployes.getSelectionModel().clearSelection();
    }

    @FXML
    private void actualiserTableau() {
        chargerEmployes();
        viderFormulaire();
        afficherMessage("Tableau actualisé.", "success");
    }

    private void selectionnerEmploye(MouseEvent event) {
        Employe employe = tableViewEmployes.getSelectionModel().getSelectedItem();
        if (employe != null) {
            employeSelectionne = employe;

            // Remplir le formulaire avec les données de l'employé sélectionné
            nomField.setText(employe.getNom());
            prenomField.setText(employe.getPrenom());
            telephoneField.setText(String.valueOf(employe.getTelephone()));
            roleComboBox.setValue(employe.getRole());
            loginField.setText(employe.getLogin());
            motDePasseField.clear(); // Ne pas afficher le mot de passe
            numeroEmployeField.setText(employe.getNumeroEmploye());

            // Désactiver le bouton ajouter et activer modifier/supprimer
            ajouterBtn.setDisable(true);
            modifierBtn.setDisable(false);
            supprimerBtn.setDisable(false);
        }
    }

    @FXML
    private void rechercherEmploye(KeyEvent event) {
        String texteRecherche = rechercheField.getText().toLowerCase().trim();

        if (texteRecherche.isEmpty()) {
            listeEmployesFiltree.clear();
            listeEmployesFiltree.addAll(listeEmployes);
        } else {
            listeEmployesFiltree.clear();
            listeEmployesFiltree.addAll(
                    listeEmployes.stream()
                            .filter(employe ->
                                    employe.getNom().toLowerCase().contains(texteRecherche) ||
                                            employe.getPrenom().toLowerCase().contains(texteRecherche) ||
                                            employe.getNumeroEmploye().toLowerCase().contains(texteRecherche) ||
                                            employe.getLogin().toLowerCase().contains(texteRecherche)
                            )
                            .toList()
            );
        }
    }

    private boolean validerFormulaire() {
        if (nomField.getText().trim().isEmpty()) {
            afficherMessage("Le nom est obligatoire.", "error");
            nomField.requestFocus();
            return false;
        }

        if (prenomField.getText().trim().isEmpty()) {
            afficherMessage("Le prénom est obligatoire.", "error");
            prenomField.requestFocus();
            return false;
        }

        if (telephoneField.getText().trim().isEmpty()) {
            afficherMessage("Le téléphone est obligatoire.", "error");
            telephoneField.requestFocus();
            return false;
        }

        try {
            Integer.parseInt(telephoneField.getText().trim());
        } catch (NumberFormatException e) {
            afficherMessage("Le téléphone doit être un nombre.", "error");
            telephoneField.requestFocus();
            return false;
        }

        if (roleComboBox.getValue() == null) {
            afficherMessage("Veuillez sélectionner un rôle.", "error");
            roleComboBox.requestFocus();
            return false;
        }

        if (loginField.getText().trim().isEmpty()) {
            afficherMessage("Le login est obligatoire.", "error");
            loginField.requestFocus();
            return false;
        }

        if (motDePasseField.getText().trim().isEmpty() && employeSelectionne == null) {
            afficherMessage("Le mot de passe est obligatoire.", "error");
            motDePasseField.requestFocus();
            return false;
        }

        if (numeroEmployeField.getText().trim().isEmpty()) {
            afficherMessage("Le numéro d'employé est obligatoire.", "error");
            numeroEmployeField.requestFocus();
            return false;
        }

        return true;
    }

    private void genererNumeroEmploye() {
        try {
            int prochainNumero = employeModele.obtenirProchainNumeroEmploye();
            numeroEmployeField.setText("EMP" + String.format("%04d", prochainNumero));
        } catch (Exception e) {
            numeroEmployeField.setText("EMP0001");
        }
    }

    private void afficherMessage(String message, String type) {
        messageLabel.setText(message);

        if ("success".equals(type)) {
            messageLabel.setStyle("-fx-text-fill: #4CAF50;");
        } else if ("error".equals(type)) {
            messageLabel.setStyle("-fx-text-fill: #f44336;");
        } else {
            messageLabel.setStyle("-fx-text-fill: #FF9800;");
        }

        // Effacer le message après 5 secondes
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                javafx.application.Platform.runLater(() -> messageLabel.setText(""));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}