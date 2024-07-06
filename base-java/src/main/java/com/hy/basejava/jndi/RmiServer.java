package com.hy.basejava.jndi;
import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.NamingException;
import javax.naming.Reference;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiServer {

    public static void main(String[] args) throws RemoteException, NamingException, AlreadyBoundException {
        String url = "http://127.0.0.1:8080/Test.class";
        Registry registry = LocateRegistry.createRegistry(1099);
        Reference reference = new Reference("Test", "Test", url);
        ReferenceWrapper referenceWrapper = new ReferenceWrapper(reference);
        registry.bind("obj",referenceWrapper);
        System.out.println("running");
    }
}
