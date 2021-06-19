/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Entity
public class Voyage implements Serializable {
    
    @ManyToOne
    private Navette navette;
    
    private boolean enCours = true;
    
    public Date dateDepart = new Date();
    
    public int nbPassagers;

    public int getNbPassagers() {
        return nbPassagers;
    }

    public void setNbPassagers(int nbPassagers) {
        this.nbPassagers = nbPassagers;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Date getDateArriveePrevue() {
        return dateArriveePrevue;
    }

    public void setDateArriveePrevue(Date dateArriveePrevue) {
        this.dateArriveePrevue = dateArriveePrevue;
    }
    
    public Date dateArriveePrevue;

    public boolean isEnCours() {
        return enCours;
    }

    public void setEnCours(boolean enCours) {
        this.enCours = enCours;
    }

    public Navette getNavette() {
        return navette;
    }

    public void setNavette(Navette navette) {
        this.navette = navette;
    }

    public Quai getDestination() {
        return destination;
    }

    public void setDestination(Quai destination) {
        this.destination = destination;
    }
    
    @ManyToOne
    private Quai depart;

    public Quai getDepart() {
        return depart;
    }

    public void setDepart(Quai depart) {
        this.depart = depart;
    }
    
    @ManyToOne
    private Quai destination;

    public Voyage() {
    }
    
    public Voyage(Navette navette, Quai depart, Quai destination, Date dateArriveePrevue, int nbPassagers) {
        this.navette = navette;
        this.depart = depart;
        this.destination = destination;
        this.dateArriveePrevue = dateArriveePrevue;
        this.nbPassagers = nbPassagers;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Voyage)) {
            return false;
        }
        Voyage other = (Voyage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.miage.m1.server.entities.Voyage[ id=" + id + " ]";
    }
    
}
