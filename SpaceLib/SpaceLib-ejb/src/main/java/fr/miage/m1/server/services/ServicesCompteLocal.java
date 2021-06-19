/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.services;

import fr.miage.m1.shared.exceptions.CompteInexistantException;
import fr.miage.m1.shared.exceptions.MotDePasseInvalideException;
import javax.ejb.Local;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Local
public interface ServicesCompteLocal {
    public String[] authentifier(String identifiant, String motDePasse)
                    throws CompteInexistantException, MotDePasseInvalideException;
}
