package dev.mateuszkowalczyk.ffm.view.workspace.elements;

import dev.mateuszkowalczyk.ffm.app.WorkspaceService;
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

    public ImageContainerController() { }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        workspaceService.setImagesContainerController(this);
        workspaceService.loadImages();
    }

    public void clearImages() {
        if (this.imagesContainer != null) {
            this.imagesContainer.getChildren().clear();
        }
    }

    public void addImage(ImageView imageView) {
        this.imagesContainer.getChildren().add(imageView);
    }
}
