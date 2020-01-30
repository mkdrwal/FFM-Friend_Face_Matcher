package dev.mateuszkowalczyk.ffm.view.workspace.elements;

import dev.mateuszkowalczyk.ffm.data.database.face.Face;
import dev.mateuszkowalczyk.ffm.data.database.face.FaceDAO;
import dev.mateuszkowalczyk.ffm.data.database.person.Person;
import dev.mateuszkowalczyk.ffm.view.workspace.MainPageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonPaneController implements Initializable {
    private FaceDAO faceDAO = FaceDAO.getInstance();
    private MainPageController mainPageController;
    private Person person;
    @FXML
    private Text personName;
    @FXML
    private ImageView faceImage;
    private boolean valid = true;

    public PersonPaneController () {
        System.out.println("Hi person");
    }

    public PersonPaneController(MainPageController mainPageController, Person person) {
        this.mainPageController = mainPageController;
        this.person = person;
    }

    public void editPerson(ActionEvent actionEvent) {
        this.mainPageController.openFacesSelectedPerson(this.person);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.personName.setText(person.getName());
        var face = this.faceDAO.getFirstFace(person);

        if (face == null) {
           this.valid = false;
        } else {
            this.setFace(face);
        }
    }

    private void setFace(Face face) {
//        File file = new File(face.getPath());
        Image image = new Image("file:" + face.getPath());

        this.faceImage.setImage(image);
    }

    public boolean valid() {
        return this.valid;
    }
}
