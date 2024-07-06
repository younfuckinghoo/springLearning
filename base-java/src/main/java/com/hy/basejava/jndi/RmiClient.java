package com.hy.basejava.jndi;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RmiClient {

    public static void main(String[] args) throws NamingException {
        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase", "true");
        String url = "rmi://127.0.0.1:1099/obj";
        InitialContext initialContext = new InitialContext();
        initialContext.lookup(url);
    }

}
