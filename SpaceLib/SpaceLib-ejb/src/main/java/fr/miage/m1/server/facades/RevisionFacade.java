/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.facades;

import fr.miage.m1.server.entities.Compte;
import fr.miage.m1.server.entities.Navette;
import fr.miage.m1.server.entities.Revision;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Stateless
public class RevisionFacade extends AbstractFacade<Revision> implements RevisionFacadeLocal {

    @PersistenceContext(unitName = "SpacelibPersistenceUnit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RevisionFacade() {
        super(Revision.class);
    }

    @Override
    public Revision creationRevision(Navette navette, Compte compte) {
        Revision revision;
        
        revision = new Revision(navette, navette.getQuai(), compte);
        create(revision);
        return revision;
    }
    
}
