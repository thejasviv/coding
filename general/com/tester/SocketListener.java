package com.tester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketListener {

    public static void main(String[] args) throws IOException {
        try (ServerSocket ssocket = new ServerSocket(26083);
                Socket socket = ssocket.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintStream ps = new PrintStream(socket.getOutputStream())) {
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            ps.println("Hello from dummy server!");
        }
    }
}
