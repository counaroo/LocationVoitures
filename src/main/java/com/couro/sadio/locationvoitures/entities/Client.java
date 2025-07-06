package com.couro.sadio.locationvoitures.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "clients")
public class Client extends Utilisateur{
    private String email;

    private int pointsFidelite;

    private  String adresse;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    //Constructeur
    public Client() {
    }

    public Client(String nom, String prenom, int telephone, Role role, String login, String motDePasse, String email, int pointsFidelite, String adresse) {
        super(nom, prenom, telephone, role, login, motDePasse);
        this.email = email;
        this.pointsFidelite = pointsFidelite;
        this.adresse = adresse;
        this.setRole(Role.CLIENT);
    }

    public Client(int id, String nom, String prenom, int telephone, Role role, String login, String motDePasse, String email, int pointsFidelite, String adresse) {
        super(id, nom, prenom, telephone, role, login, motDePasse);
        this.email = email;
        this.pointsFidelite = pointsFidelite;
        this.adresse = adresse;
        this.setRole(Role.CLIENT);
    }

    //Getter and setter

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPointsFidelite() {
        return pointsFidelite;
    }

    public void setPointsFidelite(int pointsFidelite) {
        this.pointsFidelite = pointsFidelite;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
