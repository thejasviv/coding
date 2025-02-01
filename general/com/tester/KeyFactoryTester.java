package com.tester;

import java.security.KeyFactory;

public class KeyFactoryTester {

    public static void main(String[] args) throws Exception {
        KeyFactory.getInstance("RSA", "SunJSSE");
        KeyFactory.class.getProtectionDomain().getCodeSource().getLocation();
    }

}
