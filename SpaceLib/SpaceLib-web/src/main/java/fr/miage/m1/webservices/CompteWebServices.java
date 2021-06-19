/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.webservices;

import fr.miage.m1.server.services.ServicesCompteLocal;
import fr.miage.m1.shared.exceptions.CompteInexistantException;
import fr.miage.m1.shared.exceptions.MotDePasseInvalideException;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author DamienAvetta-Raymond
 */
@WebService(serviceName = "CompteWebServices")
public class CompteWebServices {

    @EJB
    private ServicesCompteLocal ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Web Service > Add Operation"

    @WebMethod(operationName = "authentifier")
    public String[] authentifier(@WebParam(name = "identifiant") String identifiant, @WebParam(name = "motDePasse") String motDePasse) 
                    throws CompteInexistantException, MotDePasseInvalideException {
        return ejbRef.authentifier(identifiant, motDePasse);
    }
    
}
