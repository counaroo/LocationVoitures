package com.couro.sadio.locationvoitures.factory;

public class ConcreteFactory {
    public static <T> T getFactory(Class<T> factoryClass) {
        if (factoryClass == null) return null;

        // Ici on choisit l'implémentation (Hibernate par défaut)
        AbstractAppFactory appFactory = new HibernateAppFactory();

        if (factoryClass == AdminFactory.class) {
            return factoryClass.cast(appFactory.getAdminFactory());
        }
        else if (factoryClass == ChauffeurFactory.class) {
            return factoryClass.cast(appFactory.getChauffeurFactory());
        }
        else if (factoryClass == ClientFactory.class) {
            return factoryClass.cast(appFactory.getClientFactory());
        }
        else if (factoryClass == EmployeFactory.class) {
            return factoryClass.cast(appFactory.getEmployeFactory());
        }
        else if (factoryClass == ReservationFactory.class) {
            return factoryClass.cast(appFactory.getReservationFactory());
        }
        else if (factoryClass == VehiculeFactory.class) {
            return factoryClass.cast(appFactory.getVehiculeFactory());
        }
        else if (factoryClass == UtilisateurFactory.class) {
            return factoryClass.cast(appFactory.getUtilisateurFactory());
        }

        return null;
    }
}