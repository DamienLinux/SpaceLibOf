/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.facades;

import fr.miage.m1.server.entities.Quai;
import fr.miage.m1.server.entities.Station;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Local
public interface QuaiFacadeLocal {

    void create(Quai quai);

    void edit(Quai quai);

    void remove(Quai quai);

    Quai find(Object id);

    List<Quai> findAll();

    List<Quai> findRange(int[] range);

    int count();
    
    public Station creerQuais(Station station, int nbQuais);
    
    public Quai attributionQuai(Station station);
}
