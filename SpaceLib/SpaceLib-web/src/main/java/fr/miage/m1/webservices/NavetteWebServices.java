/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.webservices;

import fr.miage.m1.server.services.ServicesNavetteLocal;
import fr.miage.m1.shared.exceptions.AucuneDestinationException;
import fr.miage.m1.shared.exceptions.DestinationIncorrecteException;
import fr.miage.m1.shared.exceptions.IdReservationIncorrecteException;
import fr.miage.m1.shared.exceptions.MauvaisUtilisateurReservationException;
import fr.miage.m1.shared.exceptions.NavetteExistanteException;
import fr.miage.m1.shared.exceptions.NavetteInexistanteException;
import fr.miage.m1.shared.exceptions.NavettePasAReviserException;
import fr.miage.m1.shared.exceptions.NavettePassagersException;
import fr.miage.m1.shared.exceptions.NavettesIndisponibleException;
import fr.miage.m1.shared.exceptions.QuaiIndisponibleException;
import fr.miage.m1.shared.exceptions.RevisionInexistanteException;
import fr.miage.m1.shared.exceptions.RoleInvalideException;
import fr.miage.m1.shared.exceptions.StationInexistanteException;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
import java.text.ParseException;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author DamienAvetta-Raymond
 */
@WebService(serviceName = "NavetteWebServices")
public class NavetteWebServices {

    @EJB
    private ServicesNavetteLocal ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Web Service > Add Operation"

    @WebMethod(operationName = "debutRevision")
    public void debutRevision(@WebParam(name = "identifiant") String identifiant, @WebParam(name = "token") String token, @WebParam(name = "navette") String navette) 
                throws TokenInvalideException, NavetteInexistanteException, 
                       NavettePasAReviserException, RoleInvalideException {
        String[] infosCompte;
        
        infosCompte = new String[2];
        infosCompte[0] = identifiant;
        infosCompte[1] = token;
        ejbRef.debutRevision(infosCompte, navette);
    }

    @WebMethod(operationName = "finRevision")
    public void finRevision(@WebParam(name = "identifiant") String identifiant, @WebParam(name = "token") String token, @WebParam(name = "navette") String navette) 
                throws TokenInvalideException, NavetteInexistanteException, 
                       RevisionInexistanteException, RoleInvalideException {
        String[] infosCompte;
        
        infosCompte = new String[2];
        infosCompte[0] = identifiant;
        infosCompte[1] = token;
        ejbRef.finRevision(infosCompte, navette);
    }

    @WebMethod(operationName = "recupererInformationsRevisions")
    public List<String> recupererInformationsRevisions(@WebParam(name = "identifiant") String identifiant, @WebParam(name = "token") String token,
                                                       @WebParam(name = "stationAttache") String stationAttache) 
                        throws TokenInvalideException, RoleInvalideException {
        String[] infosCompte;
        
        infosCompte = new String[2];
        infosCompte[0] = identifiant;
        infosCompte[1] = token;
        return ejbRef.recupererInformationsRevisions(infosCompte, stationAttache);
    }
    
    @WebMethod(operationName = "ajouterNavette")
    public void ajouterNavette(@WebParam(name = "identifiant") String identifiant, @WebParam(name = "token") String token, @WebParam(name = "navette") String navette,
                               @WebParam(name = "nbPassagers") String nbPassagers, @WebParam(name = "station") String station) 
                throws TokenInvalideException, RoleInvalideException, 
                       NavetteExistanteException, QuaiIndisponibleException, 
                       StationInexistanteException {
        String[] infosCompte;
        int nbPassagersInt;
        
        infosCompte = new String[2];
        infosCompte[0] = identifiant;
        infosCompte[1] = token;
        nbPassagersInt = Integer.parseInt(nbPassagers);
        ejbRef.ajouterNavette(infosCompte, navette, nbPassagersInt, station);
    }
    
    @WebMethod(operationName = "reserve")
    public Long reserve(@WebParam(name = "identifiant") String identifiant, @WebParam(name = "token") String token, @WebParam(name = "stationAttachement") String stationAttachement, 
                        @WebParam(name = "destination") String destination, @WebParam(name = "dateDepart") String dateDepart, 
                        @WebParam(name = "nbPassagers") int nbPassagers) 
                throws TokenInvalideException, AucuneDestinationException, 
                       QuaiIndisponibleException, StationInexistanteException, 
                       NavettesIndisponibleException, ParseException, 
                       DestinationIncorrecteException, NavettePassagersException {
        String[] infosCompte;
        
        infosCompte = new String[2];
        infosCompte[0] = identifiant;
        infosCompte[1] = token;
        return ejbRef.reserve(infosCompte, stationAttachement, destination, dateDepart, nbPassagers);
    }

    @WebMethod(operationName = "annule")
    public void annule(@WebParam(name = "identifiant") String identifiant, @WebParam(name = "token") String token, 
                       @WebParam(name = "idReservation") String idReservation) 
                throws TokenInvalideException, IdReservationIncorrecteException, 
                       MauvaisUtilisateurReservationException {
        String[] infosCompte;
        
        infosCompte = new String[2];
        infosCompte[0] = identifiant;
        infosCompte[1] = token;
        ejbRef.annule(infosCompte, idReservation);
    }
}
