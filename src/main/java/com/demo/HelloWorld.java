package com.demo;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;

public class HelloWorld {

    public static void main(String[] args) throws Exception {

        String version = "Version 2 - Deployed via Jenkins CI/CD";
        String deployTime = LocalDateTime.now().toString();

        // Bind to all interfaces so it is accessible externally
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", 8000), 0);

        server.createContext("/", new HttpHandler() {

            public void handle(HttpExchange exchange) {

                try {

                    String requestURI = exchange.getRequestURI().toString();
                    String userAgent = exchange.getRequestHeaders().getFirst("User-Agent");

                    String response =
                            "Hello from Jenkins CI/CD Pipeline\n" +
                            version + "\n" +
                            "Deployment Time        : " + deployTime + "\n";

                    exchange.sendResponseHeaders(200, response.length());

                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        server.setExecutor(null);
        server.start();

        System.out.println("Server started on port 8000");
        System.out.println("Application Version: " + version);
        System.out.println("Deployment Time: " + deployTime);
    }
}
