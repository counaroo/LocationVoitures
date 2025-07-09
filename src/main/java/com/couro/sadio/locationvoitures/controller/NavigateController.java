package com.couro.sadio.locationvoitures.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.w3c.dom.Node;
import javafx.scene.Scene;

import java.io.IOException;

public class NavigateController {
    private Stage primaryStage;

    public NavigateController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void OpenAdminPage(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface admin
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/couro/sadio/view/UIAdmin.fxml.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scene
            Scene scene = new Scene(root);

            // Optionnel : ajouter le CSS si vous en avez
            // scene.getStylesheets().add(getClass().getResource("/css/admin.css").toExternalForm());

            // Changer la scene du stage principal
            primaryStage.setScene(scene);
            primaryStage.setTitle("Interface Administrateur - Location Voitures");
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'interface admin: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void OpenEmployeePage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/couro/sadio/view/UIEmploye.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Interface Employé - Location Voitures");
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'interface employé: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void OpenClientPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/couro/sadio/view/UIClient.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Interface Client - Location Voitures");
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'interface client: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void OpenLoginPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/couro/sadio/view/UILogin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Connexion - Location Voitures");
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'interface de connexion: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
