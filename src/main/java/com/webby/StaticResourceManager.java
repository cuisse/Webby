package com.webby;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StaticResourceManager implements IResourceManager {

    private final String index;
    private final Map<String, PageView> resources;

    public StaticResourceManager(String index, String path) {
        this.index = index;
        this.resources = getResources(path, new File(path), new HashMap<>());
        this.resources.put("/", this.resources.get(index));
        this.validateResources();
    }

    private void validateResources() {
        if (resources.isEmpty()) {
            throw new WebException("Resources folder is empty.");
        }
        if (resources.get("/") == null) {
            throw new WebException(index + " was not found.");
        }
    }

    private Map<String, PageView> getResources(String base, File path, Map<String, PageView> resources) {
        if (path.exists()) {
            if (path.isDirectory()) {
                File[] files = path.listFiles();
                if (files == null) {
                    throw new WebException("Unknown error occurred while listing resources.");
                }
                for (File file : files) {
                    if (file.isDirectory()) {
                        getResources(base, file, resources);
                    }
                    resources.put(getNiceResourceName(file.getPath(), base), new PageView(file));
                }
                return resources;
            }
            throw new WebException(path.getPath() + " is not a directory.");
        }
        throw new WebException(path.getPath() + " does not exists.");
    }

    private String getNiceResourceName(String file, String path) {
        file = file.replace("\\", "/");
        path = path.replace("\\", "/");
        return file.replace(path, "");
    }

    @Override
    public PageView findResource(String resource) throws IOException {
        PageView page = resources.get(resource);
        if (page == null) {
            throw new FileNotFoundException(resource + " not found");
        }
        return page;
    }

}
