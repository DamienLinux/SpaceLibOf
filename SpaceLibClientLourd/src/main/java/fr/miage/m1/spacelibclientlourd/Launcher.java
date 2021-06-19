/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.spacelibclientlourd;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author DamienAvetta-Raymond
 */
public class Launcher {
    private static final String ERREUR_CONNEXION_SERVEUR = "Erreur : Impossible de se connecter au serveur.";
    
    public static void main(String[] args) {
        AppLourdClient client;
        boolean executionValide;
        
        executionValide = true;
        client = null;
        try {
            client = new AppLourdClient();
        } catch (NamingException ex) {
            executionValide = false;
            System.out.println(ERREUR_CONNEXION_SERVEUR);
        }
        if (executionValide && client != null) {
            client.menu();
        }
    }
}
