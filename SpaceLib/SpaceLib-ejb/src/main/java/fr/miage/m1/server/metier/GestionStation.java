/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.metier;

import fr.miage.m1.server.entities.Quai;
import fr.miage.m1.server.entities.Station;
import fr.miage.m1.server.facades.CompteFacadeLocal;
import fr.miage.m1.server.facades.QuaiFacadeLocal;
import fr.miage.m1.server.facades.StationFacadeLocal;
import fr.miage.m1.server.facades.VoyageFacadeLocal;
import fr.miage.m1.shared.exceptions.RoleInvalideException;
import fr.miage.m1.shared.exceptions.StationExistanteException;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
    @EJB
    private VoyageFacadeLocal voyageFacade;
    
    private final String ERREUR_STATION_EXISTANTE = "Erreur 11 : Station existe déjà.";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public String carteStations() {
        
        
        List<Station> listeStations = stationFacade.findAll();
        
        String carte = "";
        
        for (Station station : listeStations) {
            carte += "Station " + station.getNom() + " : " + station.getLocalisation() + '\n';
        }
        return carte;
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

    @Override
    public List<String> listeStations(String stationRattachement) {
        List<Station> stations;
        List<String> nomsStation;
        
        stations = stationFacade.findAll();
        nomsStation = new ArrayList<String>();
        for (Station station : stations) {
            if (!station.getNom().equals(stationRattachement)) {
                nomsStation.add(station.getNom());
            }
        }
        return nomsStation;
    }
    
    @Override
    public Map<Station, Float> calculerQuaisDisponibles() {
        Map<Station, Float> disponibilite = new HashMap<>();
        List<Station> stations = stationFacade.findAll();
        List<Quai> quais;
        
        for(Station s : stations) {
            int dispoQuai = 0;
            quais = s.getQuais();
            for(Quai q : quais) {
                if(q.getNavette() == null) {
                    dispoQuai++;
                }
            }
            disponibilite.put(s, new Float((dispoQuai)/(s.getQuais().size())));
        }                
        return disponibilite;
    }
    
    @Override
    public Map<Station, Float> calculerNavettesDisponibles() {
        Map<Station, Float> disponibilite = new HashMap<>();
        
        return disponibilite;
    }

    @Override
    public String suggererVoyages(String[] infosCompte) 
           throws TokenInvalideException, RoleInvalideException {
        List<String> rolesAutorises;
        
        rolesAutorises = new ArrayList<String>();
        rolesAutorises.add("Conducteur");
        rolesAutorises.add("Administrateur");
        compteFacade.verificationAcces(infosCompte, rolesAutorises);
        String voyages = "Voyages recommandés :\n";
        Map<Station, Float> disponibilite = calculerQuaisDisponibles();
        for(Station s : disponibilite.keySet()) {
            if(disponibilite.get(s) <= 0.1) {
                voyages += s.getNom() + " (" + disponibilite.get(s) + "% de disponibilité)" + " vers " + stationPlusDispo(disponibilite).getNom() + "\n";
            } else if (disponibilite.get(s) >= 0.9) {
                voyages += s.getNom() + " (" + disponibilite.get(s) + "% de disponibilité)" + " vers " + stationMoinsDispo(disponibilite).getNom() + "\n";
            }
        }
        return voyages;
    }
    
    private Station stationPlusDispo(Map<Station, Float> disponibilite) {
        Station stationDispo = disponibilite.keySet().iterator().next();
        for(Station s : disponibilite.keySet()) {
            if(disponibilite.get(s) > disponibilite.get(stationDispo)) {
                stationDispo = s;
            }
        }
        return stationDispo;
    }
    
    private Station stationMoinsDispo(Map<Station, Float> disponibilite) {
        Station stationDispo = disponibilite.keySet().iterator().next();
        for(Station s : disponibilite.keySet()) {
            if(disponibilite.get(s) < disponibilite.get(stationDispo)) {
                stationDispo = s;
            }
        }
        return stationDispo;
    }

    @Override
    public Map<Station, Float> calculerQuaisDisponiblesJPlus10() {
        Map<Station, Float> disponibilite = new HashMap<>();
                      
        return disponibilite;
    }
}
