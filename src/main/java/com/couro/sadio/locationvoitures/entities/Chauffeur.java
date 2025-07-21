package com.couro.sadio.locationvoitures.entities;

import jakarta.persistence.*;

@Entity(name = "chauffeurs")
public class Chauffeur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String nom;
    private String prenom;
    private int telephone;
    private boolean dispo;
    private double tarif;
    private double prixParJour;


    // Constructeurs
    public Chauffeur() {}

    public Chauffeur(String nom, String prenom, int telephone, boolean dispo) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.dispo = dispo;
    }

    public Chauffeur(String nom, String prenom, int telephone, boolean dispo, double tarif) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.dispo = dispo;
        this.tarif = tarif;
    }

    public Chauffeur(int id, String nom, String prenom, int telephone, boolean dispo) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.dispo = dispo;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public double getPrixParJour() {
        return prixParJour;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public boolean isDispo() {
        return dispo;
    }

    public void setDispo(boolean dispo) {
        this.dispo = dispo;
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }
}
