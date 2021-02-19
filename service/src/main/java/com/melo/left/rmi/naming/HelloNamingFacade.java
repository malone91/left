package com.melo.left.rmi.naming;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloNamingFacade extends Remote {

    String helloWorld(String name) throws RemoteException;
}