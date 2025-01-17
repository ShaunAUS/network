package com.example.network.inet;

import java.net.InetAddress;
import java.net.UnknownHostException;

//Socket 내부에서 사용함
public class InetAddressMain {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress local = InetAddress.getByName("localhost");
        System.out.println(local);

        InetAddress google = InetAddress.getByName("google.com");
        System.out.println(google);


    }
}
