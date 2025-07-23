package com.couro.sadio.locationvoitures.entities;

public enum StatutFacture {
    EN_ATTENTE("En Attente"),
    PAYEE("Payee"),
    ANNULEE("Annulee"),
    ECHOUE("Echoue");

    private final String libelel;

    StatutFacture(String libelel) {
        this.libelel = libelel;
    }

    public String getLibelel() {
        return libelel;
    }
}
