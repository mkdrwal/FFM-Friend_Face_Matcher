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
    private DatabaseService databaseService;
    private CacheService cacheService;
    private MainPageController mainPageController;

    private WorkspaceService() {
        this.cacheService = CacheService.getInstance();
        this.databaseService =  DatabaseService.getInstance();
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

    public void refreshWorkspace() {
        Platform.runLater(() -> {this.getMainPageController().clearImages();});
        var t = new Thread(new DirectoryScanner());
        t.start();
    }
}
