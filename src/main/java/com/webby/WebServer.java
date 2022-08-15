package com.webby;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;

public final class WebServer {

    private HttpServer httpServer;

    public WebServer(String rawURI, String index, String resources, int nThreads) {
        if (nThreads <= 0) {
            throw new IllegalArgumentException("Number of threads must be equal or greater then 1.");
        }
        try {
            httpServer = HttpServer.create(parseURI(rawURI), 0);
            httpServer.createContext("/", new StaticHttpHandler(new StaticResourceManager(index, resources)));
            httpServer.setExecutor(Executors.newFixedThreadPool(nThreads));
        }
        catch (IOException exception) {
            if (httpServer != null) {
                stop();
            }
            throw new WebException("Could not create the web server.", exception);
        }
    }

    public void start() {
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(0);
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
