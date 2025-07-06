package com.couro.sadio.locationvoitures.factory;

public abstract class AbstractAppFactory {
    public abstract AdminFactory getAdminFactory();
    public abstract ChauffeurFactory getChauffeurFactory();
    public abstract ClientFactory getClientFactory();
    public abstract EmployeFactory getEmployeFactory();
    public abstract ReservationFactory getReservationFactory();
    public abstract VehiculeFactory getVehiculeFactory();
}
