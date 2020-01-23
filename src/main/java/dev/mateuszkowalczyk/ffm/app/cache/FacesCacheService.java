package dev.mateuszkowalczyk.ffm.app.cache;

import dev.mateuszkowalczyk.ffm.data.database.face.Face;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FacesCacheService implements Runnable {
    protected static final String DIRECTORY_NAME = "/faces";
    private final BufferedImage faceImage;
    private String path;

    public FacesCacheService (BufferedImage face) {
        this.faceImage = face;
    }

    @Override
    public void run() {

    }

    public void getPath(Face face) {
        this.path = CacheService.getInstance().getPath() + DIRECTORY_NAME + "/" + face.getName() + ".PNG";
        face.setPath(this.path);
    }

    public void createCachedFace() {
        if (faceImage != null && this.path != null) {
            File imageFile = new File(this.path);
            try {
                ImageIO.write(this.faceImage, "PNG", imageFile);
                System.out.println(String.format("Saved on location: %s", imageFile.getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
