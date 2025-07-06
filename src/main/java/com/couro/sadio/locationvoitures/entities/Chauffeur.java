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

    private String telephone;

    private boolean dispo;

}
