package dev.mateuszkowalczyk.ffm.view.workspace;

import dev.mateuszkowalczyk.ffm.app.WorkspaceService;
import dev.mateuszkowalczyk.ffm.data.database.person.Person;
import dev.mateuszkowalczyk.ffm.utils.ResourceLoader;
import dev.mateuszkowalczyk.ffm.view.SceneEnum;
import dev.mateuszkowalczyk.ffm.view.StageController;
import dev.mateuszkowalczyk.ffm.view.workspace.elements.ElementsEnum;
import dev.mateuszkowalczyk.ffm.view.workspace.elements.FaceContainerController;
import dev.mateuszkowalczyk.ffm.view.workspace.elements.ImageContainerController;
import dev.mateuszkowalczyk.ffm.view.workspace.elements.PeopleContainerController;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
    private Person selectedPerson;
    private Person person;

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
            Object controller = null;
            FXMLLoader fxmlLoader = new FXMLLoader();
            switch (element) {
                case ImagesContainerForPerson:
                    controller = new ImageContainerController(this, this.person);
                    break;
                case ImagesContainer:
                    controller = new ImageContainerController(this);
                    break;
                case PeopleContainer:
                    controller = new PeopleContainerController(this);
                    break;
                case PersonFaceContainer:
                    controller = new FaceContainerController(this, this.selectedPerson);
            }
            fxmlLoader.setLocation(ResourceLoader.getInstance().getResource(element.getPath()));
            fxmlLoader.setController(controller);

            this.mainContainer.setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openImageContainer(ActionEvent actionEvent) {
        this.setElement(ElementsEnum.ImagesContainer);
    }

    public void openPeopleModule(ActionEvent actionEvent) {
        this.setElement(ElementsEnum.PeopleContainer);
    }

    public void openFacesSelectedPerson(Person person) {
        this.selectedPerson = person;
        this.setElement(ElementsEnum.PersonFaceContainer);
    }

    public void showPhotosForPerson(Person person) {
        this.person = person;
        this.setElement(ElementsEnum.ImagesContainerForPerson);
    }

    public void cleanAppStart(Event event) {
        try {
            StageController.getInstance().setScene(SceneEnum.WelcomePage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
