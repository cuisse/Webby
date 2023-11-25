package io.github.cuisse.webby;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.concurrent.Executors;

/**
 * A simple web server.
 *
 * @author Brayan Roman
 * @since  1.0.0
 */
public final class WebServer {

    private HttpServer server;

    /**
     * Creates a new web server.
     *
     * @param index     the index page.
     * @param resources the resources' directory.
     * @param uri       the URI.
     * @param threads   the number of threads to be used.
     */
    public WebServer(String index, Path resources, String uri, int threads) {
        if (threads <= 0) {
            throw new WebException("Number of threads must be equal or greater then 1.");
        }
        try {
            server = HttpServer.create(parseURI(uri), 0);
            server.createContext("/", new StaticHttpHandler(new StaticResourceManager(index, resources)));
            server.setExecutor(Executors.newFixedThreadPool(threads));
        }
        catch (IOException exception) {
            throw new WebException("Could not create the web server.", exception);
        }
    }

    /**
     * Starts the web server.
     */
    public void start() {
        if (server != null) {
            server.start();
            Application.LOGGER.info("Server is running at " + server.getAddress());
        }
    }

    /**
     * Stops the web server.
     */
    public void stop() {
        if (server != null) {
            try {
                server.stop(0);
            } finally {
                server = null;
            }
        }
    }

    private InetSocketAddress parseURI(String rawURI) {
        try {
            URI uri = new URI(rawURI);
            return new InetSocketAddress(uri.getHost(), uri.getPort());
        }
        catch (URISyntaxException exception) {
            throw new WebException("Cannot parse URI, please verify it is correct (example: http://localhost:8080).", exception);
        }
    }

}
