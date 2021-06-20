/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.services;

import fr.miage.m1.shared.exceptions.NavetteExistanteException;
import fr.miage.m1.shared.exceptions.NavetteInexistanteException;
import fr.miage.m1.shared.exceptions.NavettePasAReviserException;
import fr.miage.m1.shared.exceptions.QuaiIndisponibleException;
import fr.miage.m1.shared.exceptions.RevisionInexistanteException;
import fr.miage.m1.shared.exceptions.RoleInvalideException;
import fr.miage.m1.shared.exceptions.StationInexistanteException;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Local
public interface ServicesNavetteLocal {
    public void debutRevision(String[] infosCompte, String navette)
                throws TokenInvalideException, NavetteInexistanteException,
                       NavettePasAReviserException, RoleInvalideException;
    
    public void finRevision(String[] infosCompte, String navette)
                throws TokenInvalideException, NavetteInexistanteException,
                       RevisionInexistanteException, RoleInvalideException;
    
    public List<String> recupererInformationsRevisions(String[] infosCompte, String stationAttache)
                        throws TokenInvalideException, RoleInvalideException;
    
    public void ajouterNavette(String[] infosCompte, String navette, int nbPassagers, String station)
                throws TokenInvalideException, RoleInvalideException, 
                       NavetteExistanteException, QuaiIndisponibleException,
                       StationInexistanteException;
}
