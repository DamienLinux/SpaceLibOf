/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.services;

import fr.miage.m1.server.metier.GestionNavetteLocal;
import fr.miage.m1.shared.exceptions.AucuneDestinationException;
import fr.miage.m1.shared.exceptions.DestinationIncorrecteException;
import fr.miage.m1.shared.exceptions.NavetteInexistanteException;
import fr.miage.m1.shared.exceptions.NavettePassagersException;
import fr.miage.m1.shared.exceptions.NavettesIndisponibleException;
import fr.miage.m1.shared.exceptions.QuaiIndisponibleException;
import fr.miage.m1.shared.exceptions.ReservationExistanteException;
import fr.miage.m1.shared.exceptions.ReservationInexistanteException;
import fr.miage.m1.shared.exceptions.StationInexistanteException;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
import fr.miage.m1.shared.services.ServicesNavetteRemoteRemote;
import java.text.ParseException;
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
    public void voyageAcheve(String[] infosCompte) 
                throws TokenInvalideException {
        gestionNavette.voyageAcheve(infosCompte);
    }

    @Override
    public void reserve(String[] infosCompte, String stationAttachement, String destination, 
                        String dateDepart, int nbPassagers)
                throws TokenInvalideException, AucuneDestinationException, 
                       QuaiIndisponibleException, StationInexistanteException, 
                       NavettesIndisponibleException, ParseException,
                       DestinationIncorrecteException, NavettePassagersException {
        gestionNavette.reserve(infosCompte, stationAttachement, destination, dateDepart, nbPassagers);
    }

    @Override
    public void debutVoyageReserve(String[] infosCompte) 
                throws TokenInvalideException, ReservationInexistanteException {
        gestionNavette.debutVoyageReserve(infosCompte);
    }
}
