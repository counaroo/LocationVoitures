package com.couro.sadio.locationvoitures.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "employes")
@DiscriminatorValue("EMPLOYE")
public class Employe extends Utilisateur{
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String numeroEmploye;

    public Employe() {
    }

    public Employe(String nom, String prenom, int telephone, Role role, String login, String motDePasse, String numeroEmploye) {
        super(nom, prenom, telephone, role, login, motDePasse);
        this.numeroEmploye = numeroEmploye;
    }

    public Employe(int id, String nom, String prenom, int telephone, Role role, String login, String motDePasse, String numeroEmploye) {
        super(id, nom, prenom, telephone, role, login, motDePasse);
        this.numeroEmploye = numeroEmploye;
    }

    public String getNumeroEmploye() {
        return numeroEmploye;
    }

    public void setNumeroEmploye(String numeroEmploye) {
        this.numeroEmploye = numeroEmploye;
    }
}
