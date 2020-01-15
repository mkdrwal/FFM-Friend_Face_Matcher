package dev.mateuszkowalczyk.ffm.app.cache;

import com.sun.javafx.sg.prism.NGRectangle;
import dev.mateuszkowalczyk.ffm.utils.PropertiesLoader;
import dev.mateuszkowalczyk.ffm.utils.Property;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CacheService {
    private static final CacheService instance = new CacheService();
    private String path = PropertiesLoader.getInstance().get(Property.PATH_TO_DIRECTORY);

    private CacheService () {
       this.check();
    }

    public static CacheService getInstance() {
        return instance;
    }

    public void check() {
        CacheStructureChecker cacheStructureChecker = new CacheStructureChecker();
        cacheStructureChecker.check();
    }

    public String createCachedThumbnail(String path) {
        this.path = PropertiesLoader.getInstance().get(Property.PATH_TO_DIRECTORY);
        BufferedImage bufferedImage = null;
        File image = null;
        File newImageFile = null;
        String pathToNewFile = null;

        try {
            image = new File(path);
            bufferedImage = ImageIO.read(image);

            float divider;
            if (bufferedImage.getWidth() > bufferedImage.getHeight()) {
                divider = bufferedImage.getWidth() / 200;
            } else {
                divider = bufferedImage.getHeight() / 200;
            }

            int width = (int) (bufferedImage.getWidth() / divider);
            int height = (int) (bufferedImage.getHeight() / divider);

            BufferedImage newImage = new BufferedImage(width, height, bufferedImage.getType());
            Graphics2D graphics2D = newImage.createGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics2D.drawImage(bufferedImage, 0, 0, width, height, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
            graphics2D.dispose();

            pathToNewFile = this.path + "/.cache/thumbnails/" + path.substring(this.path.length());
            newImageFile = new File(pathToNewFile);

            ImageIO.write(newImage, "jpg", newImageFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return pathToNewFile;
    }
}
