package com.demo;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;

public class Helloworld {

    public static void main(String[] args) throws Exception {

        String version = "Version 3 - CI/CD Live Demo";
        String deployTime = LocalDateTime.now().toString();
        String hostname = InetAddress.getLocalHost().getHostName();

        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", 8000), 0);

        server.createContext("/", new HttpHandler() {

            public void handle(HttpExchange exchange) {

                try {

                    String requestURI = exchange.getRequestURI().toString();
                    String userAgent = exchange.getRequestHeaders().getFirst("User-Agent");

                    String response =
                            "===== Jenkins CI/CD Demo Application =====\n\n" +
                            "Hello from Jenkins Pipeline!\n\n" +
                            "Application Version    : " + version + "\n" +
                            "Deployment Time        : " + deployTime + "\n" +
                            "Server Hostname        : " + hostname + "\n\n" +
                            "Request Details\n" +
                            "--------------\n" +
                            "Requested URL          : " + requestURI + "\n" +
                            "User Agent             : " + userAgent + "\n";

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
        System.out.println("Hostname: " + hostname);
    }
}
