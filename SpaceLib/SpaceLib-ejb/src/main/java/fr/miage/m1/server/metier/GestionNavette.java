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
import fr.miage.m1.shared.exceptions.DestinationIncorrecteException;
import fr.miage.m1.shared.exceptions.IdReservationIncorrecteException;
import fr.miage.m1.shared.exceptions.MauvaisUtilisateurReservationException;
import fr.miage.m1.shared.exceptions.NavetteExistanteException;
import fr.miage.m1.shared.exceptions.NavetteInexistanteException;
import fr.miage.m1.shared.exceptions.NavettePasAReviserException;
import fr.miage.m1.shared.exceptions.NavettePassagersException;
import fr.miage.m1.shared.exceptions.NavettesIndisponibleException;
import fr.miage.m1.shared.exceptions.QuaiIndisponibleException;
import fr.miage.m1.shared.exceptions.ReservationInexistanteException;
import fr.miage.m1.shared.exceptions.RevisionInexistanteException;
import fr.miage.m1.shared.exceptions.RoleInvalideException;
import fr.miage.m1.shared.exceptions.StationInexistanteException;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private final String ERREUR_DESTINATION_INCORRECTE = "Erreur 6 : La station de destination est la même que celle de départ.";

    private final String ERREUR_DESTINATION_INEXISTANTE = "Erreur 7 : Aucune destination lors de la réservation.";

    private final String ERREUR_NAVETTE_PAS_A_REVISER = "Erreur 8 : La navette n'a pas à être révisé.";

    private final String ERREUR_REVISION_INEXISTANTE = "Erreur 9 : Révision inexistante.";

    private final String ERREUR_STATION_INEXISTANTE = "Erreur 10 : Station inexistante";

    private final String ERREUR_NAVETTE_EXISTANTE = "Erreur 12 : La navette existe déjà.";

    private final String ERREUR_QUAI_INDISPONIBLE = "Erreur 13 : aucun quai n'est disponible pour accueillir la navette.";

    private final String ERREUR_NAVETTES_INDISPONIBLE = "Erreur 14 : aucune navette n'est disponible pour le voyage demandé.";

    private final String ERREUR_PASSAGER_MAXIUM = "Erreur 15 : Pas assez de place sur cette navette.";

    private final String ERREUR_ID_RESERVATION_INCORRECTE = "Erreur 16 : L'ID de réservation est incorrecte.";

    private final String ERREUR_RESERVATION_MAUVAIS_UTILISATEUR = "Erreur 17 : Cette réservation ne vous appartient pas.";

    public final String ERREUR_RESERVATION_INEXISTANTE = "Erreur 18 : Aucune réservation n'a été effectué pour ce compte.";

    @Override
    public void annule(String[] infosCompte, String idReservation)
            throws TokenInvalideException, IdReservationIncorrecteException,
            MauvaisUtilisateurReservationException {
        Compte compte = compteFacade.verificationAcces(infosCompte);
        Voyage voyage = voyageFacade.find(Long.parseLong(idReservation));
        String operation;
        Operation operationAjoute;

        if (voyage == null) {
            throw new IdReservationIncorrecteException(ERREUR_REVISION_INEXISTANTE);
        }

        Navette navette = voyage.getNavette();
        if (!compte.getIdentifiant().equals(navette.getCompte().getIdentifiant())) {
            throw new MauvaisUtilisateurReservationException(ERREUR_RESERVATION_MAUVAIS_UTILISATEUR);
        }

        operation = "Annulation de la réservation " + voyage.getId();
        operationAjoute = operationFacade.ajouterOperation(navette, operation);
        navette.ajouterOperation(operationAjoute);
        compte.ajouterOperation(operationAjoute);

        navette.setCompte(null);
        quaiFacade.retirerVoyage(voyage.getDepart(), voyage.getDestination(), voyage);
        compteFacade.edit(compte);
        navetteFacade.edit(navette);
        voyageFacade.remove(voyage);

    }

    private String calculDateArrivee(String dateDepart, String stationDepart, String stationDestination) {
        try {
            Date dateD = new SimpleDateFormat("dd/MM/yyyy").parse(dateDepart);
            Calendar c = Calendar.getInstance();
            c.setTime(dateD);

            switch (stationDepart) {
                case "Terre":
                    switch (stationDestination) {
                        case "Dimidium":
                            c.add(Calendar.DATE, 2);
                            break;
                        case "Arion":
                            c.add(Calendar.DATE, 6);
                            break;
                        case "Brahe":
                            c.add(Calendar.DATE, 2);
                            break;
                        case "Amateru":
                            c.add(Calendar.DATE, 4);
                            break;
                        case "Tadmor":
                            c.add(Calendar.DATE, 2);
                            break;
                    }
                    break;
                case "Dimidium":
                    switch (stationDestination) {
                        case "Terre":
                            c.add(Calendar.DATE, 2);
                            break;
                        case "Arion":
                            c.add(Calendar.DATE, 6);
                            break;
                        case "Brahe":
                            c.add(Calendar.DATE, 4);
                            break;
                        case "Amateru":
                            c.add(Calendar.DATE, 6);
                            break;
                        case "Tadmor":
                            c.add(Calendar.DATE, 4);
                            break;
                    }
                    break;
                case "Arion":
                    switch (stationDestination) {
                        case "Terre":
                            c.add(Calendar.DATE, 6);
                            break;
                        case "Dimidium":
                            c.add(Calendar.DATE, 6);
                            break;
                        case "Brahe":
                            c.add(Calendar.DATE, 6);
                            break;
                        case "Amateru":
                            c.add(Calendar.DATE, 8);
                            break;
                        case "Tadmor":
                            c.add(Calendar.DATE, 6);
                            break;
                    }
                    break;
                case "Brahe":
                    switch (stationDestination) {
                        case "Terre":
                            c.add(Calendar.DATE, 2);
                            break;
                        case "Dimidium":
                            c.add(Calendar.DATE, 4);
                            break;
                        case "Arion":
                            c.add(Calendar.DATE, 6);
                            break;
                        case "Amateru":
                            c.add(Calendar.DATE, 4);
                            break;
                        case "Tadmor":
                            c.add(Calendar.DATE, 2);
                            break;
                    }
                    break;
                case "Amateru":
                    switch (stationDestination) {
                        case "Terre":
                            c.add(Calendar.DATE, 4);
                            break;
                        case "Dimidium":
                            c.add(Calendar.DATE, 6);
                            break;
                        case "Arion":
                            c.add(Calendar.DATE, 8);
                            break;
                        case "Brahe":
                            c.add(Calendar.DATE, 4);
                            break;
                        case "Tadmor":
                            c.add(Calendar.DATE, 2);
                            break;
                    }
                    break;
                case "Tadmor":
                    switch (stationDestination) {
                        case "Terre":
                            c.add(Calendar.DATE, 2);
                            break;
                        case "Dimidium":
                            c.add(Calendar.DATE, 4);
                            break;
                        case "Arion":
                            c.add(Calendar.DATE, 6);
                            break;
                        case "Brahe":
                            c.add(Calendar.DATE, 2);
                            break;
                        case "Amateru":
                            c.add(Calendar.DATE, 2);
                            break;
                    }
                    break;
            }

            dateD = c.getTime();
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            return df.format(dateD);
        } catch (ParseException ex) {
            Logger.getLogger(GestionNavette.class.getName()).log(Level.SEVERE, null, ex);
            return dateDepart;
        }
    }

    @Override
    public Long reserve(String[] infosCompte, String stationAttachement, String destination,
            String dateDepart, int nbPassagers)
            throws TokenInvalideException, AucuneDestinationException,
            QuaiIndisponibleException, StationInexistanteException,
            NavettesIndisponibleException, ParseException,
            DestinationIncorrecteException, NavettePassagersException {
        Quai quaiDestination;
        Compte compte;
        Navette navetteUtilise;
        Station stationDestination;
        Station stationActuelle;
        Voyage voyage;
        List<Voyage> voyages;
        String dateArrivee;

        compte = compteFacade.verificationAcces(infosCompte);
        stationActuelle = stationFacade.findByName(stationAttachement);
        if (stationActuelle == null) {
            throw new StationInexistanteException(ERREUR_STATION_INEXISTANTE);
        }
        stationDestination = stationFacade.findByName(destination);
        if (stationDestination == null) {
            throw new AucuneDestinationException(ERREUR_DESTINATION_INEXISTANTE);
        }
        if (stationDestination == stationActuelle) {
            throw new DestinationIncorrecteException(ERREUR_DESTINATION_INCORRECTE);
        }
        navetteUtilise = stationFacade.findNavetteDisponible(stationActuelle);
        if (navetteUtilise == null) {
            throw new NavettesIndisponibleException(ERREUR_NAVETTES_INDISPONIBLE);
        }
        voyages = navetteUtilise.getVoyages();
        if (voyages != null && voyages.size() > 0 && voyages.get((voyages.size() - 1)).isEnCours()) {
            throw new NavettesIndisponibleException(ERREUR_NAVETTES_INDISPONIBLE);
        }
        quaiDestination = stationFacade.findQuaiDisponible(stationDestination);
        if (quaiDestination == null) {
            throw new QuaiIndisponibleException(ERREUR_QUAI_INDISPONIBLE);
        }
        if (nbPassagers >= navetteUtilise.getNbPassagersMaximum()) {
            throw new NavettePassagersException(ERREUR_PASSAGER_MAXIUM);
        }
        navetteUtilise.setCompte(compte);
        compte.setNavette(navetteUtilise);
        dateArrivee = calculDateArrivee(dateDepart, stationAttachement, destination);
        voyage = voyageFacade.creerVoyage(navetteUtilise, navetteUtilise.getQuai(), quaiDestination, dateDepart, dateArrivee, nbPassagers);
        navetteFacade.ajouterVoyage(navetteUtilise, voyage);
        quaiFacade.ajouterVoyage(navetteUtilise.getQuai(), quaiDestination, voyage);
        navetteFacade.edit(navetteUtilise);
        compteFacade.edit(compte);
        return voyage.getId();
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
                + " - Date d'arrivée : " + DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRANCE).format(LocalDateTime.now())
                + " - Identifiant de la station de départ : " + quaiDepart.getStation().getId()
                + " - Identifiant de la station d'arrivée : " + quaiDestination.getStation().getId()
                + " - Identifiant du quais de départ : " + quaiDepart.getId()
                + " - Identifiant du quais d'arrivé : " + quaiDestination.getId()
                + " - nombre de passagers : " + voyage.getNbPassagers()
                + " - Emprunteur de la navette : " + compte.getIdentifiant()
                + " - Date de création de l'opération : " + DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRANCE).format(LocalDateTime.now());
        operationAjoute = operationFacade.ajouterOperation(navetteUtilise, operation);
        navetteUtilise.ajouterOperation(operationAjoute);
        compte.ajouterOperation(operationAjoute);
        voyage.setEnCours(false);
        if (navetteUtilise.getVoyages().size() % 3 == 0) {
            navetteUtilise.setaReviser(true);
            operation = REVISION_NECESSAIRE + " Identifiant de la station : " + quaiDestination.getStation().getId()
                    + " - Identifiant du quai : " + quaiDestination.getId()
                    + " - Date de création de l'opération : " + DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRANCE).format(LocalDateTime.now());
            operationAjoute = operationFacade.ajouterOperation(navetteUtilise, operation);
            navetteUtilise.ajouterOperation(operationAjoute);
            compte.ajouterOperation(operationAjoute);
        }
        navetteFacade.edit(navetteUtilise);
        quaiFacade.edit(quaiDepart);
        compteFacade.edit(compte);
        voyageFacade.edit(voyage);
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
        if (!navetteAReviser.isaReviser() || (navetteAReviser.getRevisions() != null
                && navetteAReviser.getRevisions().size() > 0
                && navetteAReviser.getRevisions().get((navetteAReviser.getRevisions().size() - 1)).isEnCours())) {
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
                + " - Date de création de l'opération : " + DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRANCE).format(LocalDateTime.now());
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
        if (navetteAReviser.getRevisions().size() <= 0) {
            revision = null;
        } else {
            revision = navetteAReviser.getRevisions().get((navetteAReviser.getRevisions().size() - 1));
        }
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
    public List<String> recupererInformationsRevisions(String[] infosCompte, String stationAttache)
            throws TokenInvalideException, RoleInvalideException {
        List<Navette> navettes;
        List<String> nomsNavettes,
                rolesAutorises;
        Quai quai;

        rolesAutorises = new ArrayList<String>();
        rolesAutorises.add("Mécanicien");
        rolesAutorises.add("Administrateur");

        compteFacade.verificationAcces(infosCompte, rolesAutorises);
        navettes = navetteFacade.listNavettesAReviser();
        nomsNavettes = new ArrayList<String>();
        for (int i = 0; i < navettes.size(); i++) {
            quai = navettes.get(i).getQuai();
            if (quai != null && quai.getStation().getNom().equals(stationAttache)) {
                nomsNavettes.add(navettes.get(i).getNom());
            }
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

    @Override
    public void debutVoyageReserve(String[] infosCompte)
            throws TokenInvalideException, ReservationInexistanteException {
        Compte compte;
        List<Voyage> voyages;
        Quai quaiDepart;
        Navette navette;
        String operation;
        Voyage voyage;
        Operation operationAjoute;
        
        compte = compteFacade.verificationAcces(infosCompte);
        navette = compte.getNavette();
        if (navette == null) {
            throw new ReservationInexistanteException(ERREUR_RESERVATION_INEXISTANTE);
        }
        voyages = navette.getVoyages();
        if (voyages != null && voyages.size() > 0 && !voyages.get((voyages.size() - 1)).isEnCours()) {
            throw new ReservationInexistanteException(ERREUR_RESERVATION_INEXISTANTE);
        }
        voyage = voyages.get((voyages.size() - 1));
        quaiDepart = navette.getQuai();
        /* La navette quitte le quais */
        quaiDepart.setNavette(null);
        navette.setQuai(null);
        operation = VOYAGE_INITIE + " Date de départ : " + voyage.getDateDepart()
                + " - Date d'arrivée prévue : " + voyage.getDateArriveePrevue()
                + " - ID de la station de départ : " + voyage.getDepart().getStation().getId()
                + " - ID de la station d'arrivée : " + voyage.getDestination().getStation().getId()
                + " - ID de la navette : " + navette.getId()
                + " - Identifiant Réservation : " + voyage.getId()
                + " - Nombre de passagers : " + voyage.getNbPassagers()
                + " - Date de création de l'opération : " + DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRANCE).format(LocalDateTime.now());
        operationAjoute = operationFacade.ajouterOperation(navette, operation);
        navette.ajouterOperation(operationAjoute);
        compte.ajouterOperation(operationAjoute);
        compteFacade.edit(compte);
        quaiFacade.edit(quaiDepart);
        navetteFacade.edit(navette);
    }

    @Override
    public void utiliseNavette(String[] infosCompte, String stationAttachement, String destination, int nbPassagers) 
                throws TokenInvalideException, AucuneDestinationException, 
                       QuaiIndisponibleException, StationInexistanteException, 
                       NavettesIndisponibleException, ParseException, 
                       DestinationIncorrecteException, NavettePassagersException {
        Quai quaiDestination;
        Compte compte;
        Navette navetteUtilise;
        Station stationDestination;
        Station stationActuelle;
        Voyage voyage;
        List<Voyage> voyages;
        String operation;
        Operation operationAjoute;
        Quai quai;
        String dateArrivee,
               dateDepart;

        compte = compteFacade.verificationAcces(infosCompte);
        stationActuelle = stationFacade.findByName(stationAttachement);
        if (stationActuelle == null) {
            throw new StationInexistanteException(ERREUR_STATION_INEXISTANTE);
        }
        stationDestination = stationFacade.findByName(destination);
        if (stationDestination == null) {
            throw new AucuneDestinationException(ERREUR_DESTINATION_INEXISTANTE);
        }
        if (stationDestination == stationActuelle) {
            throw new DestinationIncorrecteException(ERREUR_DESTINATION_INCORRECTE);
        }
        navetteUtilise = stationFacade.findNavetteDisponible(stationActuelle);
        if (navetteUtilise == null) {
            throw new NavettesIndisponibleException(ERREUR_NAVETTES_INDISPONIBLE);
        }
        voyages = navetteUtilise.getVoyages();
        if (voyages != null && voyages.size() > 0 && voyages.get((voyages.size() - 1)).isEnCours()) {
            throw new NavettesIndisponibleException(ERREUR_NAVETTES_INDISPONIBLE);
        }
        quaiDestination = stationFacade.findQuaiDisponible(stationDestination);
        if (quaiDestination == null) {
            throw new QuaiIndisponibleException(ERREUR_QUAI_INDISPONIBLE);
        }
        if (nbPassagers >= navetteUtilise.getNbPassagersMaximum()) {
            throw new NavettePassagersException(ERREUR_PASSAGER_MAXIUM);
        }
        dateDepart = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRANCE).format(LocalDateTime.now());
        navetteUtilise.setCompte(compte);
        compte.setNavette(navetteUtilise);
        dateArrivee = calculDateArrivee(dateDepart, stationAttachement, destination);
        voyage = voyageFacade.creerVoyage(navetteUtilise, navetteUtilise.getQuai(), quaiDestination, dateDepart, dateArrivee, nbPassagers);
        navetteFacade.ajouterVoyage(navetteUtilise, voyage);
        quai = navetteUtilise.getQuai();
        quaiFacade.ajouterVoyage(quai, quaiDestination, voyage);
        operation = VOYAGE_INITIE + " Date de départ : " + dateDepart
                + " - Date d'arrivée prévue : " + dateArrivee
                + " - ID de la station de départ : " + stationActuelle.getId()
                + " - ID de la station d'arrivée : " + stationDestination.getId()
                + " - ID de la navette : " + navetteUtilise.getId()
                + " - Identifiant Réservation : " + voyage.getId()
                + " - Nombre de passagers : " + nbPassagers
                + " - Date de création de l'opération : " + DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRANCE).format(LocalDateTime.now());
        operationAjoute = operationFacade.ajouterOperation(navetteUtilise, operation);
        navetteUtilise.ajouterOperation(operationAjoute);
        compte.ajouterOperation(operationAjoute);
        /* La navette quitte le quais immédiatement */
        quai.setNavette(null);
        navetteUtilise.setQuai(null);
        navetteFacade.edit(navetteUtilise);
        compteFacade.edit(compte);
        quaiFacade.edit(quai);
    }
}
