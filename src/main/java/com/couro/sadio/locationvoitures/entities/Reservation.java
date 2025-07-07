package com.couro.sadio.locationvoitures.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id ;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;

    private Date date;

    private boolean statut;

    private LocalDateTime dateDebut;

    private LocalDateTime dateFin;

    @ManyToOne(cascade = CascadeType.PERSIST)
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

    //Getter et setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public Chauffeur getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
    }

    public boolean isAvecChauffeur() {
        return avecChauffeur;
    }

    public void setAvecChauffeur(boolean avecChauffeur) {
        this.avecChauffeur = avecChauffeur;
    }

    public double getMontantVehicule() {
        return montantVehicule;
    }

    public void setMontantVehicule(double montantVehicule) {
        this.montantVehicule = montantVehicule;
    }

    public double getMontantChauffeur() {
        return montantChauffeur;
    }

    public void setMontantChauffeur(double montantChauffeur) {
        this.montantChauffeur = montantChauffeur;
    }

    public double getMontantTotale() {
        return montantTotale;
    }

    public void setMontantTotale(double montantTotale) {
        this.montantTotale = montantTotale;
    }
}
