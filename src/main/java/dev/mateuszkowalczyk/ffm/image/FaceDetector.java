package dev.mateuszkowalczyk.ffm.image;

import dev.mateuszkowalczyk.ffm.app.cache.FacesCacheService;
import dev.mateuszkowalczyk.ffm.data.database.face.Face;
import dev.mateuszkowalczyk.ffm.data.database.face.FaceDAO;
import dev.mateuszkowalczyk.ffm.data.database.photo.Photo;
import dev.mateuszkowalczyk.ffm.utils.ResourceLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.opencv.imgcodecs.Imgcodecs.imread;

public class FaceDetector {
    private FaceRecognition faceRecognition = new FaceRecognition();
    private FaceDAO faceDAO = FaceDAO.getInstance();
    private static FaceDetector instance;
    private Photo photo;

    private FaceDetector() {
    }

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static FaceDetector getInstance() {
        if (instance == null) {
            instance = new FaceDetector();
        }

        return instance;
    }

    public void run(Photo photo) {
        this.photo = photo;
        this.detectFaces();
    }

    private void detectFaces() {
        CascadeClassifier cascadeClassifier = new CascadeClassifier(ResourceLoader.getInstance().getPath("haarcascade_frontalface_alt2.xml"));
        Mat imageMat = Imgcodecs.imread(this.photo.getPath());
        Mat imageGrey = new Mat();

        Imgproc.cvtColor(imageMat, imageGrey, Imgproc.COLOR_RGB2GRAY);

        MatOfRect matOfRect = new MatOfRect();
        cascadeClassifier.detectMultiScale(imageGrey, matOfRect, 1.10, 6);

        try {
            BufferedImage bufferedImage = ImageIO.read(new File(this.photo.getPath()));

            matOfRect.toList().forEach(rect -> {
               BufferedImage croppedImage = this.crop(bufferedImage, rect);
                Face face = new Face();
                face.setPhotoId(photo.getId());
                FacesCacheService facesCacheService = new FacesCacheService(croppedImage);
                facesCacheService.getPath(face);
                facesCacheService.createCachedFace();

                this.faceRecognition.recognize(face);

                this.faceDAO.add(face);
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
