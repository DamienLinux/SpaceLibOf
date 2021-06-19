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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Entity
public class Quai implements Serializable {

    
    private List<Voyage> voyagesADestination;
    
    @OneToMany(mappedBy = "depart")
    private List<Voyage> voyagesDepart;
    @OneToMany(mappedBy = "quai")
    private List<Revision> revisions;

    public List<Revision> getRevisions() {
        return revisions;
    }

    public void setRevisions(List<Revision> revisions) {
        this.revisions = revisions;
    }

    public List<Voyage> getVoyagesDepart() {
        return voyagesDepart;
    }

    public void setVoyagesDepart(List<Voyage> voyagesDepart) {
        this.voyagesDepart = voyagesDepart;
    }

    public List<Voyage> getVoyagesADestination() {
        return voyagesADestination;
    }

    public void setVoyagesADestination(List<Voyage> voyages) {
        this.voyagesADestination= voyages;
    }

    public Quai(Station station) {
        this.station = station;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToOne(mappedBy = "quai")
    private Navette navette;

    public Navette getNavette() {
        return navette;
    }

    public void setNavette(Navette navette) {
        this.navette = navette;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Quai() {
    }
    
    @ManyToOne
    private Station station;

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
        if (!(object instanceof Quai)) {
            return false;
        }
        Quai other = (Quai) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.miage.m1.server.entities.Quai[ id=" + id + " ]";
    }
    
    public void ajouterVoyagesADestination(Voyage voyage) {
        voyagesADestination.add(voyage);
    }
    
    public void ajouterVoyagesDepart(Voyage voyage) {
        voyagesDepart.add(voyage);
    }
    
    public void ajouterRevision(Revision revision) {
        revisions.add(revision);
    }
}
