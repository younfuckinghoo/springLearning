package com.hy.basejava.dp;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetTest {


    public static void main(String[] args) throws SocketException {

        Enumeration<NetworkInterface> interfacesEnum = NetworkInterface.getNetworkInterfaces();
       
    }

}