/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.facades;

import fr.miage.m1.server.entities.Navette;
import fr.miage.m1.server.entities.Quai;
import fr.miage.m1.server.entities.Station;
import fr.miage.m1.shared.exceptions.NavetteInexistanteException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Local
public interface NavetteFacadeLocal {

    void create(Navette navette);

    void edit(Navette navette);

    void remove(Navette navette);

    Navette find(Object id);

    List<Navette> findAll();

    List<Navette> findRange(int[] range);

    int count();
    
    public List<Navette> findNavetteByDestination(String destination);
    
    public void ajouterDestination(Navette navette, Station station);
    
    public Navette findByName(String name);
    
    public List<Navette> listNavettes();
    
    public List<Navette> listNavettesAReviser();
    
    public Navette verificationNavette(String navette)
                   throws NavetteInexistanteException;
    
    public Navette ajouterNavette(String navette, int nbPassagers, Quai quai);
    
}
