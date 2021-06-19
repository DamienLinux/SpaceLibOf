/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.spacelibclientlourd;


import fr.miage.m1.shared.services.ServicesNavetteRemoteRemote;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author DamienAvetta-Raymond
 */
public class RMINavetteServiceManager implements RMIServiceManager {
    
    private final String REMOTE_COLL_URL = "fr.miage.m1.shared.services.ServicesNavetteRemoteRemote";
    
    private InitialContext namingContext;
    
    private ServicesNavetteRemoteRemote serviceNavetteRemote;

    public ServicesNavetteRemoteRemote getServiceNavetteRemote() {
        return serviceNavetteRemote;
    }
    
    public RMINavetteServiceManager() throws NamingException {
        initJndi();
        remoteServiceManager();
    }

    @Override
    public void initJndi() throws NamingException {
        /* Pas de spécifications particulière */
        namingContext = new InitialContext();
    }

    @Override
    public void remoteServiceManager() throws NamingException {
        serviceNavetteRemote = (ServicesNavetteRemoteRemote) namingContext.lookup(REMOTE_COLL_URL);
    }
    
}
