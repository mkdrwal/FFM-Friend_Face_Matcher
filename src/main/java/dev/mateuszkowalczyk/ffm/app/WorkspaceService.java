package dev.mateuszkowalczyk.ffm.app;

import dev.mateuszkowalczyk.ffm.app.cache.CacheService;
import dev.mateuszkowalczyk.ffm.data.DatabaseService;
import dev.mateuszkowalczyk.ffm.view.workspace.MainPageController;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

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
        var t = new Thread(new WorkspaceWrapper());
        t.start();
    }

    public void addImage(ImageView element) {
        Platform.runLater(() -> this.mainPageController.addImage(element));
    }
}
