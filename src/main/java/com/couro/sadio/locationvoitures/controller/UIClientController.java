package com.couro.sadio.locationvoitures.controller;

import com.couro.sadio.locationvoitures.interfaces.ControlledScreen; // Nouvelle interface
import com.couro.sadio.locationvoitures.entities.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UIClientController implements Initializable, UILoginController.ControlledScreen {

    @FXML
    private StackPane contentArea;

    @FXML
    private Label userName;

    @FXML
    private Label role;

    private Utilisateur currentUser;

    @Override
    public void setUserData(Utilisateur user) {
        this.currentUser = user;
        if (user != null) {
            userName.setText(user.getNom() + " " + user.getPrenom());
            role.setText(user.getRole().toString());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadView("/com/couro/sadio/view/ClientWindows/AccueilClient.fxml", null);
    }

    public void showMesReservations(ActionEvent event) {
        loadView("/com/couro/sadio/view/ClientWindows/MesReservation.fxml", currentUser);
    }

    public void showProfil(ActionEvent event) {
        loadView("/com/couro/sadio/view/ClientWindows/ProfileClient.fxml", currentUser);
    }

    public void showAccueil(ActionEvent event) {
        loadView("/com/couro/sadio/view/ClientWindows/AccueilClient.fxml", currentUser);
    }

    private void loadView(String fxmlPath, Utilisateur user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent fxml = loader.load();

            // Récupérer le contrôleur et lui passer les données utilisateur si nécessaire
            Object controller = loader.getController();

            // Vérifier les deux interfaces possibles
            if (controller instanceof ControlledScreen && user != null) {
                ((ControlledScreen) controller).setUserData(user);
            } else if (controller instanceof UILoginController.ControlledScreen && user != null) {
                ((UILoginController.ControlledScreen) controller).setUserData(user);
            }

            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException e) {
            Logger.getLogger(UIClientController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void onLogout(ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            // Charger la nouvelle interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/couro/sadio/view/UILogin.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
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
}