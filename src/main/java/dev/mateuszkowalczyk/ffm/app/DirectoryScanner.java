package dev.mateuszkowalczyk.ffm.app;

import dev.mateuszkowalczyk.ffm.utils.PropertiesLoader;
import dev.mateuszkowalczyk.ffm.utils.Property;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DirectoryScanner implements Runnable {
    private WorkspaceService workspaceService = WorkspaceService.getInstance();
    private PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();

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
                        if(this.isImagePath(filename)) {
                            ImageView imageView = new ImageView();

                            InputStream inputStream = null;

                            Image image1 = new Image("file:" + path.toString(), 200, 200, false, true);

                            imageView.setImage(image1);
                            imageView.setFitHeight(200);
                            imageView.setFitWidth(200);
                            imageView.setSmooth(true);
                            imageView.setCache(true);
                            imageViews.add(imageView);

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
