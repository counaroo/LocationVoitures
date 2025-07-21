package com.couro.sadio.locationvoitures.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private StatutReservation statut;

    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "chauffeur_id")
    private Chauffeur chauffeur;

    private boolean avecChauffeur = false;
    private double montantVehicule;
    private double montantChauffeur;
    private double montantTotale;
    private int nbrJour;

    // --- Constructeurs ---

    public Reservation() {}

    public Reservation(Client client, Vehicule vehicule, LocalDateTime date, StatutReservation statut,
                       LocalDateTime dateDebut, LocalDateTime dateFin,
                       Chauffeur chauffeur, double montantVehicule, double montantChauffeur) {
        this.client = client;
        this.vehicule = vehicule;
        this.date = date;
        this.statut = statut;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.chauffeur = chauffeur;
        this.avecChauffeur = true;
        this.montantVehicule = montantVehicule;
        this.montantChauffeur = montantChauffeur;
        this.nbrJour = (int) ChronoUnit.DAYS.between(dateDebut, dateFin);
        this.montantTotale = montantChauffeur + montantVehicule * nbrJour;
    }

    public Reservation(Client client, Vehicule vehicule, LocalDateTime date, StatutReservation statut,
                       LocalDateTime dateDebut, LocalDateTime dateFin, double montantVehicule) {
        this.client = client;
        this.vehicule = vehicule;
        this.date = date;
        this.statut = statut;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.montantVehicule = montantVehicule;
        this.avecChauffeur = false;
        this.nbrJour = (int) ChronoUnit.DAYS.between(dateDebut, dateFin);
        this.montantTotale = montantVehicule * nbrJour;
    }

    public Reservation(int id, Client client, Vehicule vehicule, LocalDateTime date, StatutReservation statut,
                       LocalDateTime dateDebut, LocalDateTime dateFin,
                       Chauffeur chauffeur, double montantVehicule, double montantChauffeur) {
        this.id = id;
        this.client = client;
        this.vehicule = vehicule;
        this.date = date;
        this.statut = statut;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.chauffeur = chauffeur;
        this.avecChauffeur = true;
        this.montantVehicule = montantVehicule;
        this.montantChauffeur = montantChauffeur;
        this.nbrJour = (int) ChronoUnit.DAYS.between(dateDebut, dateFin);
        this.montantTotale = montantChauffeur + montantVehicule * nbrJour;
    }

    // --- Getters et Setters ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Vehicule getVehicule() { return vehicule; }
    public void setVehicule(Vehicule vehicule) { this.vehicule = vehicule; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public StatutReservation getStatut() { return statut; }
    public void setStatut(StatutReservation statut) { this.statut = statut; }

    public LocalDateTime getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDateTime dateDebut) { this.dateDebut = dateDebut; }

    public LocalDateTime getDateFin() { return dateFin; }
    public void setDateFin(LocalDateTime dateFin) { this.dateFin = dateFin; }

    public Chauffeur getChauffeur() { return chauffeur; }
    public void setChauffeur(Chauffeur chauffeur) { this.chauffeur = chauffeur; }

    public boolean isAvecChauffeur() { return avecChauffeur; }
    public void setAvecChauffeur(boolean avecChauffeur) { this.avecChauffeur = avecChauffeur; }

    public double getMontantVehicule() { return montantVehicule; }
    public void setMontantVehicule(double montantVehicule) { this.montantVehicule = montantVehicule; }

    public double getMontantChauffeur() { return montantChauffeur; }
    public void setMontantChauffeur(double montantChauffeur) { this.montantChauffeur = montantChauffeur; }

    public double getMontantTotale() { return montantTotale; }
    public void setMontantTotale(double montantTotale) { this.montantTotale = montantTotale; }

    public int getNbrJour() { return nbrJour; }
    public void setNbrJour(int nbrJour) { this.nbrJour = nbrJour; }
}
