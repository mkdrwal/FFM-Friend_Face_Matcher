package dev.mateuszkowalczyk.ffm.app.people;

import dev.mateuszkowalczyk.ffm.data.database.person.Person;
import dev.mateuszkowalczyk.ffm.data.database.person.PersonDAO;
import dev.mateuszkowalczyk.ffm.utils.ResourceLoader;
import dev.mateuszkowalczyk.ffm.view.workspace.elements.people.PeopleContainerController;
import dev.mateuszkowalczyk.ffm.view.workspace.elements.people.PeopleController;
import dev.mateuszkowalczyk.ffm.view.workspace.elements.people.PersonPaneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.io.IOException;

public class PeopleWorkspace {
    private static PeopleWorkspace instance = new PeopleWorkspace();
    private PeopleContainerController peopleContainerController;

    private PeopleWorkspace () {}

    public static PeopleWorkspace getInstance() {
        return instance;
    }


    public void setPeopleContainerController(PeopleContainerController peopleContainerController) {
        this.peopleContainerController = peopleContainerController;
    }

    public void loadPeople() {
        if (peopleContainerController != null) {
            var list = PersonDAO.getInstance().getAll();
            int i = 1;

            for (Person person : list) {
                Node node = null;
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setController(new PersonPaneController(person));
                    fxmlLoader.setLocation((ResourceLoader.getInstance().getResource("templates/workspace/elements/person_pane.fxml")));
//                    node = FXMLLoader.load);

                    node = fxmlLoader.load();
//                    node = new Button();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                this.peopleContainerController.addPerson(node, i++);
            }
        }
    }
}
