/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.spacelibclientlourd;

import fr.miage.m1.shared.exceptions.AucuneDestinationException;
import fr.miage.m1.shared.exceptions.AuthentificationException;
import fr.miage.m1.shared.exceptions.CompteInexistantException;
import fr.miage.m1.shared.exceptions.DestinationIncorrecteException;
import fr.miage.m1.shared.exceptions.IdReservationIncorrecteException;
import fr.miage.m1.shared.exceptions.IdentifiantExistantException;
import fr.miage.m1.shared.exceptions.MauvaisUtilisateurReservationException;
import fr.miage.m1.shared.exceptions.MotDePasseInvalideException;
import fr.miage.m1.shared.exceptions.NavetteInexistanteException;
import fr.miage.m1.shared.exceptions.NavettePassagersException;
import fr.miage.m1.shared.exceptions.NavettesIndisponibleException;
import fr.miage.m1.shared.exceptions.QuaiIndisponibleException;
import fr.miage.m1.shared.exceptions.StationInexistanteException;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author DamienAvetta-Raymond
 */
public class AppLourdClient {
    
    private final String TEXTE_MENU_NON_CONNECTE = "#------------------------------------------------\n"
                                                + " 1 - Créer un compte\n"
                                                + " 2 - Se connecter\n"
                                                + " 3 - Quitter\n"
                                                + "#------------------------------------------------\n"
                                                + "Quelle option choisissez-vous ? : ";
    
    
    private final String TEXTE_MENU_CONNECTE = "#------------------------------------------------\n"
                                                + " 1 - Réserver un voyage\n"
                                                + " 2 - Visualiser la carte\n"
                                                + " 3 - Se déconnecter\n"
                                                + " 4 - Annuler une réservation\n"
                                                + " 5 - Quitter\n"
                                                + "#------------------------------------------------\n"
                                                + "Quelle option choisissez-vous ? : ";
    
    private final String ERREUR_IDENTIFIANT_EXISTANT = "L'identifiant que vous avez saisi existe déjà.";
    
    private final String ERREUR_CONNEXION = "Votre identifiant ou mot de passe est invalide.";
    
    private final String ERREUR_ACCES_NON_AUTORISE = "Vous n'êtes pas autorisé à effectuer cette action.";
    
    private final String ERREUR_STATION_INEXISTANTE = "La station que vous avez mentionnée n'existe pas.";
    
    private final String ERREUR_DESTINATION_INEXISTANTE = "La destination que vous avez mentionnée n'existe pas.";
    
    private final String ERREUR_QUAI_INDISPONIBLE = "Aucun quai est disponible pour le moment sur la station que vous souhaitez rejoindre.";
    
    private final String ERREUR_NAVETTE_INDISPONIBLE = "Aucune navette ne peut vous prendre en charge sur cette station pour le moment.";
    
    private final String ERREUR_DIVERS = "Un problème a été rencontré avec le serveur.";
    
    private final String ERREUR_MEME_DESTINATION = "Vous ne pouvez pas choisir en destination une station où vous vous trouvez déjà.";
    
    private final String ERREUR_RESERVATION_EFFECTUE = "Vous avez déjà effectué une réservation.";
    
    private final String ERREUR_PASSAGERS_NAVETTE = "Il n'y a pas assez de place sur cette navette.";
    
    private final String ERREUR_ID_RESERVATION_INCORRECTE = "L'ID de réservation est incorrecte.";

    private final String ERREUR_RESERVATION_MAUVAIS_UTILISATEUR = "Cette réservation ne vous appartient pas.";
    
    private ServiceClient service;
    
    private RMICompteServiceManager compteRMIService;
    
    private RMINavetteServiceManager navetteRMIService;
    
    private RMIStationServiceManager stationRMIService;
    
    private String identifiant;
    
    private String token;
    
    private String stationRattachement;
    
    boolean voyageEnCours;
    
    public AppLourdClient() throws NamingException {
        service = new ServiceClient();
        compteRMIService = new RMICompteServiceManager();
        navetteRMIService = new RMINavetteServiceManager();
        stationRMIService = new RMIStationServiceManager();
    }

    public void authentifier() {
        ConsoleAuthentifier console;
        String[] infosCompte;
        String[] resultatCompte;
        boolean valide;
            
        console = new ConsoleAuthentifier(service);
        resultatCompte = new String[2];
        do {
            valide = true;
            try {
                infosCompte = console.saisieConnexion();
                resultatCompte = compteRMIService.getServiceCompteRemote().authentifier(infosCompte[0], infosCompte[1]);
            } catch (AuthentificationException ex) {
                valide = false;
                System.out.println(ERREUR_CONNEXION);
            }
        } while (!valide);
        identifiant = resultatCompte[0];
        token = resultatCompte[1];
        System.out.println("Bienvenue sur votre espace de connexion " + identifiant + " !");
        indicationStationRattachement();
        rechercheReservationEnCours();
    }
    
    public void rechercheReservationEnCours() {
        String destination;
        destination = compteRMIService.getServiceCompteRemote().reservationEnCours(infosCompte());
        voyageEnCours = (destination != null);
        if (voyageEnCours && destination.equals(stationRattachement)) {
            System.out.println("Vous êtes bien arrivé sur la station " + stationRattachement + " !");
            indiquerArrivee();
        }
    }
    
    public void indicationStationRattachement() {
        ConsoleAuthentifier console;
        boolean valide;
        
        console = new ConsoleAuthentifier(service);
        do {
            valide = true;
            stationRattachement = console.renseignerStation();
            try {
                compteRMIService.getServiceCompteRemote().renseignerStationRattachement(infosCompte(), stationRattachement);
            } catch (TokenInvalideException ex) {
                valide = false;
                System.out.println(ERREUR_ACCES_NON_AUTORISE);
            } catch (StationInexistanteException ex) {
                valide = false;
                System.out.println(ERREUR_STATION_INEXISTANTE);
            }
        } while (!valide);
    }
    
