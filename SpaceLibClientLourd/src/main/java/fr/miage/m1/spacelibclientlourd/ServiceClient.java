/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.spacelibclientlourd;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author DamienAvetta-Raymond
 */
public class ServiceClient {
    private Scanner clavier = new Scanner(System.in);
    
    private String ERREUR_SAISIE_NOMBRE_MIN = "Vous devez saisir un nombre supérieur ou égal à ";
    
    private String ERREUR_SAISIE_NOMBRE_MAX = "Vous devez saisir un nombre inférieur ou égal à ";
    
    public String saisieTexte(String question) {
        return saisieTexte(question, null);
    }
    
    public String saisieTexte(String question, String[] autorise) {
        boolean valide;
        String saisie;
        
        valide = false;
        do {
            System.out.println(question);
            saisie = clavier.next() + clavier.nextLine();
            valide = (autorise == null || autorise.length == 0);
            for (int i = 0 ; !valide && i < autorise.length  ; i++) {
                valide = saisie.equals(autorise[i]);
            }
        } while (!valide);
        
        return saisie;
    }
    
    public int saisieNombre(String question, int min, int max) {
        int saisie;
        boolean valide;
       
        saisie = -1;
        do {
            System.out.println(question);
            valide = clavier.hasNextInt();
            if (valide) {
                saisie = clavier.nextInt();
                valide = (saisie >= min && saisie <= max);
                if (saisie < min) {
                    System.out.println(ERREUR_SAISIE_NOMBRE_MIN + min + ".");
                } else if (saisie > max) {
                    System.out.println(ERREUR_SAISIE_NOMBRE_MAX + max + ".");
                }
            }
            /* Vidage du tampon */
            clavier.nextLine();
        } while (!valide);
        return saisie;
    }
}
