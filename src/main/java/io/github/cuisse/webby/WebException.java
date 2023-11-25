package io.github.cuisse.webby;

/**
 * Exception thrown by the web server.
 *
 * @author Brayan Roman
 * @since  1.0.0
 */
public class WebException extends RuntimeException {

    /**
     * Constructs a new web exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public WebException(String message) {
        super(message);
    }

    /**
     * Constructs a new web exception with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause.
     */
    public WebException(String message, Throwable cause) {
        super(message, cause);
    }

}
