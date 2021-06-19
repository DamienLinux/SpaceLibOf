/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.spacelibclientlourd;

import javax.naming.NamingException;

/**
 *
 * @author DamienAvetta-Raymond
 */
public interface RMIServiceManager {
    public void initJndi() throws NamingException;
    
    public void remoteServiceManager() throws NamingException;
}
