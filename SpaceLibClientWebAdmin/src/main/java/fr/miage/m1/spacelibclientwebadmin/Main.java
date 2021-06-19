/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.spacelibclientwebadmin;

import fr.miage.m1.webservices.StationWebServices;
import fr.miage.m1.webservices.StationWebServices_Service;

/**
 *
 * @author DamienAvetta-Raymond
 */
public class Main {
    static StationWebServices_Service servicet = new StationWebServices_Service();
    static StationWebServices wservice = servicet.getStationWebServicesPort();
    
    public static void main(String[] args) {
        System.out.println("Début !");
        String retour = wservice.
    }
}
