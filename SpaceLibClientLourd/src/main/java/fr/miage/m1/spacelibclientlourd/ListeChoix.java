/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.spacelibclientlourd;

import java.util.List;

/**
 *
 * @author DamienAvetta-Raymond
 */
public class ListeChoix {
    private List<String> liste;
    
    public ListeChoix(List<String> liste) {
        this.liste = liste;
    }
    
    public void afficherListe() {
        for (int i = 0 ; i < liste.size() ; i++) {
            System.out.println((i+1) + " - " + liste.get(i));
        }
    }
    
    public String choixListe(int indexChoix) {
        return liste.get((indexChoix - 1));
    }
}
