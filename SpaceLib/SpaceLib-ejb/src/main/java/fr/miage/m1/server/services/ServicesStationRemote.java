/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.services;

import fr.miage.m1.server.entities.Station;
import fr.miage.m1.server.metier.GestionStation;
import fr.miage.m1.server.metier.GestionStationLocal;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
import fr.miage.m1.shared.services.ServicesStationRemoteRemote;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Thibault
 */
@Stateless
public class ServicesStationRemote implements ServicesStationRemoteRemote {

    
    @EJB
    private GestionStationLocal gestionStation;
     
    
    @Override
    public String carteStations() throws TokenInvalideException{
        return gestionStation.carteStations();
       
    }

    @Override
    public List<String> listeStations(String stationRattachement) {
        return gestionStation.listeStations(stationRattachement);
    }
    
}
