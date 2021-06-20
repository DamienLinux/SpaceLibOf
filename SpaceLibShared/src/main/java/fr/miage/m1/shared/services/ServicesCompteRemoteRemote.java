/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.shared.services;

import fr.miage.m1.shared.exceptions.CompteInexistantException;
import fr.miage.m1.shared.exceptions.IdentifiantExistantException;
import fr.miage.m1.shared.exceptions.MotDePasseInvalideException;
import fr.miage.m1.shared.exceptions.StationInexistanteException;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
import javax.ejb.Remote;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Remote
public interface ServicesCompteRemoteRemote {
    public void creerCompte(String identifiant, String motDePasse, String role)
                throws IdentifiantExistantException ;
    
    public String[] authentifier(String identifiant, String motDePasse)
                    throws CompteInexistantException, MotDePasseInvalideException;
    
    public void renseignerStationRattachement(String[] infosCompte, String nomStation) 
                throws TokenInvalideException, StationInexistanteException;
    
    public String reservationEnCours(String[] infosCompte);
    
    public String voyageEnCours(String[] infosCompte);
}
