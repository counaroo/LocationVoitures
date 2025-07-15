package com.couro.sadio.locationvoitures.Runtime;

import com.couro.sadio.locationvoitures.entities.Administrateur;
import com.couro.sadio.locationvoitures.entities.Client;
import com.couro.sadio.locationvoitures.entities.Employe;
import com.couro.sadio.locationvoitures.entities.Role;
import com.couro.sadio.locationvoitures.modele.ClientModele;
import com.couro.sadio.locationvoitures.modele.EmployeModele;

public class MainTest1 {
    public static void main(String[] args) {
        testerCreerClient();
        testerEmploye();

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
        Client client = new Client("Sadio","Abdoul Aziz",778392552, Role.CLIENT,"client1","passer","AbdoulAzizSadio@gmail.com",0,"Mariste");
        Client client1 = new Client("Faye","Mariama",774101299, Role.CLIENT,"client2","passer","Mariama@gmail.com",0,"Rufisque");
        Client client2 = new Client("Sambe","Abdoul Aziz",771236910, Role.CLIENT,"client3","passer","AbdoulAzizSambe@gmail.com",0,"Thies");
        Client client3 = new Client("Samb","ibrahima",771234567, Role.CLIENT,"client4","passer","Ibrahima@gmail.com",0,"Mariste");

        ClientModele clientModele = new ClientModele();
        clientModele.create(client);
        clientModele.create(client1);
        clientModele.create(client2);
        clientModele.create(client3);
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
