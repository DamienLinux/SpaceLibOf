/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.webservices;

import fr.miage.m1.server.services.ServicesNavetteLocal;
import fr.miage.m1.shared.exceptions.NavetteExistanteException;
import fr.miage.m1.shared.exceptions.NavetteInexistanteException;
import fr.miage.m1.shared.exceptions.NavettePasAReviserException;
import fr.miage.m1.shared.exceptions.RevisionInexistanteException;
import fr.miage.m1.shared.exceptions.RoleInvalideException;
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
@WebService(serviceName = "NewWebService")
public class NavetteWebServices {

    @EJB
    private ServicesNavetteLocal ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Web Service > Add Operation"

    @WebMethod(operationName = "debutRevision")
    public void debutRevision(@WebParam(name = "infosCompte") String[] infosCompte, @WebParam(name = "navette") String navette) 
                throws TokenInvalideException, NavetteInexistanteException, 
                       NavettePasAReviserException, RoleInvalideException {
        ejbRef.debutRevision(infosCompte, navette);
    }

    @WebMethod(operationName = "finRevision")
    public void finRevision(@WebParam(name = "infosCompte") String[] infosCompte, @WebParam(name = "navette") String navette) 
                throws TokenInvalideException, NavetteInexistanteException, 
                       RevisionInexistanteException, RoleInvalideException {
        ejbRef.finRevision(infosCompte, navette);
    }

    @WebMethod(operationName = "recupererInformationsRevisions")
    public List<String> recupererInformationsRevisions(@WebParam(name = "infosCompte") String[] infosCompte) 
                        throws TokenInvalideException, RoleInvalideException {
        return ejbRef.recupererInformationsRevisions(infosCompte);
    }
    
    @WebMethod(operationName = "ajouterNavette")
    public void ajouterNavette(@WebParam(name = "infosCompte") String[] infosCompte,@WebParam(name = "navette") String navette) 
                throws TokenInvalideException, RoleInvalideException, 
                       NavetteExistanteException {
        ejbRef.ajouterNavette(infosCompte, navette);
    }
    
}