    public void creerCompte() {
        String[] infosCompte; 
        ConsoleCompte console;
        boolean valide;
        
        console = new ConsoleCompte(service);
        do {
            valide = true;
            try {
                infosCompte = console.saisieCompte();
                compteRMIService.getServiceCompteRemote().creerCompte(infosCompte[0], infosCompte[1], infosCompte[2]);
            } catch (IdentifiantExistantException ex) {
                valide = false;
                System.out.println(ERREUR_IDENTIFIANT_EXISTANT);
            }
        } while (!valide);
        System.out.println("Compte créé.");
    }
    
    public void reserverVoyage() {
        ListeChoix liste;
        String stationDestination,
               dateDepart;
        ConsoleVoyage console;
        boolean valide;
        int nbPassagers;
        
        if (voyageEnCours) {
            System.out.println(ERREUR_RESERVATION_EFFECTUE);
        } else {
            console = new ConsoleVoyage(service);
            liste = new ListeChoix(stationRMIService.getServiceStationRemote().listeStations(stationRattachement));
            do {
                valide = true;
                stationDestination = console.saisieVoyage(liste);
                dateDepart = console.saisieDateArrivee();
                nbPassagers = console.saisieNbPassagers();
                try {
                    navetteRMIService.getServiceNavetteRemote().reserve(infosCompte(), stationRattachement, stationDestination, dateDepart, nbPassagers);
                } catch (TokenInvalideException ex) {
                    valide = false;
                    System.out.println(ERREUR_ACCES_NON_AUTORISE);
                } catch (AucuneDestinationException ex) {
                    valide = false;
                    System.out.println(ERREUR_DESTINATION_INEXISTANTE);
                } catch (QuaiIndisponibleException ex) {
                    valide = false;
                    System.out.println(ERREUR_QUAI_INDISPONIBLE);
                } catch (StationInexistanteException ex) {
                    valide = false;
                    System.out.println(ERREUR_STATION_INEXISTANTE);
                } catch (NavettesIndisponibleException ex) {
                    valide = false;
                    System.out.println(ERREUR_NAVETTE_INDISPONIBLE);
                } catch (ParseException ex) {
                    valide = false;
                    System.out.println(ERREUR_DIVERS);
                } catch (DestinationIncorrecteException ex) {
                    System.out.println(ERREUR_MEME_DESTINATION);
                } catch (NavettePassagersException ex) {
                    System.out.println(ERREUR_PASSAGERS_NAVETTE);
                }
            } while (!valide);
            voyageEnCours = true;
        }
    }
    
    public String[] infosCompte() {
        String[] infosCompte;
        
        infosCompte = new String[2];
        infosCompte[0] = identifiant;
        infosCompte[1] = token;
        return infosCompte;
    }
    
    public void indiquerArrivee() {
        boolean bienArrivee;
        
        bienArrivee = true;
        try {
            navetteRMIService.getServiceNavetteRemote().voyageAcheve(infosCompte());
        } catch (TokenInvalideException ex) {
            bienArrivee = false;
            System.out.println(ERREUR_ACCES_NON_AUTORISE);
        }
        if (bienArrivee) {
            System.out.println("Vous êtes bien arrivé à destination.");
            voyageEnCours = false;
        }
    }
    
    public void visualiserCarte() {
        
        try {
            System.out.println(stationRMIService.getServiceStationRemote().carteStations());
        } catch (TokenInvalideException ex) {
            System.out.println(ERREUR_ACCES_NON_AUTORISE);
        }
        
    }
    
    public void annulerReservation() {
        ConsoleVoyage console = new ConsoleVoyage(service);
        String id = console.saisieIdReservation();
        
        try {
        navetteRMIService.getServiceNavetteRemote().annule(infosCompte(), id);
        } catch (TokenInvalideException ex) {
            System.out.println(ERREUR_ACCES_NON_AUTORISE);
        } catch (IdReservationIncorrecteException ex) {
            System.out.println(ERREUR_ID_RESERVATION_INCORRECTE);
        } catch (MauvaisUtilisateurReservationException ex) {
            System.out.println(ERREUR_RESERVATION_MAUVAIS_UTILISATEUR);
        }
    }
    
    public void deconnexion() {
        token = null;
        System.out.println("Vous êtes maintenant déconnecté.");
    }
    
    public void menu() {
        int choix;
        boolean executionEnCours;
        
        executionEnCours = true;
        do {
            if (token != null) {
                choix = service.saisieNombre(TEXTE_MENU_CONNECTE, 1, 5);
                switch (choix) {
                case 1:
                    System.out.println("Réservation d'un voyage.");
                    reserverVoyage();
                    break;
                case 2:
                    System.out.println("Accès à la carte.");
                    visualiserCarte();
                    break;
                case 3:
                    System.out.println("Déconnexion.");
                    deconnexion();
                    break;
                case 4:
                    System.out.println("Annulation.");
                    
                default:
                    executionEnCours = false;
                    System.out.println("Merci d'avoir utilisé nos services.");
                }
            } else {
                choix = service.saisieNombre(TEXTE_MENU_NON_CONNECTE, 1, 3);
                switch (choix) {
                case 1:
                    System.out.println("Création d'un compte.");
                    creerCompte();
                    break;
                case 2:
                    System.out.println("Demande de connexion.");
                    authentifier();
                    break;
                default:
                    executionEnCours = false;
                    System.out.println("Merci d'avoir utilisé nos services.");
                }
            }
        } while (executionEnCours);
    }
}
