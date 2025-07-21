package com.couro.sadio.locationvoitures.dao.impl;

import com.couro.sadio.locationvoitures.entities.Chauffeur;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.couro.sadio.locationvoitures.entities.HibernateUtil;


public class HibernateChauffeurDaoImpl extends HibernateObjectDaoImpl<Chauffeur>{

    public HibernateChauffeurDaoImpl(Class<Chauffeur> typeObject) {
        super(typeObject);
    }


    public List<Chauffeur> findAllDispos() {
        Session session = sessionFactory.openSession();
        List<Chauffeur> chauffeurs = session.createQuery("FROM Chauffeur c WHERE c.disponibilite = true", Chauffeur.class).list();
        session.close();
        return chauffeurs;
    }

    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();


}
