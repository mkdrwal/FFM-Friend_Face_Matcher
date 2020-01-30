package dev.mateuszkowalczyk.ffm.app.people;

import dev.mateuszkowalczyk.ffm.data.database.person.Person;
import dev.mateuszkowalczyk.ffm.data.database.person.PersonDAO;
import dev.mateuszkowalczyk.ffm.utils.ResourceLoader;
import dev.mateuszkowalczyk.ffm.view.workspace.MainPageController;
import dev.mateuszkowalczyk.ffm.view.workspace.elements.PeopleContainerController;
import dev.mateuszkowalczyk.ffm.view.workspace.elements.PersonPaneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class PeopleWorkspace {
    private static PeopleWorkspace instance = new PeopleWorkspace();

    private PeopleWorkspace () {}

    public static PeopleWorkspace getInstance() {
        return instance;
    }


    public void loadPeople(MainPageController mainPageController, PeopleContainerController peopleContainerController) {
        if (peopleContainerController != null) {
            var list = PersonDAO.getInstance().getAll(true);
            int i = 1;

            Node node = null;
            for (Person person : list) {
                node = null;
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    var controller = new PersonPaneController(mainPageController, person);
                    fxmlLoader.setController(controller);
                    fxmlLoader.setLocation((ResourceLoader.getInstance().getResource("templates/workspace/elements/person_pane.fxml")));
//                    node = FXMLLoader.load);

                    node = fxmlLoader.load();
                    if(!controller.valid()) {
                        PersonDAO.getInstance().delete(person);
                        continue;
                    }
//                    node = new Button();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                peopleContainerController.addPerson(node, i++);
            }
        }
    }
}
