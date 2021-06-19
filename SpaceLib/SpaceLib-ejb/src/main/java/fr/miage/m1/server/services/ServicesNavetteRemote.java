/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.services;

import fr.miage.m1.server.metier.GestionNavetteLocal;
import fr.miage.m1.shared.exceptions.AucuneDestinationException;
import fr.miage.m1.shared.exceptions.NavetteInexistanteException;
import fr.miage.m1.shared.exceptions.ReservationExistanteException;
import fr.miage.m1.shared.exceptions.StationInexistanteException;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
import fr.miage.m1.shared.services.ServicesNavetteRemoteRemote;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Stateless
public class ServicesNavetteRemote implements ServicesNavetteRemoteRemote {

    @EJB
    private GestionNavetteLocal gestionNavette;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void voyageInitie(String[] infosCompte, String navette, String destination) 
                throws TokenInvalideException, NavetteInexistanteException, StationInexistanteException {
        gestionNavette.ajouterDestination(infosCompte, navette, destination);
    }

    @Override
    public void voyageAcheve(String[] infosCompte) 
                throws TokenInvalideException {
        gestionNavette.voyageAcheve(infosCompte);
    }

    @Override
    public List<String> recupererListeNavettes(String[] infosCompte) 
                        throws TokenInvalideException {
        return gestionNavette.recupererListeNavettes(infosCompte);
    }

    @Override
    public String recupererInformationDestination(String[] infosCompte, String navette) 
                  throws TokenInvalideException, NavetteInexistanteException {
        return gestionNavette.recupererInformationDestination(infosCompte, navette);
    }

    @Override
    public void reserve(String[] infosCompte, String navette) 
                throws TokenInvalideException, NavetteInexistanteException, 
                       ReservationExistanteException, AucuneDestinationException {
        gestionNavette.reserve(infosCompte, navette);
    }
}
