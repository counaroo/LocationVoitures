package com.couro.sadio.locationvoitures.entities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Employe")
public class Employe extends Utilisateur {
    private String numeroEmploye;

    public Employe() {
        this.setRole(Role.EMPLOYE);
    }

    public Employe(String nom, String prenom, Integer telephone, Role role, String login, String motDePasse, String numeroEmploye) {
        super(nom, prenom, telephone, role, login, motDePasse);
        this.numeroEmploye = numeroEmploye;
        this.setRole(Role.EMPLOYE);
    }

    public Employe(int id, String nom, String prenom, int telephone, Role role, String login, String motDePasse, String numeroEmploye) {
        super(id, nom, prenom, telephone, role, login, motDePasse);
        this.numeroEmploye = numeroEmploye;
        this.setRole(Role.EMPLOYE);
    }

    public String getNumeroEmploye() { return numeroEmploye; }
    public void setNumeroEmploye(String numeroEmploye) { this.numeroEmploye = numeroEmploye; }
}
