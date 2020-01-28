package dev.mateuszkowalczyk.ffm.view.workspace.elements.people;

import dev.mateuszkowalczyk.ffm.utils.ResourceLoader;
import dev.mateuszkowalczyk.ffm.view.workspace.elements.ElementsEnum;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import jdk.swing.interop.LightweightFrameWrapper;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PeopleController implements Initializable {
    private static String FACE_CONTAINER = "templates/workspace/elements/face_container.fxml";
    private static String PEOPLE_CONTAINER = "templates/workspace/elements/people_container.fxml";

    @FXML
    private BorderPane mainContainer;

    public PeopleController() {
        System.out.println("People controller");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setCenterElement(PEOPLE_CONTAINER);
    }

    public void setCenterElement(String element) {
        try {
            this.mainContainer.setCenter(FXMLLoader.load(ResourceLoader.getInstance().getResource(element)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
