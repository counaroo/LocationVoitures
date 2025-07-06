package com.couro.sadio.locationvoitures.Runtime;

import com.couro.sadio.locationvoitures.dao.IDao;
import com.couro.sadio.locationvoitures.dao.impl.*;
import com.couro.sadio.locationvoitures.entities.*;
import com.couro.sadio.locationvoitures.exception.DAOException;
import com.couro.sadio.locationvoitures.factory.*;

import java.util.List;

public class Testeur {
    IDao<Administrateur> adminIDao;
    IDao<Chauffeur> chauffeurIDao;
    IDao<Client> clientIDao;
    IDao<Employe> employeIDao;
    IDao<Reservation> reservationIDao;
    IDao<Vehicule> vehiculeIDao;

    public Testeur(){
        adminIDao = ConcreteFactory.getFactory(AdminFactory.class)
                .getAdmin(HibernateAdminDaoImpl.class);

        chauffeurIDao = ConcreteFactory.getFactory(ChauffeurFactory.class)
                .getChauffeur(HibernateChauffeurDaoImpl.class);

        clientIDao = ConcreteFactory.getFactory(ClientFactory.class)
                .getClient(HibernateClientDaoImpl.class);

        employeIDao = ConcreteFactory.getFactory(EmployeFactory.class)
                .getEmploye(HibernateEmployeeDaoImpl.class);

        reservationIDao = ConcreteFactory.getFactory(ReservationFactory.class)
                .getReservation(HibernateReservationDaoImpl.class);

        vehiculeIDao = ConcreteFactory.getFactory(VehiculeFactory.class)
                .getVehicule(HibernateVehiculeDaoImpl.class);
    }

    // METHODES POUR TESTER CRUD ADMIN
    public void creerAdmin(Administrateur admin){
        try{
            adminIDao.create(admin);
        }catch (DAOException e){
            System.err.println("Error while creating an admin : "+e.getMessage());
        }
    }

    public void readAdmin(int id){
        try{
            Administrateur admin = adminIDao.read(id);
            if (admin != null) {
                System.out.println("Admin found : " + admin.getPrenom()+" "+admin.getNom());
            } else {
                System.out.println("Admin not found !");
            }
        }catch (DAOException e){
            System.err.println("Error while searching this admin : "+e.getMessage());
        }
    }

    public void listerAdmin(){
        try {
            List<Administrateur> administrateurList = adminIDao.list();
            System.out.println("Liste des admin : ");
            for (Administrateur administrateur : administrateurList){
                System.out.println(administrateur.getId()+" "+administrateur.getPrenom()+" "+administrateur.getNom());
            }
        } catch (DAOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateAdmin(Administrateur administrateur){
        try{
            adminIDao.update(administrateur);
            System.out.println("Admin updated successfully : " + administrateur.getPrenom() + " " + administrateur.getNom());
        }catch (DAOException e){
            System.err.println("Error while updating admin : " + e.getMessage());
        }
    }

    public void deleteAdmin(int id){
        try{
            adminIDao.delete(id);
            System.out.println("Admin deleted successfully with ID : " + id);
        }catch (DAOException e){
            System.err.println("Error while deleting admin : " + e.getMessage());
        }
    }
}
