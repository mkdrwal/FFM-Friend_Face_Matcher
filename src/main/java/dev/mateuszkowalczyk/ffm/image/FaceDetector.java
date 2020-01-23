package dev.mateuszkowalczyk.ffm.image;

import dev.mateuszkowalczyk.ffm.app.cache.FacesCacheService;
import dev.mateuszkowalczyk.ffm.data.database.face.Face;
import dev.mateuszkowalczyk.ffm.data.database.photo.Photo;
import dev.mateuszkowalczyk.ffm.utils.ResourceLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.opencv.imgcodecs.Imgcodecs.imread;

public class FaceDetector implements Runnable {

    private final Photo photo;

    public FaceDetector(Photo photo) {
        this.photo = photo;
    }

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Override
    public void run() {
        this.detectFaces();
    }

    private void detectFaces() {
        CascadeClassifier cascadeClassifier = new CascadeClassifier(ResourceLoader.getInstance().getPath("haarcascade_frontalface_alt.xml"));
        Mat imageMat = imread(this.photo.getPath());
        MatOfRect matOfRect = new MatOfRect();
        cascadeClassifier.detectMultiScale(imageMat, matOfRect);

        try {
            BufferedImage bufferedImage = ImageIO.read(new File(this.photo.getPath()));

            matOfRect.toList().forEach(rect -> {
               BufferedImage croppedImage = this.crop(bufferedImage, rect);
                Face face = new Face();
                FacesCacheService facesCacheService = new FacesCacheService(croppedImage);
                facesCacheService.getPath(face);
                facesCacheService.createCachedFace();

            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(String.format("Detected %s faces", matOfRect.toArray().length));
    }

    private BufferedImage crop(BufferedImage bufferedImage, Rect rect) {
        BufferedImage dest = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics g = dest.getGraphics();
        g.drawImage(bufferedImage, 0, 0, rect.width, rect.height, rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, null);
        g.dispose();
        return dest;
    }
}
