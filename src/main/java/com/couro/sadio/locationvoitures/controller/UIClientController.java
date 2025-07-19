package com.couro.sadio.locationvoitures.controller;

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
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UIClientController implements Initializable,UILoginController.ControlledScreen{

    @FXML
    private StackPane contentArea;

    @FXML
    private Label userName;

    @FXML
    private Label role;

    private Utilisateur currentUser;

    @Override
    public void setUserData(Utilisateur user) {
        this.currentUser=user;
        updateUserInterface();
    }


    private void updateUserInterface() {
        if(currentUser != null){
            if(userName != null){
                userName.setText(currentUser.getPrenom()+"-"+currentUser.getNom());
            }

            if(role != null){
                role.setText(currentUser.getRole().toString());
            }

        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/com/couro/sadio/view/ClientWindows/AccueilClient.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException e) {
            Logger.getLogger(ModuleLayer.Controller.class.getName()).log(Level.SEVERE,null, e);
        }
    }

    public void showMesRservations(ActionEvent event){
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/com/couro/sadio/view/ClientWindows/MesReservation.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException e) {
            Logger.getLogger(ModuleLayer.Controller.class.getName()).log(Level.SEVERE,null, e);
        }
    }

    public void showProfil(ActionEvent event){
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/com/couro/sadio/view/ClientWindows/ProfileClient.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException e) {
            Logger.getLogger(ModuleLayer.Controller.class.getName()).log(Level.SEVERE,null, e);
        }
    }

    public void showAccueil(ActionEvent event) {
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/com/couro/sadio/view/ClientWindows/AccueilClient.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException e) {
            Logger.getLogger(ModuleLayer.Controller.class.getName()).log(Level.SEVERE,null, e);
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
