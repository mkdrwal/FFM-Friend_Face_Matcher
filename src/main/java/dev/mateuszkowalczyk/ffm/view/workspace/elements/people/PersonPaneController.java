package dev.mateuszkowalczyk.ffm.view.workspace.elements.people;

import dev.mateuszkowalczyk.ffm.data.database.face.Face;
import dev.mateuszkowalczyk.ffm.data.database.face.FaceDAO;
import dev.mateuszkowalczyk.ffm.data.database.person.Person;
import dev.mateuszkowalczyk.ffm.data.database.person.PersonDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PersonPaneController implements Initializable {
    private FaceDAO faceDAO = FaceDAO.getInstance();
    private Person person;
    @FXML
    private Text personName;
    @FXML
    private ImageView faceImage;

    public PersonPaneController () {
        System.out.println("Hi person");
    }

    public PersonPaneController(Person person) {
        this.person = person;
    }

    public void editPerson(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.personName.setText(person.getName());
        var face = this.faceDAO.getFirstFace(person);

        this.setFace(face);
    }

    private void setFace(Face face) {
//        File file = new File(face.getPath());
        Image image = new Image("file:" + face.getPath());

        this.faceImage.setImage(image);
    }
}
