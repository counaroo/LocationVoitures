package com.couro.sadio.locationvoitures.entities;

public enum Role {
    ADMIN("Administrateur"),
    EMPLOYE("Employé"),
    CLIENT("Client");

    private final String libelle;

    Role(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
