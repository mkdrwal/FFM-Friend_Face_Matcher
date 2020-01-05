package dev.mateuszkowalczyk.ffm.app;

import dev.mateuszkowalczyk.ffm.data.Chooser;
import dev.mateuszkowalczyk.ffm.utils.PropertiesLoader;
import dev.mateuszkowalczyk.ffm.utils.Property;

import java.util.Properties;

public class WorkspaceService {
    private PropertiesLoader propertiesLoader = new PropertiesLoader();

    public void setupWorkspace() {
        Chooser chooser = new Chooser();
        String path = chooser.chooseDirectory();
        this.propertiesLoader.set(Property.PATH_TO_DIRECTORY, path);
    }
}
