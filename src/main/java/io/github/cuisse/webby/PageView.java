package io.github.cuisse.webby;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Brayan Roman
 * @since  1.0.0
 */
public record PageView(File file) {

    /**
     * @return the content of the file.
     */
    public byte[] content() throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            return fis.readAllBytes();
        }
    }

    /**
     * @return the content type of the file.
     */
    public String contentType() {
        return switch (file.getName().substring(file.getName().lastIndexOf('.') + 1)) {
            case "html"        -> "text/html";
            case "css"         -> "text/css";
            case "js"          -> "text/javascript";
            case "png"         -> "image/png";
            case "jpg", "jpeg" -> "image/jpeg";
            case "gif"         -> "image/gif";
            case "ico"         -> "image/x-icon";
            default            -> "text/plain";
        };
    }

}
