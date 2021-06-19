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
public class ConsoleCompte {    
    private final String ERREUR_MOT_DE_PASSE = "Vous n'avez pas saisi le même mot de passe.";
    
    private final String[] ROLE = {"Utilisateur", "Mécanicien", "Administrateur"};
    
    private ServiceClient service;
    
    public ConsoleCompte(ServiceClient service) {
        this.service = service;
    }
    
    public String[] saisieCompte() {
        String[] infosCompte;
        String question;
        String validationMotDePasse;
        boolean motDePasseValide;
        
        infosCompte = new String[3];
        infosCompte[0] = service.saisieTexte("Choisissez votre identifiant de connexion : ");
        do {
            infosCompte[1] = service.saisieTexte("Choisissez vote mot de passe : ");
            validationMotDePasse = service.saisieTexte("Resaissez votre mot de passe : ");
            motDePasseValide = infosCompte[1].equals(validationMotDePasse);
            if (!motDePasseValide) {
                System.out.println(ERREUR_MOT_DE_PASSE);
            }
        } while (!motDePasseValide);
        question = "Indiquez quelle est votre rôle entre ";
        for (String role : ROLE) {
            question += "\"" + role + "\" ";
        }
        question += ": ";
        infosCompte[2] = service.saisieTexte(question, ROLE);
        return infosCompte;
    }
}
