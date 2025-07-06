package com.couro.sadio.locationvoitures.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id ;

    @ManyToOne()
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;

    private Date date;

    private boolean statut;

    private LocalDateTime dateDebut;

    private LocalDateTime dateFin;

    @ManyToOne()
    @JoinColumn(name = "chauffeur_id")
    private Chauffeur chauffeur;

    private boolean avecChauffeur = false;

    private double montantVehicule;

    private double montantChauffeur;

    private double montantTotale;

    public Reservation() {
    }

    public Reservation(Client client, Vehicule vehicule, Date date, boolean statut, LocalDateTime dateDebut, LocalDateTime dateFin, Chauffeur chauffeur, boolean avecChauffeur, double montantVehicule, double montantChauffeur, double montantTotale) {
        this.client = client;
        this.vehicule = vehicule;
        this.date = date;
        this.statut = statut;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.chauffeur = chauffeur;
        this.avecChauffeur = avecChauffeur;
        this.montantVehicule = montantVehicule;
        this.montantChauffeur = montantChauffeur;
        this.montantTotale = montantTotale;
    }

    public Reservation(int id, Client client, Vehicule vehicule, Date date, boolean statut, LocalDateTime dateDebut, LocalDateTime dateFin, Chauffeur chauffeur, boolean avecChauffeur, double montantVehicule, double montantChauffeur, double montantTotale) {
        this.id = id;
        this.client = client;
        this.vehicule = vehicule;
        this.date = date;
        this.statut = statut;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.chauffeur = chauffeur;
        this.avecChauffeur = avecChauffeur;
        this.montantVehicule = montantVehicule;
        this.montantChauffeur = montantChauffeur;
        this.montantTotale = montantTotale;
    }
}
