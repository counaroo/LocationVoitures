package com.couro.sadio.locationvoitures.controller.clientController;

import com.couro.sadio.locationvoitures.entities.Client;
import com.couro.sadio.locationvoitures.entities.Utilisateur;
import com.couro.sadio.locationvoitures.exception.DAOException;
import com.couro.sadio.locationvoitures.interfaces.ControlledScreen;
import com.couro.sadio.locationvoitures.modele.ClientModele;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileClientController implements Initializable, ControlledScreen {

    // Service
    private ClientModele clientModele = new ClientModele();
    private Utilisateur utilisateurConnecte;
    private Client clientConnecte;

    // Champs FXML pour les informations personnelles
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelephone;
    @FXML
    private TextField txtAdresse;

    // Champs FXML pour les informations de connexion
    @FXML
    private TextField txtLogin;
    @FXML
    private PasswordField txtNouveauMotDePasse;
    @FXML
    private PasswordField txtConfirmerMotDePasse;

    // Labels pour la fidélité et messages
    @FXML
    private Label lblPointsFidelite;
    @FXML
    private Label lblStatutFidelite;
    @FXML
    private Label lblMessageMotDePasse;
    @FXML
    private Label lblMessage;

    @Override
    public void setUserData(Utilisateur user) {
        this.utilisateurConnecte = user;
        if (user != null) {
            // Récupérer les informations du client connecté
            this.clientConnecte = clientModele.getClientConnecteByUser(user);
            chargerDonneesClient();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialiser les listeners pour la validation des mots de passe
        txtNouveauMotDePasse.textProperty().addListener((obs, oldText, newText) -> validerMotsDePasse());
        txtConfirmerMotDePasse.textProperty().addListener((obs, oldText, newText) -> validerMotsDePasse());
    }

    /**
     * Charge les données du client dans les champs de l'interface
     */
    private void chargerDonneesClient() {
        if (clientConnecte != null) {
            // Informations personnelles
            txtNom.setText(clientConnecte.getNom() != null ? clientConnecte.getNom() : "");
            txtPrenom.setText(clientConnecte.getPrenom() != null ? clientConnecte.getPrenom() : "");
            txtEmail.setText(clientConnecte.getEmail() != null ? clientConnecte.getEmail() : "");
            txtTelephone.setText(clientConnecte.getTelephone() != 0 ? String.valueOf(clientConnecte.getTelephone()) : "");
            txtAdresse.setText(clientConnecte.getAdresse() != null ? clientConnecte.getAdresse() : "");

            // Informations de connexion
            txtLogin.setText(clientConnecte.getLogin() != null ? clientConnecte.getLogin() : "");

            // Points de fidélité
            int points = clientConnecte.getPointsFidelite();
            lblPointsFidelite.setText(points + " points");
            lblStatutFidelite.setText(determinerStatutFidelite(points));

            // Effacer les messages
            lblMessage.setText("");
            lblMessageMotDePasse.setText("");
        }
    }

    /**
     * Détermine le statut de fidélité en fonction des points
     */
    private String determinerStatutFidelite(int points) {
        if (points >= 300) {
            return "Or";
        } else if (points >= 100) {
            return "Argent";
        } else {
            return "Bronze";
        }
    }

    /**
     * Valide la correspondance des mots de passe
     */
    private void validerMotsDePasse() {
        String nouveauMdp = txtNouveauMotDePasse.getText();
        String confirmerMdp = txtConfirmerMotDePasse.getText();

        if (!nouveauMdp.isEmpty() || !confirmerMdp.isEmpty()) {
            if (!nouveauMdp.equals(confirmerMdp)) {
                lblMessageMotDePasse.setText("Les mots de passe ne correspondent pas");
                lblMessageMotDePasse.setStyle("-fx-text-fill: #f56565;");
            } else if (nouveauMdp.length() < 6) {
                lblMessageMotDePasse.setText("Le mot de passe doit contenir au moins 6 caractères");
                lblMessageMotDePasse.setStyle("-fx-text-fill: #f56565;");
            } else {
                lblMessageMotDePasse.setText("Mots de passe valides");
                lblMessageMotDePasse.setStyle("-fx-text-fill: #48bb78;");
            }
        } else {
            lblMessageMotDePasse.setText("");
        }
    }

    /**
     * Valide les champs obligatoires
     */
    private boolean validerChamps() {
        StringBuilder erreurs = new StringBuilder();

        if (txtNom.getText().trim().isEmpty()) {
            erreurs.append("Le nom est obligatoire. ");
        }
        if (txtPrenom.getText().trim().isEmpty()) {
            erreurs.append("Le prénom est obligatoire. ");
        }
        if (txtEmail.getText().trim().isEmpty()) {
            erreurs.append("L'email est obligatoire. ");
        } else if (!isValidEmail(txtEmail.getText().trim())) {
            erreurs.append("L'email n'est pas valide. ");
        }
        if (txtTelephone.getText().trim().isEmpty()) {
            erreurs.append("Le téléphone est obligatoire. ");
        }
        if (txtLogin.getText().trim().isEmpty()) {
            erreurs.append("Le login est obligatoire. ");
        }

        // Validation des mots de passe si renseignés
        String nouveauMdp = txtNouveauMotDePasse.getText();
        String confirmerMdp = txtConfirmerMotDePasse.getText();

        if (!nouveauMdp.isEmpty() || !confirmerMdp.isEmpty()) {
            if (!nouveauMdp.equals(confirmerMdp)) {
                erreurs.append("Les mots de passe ne correspondent pas. ");
            } else if (nouveauMdp.length() < 6) {
                erreurs.append("Le mot de passe doit contenir au moins 6 caractères. ");
            }
        }

        if (erreurs.length() > 0) {
            afficherMessage(erreurs.toString(), false);
            return false;
        }

        return true;
    }

    /**
     * Validation simple de l'email
     */
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    /**
     * Affiche un message à l'utilisateur
     */
    private void afficherMessage(String message, boolean succes) {
        lblMessage.setText(message);
        if (succes) {
            lblMessage.setStyle("-fx-text-fill: #48bb78; -fx-font-weight: bold;");
        } else {
            lblMessage.setStyle("-fx-text-fill: #f56565; -fx-font-weight: bold;");
        }
    }

    @FXML
    public void handleSauvegarder(ActionEvent event) {
        if (!validerChamps()) {
            return;
        }

        try {
            // Vérifier si l'email existe déjà (sauf pour le client actuel)
            Client clientAvecMemeEmail = clientModele.rechercherParEmail(txtEmail.getText().trim());
            if (clientAvecMemeEmail != null && clientAvecMemeEmail.getId() != clientConnecte.getId()) {
                afficherMessage("Cet email est déjà utilisé par un autre client.", false);
                return;
            }

            // Mettre à jour les informations du client
            clientConnecte.setNom(txtNom.getText().trim());
            clientConnecte.setPrenom(txtPrenom.getText().trim());
            clientConnecte.setEmail(txtEmail.getText().trim());
            clientConnecte.setTelephone(Integer.parseInt(txtTelephone.getText().trim()));
            clientConnecte.setAdresse(txtAdresse.getText().trim());
            clientConnecte.setLogin(txtLogin.getText().trim());

            // Mettre à jour le mot de passe si renseigné
            String nouveauMdp = txtNouveauMotDePasse.getText();
            if (!nouveauMdp.isEmpty()) {
                clientConnecte.setMotDePasse(nouveauMdp); // Assumons que le hashage est fait dans l'entité ou le DAO
            }

            // Sauvegarder en base
            clientModele.update(clientConnecte);

            // Mettre à jour l'utilisateur connecté si nécessaire
            utilisateurConnecte.setNom(clientConnecte.getNom());
            utilisateurConnecte.setPrenom(clientConnecte.getPrenom());
            utilisateurConnecte.setLogin(clientConnecte.getLogin());

            // Effacer les champs de mot de passe
            txtNouveauMotDePasse.clear();
            txtConfirmerMotDePasse.clear();
            lblMessageMotDePasse.setText("");

            afficherMessage("Profil mis à jour avec succès !", true);

        } catch (DAOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
            afficherMessage("Erreur lors de la sauvegarde : " + e.getMessage(), false);
        } catch (Exception e) {
            System.err.println("Erreur inattendue : " + e.getMessage());
            afficherMessage("Une erreur inattendue s'est produite.", false);
        }
    }

    @FXML
    public void handleAnnuler(ActionEvent event) {
        // Recharger les données originales du client
        chargerDonneesClient();

        // Effacer les champs de mot de passe
        txtNouveauMotDePasse.clear();
        txtConfirmerMotDePasse.clear();
        lblMessageMotDePasse.setText("");

        afficherMessage("Modifications annulées.", true);
    }

    @FXML
    public void handleActualiser(ActionEvent event) {
        try {
            // Recharger les données du client depuis la base
            clientConnecte = clientModele.read(clientConnecte.getId());
            chargerDonneesClient();

            afficherMessage("Données actualisées avec succès !", true);

        } catch (DAOException e) {
            System.err.println("Erreur lors de l'actualisation : " + e.getMessage());
            afficherMessage("Erreur lors de l'actualisation : " + e.getMessage(), false);
        } catch (Exception e) {
            System.err.println("Erreur inattendue lors de l'actualisation : " + e.getMessage());
            afficherMessage("Une erreur inattendue s'est produite.", false);
        }
    }
}