package com.melo.left.rmi.registry;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloRegistryFacade extends Remote {

    String hello(String name) throws RemoteException;
}