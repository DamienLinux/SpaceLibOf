/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.entities;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Entity
public class Compte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    public String identifiant;
    
    @NotNull
    public String motDePasse;
    
    public String token;
    
    @NotNull
    public String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    @ManyToOne
    public Station stationRattachement;

    public Station getStationRattachement() {
        return stationRattachement;
    }

    public void setStationRattachement(Station stationRattachement) {
        this.stationRattachement = stationRattachement;
    }

    public Compte() {
    }

    public String getToken() {
        return token;
    }
    
    
    @ManyToOne
    public Navette navette;

    public Navette getNavette() {
        return navette;
    }

    public void setNavette(Navette navette) {
        this.navette = navette;
    }
    
    public Compte(String identifiant, String motDePasse, String role) {
        this.identifiant = identifiant;
        this.motDePasse = motDePasse;
        this.role = role;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    public String creerToken() {
        final String KEY = "SPACEX";
        
        return KEY + UUID.randomUUID().toString().toUpperCase();
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compte)) {
            return false;
        }
        Compte other = (Compte) object;
        return (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.Compte[ id=" + id + " ]";
    }
    
}
