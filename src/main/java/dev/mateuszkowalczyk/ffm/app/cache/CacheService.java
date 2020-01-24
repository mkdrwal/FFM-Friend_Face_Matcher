package dev.mateuszkowalczyk.ffm.app.cache;

import dev.mateuszkowalczyk.ffm.utils.PropertiesLoader;
import dev.mateuszkowalczyk.ffm.utils.Property;

public class CacheService {
    private static CacheService instance;
    private String path = "";

    private CacheService () {
       this.check();
    }

    public synchronized static CacheService getInstance() {
        if(instance == null) {
            instance = new CacheService();
        }

        return instance;
    }

    public String getPath(String name) {
        return this.getPath() + "/" + name;
    }

    public String getPath() {
        return path;
    }

    public void check() {
        this.updatePath();
        if (this.path != null) {
            CacheStructureChecker cacheStructureChecker = new CacheStructureChecker();
            cacheStructureChecker.check(this.path);
        }
    }

    private String updatePath() {
        String path = PropertiesLoader.getInstance().get(Property.PATH_TO_DIRECTORY);

        if (path != null) {
            this.path = path + "/.cache";
        }

        return path;
    }
}
