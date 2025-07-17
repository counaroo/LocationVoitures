package com.couro.sadio.locationvoitures.Runtime;

import com.couro.sadio.locationvoitures.entities.*;
import com.couro.sadio.locationvoitures.modele.ClientModele;
import com.couro.sadio.locationvoitures.modele.EmployeModele;
import com.couro.sadio.locationvoitures.modele.VehiculeModele;

public class MainTest1 {
    public static void main(String[] args) {
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
        Employe employe0 = new Employe(1,"Brando","Dio",777777777,Role.EMPLOYE,"employe","passer","EMP0001");
        Employe employe = new Employe("Leclerc","Charle",783210000,Role.EMPLOYE,"employe1","passer","EMP0002");
        Employe employe1 = new Employe("Verstappen","MAx",702130026,Role.EMPLOYE,"employe2","passer","EMP0003");
        Employe employe2 = new Employe("Norris","Lando",777771010,Role.EMPLOYE,"employe3","passer","EMP0004");
        EmployeModele employeModele = new EmployeModele();
        employeModele.update(employe0);
        employeModele.update(employe);
        employeModele.update(employe1);
        employeModele.update(employe2);
        employeModele.lister();
    }

    public static void testerDeleteEmploye(){
        EmployeModele employeModele= new EmployeModele();
        employeModele.delete(102);
    }

    //Vehicule test
    public static  void testerAjouterVehicule(){
        VehiculeModele vehiculeModele = new VehiculeModele();

        Vehicule vehicule1 = new Vehicule("Bmw","M5CS",400000,true,"AA-132-SN");
        Vehicule vehicule2 = new Vehicule("Aston Martin","DBX",450000,true,"AA-662-NA");
        Vehicule vehicule3 = new Vehicule("Mercedes","C63-AMG-BlackSeries",400000,true,"AA-012-BA");
        Vehicule vehicule4 = new Vehicule("Bmw","x1",80000,true,"AA-300-BC");

        vehiculeModele.create(vehicule1);
        vehiculeModele.create(vehicule2);
        vehiculeModele.create(vehicule3);
        vehiculeModele.create(vehicule4);

        vehiculeModele.lister();
    }
}
