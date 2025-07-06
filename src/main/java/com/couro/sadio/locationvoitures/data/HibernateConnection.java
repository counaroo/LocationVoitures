package com.couro.sadio.locationvoitures.data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateConnection {
    private static HibernateConnection instance;
    private final SessionFactory sessionFactory;

    private HibernateConnection() {
        try {
            // Créer le registre de services
            StandardServiceRegistry ssr = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build();

            // Créer les métadonnées
            Metadata meta = new MetadataSources(ssr)
                    .getMetadataBuilder()
                    .build();

            // Créer la SessionFactory
            sessionFactory = meta.getSessionFactoryBuilder().build();

            System.out.println("SessionFactory créée avec succès");

        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation d'Hibernate: " + e.getMessage());
            e.printStackTrace();
            throw new ExceptionInInitializerError("Impossible d'initialiser Hibernate: " + e.getMessage());
        }
    }

    public static synchronized HibernateConnection getInstance() {
        if (instance == null) {
            instance = new HibernateConnection();
        }
        return instance;
    }

    /**
     * Retourne une nouvelle session à chaque appel
     * IMPORTANT: La session doit être fermée après utilisation
     */
    public Session getSession() {
        return sessionFactory.openSession();
    }

    /**
     * Retourne la session courante du thread (si configurée)
     */
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Ferme la SessionFactory
     */
    public void closeSessionFactory() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}