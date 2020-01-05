package dev.mateuszkowalczyk.ffm.view.workspace;

import dev.mateuszkowalczyk.ffm.app.WorkspaceService;
import javafx.fxml.FXML;

public class WelcomePageController {
    public WelcomePageController() { }

    @FXML
    public void startNewApp(){
        WorkspaceService.getInstance().setupWorkspace();
    }
}
