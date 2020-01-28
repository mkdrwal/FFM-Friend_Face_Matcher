package dev.mateuszkowalczyk.ffm.view.workspace;

import dev.mateuszkowalczyk.ffm.app.WorkspaceService;
import dev.mateuszkowalczyk.ffm.utils.ResourceLoader;
import dev.mateuszkowalczyk.ffm.view.workspace.elements.ElementsEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    private WorkspaceService workspaceService;

    @FXML
    private ScrollPane mainContainer;

    public MainPageController() {
         this.workspaceService = WorkspaceService.getInstance();
        this.workspaceService.setMainPageController(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setElement(ElementsEnum.ImagesContainer);
    }

    public void setElement(ElementsEnum element) {
        try {
            this.mainContainer.setContent(FXMLLoader.load(ResourceLoader.getInstance().getResource(element.getPath())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openImageContainer(ActionEvent actionEvent) {
        this.setElement(ElementsEnum.ImagesContainer);
    }

    public void openPeopleModule(ActionEvent actionEvent) {
        this.setElement(ElementsEnum.People);
    }
}
