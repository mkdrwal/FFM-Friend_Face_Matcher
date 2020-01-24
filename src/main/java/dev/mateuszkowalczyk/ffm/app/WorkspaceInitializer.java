package dev.mateuszkowalczyk.ffm.app;

import dev.mateuszkowalczyk.ffm.data.Chooser;
import dev.mateuszkowalczyk.ffm.utils.PropertiesLoader;
import dev.mateuszkowalczyk.ffm.utils.Property;
import dev.mateuszkowalczyk.ffm.view.SceneEnum;
import dev.mateuszkowalczyk.ffm.view.StageController;

import java.io.IOException;

public class WorkspaceInitializer {
    public void init() {
        Chooser chooser = new Chooser();
        String path = chooser.chooseDirectory();

        PropertiesLoader.getInstance().set(Property.PATH_TO_DIRECTORY, path);
        try {
            StageController.getInstance().setScene(SceneEnum.MainPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
