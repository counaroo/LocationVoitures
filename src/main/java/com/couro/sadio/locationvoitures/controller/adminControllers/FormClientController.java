package com.couro.sadio.locationvoitures.controller.adminControllers;

import javafx.fxml.FXML;

import java.awt.*;

public class FormClientController {
    @FXML
    private Label titleForm;

    public void setTitleForm(String titre) {
        titleForm.setText(titre);
    }
}
