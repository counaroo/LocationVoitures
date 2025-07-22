package com.couro.sadio.locationvoitures.entities;

import jakarta.persistence.*;

@Entity(name = "vehicules")
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String marque;

    private  String modele;

    private double tarif;


    private Boolean dispo;

    private String immatriculation;


    public Vehicule() {
    }

    public Vehicule(String marque, String modele, double tarif, Boolean dispo, String immatriculation) {
        this.marque = marque;
        this.modele = modele;
        this.tarif = tarif;
        this.dispo = dispo;
        this.immatriculation = immatriculation;
    }

    public Vehicule(int id, String marque, String modele, double tarif, Boolean dispo, String immatriculation) {
        this.id = id;
        this.marque = marque;
        this.modele = modele;
        this.tarif = tarif;
        this.dispo = dispo;
        this.immatriculation = immatriculation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }

    public Boolean getDispo() {
        return dispo;
    }

    public void setDispo(Boolean dispo) {
        this.dispo = dispo;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    @Override
    public String toString() {
        return marque + " " + modele + " - " + immatriculation;
    }

}
