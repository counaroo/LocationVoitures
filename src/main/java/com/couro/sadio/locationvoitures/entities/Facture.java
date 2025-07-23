package com.couro.sadio.locationvoitures.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "factures")
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private double montant;

    private LocalDateTime dateFacture;

    @Enumerated(EnumType.STRING)
    private StatutFacture statutFacture;

    // Constructeurs
    public Facture() {}

    public Facture(Reservation reservation, double montant, LocalDateTime dateFacture, StatutFacture statutFacture) {
        this.reservation = reservation;
        this.montant = montant;
        this.dateFacture = dateFacture;
        this.statutFacture = statutFacture;
    }

    public Facture(int id, Reservation reservation, double montant, LocalDateTime dateFacture, StatutFacture statutFacture) {
        this.id = id;
        this.reservation = reservation;
        this.montant = montant;
        this.dateFacture = dateFacture;
        this.statutFacture = statutFacture;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Reservation getReservation() { return reservation; }
    public void setReservation(Reservation reservation) { this.reservation = reservation; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public LocalDateTime getDateFacture() { return dateFacture; }
    public void setDateFacture(LocalDateTime dateFacture) { this.dateFacture = dateFacture; }

    public StatutFacture getStatutFacture() { return statutFacture; }
    public void setStatutFacture(StatutFacture statutFacture) { this.statutFacture = statutFacture; }
}