package com.tester;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressTester {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost(); // Both hostname and canonical hostname are matching
        outMe(address);
        address = InetAddress.getByAddress(InetAddress.getLocalHost().getHostName(), new byte[] {0, 0, 0, 0}); // Matching hostname, non-matching canonical hostname
        outMe(address);
        address = InetAddress.getByAddress("some_invalid_hostname", InetAddress.getLocalHost().getAddress()); // Non-matching hostname, matching caninical hostname
        outMe(address);
    }
    
    private static void outMe(InetAddress address) {
        System.out.println(address.getHostName());
        System.out.println(address.getCanonicalHostName());
        System.out.println(address.getHostAddress());
        System.out.println(address);
        System.out.println("**********************************************");
    }

}
