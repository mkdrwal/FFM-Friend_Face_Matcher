package dev.mateuszkowalczyk.ffm.view.workspace;

import dev.mateuszkowalczyk.ffm.app.WorkspaceInitializer;
import dev.mateuszkowalczyk.ffm.app.WorkspaceService;
import javafx.fxml.FXML;

public class WelcomePageController {
    public WelcomePageController() { }

    @FXML
    public void startNewApp(){
        WorkspaceInitializer workspaceInitializer = new WorkspaceInitializer();
        workspaceInitializer.init();
    }
}
