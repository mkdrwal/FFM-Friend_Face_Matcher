package dev.mateuszkowalczyk.ffm.app.face;

import dev.mateuszkowalczyk.ffm.app.exception.NotFoundException;
import dev.mateuszkowalczyk.ffm.data.database.face.Face;
import dev.mateuszkowalczyk.ffm.data.database.face.FaceDAO;
import dev.mateuszkowalczyk.ffm.data.database.person.Person;
import dev.mateuszkowalczyk.ffm.data.database.person.PersonDAO;
import dev.mateuszkowalczyk.ffm.utils.ResourceLoader;
import dev.mateuszkowalczyk.ffm.view.workspace.MainPageController;
import dev.mateuszkowalczyk.ffm.view.workspace.elements.ElementsEnum;
import dev.mateuszkowalczyk.ffm.view.workspace.elements.FaceContainerController;
import dev.mateuszkowalczyk.ffm.view.workspace.elements.FacePaneController;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.List;

public class FaceWorkspace {
    private static FaceWorkspace instance = new FaceWorkspace();

    public static FaceWorkspace getInstance() {
        return instance;
    }

    public void loadFacesForPerson(MainPageController mainPageController, FaceContainerController faceContainerController, Person person) throws NotFoundException {
        List<Face> faceList = FaceDAO.getInstance().getAllForPerson(person);
        List<Person> personList = PersonDAO.getInstance().getAll(true);
        var resourceLoader = ResourceLoader.getInstance();

        if (faceList.size() == 0) {
            throw new NotFoundException();
        }

        for (Face face : faceList) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(resourceLoader.getResource("templates/workspace/elements/face_pane.fxml"));
            var controller = new FacePaneController(mainPageController, faceContainerController, face);
            controller.setPersonList(personList);
            fxmlLoader.setController(controller);

            try {
                faceContainerController.addFacePane(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
