package dev.mateuszkowalczyk.ffm.app;

import dev.mateuszkowalczyk.ffm.data.database.photo.Photo;
import dev.mateuszkowalczyk.ffm.data.database.photo.PhotoDAO;
import dev.mateuszkowalczyk.ffm.image.FaceDetector;
import dev.mateuszkowalczyk.ffm.image.ThumbnailService;
import dev.mateuszkowalczyk.ffm.utils.PropertiesLoader;
import dev.mateuszkowalczyk.ffm.utils.Property;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DirectoryScanner implements Runnable {
    private WorkspaceService workspaceService = WorkspaceService.getInstance();
    private PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
    private FaceDetector faceDetector = FaceDetector.getInstance();
    private ThumbnailService thumbnailService = new ThumbnailService();
    private PhotoDAO photoDAO = PhotoDAO.getInstance();

    @Override
    public void run() {
        System.out.println("Run photos reader");
        this.readImages();
    }

    private ArrayList<ImageView> readImages() {
        ArrayList<ImageView> imageViews = new ArrayList<>();
        try {
            Files.walk(Paths.get(this.propertiesLoader.get(Property.PATH_TO_DIRECTORY)))
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        String filename = path.getFileName().toString();
                        ImageView imageView;

                        if(this.isImagePath(filename) && !path.toString().contains(".cache")) {
                            Photo photo;

                            photo = this.photoDAO.findByPath(path.toString());

                            if (photo == null) {
                                photo = new Photo();

                                photo.setPath(path.toString());
                                photo.setFileName(filename);

                                BufferedImage image = this.thumbnailService.createThumbnail(photo);
                                this.photoDAO.add(photo);

                                WritableImage wr = null;
                                if (image != null) {
                                    wr = new WritableImage(image.getWidth(), image.getHeight());
                                    PixelWriter pw = wr.getPixelWriter();
                                    for (int x = 0; x < image.getWidth(); x++) {
                                        for (int y = 0; y < image.getHeight(); y++) {
                                            pw.setArgb(x, y, image.getRGB(x, y));
                                        }
                                    }
                                }

                                this.faceDetector.run(photo);

                                imageView = new ImageView(wr);
                                imageViews.add(imageView);

                                Platform.runLater(() -> WorkspaceService.getInstance().addImage(imageView));
                            }
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageViews;
    }

    private boolean isImagePath(String filename) {
        List<String> whiteListExtension = new ArrayList<String>();
        whiteListExtension.add("jpg");
        whiteListExtension.add("jpeg");
        whiteListExtension.add("png");

        for (String extension: whiteListExtension) {
            var fileExtension = filename.substring(filename.length() - extension.length());

            if (fileExtension.toLowerCase().equals(extension)){
                return true;
            }
        }

        return false;

//        filename.endsWith("jpeg");
//
//        switch (filename.substring(filename.length() - 3).toLowerCase()) {
//            case "jpeg":
//            case "jpg":
//                return true;
//            default:
//                return false;
//        }
    }
}
