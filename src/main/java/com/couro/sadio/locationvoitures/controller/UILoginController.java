package com.couro.sadio.locationvoitures.controller;

import com.couro.sadio.locationvoitures.entities.Utilisateur;
import com.couro.sadio.locationvoitures.modele.UserModele;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UILoginController {
    UserModele userModele = new UserModele();

    @FXML
    TextField loginField;

    @FXML
    PasswordField passwordField;

    public void onLoginClicked(ActionEvent actionEvent) {
        String login = loginField.getText().trim();
        String password = passwordField.getText().trim();

        // Validation des champs
        if (login.isEmpty() || password.isEmpty()) {
            showError("Veuillez remplir tous les champs");
            return;
        }

        try {
            Utilisateur user = userModele.authenticate(login, password);

            if (user != null) {
                // Récupérer le Stage depuis l'événement
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                navigateTo(user.getRole().toString(), stage);
            } else {
                showError("Identifiants incorrects");
            }
        } catch (Exception e) {
            showError("Erreur d'authentification: " + e.getMessage());
        }
    }

    private void navigateTo(String role, Stage stage) {
        NavigateController navigateController = new NavigateController(stage);

        switch (role.toUpperCase()) {
            case "ADMIN":
                navigateController.OpenAdminPage(null);
                break;
            case "EMPLOYE":
                navigateController.OpenEmployeePage(null);
                break;
            case "CLIENT":
                navigateController.OpenClientPage(null);
                break;
            default:
                showError("Rôle non reconnu: " + role);
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
    public void clearFields() {
        loginField.clear();
        passwordField.clear();
    }
}