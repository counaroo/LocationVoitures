package com.couro.sadio.locationvoitures.entities;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "utilisateurs")
@DiscriminatorColumn(name = "type_utilisateur")
public abstract class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    private String nom;

    private String prenom;

    private int telephone;

    @Enumerated(EnumType.STRING)  // ← Ajoutez cette annotation
    @Column(name = "role")
    private Role role;

    private String login;

    private String password;

    //Constructeur
    public Utilisateur() {
    }

    public Utilisateur(String nom, String prenom,int telephone, Role role, String login, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.role = role;
        this.login = login;
        this.password = motDePasse;
    }

    public Utilisateur(int id, String nom, String prenom,int telephone, Role role, String login, String motDePasse) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.role = role;
        this.login = login;
        this.password = motDePasse;
    }

    //Getter and Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return password;
    }

    public void setMotDePasse(String motDePasse) {
        this.password = motDePasse;
    }
}
