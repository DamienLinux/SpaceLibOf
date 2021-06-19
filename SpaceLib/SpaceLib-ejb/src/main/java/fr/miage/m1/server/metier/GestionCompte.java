/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.metier;

import fr.miage.m1.server.entities.Compte;
import fr.miage.m1.server.entities.Station;
import fr.miage.m1.server.facades.CompteFacadeLocal;
import fr.miage.m1.server.facades.StationFacadeLocal;
import fr.miage.m1.shared.exceptions.CompteInexistantException;
import fr.miage.m1.shared.exceptions.IdentifiantExistantException;
import fr.miage.m1.shared.exceptions.MotDePasseInvalideException;
import fr.miage.m1.shared.exceptions.NavetteInexistanteException;
import fr.miage.m1.shared.exceptions.StationInexistanteException;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Stateless
public class GestionCompte implements GestionCompteLocal {
    
    private final String ERREUR_STATION_INEXISTANTE = "Erreur 10 : Station inexistante";

    @EJB
    private StationFacadeLocal stationFacade;

    @EJB
    private CompteFacadeLocal compteFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private final String ERREUR_MESSAGE_IDENTIFIANT_EXISTE = "Erreur 1 : l'identifiant existe déjà !";
    
    private final String ERREUR_MESSAGE_COMPTE_INEXISTANT = "Erreur 2 : compte inexistant !";
    
    private final String ERREUR_MOT_DE_PASSE_INVALIDE = "Erreur 3 : mot de passe invalide !";
    
    @Override
    public void creerCompte(String identifiant, String motDePasse, String role) 
                throws IdentifiantExistantException {
        if (compteFacade.findCompteByIdentifiant(identifiant) != null) {
            throw new IdentifiantExistantException(ERREUR_MESSAGE_IDENTIFIANT_EXISTE);
        }
        compteFacade.creerCompte(identifiant, hashMotDePasse(motDePasse), role);
    }

    @Override
    public String[] authentifier(String identifiant, String motDePasse) 
                    throws CompteInexistantException, MotDePasseInvalideException {
        Compte compte;
        String[] resultat = new String[2];
        
        compte = compteFacade.findCompteByIdentifiant(identifiant);
        if (compte == null) {
            throw new CompteInexistantException(ERREUR_MESSAGE_COMPTE_INEXISTANT);
        }
        if (!compte.getMotDePasse().equals(hashMotDePasse(motDePasse))) {
            throw new MotDePasseInvalideException(ERREUR_MOT_DE_PASSE_INVALIDE);
        }
        resultat[0] = compte.getIdentifiant();
        resultat[1] = compteFacade.creerToken(compte);
        return resultat;
    }
    
    public String hashMotDePasse(String motDePasse) {
        String motDePasseHash;
        
        motDePasseHash = "SPACEX - "; // Clé spéciale au service
        /* 
         * Note : Algorithme de modification de mot de passe rapide, 
         *        non-sécurisé
         */
        for (int i = 0 ; i < motDePasse.length() ; i++) {
            motDePasseHash += (motDePasse.charAt(i) + 10);
        }
        return motDePasseHash;
    }

    @Override
    public void renseignerStationRattachement(String[] infosCompte, String nomStation) 
                throws TokenInvalideException, StationInexistanteException {
        Station station;
        Compte compte;
        
        compte = compteFacade.verificationAcces(infosCompte);
        station = stationFacade.findByName(nomStation);
        if (station == null) {
            throw new StationInexistanteException(ERREUR_STATION_INEXISTANTE);
        }
        if (compte.getStationRattachement() != null) {
            station.retirerCompte(compte);
        }
        compte.setStationRattachement(station);
        station.ajouterCompte(compte);
    }
}
