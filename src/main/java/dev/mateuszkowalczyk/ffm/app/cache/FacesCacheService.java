package dev.mateuszkowalczyk.ffm.app.cache;

import dev.mateuszkowalczyk.ffm.data.database.face.Face;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FacesCacheService implements Runnable {
    protected static final String DIRECTORY_NAME = "/faces";
    private CacheService cacheService = CacheService.getInstance();
    private BufferedImage faceImage;
    private String path;

    public FacesCacheService(BufferedImage face) {
        this.faceImage = face;
    }

    public FacesCacheService() {

    }

    public Mat readFaceToProcess(Face face) {
        Mat image = Imgcodecs.imread(face.getPath());
        Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2GRAY);

        return image;
    }

    public Mat readFaceToProcess(String name) {
        Mat image = Imgcodecs.imread(this.cacheService.getPath() + DIRECTORY_NAME + "/" + name + ".JPG");
        Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2GRAY);

        return image;
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
