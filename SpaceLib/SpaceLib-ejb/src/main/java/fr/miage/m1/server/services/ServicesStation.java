/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.services;

import fr.miage.m1.server.metier.GestionStationLocal;
import fr.miage.m1.shared.exceptions.RoleInvalideException;
import fr.miage.m1.shared.exceptions.StationExistanteException;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Stateless
public class ServicesStation implements ServicesStationLocal {

    @EJB
    private GestionStationLocal gestionStation;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void ajouterStation(String[] infosCompte, String nom, String localisation, int nbQuais) 
                throws TokenInvalideException, StationExistanteException,
                       RoleInvalideException{
        gestionStation.ajouterStation(infosCompte, nom, localisation, nbQuais);
    }
}
