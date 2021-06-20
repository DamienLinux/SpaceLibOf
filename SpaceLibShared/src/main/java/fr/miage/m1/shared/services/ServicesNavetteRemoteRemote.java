/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.shared.services;

import fr.miage.m1.shared.exceptions.AucuneDestinationException;
import fr.miage.m1.shared.exceptions.DestinationIncorrecteException;
import fr.miage.m1.shared.exceptions.IdReservationIncorrecteException;
import fr.miage.m1.shared.exceptions.MauvaisUtilisateurReservationException;
import fr.miage.m1.shared.exceptions.NavetteInexistanteException;
import fr.miage.m1.shared.exceptions.NavettePassagersException;
import fr.miage.m1.shared.exceptions.NavettesIndisponibleException;
import fr.miage.m1.shared.exceptions.QuaiIndisponibleException;
import fr.miage.m1.shared.exceptions.ReservationExistanteException;
import fr.miage.m1.shared.exceptions.ReservationInexistanteException;
import fr.miage.m1.shared.exceptions.StationInexistanteException;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
import java.text.ParseException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Remote
public interface ServicesNavetteRemoteRemote {
    public void voyageAcheve(String[] infosCompte)
                throws TokenInvalideException ;
    
    public void annule(String[] infosCompte, String idReservation) throws TokenInvalideException, IdReservationIncorrecteException, MauvaisUtilisateurReservationException;
    
    public void reserve(String[] infosCompte, String stationAttachement, String destination, 
                        String dateDepart, int nbPassagers)
                throws TokenInvalideException, AucuneDestinationException, 
                       QuaiIndisponibleException, StationInexistanteException, 
                       NavettesIndisponibleException, ParseException,
                       DestinationIncorrecteException, NavettePassagersException;
    
    public void debutVoyageReserve(String[] infosCompte)
                throws TokenInvalideException, ReservationInexistanteException;
}
