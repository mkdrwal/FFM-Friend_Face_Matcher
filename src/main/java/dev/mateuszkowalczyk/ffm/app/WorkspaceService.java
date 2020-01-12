package dev.mateuszkowalczyk.ffm.app;

import dev.mateuszkowalczyk.ffm.data.Chooser;
import dev.mateuszkowalczyk.ffm.utils.PropertiesLoader;
import dev.mateuszkowalczyk.ffm.utils.Property;
import dev.mateuszkowalczyk.ffm.view.SceneEnum;
import dev.mateuszkowalczyk.ffm.view.StageController;
import dev.mateuszkowalczyk.ffm.view.workspace.MainPageController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WorkspaceService {
    private static final WorkspaceService instance = new WorkspaceService();
    private PropertiesLoader propertiesLoader = new PropertiesLoader();
    private MainPageController mainPageController;

    private WorkspaceService() {
    }

    public static WorkspaceService getInstance() {
        return instance;
    }

    public MainPageController getMainPageController() {
        return mainPageController;
    }

    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    public void setupWorkspace() {
        Chooser chooser = new Chooser();
        String path = chooser.chooseDirectory();
        this.propertiesLoader.set(Property.PATH_TO_DIRECTORY, path);
        try {
            StageController.getInstance().setScene(SceneEnum.MainPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshWorkspace() {
        this.getMainPageController().clearImages();
        var t = new Thread(new DirectoryScanner());
        t.start();
    }

    private void readImages() {
        try {
            Files.walk(Paths.get(this.propertiesLoader.get(Property.PATH_TO_DIRECTORY)))
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        String filename = path.getFileName().toString();
                        if(this.isImagePath(filename)) {
                            System.out.println("read image");
                            ImageView imageView = new ImageView();
                            InputStream inputStream = null;

                            try {
                                inputStream = new FileInputStream(path.toString());
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            Image image = new Image(inputStream);

                            Image image1 = new Image("file:" + path.toString(), 200, 200, false, true);

                            imageView.setImage(image1);
                            imageView.setFitHeight(200);
                            imageView.setFitWidth(200);
                            imageView.setSmooth(true);
                            imageView.setCache(true);



                            this.mainPageController.addImage(imageView);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isImagePath(String filename) {
        switch (filename.substring(filename.length() - 3).toLowerCase()) {
            case "jpg":
                return true;
            default:
                return false;
        }
    }
}
