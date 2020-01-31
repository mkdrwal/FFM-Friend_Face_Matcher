package dev.mateuszkowalczyk.ffm.image;

import dev.mateuszkowalczyk.ffm.app.cache.ThumbnailCacheService;
import dev.mateuszkowalczyk.ffm.data.database.photo.Photo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ThumbnailService {
    ThumbnailCacheService thumbnailCacheService = ThumbnailCacheService.getInstance();

    public BufferedImage createThumbnail(Photo photo) {
        BufferedImage originalImage;
        BufferedImage image = null;

        try {
            File originalImageFile = new File(photo.getPath());
            originalImage =  ImageIO.read(originalImageFile);

            float divider;
            if (originalImage.getWidth() > originalImage.getHeight()) {
                divider = (float) originalImage.getWidth() / (float) 200;
            } else {
                divider = (float) originalImage.getHeight() / (float) 200;
            }

            int width = (int) (originalImage.getWidth() / divider);
            int height = (int) (originalImage.getHeight() / divider);

            image = new BufferedImage(width, height, originalImage.getType());
            Graphics2D graphics2D = image.createGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics2D.drawImage(originalImage, 0, 0, width, height, 0, 0, originalImage.getWidth(), originalImage.getHeight(), null);
            graphics2D.dispose();

            this.thumbnailCacheService.createPath(photo);
            this.thumbnailCacheService.createCachedThumbnail(image);;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

}
