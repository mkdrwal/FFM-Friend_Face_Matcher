package dev.mateuszkowalczyk.ffm.data;

import dev.mateuszkowalczyk.ffm.view.StageController;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class Chooser {
    private StageController stageController = StageController.getInstance();

    public String chooseDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();

        Stage stage = this.stageController.getStage();
        File selectedDirectory = directoryChooser.showDialog(stage);

        return selectedDirectory.getAbsolutePath();
    }
}
