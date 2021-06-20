/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.services;

import fr.miage.m1.server.metier.GestionNavetteLocal;
import fr.miage.m1.shared.exceptions.NavetteExistanteException;
import fr.miage.m1.shared.exceptions.NavetteInexistanteException;
import fr.miage.m1.shared.exceptions.NavettePasAReviserException;
import fr.miage.m1.shared.exceptions.QuaiIndisponibleException;
import fr.miage.m1.shared.exceptions.RevisionInexistanteException;
import fr.miage.m1.shared.exceptions.RoleInvalideException;
import fr.miage.m1.shared.exceptions.StationInexistanteException;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Stateless
public class ServicesNavette implements ServicesNavetteLocal {

    @EJB
    private GestionNavetteLocal gestionNavette;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void debutRevision(String[] infosCompte, String navette) 
                throws TokenInvalideException, NavetteInexistanteException, 
                       NavettePasAReviserException, RoleInvalideException {
        gestionNavette.debutRevision(infosCompte, navette);
    }

    @Override
    public void finRevision(String[] infosCompte, String navette) 
                throws TokenInvalideException, NavetteInexistanteException, 
                       RevisionInexistanteException, RoleInvalideException {
        gestionNavette.finRevision(infosCompte, navette);
    }

    @Override
    public List<String> recupererInformationsRevisions(String[] infosCompte, String stationAttache) 
                        throws TokenInvalideException, RoleInvalideException {
        return gestionNavette.recupererInformationsRevisions(infosCompte, stationAttache);
    }

    @Override
    public void ajouterNavette(String[] infosCompte, String navette, int nbPassagers, String station)
                throws TokenInvalideException, RoleInvalideException, 
                       NavetteExistanteException, QuaiIndisponibleException,
                       StationInexistanteException {
        gestionNavette.ajouterNavette(infosCompte, navette, nbPassagers, station);
    }
}
