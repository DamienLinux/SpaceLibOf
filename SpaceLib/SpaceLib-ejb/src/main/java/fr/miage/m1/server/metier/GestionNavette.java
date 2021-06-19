/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.metier;

import fr.miage.m1.server.entities.Compte;
import fr.miage.m1.server.entities.Navette;
import fr.miage.m1.server.entities.Station;
import fr.miage.m1.server.facades.CompteFacadeLocal;
import fr.miage.m1.server.facades.NavetteFacadeLocal;
import fr.miage.m1.server.facades.OperationFacadeLocal;
import fr.miage.m1.server.facades.StationFacadeLocal;
import fr.miage.m1.shared.exceptions.AucuneDestinationException;
import fr.miage.m1.shared.exceptions.NavetteExistanteException;
import fr.miage.m1.shared.exceptions.NavetteInexistanteException;
import fr.miage.m1.shared.exceptions.NavettePasAReviserException;
import fr.miage.m1.shared.exceptions.ReservationExistanteException;
import fr.miage.m1.shared.exceptions.RevisionInexistanteException;
import fr.miage.m1.shared.exceptions.RoleInvalideException;
import fr.miage.m1.shared.exceptions.StationInexistanteException;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Stateless
public class GestionNavette implements GestionNavetteLocal {

    @EJB
    private CompteFacadeLocal compteFacade;

    @EJB
    private OperationFacadeLocal operationFacade;

    @EJB
    private NavetteFacadeLocal navetteFacade;

    @EJB
    private StationFacadeLocal stationFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    private final String VOYAGE_INITIE = "Voyage initié.";
    
    private final String VOYAGE_ACHEVE = "Voyage achevé.";
    
    private final String DEBUT_REVISION = "Début révision.";
    
    private final String FIN_REVISION = "Fin révision.";
    
    private final String ERREUR_DEJA_RESERVER = "Erreur 6 : Une réservation a déjà eu lieu.";
    
    private final String ERREUR_DESTINATION_INEXISTANTE = "Erreur 7 : Aucune destination lors de la réservation.";

    private final String ERREUR_NAVETTE_PAS_A_REVISER = "Erreur 8 : La navette n'a pas à être révisé.";
    
    private final String ERREUR_REVISION_INEXISTANTE = "Erreur 9 : Révision inexistante.";
    
    private final String ERREUR_STATION_INEXISTANTE = "Erreur 10 : Station inexistante";
    
    private final String ERREUR_NAVETTE_EXISTANTE = "Erreur 12 : La navette existe déjà.";
    
     @Override
    public void ajouterDestination(String[] infosCompte, String navette, String destination)
                throws TokenInvalideException, NavetteInexistanteException,
                       StationInexistanteException {
        Station station;
        
        compteFacade.verificationAcces(infosCompte);
        station = stationFacade.findByName(destination);
        if (station == null) {
            throw new StationInexistanteException(ERREUR_STATION_INEXISTANTE);
        }
        navetteFacade.verificationNavette(navette).setDestination(station);
    }
    
    @Override
    public void reserve(String[] infosCompte, String navette)
                throws TokenInvalideException, NavetteInexistanteException,
                       ReservationExistanteException, AucuneDestinationException {
        Compte compte;
        Navette navetteUtilise;
        
        compte = compteFacade.verificationAcces(infosCompte);
        navetteUtilise = navetteFacade.verificationNavette(navette);
        if (navetteUtilise.getDestination() == null) {
            throw new AucuneDestinationException(ERREUR_DESTINATION_INEXISTANTE);
        }
        if (compte.getNavette() != null || navetteUtilise.compteExist(compte)) {
            throw new ReservationExistanteException(ERREUR_DEJA_RESERVER);
        }
        navetteUtilise.addCompte(compte);
        compte.setNavette(navetteUtilise);
        operationFacade.ajouterOperation(navetteUtilise, VOYAGE_INITIE);
    }

