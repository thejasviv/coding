package com.tester;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import com.google.common.collect.ImmutableList;

public class ProtocolsNCipherSuites {

    public static void main(String[] args) throws Exception {
        SSLServerSocketFactory serverFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket serverSocket = (SSLServerSocket) serverFactory.createServerSocket();
        printAll(serverSocket.getEnabledProtocols(), serverSocket.getEnabledCipherSuites());
        printAll(serverSocket.getSupportedProtocols(), serverSocket.getSupportedCipherSuites());
        SSLSocketFactory clientFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket clientSocket = (SSLSocket) clientFactory.createSocket();
        printAll(clientSocket.getEnabledProtocols(), clientSocket.getEnabledCipherSuites());
        printAll(clientSocket.getSupportedProtocols(), clientSocket.getSupportedCipherSuites());
        //printAll(SSLContext.getDefault().createSSLEngine().getEnabledProtocols(), new String[] {});
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(null, null, null);
        SSLEngine engine = context.createSSLEngine();
        printAll(engine.getEnabledProtocols(), new String[] {});
    }

    private static void printAll(String[] protocols, String[] cipherSuites) {
        for (String protocol : protocols) {
            System.out.println(protocol);
        }
        for (String ciphersuite : cipherSuites) {
            System.out.println(ciphersuite);
        }
    }
}
