package com.couro.sadio.locationvoitures.controller.adminControllers;

import com.couro.sadio.locationvoitures.controller.UILoginController;
import com.couro.sadio.locationvoitures.entities.Client;
import com.couro.sadio.locationvoitures.entities.Role;
import com.couro.sadio.locationvoitures.modele.ClientModele;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.UnaryOperator;

public class FormClientController {
    @FXML
    private Label titleForm;

    @FXML
    private TextField NomTextField;

    @FXML
    private TextField PrenomTextField;

    @FXML
    private TextField TelephoneTextField;

    @FXML
    private TextField LoginTextField;

    @FXML
    private TextField MotDePasseTextField;

    @FXML
    private TextField EmailTextField;

    @FXML
    private TextField AdresseTextField;

    private ClientModele clientModele = new ClientModele();
    private Client clientAModifier = null; // Client à modifier (null pour création)

    /**
     * Méthode d'initialisation appelée après le chargement du FXML
     */
    @FXML
    public void initialize() {
        // Configuration du TextFormatter pour accepter uniquement les chiffres
        // Vérification que le TextField n'est pas null avant de l'utiliser
        if (TelephoneTextField != null) {
            setupNumericTextField(TelephoneTextField);
        }
    }

    /**
     * Définit le client à modifier (pour la modification)
     */
    public void setClientAModifier(Client client) {
        this.clientAModifier = client;
        if (client != null) {
            remplirFormulaire(client);
        }
    }

    /**
     * Remplit le formulaire avec les données du client
     */
    private void remplirFormulaire(Client client) {
        if (NomTextField != null) NomTextField.setText(client.getNom());
        if (PrenomTextField != null) PrenomTextField.setText(client.getPrenom());
        if (TelephoneTextField != null) TelephoneTextField.setText(String.valueOf(client.getTelephone()));
        if (LoginTextField != null) LoginTextField.setText(client.getLogin());
        if (MotDePasseTextField != null) MotDePasseTextField.setText(client.getMotDePasse());
        if (EmailTextField != null) EmailTextField.setText(client.getEmail());
        if (AdresseTextField != null) AdresseTextField.setText(client.getAdresse());
    }

    /**
     * Configure un TextField pour accepter uniquement les chiffres
     */
    private void setupNumericTextField(TextField textField) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // Accepte uniquement les chiffres
                return change;
            }
            return null; // Rejette la modification
        };

        TextFormatter<Integer> textFormatter = new TextFormatter<>(filter);
        textField.setTextFormatter(textFormatter);
    }

    public void setTitleForm(String titre) {
        titleForm.setText(titre);
    }

    public void onValidateButton(ActionEvent event) {
        try {
            // Validation des champs
            if (validateFields()) {
                if (clientAModifier == null) {
                    // Mode création
                    Client client = new Client(
                            NomTextField.getText(),
                            PrenomTextField.getText(),
                            Integer.parseInt(TelephoneTextField.getText()),
                            Role.CLIENT,
                            LoginTextField.getText(),
                            MotDePasseTextField.getText(),
                            EmailTextField.getText(),
                            0, // Points de fidélité initiaux
                            AdresseTextField.getText(),
                            50000000
                    );

                    // Création du client
                    clientModele.create(client);
                    showAlert("Succès", "Client créé avec succès !", Alert.AlertType.INFORMATION);
                    ((Stage) NomTextField.getScene().getWindow()).close();
                    clearFields();
                } else {
                    // Mode modification
                    clientAModifier.setNom(NomTextField.getText());
                    clientAModifier.setPrenom(PrenomTextField.getText());
                    clientAModifier.setTelephone(Integer.parseInt(TelephoneTextField.getText()));
                    clientAModifier.setLogin(LoginTextField.getText());
                    clientAModifier.setMotDePasse(MotDePasseTextField.getText());
                    clientAModifier.setEmail(EmailTextField.getText());
                    clientAModifier.setAdresse(AdresseTextField.getText());

                    // Mise à jour du client
                    clientModele.update(clientAModifier);
                    showAlert("Succès", "Client modifié avec succès !", Alert.AlertType.INFORMATION);

                    // Fermer la fenêtre après modification
                    ((Stage) NomTextField.getScene().getWindow()).close();
                }
            }
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le numéro de téléphone doit être un nombre valide.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur inattendue s'est produite : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean validateFields() {
        if (NomTextField == null || NomTextField.getText().trim().isEmpty()) {
            showAlert("Erreur", "Le nom est obligatoire.", Alert.AlertType.WARNING);
            return false;
        }
        if (PrenomTextField == null || PrenomTextField.getText().trim().isEmpty()) {
            showAlert("Erreur", "Le prénom est obligatoire.", Alert.AlertType.WARNING);
            return false;
        }
        if (TelephoneTextField == null || TelephoneTextField.getText().trim().isEmpty()) {
            showAlert("Erreur", "Le téléphone est obligatoire.", Alert.AlertType.WARNING);
            return false;
        }
        if (LoginTextField == null || LoginTextField.getText().trim().isEmpty()) {
            showAlert("Erreur", "Le login est obligatoire.", Alert.AlertType.WARNING);
            return false;
        }
        if (MotDePasseTextField == null || MotDePasseTextField.getText().trim().isEmpty()) {
            showAlert("Erreur", "Le mot de passe est obligatoire.", Alert.AlertType.WARNING);
            return false;
        }
        if (EmailTextField == null || EmailTextField.getText().trim().isEmpty()) {
            showAlert("Erreur", "L'email est obligatoire.", Alert.AlertType.WARNING);
            return false;
        }
        if (AdresseTextField == null || AdresseTextField.getText().trim().isEmpty()) {
            showAlert("Erreur", "L'adresse est obligatoire.", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        if (NomTextField != null) NomTextField.clear();
        if (PrenomTextField != null) PrenomTextField.clear();
        if (TelephoneTextField != null) TelephoneTextField.clear();
        if (LoginTextField != null) LoginTextField.clear();
        if (MotDePasseTextField != null) MotDePasseTextField.clear();
        if (EmailTextField != null) EmailTextField.clear();
        if (AdresseTextField != null) AdresseTextField.clear();
    }

    public void onAnnulerButton(ActionEvent event) {
        // Fermer la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de connexion");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}