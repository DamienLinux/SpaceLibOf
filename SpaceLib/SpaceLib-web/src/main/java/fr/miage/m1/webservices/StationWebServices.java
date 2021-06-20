/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.webservices;

import fr.miage.m1.server.services.ServicesStationLocal;
import fr.miage.m1.shared.exceptions.RoleInvalideException;
import fr.miage.m1.shared.exceptions.StationExistanteException;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author DamienAvetta-Raymond
 */
@WebService(serviceName = "StationWebServices")
public class StationWebServices {

    @EJB
    private ServicesStationLocal ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Web Service > Add Operation"

    @WebMethod(operationName = "ajouterStation")
    public void ajouterStation(@WebParam(name = "identifiant") String identifiant, @WebParam(name = "token") String token, @WebParam(name = "nom") String nom, 
            @WebParam(name = "localisation") String localisation, @WebParam(name = "nbQuais") int nbQuais) 
                throws TokenInvalideException, StationExistanteException,
                       RoleInvalideException {
        String[] infosCompte;
        
        infosCompte = new String[2];
        infosCompte[0] = identifiant;
        infosCompte[1] = token;
        ejbRef.ajouterStation(infosCompte, nom, localisation, nbQuais);
    }
    
    @WebMethod(operationName = "carteStation")
    public String carteStation() {
        return ejbRef.carteStations();
    }
}
