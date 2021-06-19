/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.spacelibclientlourd;


import fr.miage.m1.shared.services.ServicesStationRemoteRemote;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author DamienAvetta-Raymond
 */
public class RMIStationServiceManager implements RMIServiceManager {
    
    private final String REMOTE_COLL_URL = "fr.miage.m1.shared.services.ServicesStationRemoteRemote";
    
    private InitialContext namingContext;
    
    private ServicesStationRemoteRemote serviceStationRemote;

    public ServicesStationRemoteRemote getServiceStationRemote() {
        return serviceStationRemote;
    }
    
    public RMIStationServiceManager() throws NamingException {
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
        serviceStationRemote = (ServicesStationRemoteRemote) namingContext.lookup(REMOTE_COLL_URL);
    }
    
}
