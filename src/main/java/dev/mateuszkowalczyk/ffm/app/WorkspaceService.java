package dev.mateuszkowalczyk.ffm.app;

import dev.mateuszkowalczyk.ffm.app.cache.CacheService;
import dev.mateuszkowalczyk.ffm.data.DatabaseService;
import dev.mateuszkowalczyk.ffm.data.database.person.Person;
import dev.mateuszkowalczyk.ffm.view.workspace.MainPageController;
import dev.mateuszkowalczyk.ffm.view.workspace.elements.ImageContainerController;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

public class WorkspaceService {
    private static final WorkspaceService instance = new WorkspaceService();
    private DatabaseService databaseService;
    private CacheService cacheService;
    private MainPageController mainPageController;
    private ImageContainerController imagesContainerController;

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

    public void loadImages(Person person) {
        if (imagesContainerController != null) {
            Platform.runLater(() -> {this.imagesContainerController.clearImages();});
            var workspaceWrapper = WorkspaceWrapper.getInstance();
            workspaceWrapper.setPerson(person);
            var t = new Thread(workspaceWrapper);
            t.start();
        } else {
            System.out.println("Cannot load images if imagesContainerController isn't exists");
        }
    }

    public void addImage(ImageView element) {
        Platform.runLater(() -> this.imagesContainerController.addImage(element));
    }

    public void setImagesContainerController(ImageContainerController imageContainerController) {
        this.imagesContainerController = imageContainerController;
    }
}
