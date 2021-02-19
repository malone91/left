package com.melo.left.rmi.registry;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloRegistryFacadeImpl extends UnicastRemoteObject implements HelloRegistryFacade {

    /**
     * 防止类上显示编译异常
     * @throws RemoteException
     */
    public HelloRegistryFacadeImpl() throws RemoteException {
        super();
    }

    @Override
    public String hello(String name) throws RemoteException {
        return "hello" + name;
    }
}