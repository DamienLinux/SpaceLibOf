/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Entity
public class Station implements Serializable {

    
    @OneToMany(mappedBy = "destination")
    private List<Navette> navettes;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToMany(mappedBy = "station")
    private List<Quai> quais;

    public List<Quai> getQuais() {
        return quais;
    }

    public void setQuais(List<Quai> quais) {
        this.quais = quais;
    }
    
    @NotNull
    private String localisation;
    
    @NotNull
    private String nom;
    @OneToMany(mappedBy = "stationRattachement")
    private List<Compte> comptes;

    public Station() {
    }

    public Station(String nom, String localisation) {
        this.nom = nom;
        this.localisation = localisation;
    }
    
    public String getLocalisation() {
        return localisation;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(final String nom) {
        this.nom = nom;
    }

    public List<Navette> getNavettes() {
        return navettes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Station)) {
            return false;
        }
        Station other = (Station) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.Station[ id=" + id + " ]";
    }
    
    public void ajouterCompte(Compte compte) {
        comptes.add(compte);
    }
    
    public void retirerCompte(Compte compte) {
        comptes.remove(compte);
    }
    
    public int quaisDisponibles() {
        int nbQuaisDisponibles;
        
        nbQuaisDisponibles = 0;
        for (int i = 0 ; i < quais.size() ; i++) {
            if (quais.get(i).getNavette() == null) {
                nbQuaisDisponibles++;
            }
        }
        /* On supprime les navettes qui sont à destination de la station */
        return (nbQuaisDisponibles - navettes.size());
    }
}
