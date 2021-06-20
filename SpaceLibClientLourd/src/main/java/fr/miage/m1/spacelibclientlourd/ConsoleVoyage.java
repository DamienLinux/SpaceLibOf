/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.spacelibclientlourd;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.List;

/**
 *
 * @author DamienAvetta-Raymond
 */
public class ConsoleVoyage {
    
    private ServiceClient service;
    
    public ConsoleVoyage(ServiceClient service) {
        this.service = service;
    }
    
    
    public String saisieIdReservation() {
        return service.saisieTexte("Quelle est l'ID de votre réservation ?");
    }
    public String saisieVoyage(ListeChoix liste) {
        liste.afficherListe();
        return liste.choixListe(service.saisieNombre("A quelle station souhaitez-vous vous rendre ? : ", 1, liste.getListe().size()));
    }
    
    public String saisieDateArrivee() {
        int annee,
            mois;

        System.out.println("Saisissez la date de départ : ");
        annee = saisieAnnee();
        mois = saisieMois();
        return (saisieJour(annee, mois) + "/" + mois + "/" + annee);
    }
    
    public int saisieAnnee() {
        Calendar calendar;

        calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        return service.saisieNombre("L'année : ", (calendar.get(Calendar.YEAR)), (Integer.MAX_VALUE - 2));
    }
    
    public int saisieMois() {
        return service.saisieNombre("Le mois : ", 1, 12);
    }
    
    public int saisieJour(int annee, int mois) {
        int max;
        List<Integer> moisJours30;
        
        moisJours30 = new ArrayList<Integer>(1);
        moisJours30.add(4);
        moisJours30.add(6);
        moisJours30.add(9);
        moisJours30.add(11);
        if (mois != 2) {
            max = (moisJours30.contains(mois) ? 30 : 31);
        } else {
            max = (annee % 4 == 0 && (annee % 100 != 0 || annee %400 == 0)) ? 29 : 28;
        }
        return service.saisieNombre("Le jour : ", 1, max);
    }
    
    public int saisieNbPassagers() {
        return service.saisieNombre("Combien de passagez seront avec vous ? : ", 0, (Integer.MAX_VALUE - 2));
    }
}
