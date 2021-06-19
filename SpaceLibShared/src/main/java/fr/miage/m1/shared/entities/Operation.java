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
public class Operation implements Serializable {
    private Long id;

    private String operation;
    
    private Navette navette;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Navette getNavette() {
        return navette;
    }

    public void setNavette(Navette navette) {
        this.navette = navette;
    }

    public Operation(Long id, String operation, Navette navette) {
        this.id = id;
        this.operation = operation;
        this.navette = navette;
    }

    public Operation() {
    }
}
