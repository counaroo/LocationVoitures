package com.couro.sadio.locationvoitures.Runtime;

import com.couro.sadio.locationvoitures.entities.Administrateur;
import com.couro.sadio.locationvoitures.entities.Client;
import com.couro.sadio.locationvoitures.entities.Employe;
import com.couro.sadio.locationvoitures.entities.Role;
import com.couro.sadio.locationvoitures.modele.ClientModele;
import com.couro.sadio.locationvoitures.modele.EmployeModele;

public class MainTest1 {
    public static void main(String[] args) {
        testerDeleteEmploye();

    }

    //Admin test
    public static void testerCreationAdmin(){
        Testeur testeur = new Testeur();

        Administrateur administrateur = new Administrateur("Sadio","Mohamed",760121099, Role.ADMIN,"admin","passer");
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

    //Client test
    public static void testerCreerClient(){
        Client client = new Client("Gueye","Sidy",760121099, Role.CLIENT,"client","passer","sidy@gmail.com",0,"Point E");
        ClientModele clientModele = new ClientModele();
        clientModele.create(client);
        clientModele.lister();
    }

    //Employe test
    public  static  void testerEmploye(){
        Employe employe = new Employe("Dio","Brando",778518710,Role.EMPLOYE,"employe","passer");
        EmployeModele employeModele = new EmployeModele();
        employeModele.create(employe);
        employeModele.lister();
    }

    public static void testerDeleteEmploye(){
        EmployeModele employeModele= new EmployeModele();
        employeModele.delete(102);
    }
}
