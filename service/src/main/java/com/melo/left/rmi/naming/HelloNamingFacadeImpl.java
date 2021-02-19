package com.melo.left.rmi.naming;

import java.io.Serializable;
import java.rmi.RemoteException;

public class HelloNamingFacadeImpl implements HelloNamingFacade, Serializable {

    @Override
    public String helloWorld(String name) throws RemoteException {
        return "hello" + name;
    }
}