package dev.mateuszkowalczyk.ffm.app.cache;

import dev.mateuszkowalczyk.ffm.utils.PropertiesLoader;
import dev.mateuszkowalczyk.ffm.utils.Property;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CacheStructureChecker {
    private String path = PropertiesLoader.getInstance().get(Property.PATH_TO_DIRECTORY) + "/.cache";
    private List<String> listToCheck = new ArrayList<String>();

    public void check() {
        this.setupPathsToCheck();

        this.listToCheck.forEach(s -> this.checkDirectory(s));
    }

    private void setupPathsToCheck() {
        this.listToCheck.add(this.path);
        this.listToCheck.add(this.path + "/thumbnails");
    }


    private void checkDirectory(String path) {
        if (!this.directoryExists(path)) {
            this.createDirectory(path);
        }
    }

    private void createDirectory(String path) {
        File file = new File(path);

        file.mkdir();
    }

    private boolean directoryExists(String path) {
        File file = new File(path);

        return file.exists();
    }
}
