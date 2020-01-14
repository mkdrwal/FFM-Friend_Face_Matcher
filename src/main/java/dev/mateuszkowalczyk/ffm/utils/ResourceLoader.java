package dev.mateuszkowalczyk.ffm.utils;

import java.io.InputStream;
import java.net.URL;

public class ResourceLoader {
    private static final ResourceLoader instance = new ResourceLoader();

    private ResourceLoader(){}

    public URL getResource(String name) {
        return getClass().getClassLoader().getResource(name);
    }

    public String getPath() {
        return this.getResource("").toString();
    }

    public InputStream getResourceAsStream(String name) {
        return getClass().getClassLoader().getResourceAsStream( name);
    }

    public String getPath(String name) {
        return this.getResource(name).getPath();
    }

    public static ResourceLoader getInstance() {
        return instance;
    }
}
