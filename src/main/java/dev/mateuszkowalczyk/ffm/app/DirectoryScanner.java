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

public class DirectoryScanner implements Runnable {
    private WorkspaceService workspaceService = WorkspaceService.getInstance();
    private PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
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

//                            new Thread(
//                                    new FaceDetector(photo)
//                            ).start();

                                new FaceDetector(photo).run();

                                imageView = new ImageView(wr);
                                imageViews.add(imageView);

                            } else {
                                Image image = new Image("file:" + photo.getCachedPath());
                                imageView = new ImageView(image);
                            }
                            Platform.runLater(() -> WorkspaceService.getInstance().getMainPageController().addImage(imageView));
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageViews;
    }

    private boolean isImagePath(String filename) {
        switch (filename.substring(filename.length() - 3).toLowerCase()) {
            case "jpg":
                return true;
            default:
                return false;
        }
    }

    private void clearImages() {
        this.workspaceService.getMainPageController().clearImages();
    }
}
