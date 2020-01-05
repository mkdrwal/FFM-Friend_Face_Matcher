package dev.mateuszkowalczyk.ffm.view.controller;

import dev.mateuszkowalczyk.ffm.app.WorkspaceService;
import javafx.fxml.FXML;

public class WelcomePageController {
    public WelcomePageController() {
        System.out.println("Controller work");
    }

    @FXML
    public void startNewApp(){
        var workspaceService = new WorkspaceService();
        workspaceService.setupWorkspace();
    }
}
