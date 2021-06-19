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
public class Navette implements Serializable {
    private Long id;
    
    private String nom;
    
    private boolean aReviser;
    
    private boolean revisionEnCours;
    
    private Station destination;
    
    private List<Operation> operations;
    
    private List<Compte> comptes;

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

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    public Navette(Long id, String nom, boolean aReviser, boolean revisionEnCours, Station destination, List<Operation> operations, List<Compte> comptes) {
        this.id = id;
        this.nom = nom;
        this.aReviser = aReviser;
        this.revisionEnCours = revisionEnCours;
        this.destination = destination;
        this.operations = operations;
        this.comptes = comptes;
    }

    public Navette() {
    }
}
