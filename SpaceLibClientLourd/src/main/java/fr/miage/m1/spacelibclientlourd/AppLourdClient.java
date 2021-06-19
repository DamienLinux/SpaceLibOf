/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.spacelibclientlourd;

import fr.miage.m1.shared.exceptions.AuthentificationException;
import fr.miage.m1.shared.exceptions.CompteInexistantException;
import fr.miage.m1.shared.exceptions.IdentifiantExistantException;
import fr.miage.m1.shared.exceptions.MotDePasseInvalideException;
import fr.miage.m1.shared.exceptions.NavetteInexistanteException;
import fr.miage.m1.shared.exceptions.StationInexistanteException;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
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
                                                + " 1 - Créer un compte\n"
                                                + " 2 - Se connecter\n"
                                                + " 3 - Réserver un voyage\n"
                                                + " 4 - Indiquer son arrivé\n"
                                                + " 5 - Visualiser la carte\n"
                                                + " 6 - Quitter\n"
                                                + "#------------------------------------------------\n"
                                                + "Quelle option choisissez-vous ? : ";
    
    private final String ERREUR_IDENTIFIANT_EXISTANT = "L'identifiant que vous avez saisi existe déjà.";
    
    private final String ERREUR_CONNEXION = "Votre identifiant ou mot de passe est invalide.";
    
    private final String ERREUR_ACCES_NON_AUTORISE = "Vous n'êtes pas autorisé à effectuer cette action.";
    
    private final String ERREUR_STATION_INEXISTANTE = "La station que vous avez mentionnée n'existe pas.";
    
    private ServiceClient service;
    
    private RMICompteServiceManager compteRMIService;
    
    private RMINavetteServiceManager navetteRMIService;
    
    private RMIStationServiceManager stationRMIService;
    
    private String identifiant;
    
    private String token;
    
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
    }
    
    public void indicationStationRattachement() {
        ConsoleAuthentifier console;
        String station;
        boolean valide;
        
        console = new ConsoleAuthentifier(service);
        do {
            valide = true;
            station = console.renseignerStation();
            try {
                compteRMIService.getServiceCompteRemote().renseignerStationRattachement(infosCompte(), station);
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
        }
    }
    
    public void visualiserCarte() {
        
        try {
            System.out.println(stationRMIService.getServiceStationRemote().carteStations());
        } catch (TokenInvalideException ex) {
            System.out.println(ERREUR_ACCES_NON_AUTORISE);
        }
        
    }
    
    public void menu() {
        int choix;
        boolean executionEnCours;
        
        executionEnCours = true;
        do {
            if (token != null) {
                choix = service.saisieNombre(TEXTE_MENU_CONNECTE, 1, 6);
            } else {
                choix = service.saisieNombre(TEXTE_MENU_NON_CONNECTE, 1, 3);
            }
            switch (choix) {
                case 1:
                    System.out.println("Création d'un compte.");
                    creerCompte();
                    break;
                case 2:
                    System.out.println("Demande de connexion.");
                    authentifier();
                    break;
                case 3:
                    if (token != null) {
                        System.out.println("Réservation d'un voyage.");
                        reserverVoyage();
                    } else {
                        executionEnCours = false;
                        System.out.println("Merci d'avoir utilisé nos services.");
                    }
                    break;
                case 4:
                    System.out.println("Indiquer son arrivé");
                    indiquerArrivee();
                    break;
                case 5:
                    System.out.println("Accès à la carte");
                    visualiserCarte();
                    break;
                default:
                    executionEnCours = false;
                    System.out.println("Merci d'avoir utilisé nos services.");
            }
        } while (executionEnCours);
    }
}
