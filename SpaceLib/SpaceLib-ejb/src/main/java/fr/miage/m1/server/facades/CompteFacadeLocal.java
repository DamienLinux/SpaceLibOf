/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.facades;

import fr.miage.m1.server.entities.Compte;
import fr.miage.m1.shared.exceptions.RoleInvalideException;
import fr.miage.m1.shared.exceptions.TokenInvalideException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Local
public interface CompteFacadeLocal {

    void create(Compte compte);

    void edit(Compte compte);

    void remove(Compte compte);

    Compte find(Object id);

    List<Compte> findAll();

    List<Compte> findRange(int[] range);

    int count();
    
    public Compte findCompteByIdentifiant(String identifiant);
    
    public void creerCompte(String identifiant, String motDePasse, String role);
    
    public String creerToken(Compte compte);
    
    public Compte verificationAcces(String[] infosCompte, List<String> rolesAutorises)
                  throws TokenInvalideException, RoleInvalideException;
    
    public Compte verificationAcces(String[] infosCompte)
                  throws TokenInvalideException;
    
}
