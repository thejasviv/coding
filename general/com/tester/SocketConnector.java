package com.tester;

import java.net.Socket;

public class SocketConnector {

    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("localhost", 7443)) {
            socket.getOutputStream().write(1);
        }
    }
}
