package com.tester;

import java.security.KeyStore;

import javax.net.ssl.TrustManagerFactory;

public class KeyStoreTester {

    public static void main(String[] args) throws Exception {
        KeyStore store = KeyStore.getInstance("BCFKS");
        System.out.println(TrustManagerFactory.getDefaultAlgorithm());
    }

}
