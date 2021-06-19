/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.metier;

import fr.miage.m1.server.entities.Compte;
import fr.miage.m1.server.entities.Navette;
import fr.miage.m1.server.entities.Operation;
import fr.miage.m1.server.entities.Quai;
import fr.miage.m1.server.entities.Revision;
import fr.miage.m1.server.entities.Station;
import fr.miage.m1.server.entities.Voyage;
import fr.miage.m1.server.facades.CompteFacadeLocal;
import fr.miage.m1.server.facades.NavetteFacadeLocal;
import fr.miage.m1.server.facades.OperationFacadeLocal;
import fr.miage.m1.server.facades.QuaiFacadeLocal;
import fr.miage.m1.server.facades.RevisionFacadeLocal;
import fr.miage.m1.server.facades.StationFacadeLocal;
import fr.miage.m1.server.facades.VoyageFacadeLocal;
import fr.miage.m1.shared.exceptions.AucuneDestinationException;
import fr.miage.m1.shared.exceptions.NavetteExistanteException;
import fr.miage.m1.shared.exceptions.NavetteInexistanteException;
import fr.miage.m1.shared.exceptions.NavettePasAReviserException;
import fr.miage.m1.shared.exceptions.NavettesIndisponibleException;
import fr.miage.m1.shared.exceptions.QuaiIndisponibleException;
import fr.miage.m1.shared.exceptions.ReservationExistanteException;
import fr.miage.m1.shared.exceptions.RevisionInexistanteException;
import fr.miage.m1.shared.exceptions.RoleInvalideException;
import fr.miage.m1.shared.exceptions.StationInexistanteException;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private RevisionFacadeLocal revisionFacade;

    @EJB
    private VoyageFacadeLocal voyageFacade;

    @EJB
    private QuaiFacadeLocal quaiFacade;

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
    
    private final String REVISION_NECESSAIRE = "Révision nécessaire.";
    
    private final String DEBUT_REVISION = "Début révision.";
    
    private final String FIN_REVISION = "Fin révision.";
    
    private final String ERREUR_DEJA_RESERVER = "Erreur 6 : Une réservation a déjà eu lieu.";
    
    private final String ERREUR_DESTINATION_INEXISTANTE = "Erreur 7 : Aucune destination lors de la réservation.";

    private final String ERREUR_NAVETTE_PAS_A_REVISER = "Erreur 8 : La navette n'a pas à être révisé.";
    
    private final String ERREUR_REVISION_INEXISTANTE = "Erreur 9 : Révision inexistante.";
    
    private final String ERREUR_STATION_INEXISTANTE = "Erreur 10 : Station inexistante";
    
    private final String ERREUR_NAVETTE_EXISTANTE = "Erreur 12 : La navette existe déjà.";
    
    private final String ERREUR_QUAI_INDISPONIBLE = "Erreur 13 : aucun quai n'est disponible pour accueillir la navette.";
    
    private final String ERREUR_NAVETTES_INDISPONIBLE = "Erreur 14 : aucune navette n'est disponible pour le voyage demandé.";
    
    @Override
    public void reserve(String[] infosCompte, String stationAttachement, String destination, 
                        String dateArrivee, int nbPassagers)
                throws TokenInvalideException, NavetteInexistanteException,
                       AucuneDestinationException, QuaiIndisponibleException,
                       StationInexistanteException, NavettesIndisponibleException,
                       ParseException {
        Quai quaiDestination;
        Compte compte;
        Navette navetteUtilise;
        Station stationDestination;
        Station stationActuelle;
        Voyage voyage;
        String operation;
        Operation operationAjoute;
        
        compte = compteFacade.verificationAcces(infosCompte);
        stationActuelle = stationFacade.findByName(stationAttachement);
        if (stationActuelle == null) {
            throw new StationInexistanteException(ERREUR_STATION_INEXISTANTE);
        }
        stationDestination = stationFacade.findByName(destination);
        if (stationDestination == null) {
            throw new AucuneDestinationException(ERREUR_DESTINATION_INEXISTANTE);
        }
        navetteUtilise = stationFacade.findNavetteDisponible(stationActuelle);
        if (navetteUtilise == null) {
            throw new NavettesIndisponibleException(ERREUR_NAVETTES_INDISPONIBLE);
        }
        quaiDestination = stationFacade.findQuaiDisponible(stationDestination);
        if (quaiDestination == null) {
            throw new QuaiIndisponibleException(ERREUR_QUAI_INDISPONIBLE);
        }
        navetteUtilise.setCompte(compte);
        compte.setNavette(navetteUtilise);
        voyage = voyageFacade.creerVoyage(navetteUtilise, navetteUtilise.getQuai(), quaiDestination, dateArrivee, nbPassagers);
        navetteFacade.ajouterVoyage(navetteUtilise, voyage);
        quaiFacade.ajouterVoyage(navetteUtilise.getQuai(), quaiDestination, voyage);
        operation = VOYAGE_INITIE + " Date de départ : " + new Date()
                                  + " - Date d'arrivée prévue : " + dateArrivee
                                  + " - ID de la station de départ : " + stationActuelle.getId()
                                  + " - ID de la station d'arrivée : " + stationDestination.getId()
                                  + " - ID de la navette : " +  navetteUtilise.getId()
                                  + " - Nombre de passagers : " + nbPassagers
                                  + " - Date de création de l'opération : " + new Date();
        operationAjoute = operationFacade.ajouterOperation(navetteUtilise, operation);
        navetteUtilise.ajouterOperation(operationAjoute);
        compte.ajouterOperation(operationAjoute);
        navetteFacade.edit(navetteUtilise);
        compteFacade.edit(compte);
    }

    @Override
    public void voyageAcheve(String[] infosCompte)
                throws TokenInvalideException {
        Compte compte;
        Navette navetteUtilise;
        List<Voyage> voyages;
        Voyage voyage;
        String operation;
        Quai quaiDestination;
        Quai quaiDepart;
        Operation operationAjoute;
        
        compte = compteFacade.verificationAcces(infosCompte);
        navetteUtilise = compte.getNavette();
        voyages = navetteUtilise.getVoyages();
        voyage = voyageFacade.voyageAcheve(voyages.get((voyages.size() - 1)));
        quaiDepart = voyage.getDepart();
        quaiDestination = voyage.getDestination();
        navetteUtilise.setQuai(quaiDestination);
        quaiDestination.setNavette(navetteUtilise);
        navetteUtilise.setCompte(null);
        compte.setNavette(null); 
        operation = VOYAGE_ACHEVE + " Date de départ : " + voyage.getDateDepart()
                                  + " - Date d'arrivée : " + new Date()
                                  + " - Identifiant de la station de départ : " + quaiDepart.getStation().getId()
                                  + " - Identifiant de la station d'arrivée : " + quaiDestination.getStation().getId()
                                  + " - Identifiant du quais de départ : " + quaiDepart.getId()
                                  + " - Identifiant du quais d'arrivé : " + quaiDestination.getId()
                                  + " - nombre de passagers : " + voyage.getNbPassagers()
                                  + " - Emprunteur de la navette : " + compte.getIdentifiant()
                                  + " - Date de création de l'opération : " + new Date();
        operationAjoute = operationFacade.ajouterOperation(navetteUtilise, operation);
        navetteUtilise.ajouterOperation(operationAjoute);
        compte.ajouterOperation(operationAjoute);
        if (navetteUtilise.getVoyages().size() % 3 == 0) {
            navetteUtilise.setaReviser(true);
            operation = REVISION_NECESSAIRE + " Identifiant de la station : " + quaiDestination.getStation().getId()
                                            + " - Identifiant du quai : " + quaiDestination.getId()
                                            + " - Date de création de l'opération : " + new Date();
            operationAjoute = operationFacade.ajouterOperation(navetteUtilise, operation);
            navetteUtilise.ajouterOperation(operationAjoute);
            compte.ajouterOperation(operationAjoute);
        }
        navetteFacade.edit(navetteUtilise);
        quaiFacade.edit(quaiDepart);
        compteFacade.edit(compte);
    }

    @Override
    public void debutRevision(String[] infosCompte, String navette)
                throws TokenInvalideException, NavetteInexistanteException,
                       NavettePasAReviserException, RoleInvalideException {
        Navette navetteAReviser;
        Compte compte;
        List<String> rolesAutorises;
        Quai quaiNavetteAReviser;
        Revision revision;
        String operation;
        Operation operationAjoute;
        
        rolesAutorises = new ArrayList<String>();
        rolesAutorises.add("Mécanicien");
        rolesAutorises.add("Administrateur");
        compte = compteFacade.verificationAcces(infosCompte, rolesAutorises);
        navetteAReviser = navetteFacade.verificationNavette(navette);
        if (!navetteAReviser.isaReviser()) {
            throw new NavettePasAReviserException(ERREUR_NAVETTE_PAS_A_REVISER);
        }
        revision = revisionFacade.creationRevision(navetteAReviser, compte);
        quaiNavetteAReviser = navetteAReviser.getQuai();
        quaiNavetteAReviser.ajouterRevision(revision);
        navetteAReviser.ajouterRevision(revision);
        compte.ajouterRevision(revision);
        operation = DEBUT_REVISION + " Identifiant de la station : " + quaiNavetteAReviser.getStation().getId()
                                   + " - Identifiant du quai : " + quaiNavetteAReviser.getId()
                                   + " - Mécanicien en charge : " + compte.getIdentifiant()
                                   + " - Date de création de l'opération : " + new Date();
        operationAjoute = operationFacade.ajouterOperation(navetteAReviser, operation);
        navetteAReviser.ajouterOperation(operationAjoute);
        compteFacade.edit(compte);
        quaiFacade.edit(quaiNavetteAReviser);
        navetteFacade.edit(navetteAReviser);
    }

    @Override
    public void finRevision(String[] infosCompte, String navette)
                throws TokenInvalideException, NavetteInexistanteException,
                       RevisionInexistanteException, RoleInvalideException {
        Navette navetteAReviser;
        List<String> rolesAutorises;
        Revision revision;
        String operation;
        Operation operationAjoute;
        Compte compte;
        
        rolesAutorises = new ArrayList<String>();
        rolesAutorises.add("Mécanicien");
        rolesAutorises.add("Administrateur");
        compte = compteFacade.verificationAcces(infosCompte, rolesAutorises);
        navetteAReviser = navetteFacade.verificationNavette(navette);
        revision = navetteAReviser.getRevisions().get((navetteAReviser.getRevisions().size() - 1));
        if (revision == null || !revision.isEnCours()) {
            throw new RevisionInexistanteException(ERREUR_REVISION_INEXISTANTE);
        }
        revision.setEnCours(false);
        navetteAReviser.setaReviser(false);
        operation = FIN_REVISION + " ID de la station : " + revision.getQuai().getStation().getId()
                                 + " - ID du quai : " + revision.getQuai().getId()
                                 + " - Mécanicien en charge : " + compte.getIdentifiant()
                                 + " - Date de création de l'opération : " + new Date();
        operationAjoute = operationFacade.ajouterOperation(navetteAReviser, operation);
        navetteAReviser.ajouterOperation(operationAjoute);
        revisionFacade.edit(revision);
        navetteFacade.edit(navetteAReviser);
    }

    @Override
    public List<String> recupererInformationsRevisions(String[] infosCompte)
                        throws TokenInvalideException, RoleInvalideException {
        List<Navette> navettes;
        List<String> nomsNavettes;
        List<String> rolesAutorises;
        
        rolesAutorises = new ArrayList<String>();
        rolesAutorises.add("Mécanicien");
        rolesAutorises.add("Administrateur");
        
        compteFacade.verificationAcces(infosCompte, rolesAutorises);
        navettes = navetteFacade.listNavettesAReviser();
        nomsNavettes = new ArrayList<String>();
        for (int i = 0 ; i < navettes.size() ; i++) {
            nomsNavettes.add(navettes.get(i).getNom());
        }
        return nomsNavettes;
    }

    @Override
    public void ajouterNavette(String[] infosCompte, String navette, int nbPassagers, String station)
                throws TokenInvalideException, RoleInvalideException, 
                       NavetteExistanteException, QuaiIndisponibleException,
                       StationInexistanteException {
        List<String> rolesAutorises;
        Station stationRecherche;
        Quai quai;
        Navette navetteAjoutee;
        
        rolesAutorises = new ArrayList<String>();
        rolesAutorises.add("Administrateur");
        
        compteFacade.verificationAcces(infosCompte, rolesAutorises);
        if (navetteFacade.findByName(navette) != null) {
            throw new NavetteExistanteException(ERREUR_NAVETTE_EXISTANTE);
        }
        stationRecherche = stationFacade.findByName(station);
        if (stationRecherche == null) {
            throw new StationInexistanteException(ERREUR_STATION_INEXISTANTE);
        }
        if (stationRecherche.quaisDisponibles() <= 0) {
            throw new QuaiIndisponibleException(ERREUR_QUAI_INDISPONIBLE);
        }
        quai = quaiFacade.attributionQuai(stationRecherche);
        navetteAjoutee = navetteFacade.ajouterNavette(navette, nbPassagers, quai);
        quai.setNavette(navetteAjoutee);
        quaiFacade.edit(quai);
    }
}
