package com.couro.sadio.locationvoitures.entities;

public enum StatutReservation {

    EN_ATTENTE("En attente"),
    CONFIRMEE("Confirmée"),
    EN_COURS("En cours"),
    TERMINEE("Terminee"),
    ANNULEE("Annulee");


    private final String libelle;

    StatutReservation(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
