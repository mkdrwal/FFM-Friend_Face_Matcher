package dev.mateuszkowalczyk.ffm.app.cache;

import dev.mateuszkowalczyk.ffm.data.database.photo.Photo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ThumbnailCacheService {
    protected static final String DIRECTORY_NAME = "/thumbnails";
    private static ThumbnailCacheService instance;
    private CacheService cacheService = CacheService.getInstance();
    private String path;

    private ThumbnailCacheService() {

    }

    public static ThumbnailCacheService getInstance() {
        if (instance == null) {
            instance = new ThumbnailCacheService();
        }

        return instance;
    }

    public void createPath(Photo photo) {
        this.path = this.cacheService.getPath() + DIRECTORY_NAME + "/" + photo.getFileName();
        photo.setCachedPath(this.path);
    }

    public void readThumbnail(Photo photo) throws FileNotFoundException {
        var file = new File(photo.getCachedPath());

        if(file.exists()) {
            try {
                BufferedImage image = ImageIO.read(file);
                photo.setBufferedImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new FileNotFoundException();
        }
    }

    public void createCachedThumbnail(BufferedImage bufferedImage) {
        if (bufferedImage != null && this.path != null) {
            File imageFile = new File(this.path);
            try {
                ImageIO.write(bufferedImage, this.fileExtension(), imageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String fileExtension() {
        return this.path.substring(this.path.length() - 3);
    }
}
