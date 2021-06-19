/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.metier;

import fr.miage.m1.server.entities.Station;
import fr.miage.m1.server.facades.CompteFacadeLocal;
import fr.miage.m1.server.facades.QuaiFacadeLocal;
import fr.miage.m1.server.facades.StationFacadeLocal;
import fr.miage.m1.shared.exceptions.RoleInvalideException;
import fr.miage.m1.shared.exceptions.StationExistanteException;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Stateless
public class GestionStation implements GestionStationLocal {

    @EJB
    private QuaiFacadeLocal quaiFacade;

    @EJB
    private StationFacadeLocal stationFacade;

    @EJB
    private CompteFacadeLocal compteFacade;
    
    private final String ERREUR_STATION_EXISTANTE = "Erreur 11 : Station existe déjà.";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public List<Station> listeStations() {
        return stationFacade.findAll();
    }
    
    @Override
    public void ajouterStation(String[] infosCompte, String nom, String localisation, int nbQuais)
                throws TokenInvalideException, StationExistanteException,
                       RoleInvalideException {
        Station station;
        List<String> rolesAutorises;
        
        rolesAutorises = new ArrayList<String>();
        rolesAutorises.add("Administrateur");
        compteFacade.verificationAcces(infosCompte, rolesAutorises);
        station = stationFacade.findByName(nom);
        if (station != null) {
            throw new StationExistanteException(ERREUR_STATION_EXISTANTE);
        }
        station = stationFacade.creerStation(nom, localisation);
        stationFacade.edit(quaiFacade.creerQuais(station, nbQuais));
    }
}
