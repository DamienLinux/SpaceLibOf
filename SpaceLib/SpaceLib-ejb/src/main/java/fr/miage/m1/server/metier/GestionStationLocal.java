/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.metier;

import fr.miage.m1.shared.exceptions.RoleInvalideException;
import fr.miage.m1.shared.exceptions.StationExistanteException;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
import javax.ejb.Local;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Local
public interface GestionStationLocal {
     public void ajouterStation(String[] infosComptes, String nom, int nbQuais)
                throws TokenInvalideException, StationExistanteException,
                       RoleInvalideException;
}
