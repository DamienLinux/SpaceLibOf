/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.facades;

import fr.miage.m1.server.entities.Navette;
import fr.miage.m1.server.entities.Quai;
import fr.miage.m1.server.entities.Station;
import fr.miage.m1.server.entities.Voyage;
import java.util.ArrayList;
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
public class QuaiFacade extends AbstractFacade<Quai> implements QuaiFacadeLocal {

    @PersistenceContext(unitName = "SpacelibPersistenceUnit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuaiFacade() {
        super(Quai.class);
    }

    @Override
    public Station creerQuais(Station station, int nbQuais) {
        List<Quai> quais;
        Quai quai;
        
        quais = new ArrayList<Quai>();
        for (int i = 0 ; i < nbQuais ; i++) {
            quai = new Quai(station);
            create(quai);
            quais.add(quai);
        }
        station.setQuais(quais);
        return station;
    }

    @Override
    public Quai attributionQuai(Station station) {
        List<Quai> quais;
        
        CriteriaBuilder build = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Quai> query = build.createQuery(Quai.class);
        Root<Quai> root = query.from(Quai.class);
        query.where(
            build.and(
                build.equal(root.get("station"), station),
                build.isNull(root.get("navette"))
            )
        );
        quais = getEntityManager().createQuery(query).getResultList();
        if (quais != null && quais.size() > 0) {
            return quais.get(0);
        } //else
        return null;
    }

    @Override
    public void ajouterVoyage(Quai depart, Quai destination, Voyage voyage) {
        depart.ajouterVoyagesDepart(voyage);
        destination.ajouterVoyagesADestination(voyage);
        depart.setNavette(null); // La Navette quitte le quai
        edit(depart);
        edit(destination);
    }

    @Override
    public Quai findByNavette(Navette navette) {
        List<Quai> quais;
        
        CriteriaBuilder build = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Quai> query = build.createQuery(Quai.class);
        Root<Quai> root = query.from(Quai.class);
        query.where(build.equal(root.get("navette"), navette));
        quais = getEntityManager().createQuery(query).getResultList();
        if (quais != null && quais.size() > 0) {
            return quais.get(0);
        } //else
        return null;
    }
}
