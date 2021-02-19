package com.melo.left.rmi.registry;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RegistryClient {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(1000);
            HelloRegistryFacade helloRegistryFacade = (HelloRegistryFacade) registry.lookup("helloRegistry");
            String response = helloRegistryFacade.hello("melo");
            System.out.println(response);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}