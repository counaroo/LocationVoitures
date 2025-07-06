package com.couro.sadio.locationvoitures.factory;

import com.couro.sadio.locationvoitures.dao.IDao;
import com.couro.sadio.locationvoitures.dao.impl.*;
import com.couro.sadio.locationvoitures.entities.*;

public class HibernateAppFactory extends AbstractAppFactory{
    @Override
    public AdminFactory getAdminFactory() {
        return new AdminFactory() {
            @Override
            public IDao<Administrateur> getAdmin(Class<? extends IDao<Administrateur>> daoAdmin) {
                if (daoAdmin == HibernateAdminDaoImpl.class) {
                    return new HibernateAdminDaoImpl(Administrateur.class);
                }
                return null;
            }
        };
    }

    @Override
    public ChauffeurFactory getChauffeurFactory() {
        return new ChauffeurFactory() {
            @Override
            public IDao<Chauffeur> getChauffeur(Class<? extends IDao<Chauffeur>> daoChauffeur) {
                if (daoChauffeur == HibernateChauffeurDaoImpl.class) {
                    return new HibernateChauffeurDaoImpl(Chauffeur.class);
                }
                return null;
            }
        };
    }

    @Override
    public ClientFactory getClientFactory() {
        return new ClientFactory() {
            @Override
            public IDao<Client> getClient(Class<? extends IDao<Client>> daoClient) {
                if (daoClient == HibernateClientDaoImpl.class) {
                    return new HibernateClientDaoImpl(Client.class);
                }
                return null;
            }
        };
    }

    @Override
    public EmployeFactory getEmployeFactory() {
        return new EmployeFactory() {
            @Override
            public IDao<Employe> getEmploye(Class<? extends IDao<Employe>> daoEmploye) {
                if (daoEmploye == HibernateEmployeeDaoImpl.class) {
                    return new HibernateEmployeeDaoImpl(Employe.class);
                }
                return null;
            }
        };
    }

    @Override
    public ReservationFactory getReservationFactory() {
        return new ReservationFactory() {
            @Override
            public IDao<Reservation> getReservation(Class<? extends IDao<Reservation>> daoReservation) {
                if (daoReservation == HibernateReservationDaoImpl.class) {
                    return new HibernateReservationDaoImpl(Reservation.class);
                }
                return null;
            }
        };
    }

    @Override
    public VehiculeFactory getVehiculeFactory() {
        return new VehiculeFactory() {
            @Override
            public IDao<Vehicule> getVehicule(Class<? extends IDao<Vehicule>> daoVehicule) {
                if (daoVehicule == HibernateVehiculeDaoImpl.class) {
                    return new HibernateVehiculeDaoImpl(Vehicule.class);
                }
                return null;
            }
        };
    }
}
