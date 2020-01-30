package dev.mateuszkowalczyk.ffm.view.workspace.elements;

import dev.mateuszkowalczyk.ffm.data.database.face.Face;
import dev.mateuszkowalczyk.ffm.data.database.face.FaceDAO;
import dev.mateuszkowalczyk.ffm.view.workspace.MainPageController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class FacePaneController implements Initializable {
    private MainPageController mainPageController;
    private FaceContainerController faceContainerController;
    private Face face;

    @FXML
    private ImageView faceImage;

    public FacePaneController(MainPageController mainPageController, FaceContainerController faceContainerController, Face face) {

        this.mainPageController = mainPageController;
        this.faceContainerController = faceContainerController;
        this.face = face;
    }

    public void removeThis(Event event) {
        FaceDAO.getInstance().delete(this.face);
        this.faceContainerController.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loadFace();
    }

    private void loadFace() {
        Image image = new Image("file:" + this.face.getPath());
        this.faceImage.setImage(image);
    }
}
