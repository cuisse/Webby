package io.github.cuisse.webby;

import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Application entry point.
 *
 * @author Brayan Roman
 * @since  1.0.0
 */
public class Application {

    public static final Logger LOGGER = Logger.getLogger(Application.class.getName());
    public static final boolean DEBUG = System.getProperty("debug", "false").equals("true");

    static {
        if (System.getProperty("java.util.logging.SimpleFormatter.format") == null) {
            System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] %4$s: %5$s%6$s%n");
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            main(new String[] { "--help" });
        } else {
            if (args.length == 1) {
                switch (args[0].toLowerCase()) {
                    case "-h", "--help"    -> help();
                    case "-v", "--version" -> version();
                    case "-a", "--auto"    -> auto();
                    default                -> main(new String[] { "--help" });
                }
            } else {
                var server = new WebServer(
                        argument(args, 0),                                // Index page
                        Path.of(argument(args, 1)), // Resources directory
                        argument(args, 2),                                // URI
                        Integer.parseInt(argument(args, 3))               // Number of threads to be used
                );
                server.start();
            }
        }
    }

    private static void help() {
        System.out.println("Basic Usage:");
        System.out.println("   1. java -jar webby.jar [index] [path] [uri] [threads]");
        System.out.println("   2. java -jar webby.jar [-a, -auto]");
        System.out.println("   3. java -jar webby.jar [-h, -help]");
        System.out.println("   4. java -jar webby.jar [-v, -version]");
        System.out.println("Example: java -jar webby.jar index.html ./static http://localhost:8080 3");
    }

    private static void version() {
        System.out.println("Version: 1.0.0");
    }

    private static void auto() {
        main(
             new String[] { "index.html", "./static",  "http://localhost:8080", "3" }
        );
    }

    private static String argument(String[] args, int index) {
        if (index >= args.length || args[index] == null) {
            LOGGER.log(Level.SEVERE, String.format("Argument No.%s is missing", index));
            System.exit(1);
        }
        return args[index];
    }

}
