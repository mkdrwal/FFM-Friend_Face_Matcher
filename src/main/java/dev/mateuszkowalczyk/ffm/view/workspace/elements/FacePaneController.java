package dev.mateuszkowalczyk.ffm.view.workspace.elements;

import dev.mateuszkowalczyk.ffm.app.face.FaceWorkspace;
import dev.mateuszkowalczyk.ffm.data.database.face.Face;
import dev.mateuszkowalczyk.ffm.data.database.face.FaceDAO;
import dev.mateuszkowalczyk.ffm.data.database.person.Person;
import dev.mateuszkowalczyk.ffm.data.database.person.PersonDAO;
import dev.mateuszkowalczyk.ffm.view.workspace.MainPageController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FacePaneController implements Initializable {
    private MainPageController mainPageController;
    private FaceContainerController faceContainerController;
    private FaceWorkspace faceWorkspace = FaceWorkspace.getInstance();
    private Face face;
    private ObservableList<String> list = FXCollections.observableArrayList();

    @FXML
    private ImageView faceImage;

    @FXML
    private ChoiceBox<String> choicePerson;

    private List<Person> personList;

    public FacePaneController(MainPageController mainPageController, FaceContainerController faceContainerController, Face face) {

        this.mainPageController = mainPageController;
        this.faceContainerController = faceContainerController;
        this.face = face;
    }

    public void removeThis(Event event) {
        FaceDAO.getInstance().delete(this.face);
        this.faceContainerController.refresh();
    }

    public void markAsNewPerson(Event event) {
        Person person = new Person();
        PersonDAO.getInstance().add(person);

        this.changePerson(person, this.face);
    }

    private void changePerson(Person person, Face face) {
        face.setPersonId(person.getId());
        FaceDAO.getInstance().update(face);
        PersonDAO.getInstance().getAll(true);

        this.faceContainerController.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loadFace();
        this.setChoiceBox();
    }

    private void setChoiceBox() {
        for (Person person: this.personList) {
            this.list.add(person.getName());
        }

        this.choicePerson.setItems(this.list);
    }

    private void loadFace() {
        Image image = new Image("file:" + this.face.getPath());
        this.faceImage.setImage(image);
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public void writePersonToFace(Event event) {
        var selectedPerson = personList.get(list.indexOf(this.choicePerson.getValue()));

        this.changePerson(selectedPerson, this.face);
    }
}
