/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.shared.entities;

import java.io.Serializable;

/**
 *
 * @author DamienAvetta-Raymond
 */
public class Compte implements Serializable {
    private Long id;

    public String identifiant;

    public String motDePasse;
    
    public String token;
    
    public Navette navette;

    public Compte(Long id, String identifiant, String motDePasse, String token, Navette navette) {
        this.id = id;
        this.identifiant = identifiant;
        this.motDePasse = motDePasse;
        this.token = token;
        this.navette = navette;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Navette getNavette() {
        return navette;
    }

    public void setNavette(Navette navette) {
        this.navette = navette;
    }

    public Compte() {
    }
}
