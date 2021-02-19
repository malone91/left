package com.melo.left.rmi.registry;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RegistryService {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1000);
            //create remote object
            HelloRegistryFacade helloRegistryFacade = new HelloRegistryFacadeImpl();
            //remote object 注册到rmi注册服务器上
            registry.rebind("helloRegistry", helloRegistryFacade);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}