    @Override
    public void voyageAcheve(String[] infosCompte)
                throws TokenInvalideException {
        Compte compte;
        Navette navetteUtilise;
        
        compte = compteFacade.verificationAcces(infosCompte);
        navetteUtilise = compte.getNavette();
        navetteUtilise.setDestination(null);
        navetteUtilise.removeCompte(compte);
        compte.setNavette(null);
        operationFacade.ajouterOperation(navetteUtilise, VOYAGE_ACHEVE);
    }

    @Override
    public List<String> recupererListeNavettes(String[] infosCompte)
                        throws TokenInvalideException {
        List<Navette> navettes;
        List<String> nomsNavettes;
        
        compteFacade.verificationAcces(infosCompte);
        navettes = navetteFacade.listNavettes();
        nomsNavettes = new ArrayList<String>();
        for (int i = 0 ; i < navettes.size() ; i++) {
            nomsNavettes.add(navettes.get(i).getNom());
        }
        return nomsNavettes;
    }

    @Override
    public String recupererInformationDestination(String[] infosCompte, String navette)
                  throws TokenInvalideException, NavetteInexistanteException {
        compteFacade.verificationAcces(infosCompte);
        return navetteFacade.verificationNavette(navette).getDestination().getNom();
    }

    @Override
    public void debutRevision(String[] infosCompte, String navette)
                throws TokenInvalideException, NavetteInexistanteException,
                       NavettePasAReviserException, RoleInvalideException {
        Navette navetteAReviser;
        List<String> rolesAutorises;
        
        rolesAutorises = new ArrayList<String>();
        rolesAutorises.add("Mécanicien");
        compteFacade.verificationAcces(infosCompte, rolesAutorises);
        navetteAReviser = navetteFacade.verificationNavette(navette);
        if (!navetteAReviser.isaReviser()) {
            throw new NavettePasAReviserException(ERREUR_NAVETTE_PAS_A_REVISER);
        }
        navetteAReviser.setRevisionEnCours(true);
        operationFacade.ajouterOperation(navetteAReviser, DEBUT_REVISION);
    }

    @Override
    public void finRevision(String[] infosCompte, String navette)
                throws TokenInvalideException, NavetteInexistanteException,
                       RevisionInexistanteException, RoleInvalideException {
        Navette navetteAReviser;
        List<String> rolesAutorises;
        
        rolesAutorises = new ArrayList<String>();
        rolesAutorises.add("Mécanicien");
        compteFacade.verificationAcces(infosCompte, rolesAutorises);
        navetteAReviser = navetteFacade.verificationNavette(navette);
        if (!navetteAReviser.isRevisionEnCours()) {
            throw new RevisionInexistanteException(ERREUR_REVISION_INEXISTANTE);
        }
        navetteAReviser.setRevisionEnCours(false);
        operationFacade.ajouterOperation(navetteAReviser, FIN_REVISION);
    }

    @Override
    public List<String> recupererInformationsRevisions(String[] infosCompte)
                        throws TokenInvalideException, RoleInvalideException {
        List<Navette> navettes;
        List<String> nomsNavettes;
        List<String> rolesAutorises;
        
        rolesAutorises = new ArrayList<String>();
        rolesAutorises.add("Mécanicien");
        
        compteFacade.verificationAcces(infosCompte, rolesAutorises);
        navettes = navetteFacade.listNavettesAReviser();
        nomsNavettes = new ArrayList<String>();
        for (int i = 0 ; i < navettes.size() ; i++) {
            nomsNavettes.add(navettes.get(i).getNom());
        }
        return nomsNavettes;
    }

    @Override
    public void ajouterNavette(String[] infosCompte, String navette) 
                throws TokenInvalideException, RoleInvalideException, 
                       NavetteExistanteException {
        List<String> rolesAutorises;
        
        rolesAutorises = new ArrayList<String>();
        rolesAutorises.add("Administrateur");
        
        compteFacade.verificationAcces(infosCompte, rolesAutorises);
        if (navetteFacade.findByName(navette) != null) {
            throw new NavetteExistanteException(ERREUR_NAVETTE_EXISTANTE);
        }
        navetteFacade.ajouterNavette(navette);
    }
}
