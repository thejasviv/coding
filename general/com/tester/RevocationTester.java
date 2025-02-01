package com.tester;

import java.net.URL;
import java.net.URLConnection;

public class RevocationTester {
    public static void main(String[] args) throws Throwable {
        testRevocation();
    }
    
    private static void testRevocation() throws Exception {
        System.setProperty("com.sun.net.ssl.checkRevocation", "true");
        System.setProperty("com.sun.security.ocsp.timeout", "5");
        URL url = new URL("https://www.salesforce.com");
        URLConnection conn = url.openConnection();
        System.out.println(conn.getInputStream().read());
    }
}
