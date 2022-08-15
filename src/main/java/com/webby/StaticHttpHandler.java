package com.webby;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.FileNotFoundException;
import java.io.IOException;

public class StaticHttpHandler implements HttpHandler {

    private final IResourceManager resourceManager;

    public StaticHttpHandler(IResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equals("GET")) {
                sendResource(exchange);
            }
            else {
                sendResponse(405, "405 - Method not allowed", exchange);
            }
        }
        catch (IOException exception) {
            // Catch any IOException. For example while trying to read a file.
            sendResponse(500, "500 - Internal server error occurred.", exchange);
        }
    }

    private void sendResource(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Connection", "close");
        try {
            PageView page = resourceManager.findResource(exchange.getRequestURI().toString());
            sendResponse(200, page.getContent(), exchange);
        }
        catch (FileNotFoundException exception) {
            sendResponse(404, "404 - Resource \"" + exchange.getRequestURI() + "\" was not found. ", exchange);
        }
    }

    private void sendResponse(int status, String body, HttpExchange exchange) throws IOException {
        sendResponse(status, body.getBytes(), exchange);
    }

    private void sendResponse(int status, byte[] body, HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(status, body.length);
        exchange.getResponseBody().write(body);
    }

}
