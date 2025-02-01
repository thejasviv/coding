package com.tester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Arrays;

public class HttpCalloutTester {

    public static void main(String[] args) throws IOException, InterruptedException {
        URL url = new URL("https://apigateway.cunamutual.com/");
        //URL url = new URL("https://www.salesforce.com/");
        //URL url = new URL("http://localhost:26083");
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
//        System.out.println(connection.getHeaderField("Accept"));
//        System.out.println(connection.getContentType());
//        System.out.println("****************************");
//        System.out.println(connection.getHeaderField("Accept"));
//        System.out.println(connection.getContentType());
//        connection.getHeaderFields().forEach((k, v) -> System.out.println(k + "=" + v));
        try (PrintStream pw = new PrintStream(connection.getOutputStream())) {
            pw.println("Hello!");
        }
        connection.getHeaderFields().forEach((k, v) -> System.out.println(k + "=" + v));
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
//        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
//        response.headers().map().forEach((k, v) -> System.out.println(k + "=" + v));
//        client.sendAsync(request, BodyHandlers.ofString())
//              .thenApply(HttpResponse::body)
//              .thenAccept(System.out::println)
//              .join();
    }
}
