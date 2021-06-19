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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Stateless
public class CompteFacade extends AbstractFacade<Compte> implements CompteFacadeLocal {
    
    private final String ERREUR_TOKEN_INVALIDE = "Erreur 4 : Token invalide.";
    
    private final String ERREUR_ROLE_INVALIDE = "Erreur 11 : Rôle invalide.";

    @PersistenceContext(unitName = "SpacelibPersistenceUnit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompteFacade() {
        super(Compte.class);
    }

    @Override
    public Compte findCompteByIdentifiant(String identifiant) {
        List<Compte> comptes;
        CriteriaBuilder build = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Compte> query = build.createQuery(Compte.class);
        Root<Compte> root = query.from(Compte.class);
        query.where(build.equal(root.get("identifiant"), identifiant));
        comptes = getEntityManager().createQuery(query).getResultList();
        if (comptes != null && comptes.size() > 0) {
            return comptes.get(0);
        } //else
        return null;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void creerCompte(String identifiant, String motDePasse, String role) {
        Compte compte = new Compte(identifiant, motDePasse, role);
        create(compte);
    }

    @Override
    public String creerToken(Compte compte) {
        String token;
        token = compte.creerToken();
        edit(compte);
        return token;
    }

    @Override
    public Compte verificationAcces(String[] infosCompte)
                  throws TokenInvalideException {
        try {
            return verificationAcces(infosCompte, null);
        } catch (RoleInvalideException ex) {
            /* IMPOSSIBLE */
        }
        return null;
    }

    @Override
    public Compte verificationAcces(String[] infosCompte, List<String> rolesAutorises) 
           throws TokenInvalideException, RoleInvalideException {
        Compte compte;
        
        compte = findCompteByIdentifiant(infosCompte[0]);
        if (compte == null || !compte.getToken().equals((infosCompte[1]))) {
            throw new TokenInvalideException(ERREUR_TOKEN_INVALIDE);
        }
        if (rolesAutorises != null && !rolesAutorises.contains(compte.getRole())) {
            throw new RoleInvalideException(ERREUR_ROLE_INVALIDE);
        }
        return compte;
    }
    
}
