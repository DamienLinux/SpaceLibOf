/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.facades;

import fr.miage.m1.server.entities.Navette;
import fr.miage.m1.server.entities.Quai;
import fr.miage.m1.server.entities.Station;
import java.util.ArrayList;
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

    @Override
    public Navette findNavetteDisponible(Station station) {
        List<Navette> navettesDisponibles;
        int choixNavette;
        
        navettesDisponibles = new ArrayList<Navette>();
        for (int i = 0 ; i < station.getQuais().size() ; i++) {
            if (station.getQuais().get(i).getNavette() != null) {
                navettesDisponibles.add(station.getQuais().get(i).getNavette());
            }
        }
        /* Retourne une des navettes aléatoirement */
        choixNavette = (int) (Math.random() * (navettesDisponibles.size() - 1));
        if (choixNavette >= 0) {
            return navettesDisponibles.get(choixNavette);
        }
        return null;
    }

    @Override
    public Quai findQuaiDisponible(Station station) {
        List<Quai> quaisDisponibles;
        int choixQuais;
        
        quaisDisponibles = new ArrayList<Quai>();
        for (int i = 0 ; i < station.getQuais().size() ; i++) {
            if (station.getQuais().get(i).getNavette() == null) {
                quaisDisponibles.add(station.getQuais().get(i));
            }
        }
        choixQuais = (int) (Math.random() * (quaisDisponibles.size() - 1));
        if (choixQuais > 0) {
            return quaisDisponibles.get(choixQuais);
        }
        return null;
    }
    
}
