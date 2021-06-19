/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.services;

import fr.miage.m1.server.metier.GestionCompteLocal;
import fr.miage.m1.shared.exceptions.CompteInexistantException;
import fr.miage.m1.shared.exceptions.MotDePasseInvalideException;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Stateless
public class ServicesCompte implements ServicesCompteLocal {

    @EJB
    private GestionCompteLocal gestionCompte;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public String[] authentifier(String identifiant, String motDePasse) 
                    throws CompteInexistantException, MotDePasseInvalideException {
        return gestionCompte.authentifier(identifiant, motDePasse);
    }
}
