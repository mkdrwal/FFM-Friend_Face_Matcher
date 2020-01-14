package dev.mateuszkowalczyk.ffm.app;

import dev.mateuszkowalczyk.ffm.app.cache.CacheService;
import dev.mateuszkowalczyk.ffm.data.Chooser;
import dev.mateuszkowalczyk.ffm.data.DatabaseService;
import dev.mateuszkowalczyk.ffm.utils.PropertiesLoader;
import dev.mateuszkowalczyk.ffm.utils.Property;
import dev.mateuszkowalczyk.ffm.view.SceneEnum;
import dev.mateuszkowalczyk.ffm.view.StageController;
import dev.mateuszkowalczyk.ffm.view.workspace.MainPageController;
import javafx.application.Platform;

import java.io.*;
public class WorkspaceService {
    private static final WorkspaceService instance = new WorkspaceService();
    private PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
    private DatabaseService databaseService = DatabaseService.getInstance();
    private CacheService cacheService = CacheService.getInstance();
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
        this.cacheService.check();
        try {
            StageController.getInstance().setScene(SceneEnum.MainPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshWorkspace() {
        Platform.runLater(() -> {this.getMainPageController().clearImages();});
        var t = new Thread(new DirectoryScanner());
        t.start();
    }
}
