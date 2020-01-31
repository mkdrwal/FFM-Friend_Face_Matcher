package dev.mateuszkowalczyk.ffm.view.workspace.elements;

import dev.mateuszkowalczyk.ffm.app.WorkspaceService;
import dev.mateuszkowalczyk.ffm.data.database.person.Person;
import dev.mateuszkowalczyk.ffm.data.database.photo.PhotoDAO;
import dev.mateuszkowalczyk.ffm.view.workspace.MainPageController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ImageContainerController implements Initializable {
    private WorkspaceService workspaceService = WorkspaceService.getInstance();

    @FXML
    private FlowPane imagesContainer;
    private Person person;

    public ImageContainerController(MainPageController mainPageController) {
        this.person = null;
    }

    public ImageContainerController(MainPageController mainPageController, Person person) {
        this.person = person;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        workspaceService.setImagesContainerController(this);
        workspaceService.loadImages(this.person);
    }

    public void clearImages() {
        if (this.imagesContainer != null) {
            this.imagesContainer.getChildren().clear();
        }
    }

    public void forceRefresh(Event event) {
        PhotoDAO.getInstance().getAll(true);
        this.workspaceService.loadImages(this.person);
    }

    public void addImage(ImageView imageView) {
        this.imagesContainer.getChildren().add(imageView);
    }
}
