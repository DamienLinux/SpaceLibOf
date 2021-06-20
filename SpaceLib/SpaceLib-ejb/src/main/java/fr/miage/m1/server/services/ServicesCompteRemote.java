/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.services;

import fr.miage.m1.server.metier.GestionCompteLocal;
import fr.miage.m1.shared.exceptions.CompteInexistantException;
import fr.miage.m1.shared.exceptions.IdentifiantExistantException;
import fr.miage.m1.shared.exceptions.MotDePasseInvalideException;
import fr.miage.m1.shared.exceptions.StationInexistanteException;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
import fr.miage.m1.shared.services.ServicesCompteRemoteRemote;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Stateless
public class ServicesCompteRemote implements ServicesCompteRemoteRemote {

    @EJB
    private GestionCompteLocal gestionCompte;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    
    @Override
    public void creerCompte(String identifiant, String motDePasse, String role) 
                throws IdentifiantExistantException {
        gestionCompte.creerCompte(identifiant, motDePasse, role);
    }

    @Override
    public String[] authentifier(String identifiant, String motDePasse) 
                    throws CompteInexistantException, MotDePasseInvalideException {
        return gestionCompte.authentifier(identifiant, motDePasse);
    }

    @Override
    public void renseignerStationRattachement(String[] infosCompte, String nomStation) throws TokenInvalideException, StationInexistanteException {
        gestionCompte.renseignerStationRattachement(infosCompte, nomStation);
    }

    @Override
    public String reservationEnCours(String[] infosCompte) {
        return gestionCompte.reservationEnCours(infosCompte);
    }
}
