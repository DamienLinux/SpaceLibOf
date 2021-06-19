/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.spacelibclientlourd;

/**
 *
 * @author DamienAvetta-Raymond
 */
public class ConsoleAuthentifier {
    private ServiceClient service;
    
    public ConsoleAuthentifier(ServiceClient service) {
        this.service = service;
    }
    
    public String[] saisieConnexion() {
        String[] infosCompte;
        
        infosCompte = new String[2];
        infosCompte[0] = service.saisieTexte("Saisissez votre identifiant : ");
        infosCompte[1] = service.saisieTexte("Saisissez votre mot de passe : ");
        return infosCompte;
    }
    
    public String renseignerStation() {
        return service.saisieTexte("Quelle est votre station de rattachement ? : ");
    }
}
