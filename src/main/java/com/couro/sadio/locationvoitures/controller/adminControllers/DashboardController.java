package com.couro.sadio.locationvoitures.controller.adminControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label dashboardLabel;

    // Constructeur par défaut requis par JavaFX
    public DashboardController() {
        // Constructeur vide - obligatoire pour JavaFX
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Cette méthode est appelée après le chargement du FXML
        System.out.println("Dashboard Controller initialized");
    }
}