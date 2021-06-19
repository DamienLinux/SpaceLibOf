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
import javax.validation.constraints.NotNull;

/**
 *
 * @author DamienAvetta-Raymond
 */
@Entity
public class Navette implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    private String nom;
    
    private boolean aReviser;

    public List<Revision> getRevisions() {
        return revisions;
    }

    public void setRevisions(List<Revision> revisions) {
        this.revisions = revisions;
    }

    public List<Voyage> getVoyages() {
        return voyages;
    }

    public void setVoyages(List<Voyage> voyages) {
        this.voyages = voyages;
    }

    public Quai getQuai() {
        return quai;
    }

    public void setQuai(Quai quai) {
        this.quai = quai;
    }
    
    @NotNull
    int nbPassagersMaximum;
    @OneToMany(mappedBy = "navette")
    private List<Revision> revisions;
    @OneToMany(mappedBy = "navette")
    private List<Voyage> voyages;

    public int getNbPassagersMaximum() {
        return nbPassagersMaximum;
    }

    public void setNbPassagersMaximum(int nbPassagersMaximum) {
        this.nbPassagersMaximum = nbPassagersMaximum;
    }
    
    
    @OneToOne
    private Compte compte;

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }
    
    @OneToMany(mappedBy = "navette")
    private List<Operation> operations;
    
    
    @OneToOne
    private Quai quai;

    public Navette() {
    }
    
    public Navette(String nom, int nbPassagersMaximum, Quai quai) {
        this.nom = nom;
        this.nbPassagersMaximum = nbPassagersMaximum;
        this.quai = quai;
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

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isaReviser() {
        return aReviser;
    }

    public void setaReviser(boolean aReviser) {
        this.aReviser = aReviser;
    }

    public List<Operation> getOperations() {
        return operations;
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
        if (!(object instanceof Navette)) {
            return false;
        }
        Navette other = (Navette) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.Navette[ id=" + id + " ]";
    }
    
    public void ajouterOperation(Operation operation) {
        operations.add(operation);
    }
    
    public void ajouterVoyage(Voyage voyage) {
        voyages.add(voyage);
    }
    
    public void ajouterRevision(Revision revision) {
        revisions.add(revision);
    }
    
}
