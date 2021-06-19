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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    public Voyage creerVoyage(Navette navette, Quai quaiDepart, Quai quaiDestination, String dateArriveePrevu,
                              int nbPassagers) 
                  throws ParseException {
        Voyage voyage;
        voyage = new Voyage(navette, quaiDepart, quaiDestination, 
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
    
}
