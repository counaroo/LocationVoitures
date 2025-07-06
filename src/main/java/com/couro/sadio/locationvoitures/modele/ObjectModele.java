package com.couro.sadio.locationvoitures.modele;

import com.couro.sadio.locationvoitures.dao.IDao;
import com.couro.sadio.locationvoitures.exception.DAOException;

import java.util.List;

/**
 * Classe abstraite générique pour éviter la répétition de code CRUD
 * @param <T> Le type d'entité manipulée
 */
public abstract class ObjectModele<T> {

    protected IDao<T> dao;

    /**
     * Constructeur qui initialise le DAO via la factory
     */
    public ObjectModele() {
        this.dao = initializeDao();
    }

    /**
     * Méthode abstraite pour initialiser le DAO spécifique à chaque entité
     * @return L'instance du DAO initialisée
     */
    protected abstract IDao<T> initializeDao();

    /**
     * Crée une nouvelle entité
     * @param entity L'entité à créer
     */
    public void create(T entity) {
        try {
            dao.create(entity);
            System.out.println("Entité créée avec succès : " + getEntityName(entity));
        } catch (DAOException e) {
            System.err.println("Erreur lors de la création de " + getEntityTypeName() + " : " + e.getMessage());
        }
    }

    /**
     * Lit une entité par son ID
     * @param id L'ID de l'entité à lire
     * @return L'entité trouvée ou null
     */
    public T read(int id) {
        try {
            T entity = dao.read(id);
            if (entity != null) {
                System.out.println(getEntityTypeName() + " trouvé : " + getEntityName(entity));
            } else {
                System.out.println(getEntityTypeName() + " non trouvé !");
            }
            return entity;
        } catch (DAOException e) {
            System.err.println("Erreur lors de la recherche de " + getEntityTypeName() + " : " + e.getMessage());
            return null;
        }
    }

    /**
     * Liste toutes les entités
     * @return La liste des entités
     */
    public List<T> lister() {
        try {
            List<T> entities = dao.list();
            System.out.println("Liste des " + getEntityTypeName() + "s : ");
            for (T entity : entities) {
                System.out.println(getEntityDetails(entity));
            }
            return entities;
        } catch (DAOException e) {
            System.err.println("Erreur lors de la récupération de la liste : " + e.getMessage());
            return null;
        }
    }

    /**
     * Met à jour une entité
     * @param entity L'entité à mettre à jour
     */
    public void update(T entity) {
        try {
            dao.update(entity);
            System.out.println(getEntityTypeName() + " modifié avec succès : " + getEntityName(entity));
        } catch (DAOException e) {
            System.err.println("Erreur lors de la modification de " + getEntityTypeName() + " : " + e.getMessage());
        }
    }

    /**
     * Supprime une entité par son ID
     * @param id L'ID de l'entité à supprimer
     */
    public void delete(int id) {
        try {
            dao.delete(id);
            System.out.println(getEntityTypeName() + " supprimé avec succès avec l'ID : " + id);
        } catch (DAOException e) {
            System.err.println("Erreur lors de la suppression de " + getEntityTypeName() + " : " + e.getMessage());
        }
    }

    /**
     * Méthode abstraite pour obtenir le nom de l'entité (ex: "Jean Dupont")
     * @param entity L'entité
     * @return Le nom de l'entité
     */
    protected abstract String getEntityName(T entity);

    /**
     * Méthode abstraite pour obtenir le nom du type d'entité (ex: "Administrateur")
     * @return Le nom du type d'entité
     */
    protected abstract String getEntityTypeName();

    /**
     * Méthode abstraite pour obtenir les détails complets de l'entité pour l'affichage
     * @param entity L'entité
     * @return Les détails de l'entité
     */
    protected abstract String getEntityDetails(T entity);
}