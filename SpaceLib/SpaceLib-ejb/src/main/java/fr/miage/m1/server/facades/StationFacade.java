/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.facades;

import fr.miage.m1.server.entities.Station;
import javax.ejb.Stateless;
import java.util.List;
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
public class StationFacade extends AbstractFacade<Station> implements StationFacadeLocal {

    @PersistenceContext(unitName = "SpacelibPersistenceUnit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StationFacade() {
        super(Station.class);
    }
    
   /* public List<Station> findAllStations() {
        super()
    }*/
    
    @Override
    public Station findByName(String nom) {
        List<Station> stations;
        
        CriteriaBuilder build = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Station> query = build.createQuery(Station.class);
        Root<Station> root = query.from(Station.class);
        query.where(build.equal(root.get("nom"), nom));
        stations = getEntityManager().createQuery(query).getResultList();
        if (stations != null && stations.size() > 0) {
            return stations.get(0);
        } //else
        return null;
    }

    @Override
    public Station creerStation(String nom, String localisation) {
        Station station = new Station(nom, localisation);
        create(station);
        return station;
    }
    
}
