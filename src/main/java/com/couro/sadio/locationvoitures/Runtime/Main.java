package com.couro.sadio.locationvoitures.Runtime;

import com.couro.sadio.locationvoitures.entities.Administrateur;
import com.couro.sadio.locationvoitures.entities.Role;

public class Main {
    public static void main(String[] args) {
        //testerCreationAdmin();
        //testerReadAdmin();
        //testerUpdateAdmin();
        testerDeleteAdmin();
        testerListerAdmin();
    }

    public static void testerCreationAdmin(){
        Testeur testeur = new Testeur();

        Administrateur administrateur = new Administrateur("Sambe","Aziz",761254040, Role.ADMIN,"admin","passer");
        testeur.creerAdmin(administrateur);
    }

    public static void testerReadAdmin(){
        Testeur testeur = new Testeur();
        testeur.readAdmin(2);
    }

    public static void testerListerAdmin(){
        Testeur testeur = new Testeur();
        testeur.listerAdmin();
    }

    public static void testerUpdateAdmin(){
        Testeur testeur = new Testeur();

        Administrateur administrateur = new Administrateur(2,"Diop","Souley",700000000, Role.ADMIN,"admin","passer");
        testeur.updateAdmin(administrateur);
    }

    public static void testerDeleteAdmin(){
        Testeur testeur = new Testeur();
        testeur.deleteAdmin(52);
    }
}
