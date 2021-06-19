/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.shared.entities;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author DamienAvetta-Raymond
 */
public class Station implements Serializable {
    private Long id;

    private String nom;
    
    private List<Navette> navettes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Navette> getNavettes() {
        return navettes;
    }

    public void setNavettes(List<Navette> navettes) {
        this.navettes = navettes;
    }

    public Station(Long id, String nom, List<Navette> navettes) {
        this.id = id;
        this.nom = nom;
        this.navettes = navettes;
    }

    public Station() {
    }
}
