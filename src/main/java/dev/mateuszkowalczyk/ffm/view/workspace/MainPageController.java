package dev.mateuszkowalczyk.ffm.view.workspace;

import dev.mateuszkowalczyk.ffm.app.WorkspaceService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    private WorkspaceService workspaceService;

    @FXML
    private FlowPane imagesContainer;

    public MainPageController() {
         this.workspaceService = WorkspaceService.getInstance();
        this.workspaceService.setMainPageController(this);
    }

    public void clearImages() {
        if (this.imagesContainer != null) {
            this.imagesContainer.getChildren().clear();
        }
    }

    public void addImage(ImageView imageView) {
        this.imagesContainer.getChildren().add(imageView);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.workspaceService.refreshWorkspace();
    }
}
