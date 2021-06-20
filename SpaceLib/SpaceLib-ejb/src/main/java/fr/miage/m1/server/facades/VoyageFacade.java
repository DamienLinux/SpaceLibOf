/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.facades;

import fr.miage.m1.server.entities.Navette;
import fr.miage.m1.server.entities.Quai;
import fr.miage.m1.server.entities.Voyage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class VoyageFacade extends AbstractFacade<Voyage> implements VoyageFacadeLocal {

    @PersistenceContext(unitName = "SpacelibPersistenceUnit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VoyageFacade() {
        super(Voyage.class);
    }
   

    @Override
    public Voyage creerVoyage(Navette navette, Quai quaiDepart, Quai quaiDestination, String dateDepart, String dateArriveePrevu,
                              int nbPassagers) 
                  throws ParseException {
        Voyage voyage;
        voyage = new Voyage(navette, quaiDepart, quaiDestination, new SimpleDateFormat("dd/MM/yyy").parse(dateDepart),
                            new SimpleDateFormat("dd/MM/yyy").parse(dateArriveePrevu), nbPassagers);
        create(voyage);
        return voyage;
    }

    @Override
    public Voyage voyageAcheve(Voyage voyage) {
        voyage.setEnCours(false);
        edit(voyage);
        return voyage;
    }

    @Override
    public Voyage findByNavette(Navette navette) {
        List<Voyage> voyages;
        
        CriteriaBuilder build = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Voyage> query = build.createQuery(Voyage.class);
        Root<Voyage> root = query.from(Voyage.class);
        query.where(build.equal(root.get("navette"), navette));
        voyages = getEntityManager().createQuery(query).getResultList();
        if (voyages != null && voyages.size() > 0) {
            return voyages.get(0);
        } //else
        return null;
    }
    
}
