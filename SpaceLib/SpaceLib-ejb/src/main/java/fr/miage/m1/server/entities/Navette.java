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
    
    private boolean revisionEnCours;
    
    @ManyToOne
    private Station destination;
    
    @NotNull
    int nbPassagersMaximum;

    public int getNbPassagersMaximum() {
        return nbPassagersMaximum;
    }

    public void setNbPassagersMaximum(int nbPassagersMaximum) {
        this.nbPassagersMaximum = nbPassagersMaximum;
    }
    
    
    @OneToMany(mappedBy = "navette")
    private List<Compte> comptes;
    
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

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }
    
    public void addCompte(Compte compte) {
        comptes.add(compte);
    }
    
    public void removeCompte(Compte compte) {
        comptes.remove(compte);
    }
    
    public boolean compteExist(Compte compte) {
        return comptes.contains(compte);
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

    public boolean isRevisionEnCours() {
        return revisionEnCours;
    }

    public void setRevisionEnCours(boolean revisionEnCours) {
        this.revisionEnCours = revisionEnCours;
    }

    public Station getDestination() {
        return destination;
    }

    public void setDestination(Station destination) {
        this.destination = destination;
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
    
    public void ajouterOperation(String value) {
        operations.add(new Operation(value, this));
    }
    
}
