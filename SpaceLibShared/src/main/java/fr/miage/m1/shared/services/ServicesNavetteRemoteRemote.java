/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.shared.services;

import fr.miage.m1.shared.exceptions.AucuneDestinationException;
import fr.miage.m1.shared.exceptions.NavetteInexistanteException;
import fr.miage.m1.shared.exceptions.ReservationExistanteException;
import fr.miage.m1.shared.exceptions.StationInexistanteException;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Remote
public interface ServicesNavetteRemoteRemote {
    public void voyageInitie(String[] infosCompte, String navette, String destination)
                throws TokenInvalideException, NavetteInexistanteException,
                       StationInexistanteException ;
    
    public void voyageAcheve(String[] infosCompte)
                throws TokenInvalideException ;
    
    public List<String> recupererListeNavettes(String[] infosCompte)
                        throws TokenInvalideException;
    
    public String recupererInformationDestination(String[] infosCompte, String navette)
                  throws TokenInvalideException, NavetteInexistanteException;
    
    public void reserve(String[] infosCompte, String navette)
                throws TokenInvalideException, NavetteInexistanteException,
                       ReservationExistanteException, AucuneDestinationException;
}
