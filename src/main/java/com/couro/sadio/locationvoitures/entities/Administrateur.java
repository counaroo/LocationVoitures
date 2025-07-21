package com.couro.sadio.locationvoitures.entities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Administrateur")
public class Administrateur extends Utilisateur {
    public Administrateur() {
        this.setRole(Role.ADMIN);
    }

    public Administrateur(String nom, String prenom, int telephone, Role role, String login, String motDePasse) {
        super(nom, prenom, telephone, role, login, motDePasse);
        this.setRole(Role.ADMIN);
    }

    public Administrateur(int id, String nom, String prenom, Integer telephone, Role role, String login, String motDePasse) {
        super(id, nom, prenom, telephone, role, login, motDePasse);
        this.setRole(Role.ADMIN);
    }
}
