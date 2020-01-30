package dev.mateuszkowalczyk.ffm.view.workspace.elements;

import dev.mateuszkowalczyk.ffm.app.exception.NotFoundException;
import dev.mateuszkowalczyk.ffm.app.face.FaceWorkspace;
import dev.mateuszkowalczyk.ffm.data.database.face.FaceDAO;
import dev.mateuszkowalczyk.ffm.data.database.person.Person;
import dev.mateuszkowalczyk.ffm.data.database.person.PersonDAO;
import dev.mateuszkowalczyk.ffm.view.workspace.MainPageController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class FaceContainerController implements Initializable {
    private MainPageController mainPageController;
    private Person person;

    @FXML
    private FlowPane facesContainer;

    public FaceContainerController(MainPageController mainPageController, Person person) {
        this.mainPageController = mainPageController;
        this.person = person;
        System.out.println("read face container");
    }

    public void addFacePane(Node node) {
        this.facesContainer.getChildren().add(node);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.refresh();
    }

    public void refresh() {
        this.facesContainer.getChildren().clear();
        try {
            FaceWorkspace.getInstance().loadFacesForPerson(mainPageController, this, person);
        } catch (NotFoundException e) {
            PersonDAO.getInstance().delete(person);
            this.mainPageController.setElement(ElementsEnum.PeopleContainer);
        }
    }
}
