package com.tester;

import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class URLConnectionTester {
    
    private static final int POOL_SIZE = 1;
        
    public static void main(String[] args) throws Exception {
        final URL localURL = new URL("jar:file:/Users/tvoniadka/tmp/httpclient-4.5.13.jar!/org/apache/http/client/version.properties");
        Thread[] threads = new Thread[POOL_SIZE];
        for (int i = 0; i < POOL_SIZE; i++) {
            Thread t = new Thread(() -> {
                try {
                    URLConnection connection= localURL.openConnection();
                    connection.setDefaultUseCaches(false);
                    connection.setUseCaches(false);
                    JarURLConnection jarConnection = (JarURLConnection) connection;
                    if (jarConnection.getJarEntry() == null) {
                        throw new RuntimeException("Null Value");
                    }
                } catch (Exception e) {
                    System.err.println("Exception in thread " + Thread.currentThread().getName());
                    e.printStackTrace();
                }
            }, String.valueOf(i));
            threads[i] = t;
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }
}
