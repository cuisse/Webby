package io.github.cuisse.webby;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * A static resource manager.
 *
 * @author Brayan Roman
 * @since  1.0.0
 */
public class StaticResourceManager {

    private final Map<String, PageView> resources;

    /**
     * Creates a new static resource manager.
     *
     * @param index the index page.
     * @param path  the resources' directory.
     */
    public StaticResourceManager(String index, Path path) {
        this.resources = listResources(path, path.toFile(), new HashMap<>());
        this.resources.put("/", resources.get("/".concat(index)));
        if (this.resources.get("/") == null) {
            throw new WebException("Index file was not found.");
        }
    }

    /**
     * Finds a specific resource.
     *
     * @param resource     the resource to find
     * @return             the resource as a {@link PageView} instance
     * @throws IOException if the resource isn't found, or if the resource cannot be read
     */
    public PageView find(String resource) throws IOException {
        return required(
                resources.get(resource)
        );
    }

    private Map<String, PageView> listResources(Path base, File path, Map<String, PageView> resources) {
        if (path.exists()) {
            if (path.isDirectory()) {
                File[] files = path.listFiles();
                if (files == null) {
                    throw new WebException("Unknown error occurred while listing resources.");
                }
                for (File file : files) {
                    if (file.isDirectory()) {
                        listResources(base, file, resources);
                    } else {
                        resources.put(escape(file.getPath(), base.toString()), new PageView(file));
                    }
                }
                return resources;
            }
            throw new WebException(path.getPath() + " is not a directory.");
        }
        throw new WebException("Path '" + path.getPath() + "' is required but does not exist.");
    }

    private String escape(String file, String path) {
        return file.replace("\\", "/").replace(path.replace("\\", "/"), "");
    }

    private PageView required(PageView page) throws FileNotFoundException {
        if (page == null) {
            throw new FileNotFoundException("The resource file was not found.");
        } else {
            return page;
        }
    }

}
