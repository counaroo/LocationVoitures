package com.couro.sadio.locationvoitures.controller;

import com.couro.sadio.locationvoitures.entities.Utilisateur;
import com.couro.sadio.locationvoitures.modele.UserModele;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UILoginController {
    UserModele userModele = new UserModele();

    @FXML
    TextField loginField;

    @FXML
    PasswordField passwordField;

    public void onLoginClicked(ActionEvent actionEvent) {
        String login = loginField.getText().trim();
        String password = passwordField.getText().trim();

        if (login.isEmpty() || password.isEmpty()) {
            showError("Veuillez remplir tous les champs");
            return;
        }

        try {
            Utilisateur user = userModele.authenticate(login, password);

            if (user != null) {
                switch (user.getRole()){
                    case ADMIN ->
                            navigateToInterface(actionEvent, "/com/couro/sadio/view/UIAdmin.fxml", "Administration", user);
                    case EMPLOYE ->
                            navigateToInterface(actionEvent, "/com/couro/sadio/view/UIAdmin.fxml", "Employe", user);
                    case CLIENT ->
                            navigateToInterface(actionEvent, "/com/couro/sadio/view/UIClient.fxml", "Client", user);
                }
            } else {
                showError("Identifiants incorrects");
            }
        } catch (Exception e) {
            showError("Erreur d'authentification: " + e.getMessage());
        }
    }

    /**
     * Méthode générique pour naviguer vers une nouvelle interface
     * @param event L'événement d'action
     * @param fxmlPath Chemin vers le fichier FXML de la nouvelle interface
     * @param title Titre de la nouvelle fenêtre
     * @param user L'utilisateur connecté (peut être null)
     */
    private void navigateToInterface(ActionEvent event, String fxmlPath, String title, Utilisateur user) {
        try {
            // Fermer la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            // Charger la nouvelle interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Si le contrôleur implémente une interface commune (recommandé)
            if (loader.getController() instanceof ControlledScreen) {
                ((ControlledScreen) loader.getController()).setUserData(user);
            }

            // Créer et afficher la nouvelle scène
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
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
    public void clearFields() {
        loginField.clear();
        passwordField.clear();
    }

    /**
     * Interface optionnelle pour standardiser la communication entre contrôleurs
     */
    public interface ControlledScreen {
        void setUserData(Utilisateur user);
    }
}