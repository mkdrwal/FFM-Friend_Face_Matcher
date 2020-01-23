package dev.mateuszkowalczyk.ffm.app.cache;

import dev.mateuszkowalczyk.ffm.data.database.photo.Photo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ThumbnailCacheService implements Runnable {
    protected static final String DIRECTORY_NAME = "/thumbnails";
    private BufferedImage image;
    private CacheService cacheService = CacheService.getInstance();
    private String path;

    public ThumbnailCacheService() {

    }

    public ThumbnailCacheService(BufferedImage bufferedImage) {
        this.image = bufferedImage;
    }

    @Override
    public void run() {
        this.createCachedThumbnail();
    }

    public void createPath(Photo photo) {
        this.path = this.cacheService.getPath() + DIRECTORY_NAME + "/" + photo.getFileName();
        photo.setCachedPath(this.path);
    }


    private void createCachedThumbnail() {
        if (image != null && this.path != null) {
            File imageFile = new File(this.path);
            try {
                ImageIO.write(this.image, this.fileExtension(), imageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String fileExtension() {
        return this.path.substring(this.path.length() - 3);
    }
}
