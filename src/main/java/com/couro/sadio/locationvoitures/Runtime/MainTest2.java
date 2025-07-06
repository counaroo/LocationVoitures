package com.couro.sadio.locationvoitures.Runtime;

import com.couro.sadio.locationvoitures.entities.Chauffeur;
import com.couro.sadio.locationvoitures.modele.ChauffeurModele;

public class MainTest2 {
    public static void main(String[] args) {
        ChauffeurModele chauffeurModele = new ChauffeurModele();
        Chauffeur chauffeur1 = new Chauffeur(2,"Leclerc","Charle",777777777,true);
        chauffeurModele.update(chauffeur1);
        chauffeurModele.lister();
    }
}
