/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.spacelibclientlourd;

import fr.miage.m1.shared.services.ServicesCompteRemoteRemote;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author DamienAvetta-Raymond
 */
public class RMICompteServiceManager implements RMIServiceManager {
    
    private final String REMOTE_COLL_URL = "fr.miage.m1.shared.services.ServicesCompteRemoteRemote";
    
    private InitialContext namingContext;
    
    private ServicesCompteRemoteRemote serviceCompteRemote;

    public RMICompteServiceManager() throws NamingException {
        initJndi();
        remoteServiceManager();
    }

    public ServicesCompteRemoteRemote getServiceCompteRemote() {
        return serviceCompteRemote;
    }

    @Override
    public void initJndi() throws NamingException {
        /* Pas de spécifications particulière */
        namingContext = new InitialContext();
    }

    @Override
    public void remoteServiceManager() throws NamingException {
        serviceCompteRemote = (ServicesCompteRemoteRemote) namingContext.lookup(REMOTE_COLL_URL);
    }
    
}
