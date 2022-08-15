package com.webby;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public record PageView(File file) {

    public byte[] getContent() throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            return fis.readAllBytes();
        }
    }

}
