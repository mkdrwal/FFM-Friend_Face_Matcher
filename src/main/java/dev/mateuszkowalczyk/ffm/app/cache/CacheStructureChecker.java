package dev.mateuszkowalczyk.ffm.app.cache;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CacheStructureChecker {
    private String path;
    private List<String> listToCheck = new ArrayList<String>();

    public CacheStructureChecker() {}

    public void check() {
        this.check(CacheService.getInstance().getPath());
    }

    public void check(String path) {
        this.path = path;

        if (this.path != null) {
            this.setupPathsToCheck();
            this.listToCheck.forEach(this::checkDirectory);
        }
    }

    private void setupPathsToCheck() {
        this.listToCheck.add(this.path);
        this.listToCheck.add(this.path + ThumbnailCacheService.DIRECTORY_NAME);
        this.listToCheck.add(this.path + FacesCacheService.DIRECTORY_NAME);
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
