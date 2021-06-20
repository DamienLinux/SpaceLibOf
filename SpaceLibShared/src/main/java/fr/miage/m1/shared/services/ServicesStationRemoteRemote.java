/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.shared.services;

import fr.miage.m1.shared.entities.Station;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Thibault
 */
@Remote
public interface ServicesStationRemoteRemote {

    public String carteStations() throws TokenInvalideException;
    
     public List<String> listeStations(String stationRattachement);
      
}
