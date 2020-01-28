package dev.mateuszkowalczyk.ffm.view.workspace.elements.people;

import dev.mateuszkowalczyk.ffm.app.WorkspaceService;
import dev.mateuszkowalczyk.ffm.app.people.PeopleWorkspace;
import dev.mateuszkowalczyk.ffm.data.database.person.Person;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PeopleContainerController implements Initializable {
    private PeopleWorkspace peopleWorkspace = PeopleWorkspace.getInstance();

    @FXML
    public GridPane peopleContainer;

    public PeopleContainerController() {
        System.out.println("people container");;
    }

    public void addPerson(Node node, int i) {
        this.peopleContainer.getChildren().add(i, node);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.peopleWorkspace.setPeopleContainerController(this);
        this.peopleWorkspace.loadPeople();
    }
}
