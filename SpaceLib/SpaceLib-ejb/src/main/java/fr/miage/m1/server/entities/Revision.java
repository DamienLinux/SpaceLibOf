/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.server.entities;

import java.io.Serializable;
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
public class Revision implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private Compte mecanicienEnCharge;
    
    @ManyToOne
    private Quai quai;
    
    public Revision() {
    }
    
    public Revision(Navette navette, Quai quai, Compte mecanicienEnCharge) {
        this.navette = navette;
        this.quai = quai;
        this.mecanicienEnCharge = mecanicienEnCharge;
    }

    public Quai getQuai() {
        return quai;
    }

    public void setQuai(Quai quai) {
        this.quai = quai;
    }
    
    private boolean enCours;

    public boolean isEnCours() {
        return enCours;
    }

    public void setEnCours(boolean enCours) {
        this.enCours = enCours;
    }
    
    @ManyToOne
    private Navette navette;

    public Compte getMecanicienEnCharge() {
        return mecanicienEnCharge;
    }

    public void setMecanicienEnCharge(Compte mecanicienEnCharge) {
        this.mecanicienEnCharge = mecanicienEnCharge;
    }

    public Navette getNavette() {
        return navette;
    }

    public void setNavette(Navette navette) {
        this.navette = navette;
    }

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
        if (!(object instanceof Revision)) {
            return false;
        }
        Revision other = (Revision) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.miage.m1.server.entities.Revision[ id=" + id + " ]";
    }
    
}
