package io.github.cuisse.webby;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

/**
 * A static HTTP handler.
 *
 * @author Brayan Roman
 * @since  1.0.0
 */
public class StaticHttpHandler implements HttpHandler {

    private final StaticResourceManager resources;

    // Cached responses
    private static final byte[] NOT_FOUND = "404 - Not found.".getBytes();
    private static final byte[] METHOD_NOT_ALLOWED = "405 - Method not allowed.".getBytes();
    private static final byte[] INTERNAL_SERVER_ERROR = "500 - Internal server error.".getBytes();

    /**
     * Creates a new static HTTP handler.
     *
     * @param resources the static resource manager.
     */
    public StaticHttpHandler(StaticResourceManager resources) {
        this.resources = resources;
    }

    @Override
    public void handle(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Connection", "close");
        if (exchange.getRequestMethod().equals("GET")) {
            sendResource(exchange);
            if (Application.DEBUG) {
                Application.LOGGER.info(String.format("%s is accessing to %s", exchange.getRemoteAddress(),exchange.getRequestURI()));
            }
        } else {
            sendResponse(405, METHOD_NOT_ALLOWED, exchange);
            if (Application.DEBUG) {
                Application.LOGGER.log(Level.WARNING, String.format("%s tried to used invalid method %s in %s", exchange.getRemoteAddress(), exchange.getRequestMethod(), exchange.getRequestURI()));
            }
        }
    }

    private void sendResource(HttpExchange exchange) {
        Thread.ofVirtual().start(() -> {
            try {
                PageView page = resources.find(exchange.getRequestURI().toString());
                exchange.getResponseHeaders().set("Content-Type", page.contentType());
                sendResponse(200, page.content(), exchange);
            } catch (IOException exception) {
                if (exception instanceof FileNotFoundException) {
                    sendResponse(404, NOT_FOUND, exchange);
                    if (Application.DEBUG) {
                        Application.LOGGER.log(Level.WARNING, String.format("%s tried to access to %s but it does not exist", exchange.getRemoteAddress(), exchange.getRequestURI()));
                    }
                } else {
                    sendResponse(500, INTERNAL_SERVER_ERROR, exchange);
                    if (Application.DEBUG) {
                        Application.LOGGER.log(Level.SEVERE, String.format("%s tried to access to %s but an error occurred", exchange.getRemoteAddress(), exchange.getRequestURI()), exception);
                    }
                }
            }
        });
    }

    private void sendResponse(int status, byte[] body, HttpExchange exchange) {
        try {
            exchange.sendResponseHeaders(status, body.length);
            exchange.getResponseBody().write(body);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
