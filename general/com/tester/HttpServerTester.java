package com.tester;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

class MyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
        String response = "This is your response";
        try {
            t.sendResponseHeaders(204, response.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            t.getRequestBody().read();
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
            t.close();
        }
    }
}

public class HttpServerTester {
    
    public static void main(String[] args) throws Exception {
        testRequestCompressionBody();
    }
    
    public static void testRequestCompressionBody() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(0), 0);
        MyHandler gzipHandler = new MyHandler();
        server.createContext("/products", gzipHandler);
        server.setExecutor(null);
        server.start();
        String endpoint = "http://localhost:" + server.getAddress().getPort();
        try {
            URL url = new URL(endpoint + "/products");
            URLConnection conn = url.openConnection();
            int i = -1;
            while ((i = conn.getInputStream().read()) != -1) {
                System.out.print((char) i);
            }
            System.out.println();
        } finally {
            server.stop(0);
        }
    }
}
