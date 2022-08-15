package com.webby;

import java.util.logging.Logger;

public class Application {

    private static final Logger LOGGER = Logger.getLogger("webby");

    public static void main(String[] args) {
        WebServer server = new WebServer(
                getArgument(args, 2, "http://localhost:8080"),                    // URI
                getArgument(args, 0, "/index.html"),                              // Main page
                getArgument(args, 1, System.getProperty("user.dir") + "/static"), // Resources directory
                Integer.parseInt(getArgument(args, 3, "3"))                       // Amount of threads to be use
        );
        server.start();
    }

    private static String getArgument(String[] args, int index, String defaultValue) {
        if (index >= args.length || args[index] == null) {
            LOGGER.info(String.format("Argument No.%s is missing, using default %s", index, defaultValue));
            return defaultValue;
        }
        return args[index];
    }

}
