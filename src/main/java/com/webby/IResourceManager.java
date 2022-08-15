package com.webby;

import java.io.IOException;

public interface IResourceManager {

    /**
     * Finds a specific resource.
     *
     * @param file The resource to find
     * @return The resource as a {@link com.webby.PageView} instance
     * @throws IOException if resource not found, or if the resource cannot be read
     */
    PageView findResource(String file) throws IOException;

}
