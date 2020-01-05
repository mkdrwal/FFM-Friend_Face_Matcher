package dev.mateuszkowalczyk.ffm.app;

import dev.mateuszkowalczyk.ffm.data.Chooser;
import dev.mateuszkowalczyk.ffm.utils.PropertiesLoader;
import dev.mateuszkowalczyk.ffm.utils.Property;
import dev.mateuszkowalczyk.ffm.view.SceneEnum;
import dev.mateuszkowalczyk.ffm.view.StageController;

import java.io.IOException;

public class WorkspaceService {
    private static final WorkspaceService instance = new WorkspaceService();
    private PropertiesLoader propertiesLoader = new PropertiesLoader();

    private WorkspaceService(){}

    public static WorkspaceService getInstance() {
        return instance;
    }

    public void setupWorkspace() {
        Chooser chooser = new Chooser();
        String path = chooser.chooseDirectory();
        this.propertiesLoader.set(Property.PATH_TO_DIRECTORY, path);
        try {
            StageController.getInstance().setScene(SceneEnum.MainPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshWorkspace() {
        System.out.println("Refreshing workspace");
    }
}
