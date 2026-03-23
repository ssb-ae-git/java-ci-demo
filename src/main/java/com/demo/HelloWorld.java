package com.demo;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HelloWorld {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", 8000), 0);

        server.createContext("/", new HttpHandler() {

            public void handle(HttpExchange exchange) {

                try {

                    String response = "Hello from Jenkins CI/CD Pipeline!";

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
    }
}
