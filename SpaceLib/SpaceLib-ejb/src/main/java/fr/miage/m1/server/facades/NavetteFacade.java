/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.facades;

import fr.miage.m1.server.entities.Navette;
import fr.miage.m1.server.entities.Station;
import fr.miage.m1.shared.exceptions.NavetteInexistanteException;
import java.util.List;
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
public class NavetteFacade extends AbstractFacade<Navette> implements NavetteFacadeLocal {
    
    private final String ERREUR_NAVETTE_INEXISTANTE = "Erreur 5 : Navette inexistante.";

    @PersistenceContext(unitName = "SpacelibPersistenceUnit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NavetteFacade() {
        super(Navette.class);
    }
    
    @Override
    public List<Navette> findNavetteByDestination(String destination) {
        return null;
    }
    
    public Navette findByName(String nom) {
        List<Navette> navettes;
        
        CriteriaBuilder build = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Navette> query = build.createQuery(Navette.class);
        Root<Navette> root = query.from(Navette.class);
        query.where(build.equal(root.get("nom"), nom));
        navettes = getEntityManager().createQuery(query).getResultList();
        if (navettes.size() > 0) {
            return navettes.get(0);
        } //else
        return null;
    }
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void ajouterDestination(Navette navette, Station station) {
        navette.setDestination(station);
    }

    @Override
    public List<Navette> listNavettes() {
        List<Navette> navettes;
        
        CriteriaBuilder build = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Navette> query = build.createQuery(Navette.class);
        Root<Navette> root = query.from(Navette.class);
        query.where(build.notEqual(root.get("destination"), null));
        return getEntityManager().createQuery(query).getResultList();
    }

    @Override
    public List<Navette> listNavettesAReviser() {
        CriteriaBuilder build = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Navette> query = build.createQuery(Navette.class);
        Root<Navette> root = query.from(Navette.class);
        query.where(build.equal(root.get("aReviser"), true));
        return getEntityManager().createQuery(query).getResultList();
    }

    @Override
    public Navette verificationNavette(String navette)
                   throws NavetteInexistanteException {
        Navette navetteUtilise;
        
        navetteUtilise = findByName(navette);
        if (navetteUtilise == null) {
            throw new NavetteInexistanteException(ERREUR_NAVETTE_INEXISTANTE);
        }
        return navetteUtilise;
    }

    @Override
    public void ajouterNavette(String navette) {
        Navette navetteAAjouter = new Navette(navette);
        create(navetteAAjouter);
    }
    
}
