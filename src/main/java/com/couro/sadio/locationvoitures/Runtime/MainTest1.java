package com.couro.sadio.locationvoitures.Runtime;

import com.couro.sadio.locationvoitures.entities.*;
import com.couro.sadio.locationvoitures.modele.*;

import java.time.LocalDateTime;

public class MainTest1 {
    public static void main(String[] args) {
        testerCreationAdmin();
    }

    //Admin test
    public static void testerCreationAdmin(){
        Testeur testeur = new Testeur();

        Administrateur administrateur = new Administrateur("Sadio","Mohamed",760121099, Role.ADMIN,"mohamedLogin","passer");
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
    public static void testerUpdateClient(){
        Client client = new Client(2,"Sadio","Abdoul Aziz",778392552, Role.CLIENT,"client1","passer","AbdoulAzizSadio@gmail.com",0,"Mariste",500000000);
        Client client1 = new Client(3,"Faye","Mariama",774101299, Role.CLIENT,"client2","passer","Mariama@gmail.com",0,"Rufisque",2000000000);
        Client client2 = new Client(4,"Sambe","Abdoul Aziz",771236910, Role.CLIENT,"client3","passer","AbdoulAzizSambe@gmail.com",0,"Thies",600000000);
        Client client3 = new Client(5,"Samb","ibrahima",771234567, Role.CLIENT,"client4","passer","Ibrahima@gmail.com",0,"Mariste",950000000);

        ClientModele clientModele = new ClientModele();
        clientModele.update(client);
        clientModele.update(client1);
        clientModele.update(client2);
        clientModele.update(client3);
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

        Vehicule vehicule1 = new Vehicule("Bmw","M5CS",400000.0,true,"AA-132-SN");
        Vehicule vehicule2 = new Vehicule("Aston Martin","DBX",450000.0,true,"AA-662-NA");
        Vehicule vehicule3 = new Vehicule("Mercedes","C63-AMG-BlackSeries",400000.0,true,"AA-012-BA");
        Vehicule vehicule4 = new Vehicule("Bmw","x1",80000.0,true,"AA-300-BC");

        vehiculeModele.create(vehicule1);
        vehiculeModele.create(vehicule2);
        vehiculeModele.create(vehicule3);
        vehiculeModele.create(vehicule4);

        vehiculeModele.lister();
    }

    //Rerervation test
    public  static  void testerRservation(){
        ClientModele clientModele = new ClientModele();
        Client client = clientModele.read(2);
        VehiculeModele vehiculeModele = new VehiculeModele();
        Vehicule vehicule = vehiculeModele.read(3);
        ChauffeurModele chauffeurModele = new ChauffeurModele();
        Chauffeur chauffeur = chauffeurModele.read(1);
        Reservation reservation = new Reservation(client,vehicule,LocalDateTime.now(),StatutReservation.EN_ATTENTE,LocalDateTime.of(2025,8,1,9,0),LocalDateTime.of(2025,10,1,9,0),chauffeur,vehicule.getTarif(),chauffeur.getTarif());

        ReservationModele reservationModele =new ReservationModele();
        reservationModele.create(reservation);
    }

    public static  void testetModifierReservation(){
        ClientModele clientModele = new ClientModele();
        Client client = clientModele.read(2);
        VehiculeModele vehiculeModele = new VehiculeModele();
        Vehicule vehicule = vehiculeModele.read(3);
        ChauffeurModele chauffeurModele = new ChauffeurModele();
        Chauffeur chauffeur = chauffeurModele.read(1);
        Reservation reservation = new Reservation(1,client,vehicule,LocalDateTime.now(),StatutReservation.EN_ATTENTE,LocalDateTime.of(2025,8,1,9,0),LocalDateTime.of(2025,10,1,9,0),chauffeur,vehicule.getTarif(),chauffeur.getTarif());

        ReservationModele reservationModele =new ReservationModele();
        reservationModele.update(reservation);
    }

    public static void testerDeleteReservation(){
        ReservationModele reservationModele = new ReservationModele();
        //reservationModele.delete(5);
        reservationModele.delete(7);
    }

    //Test Chauffeur
    public static  void testerCreationChauffeur(){
        ChauffeurModele chauffeurModele = new ChauffeurModele();
        Chauffeur chauffeur = new Chauffeur("Tsunoda","Yuki",760120012,true);
        chauffeurModele.create(chauffeur);
    }


}
