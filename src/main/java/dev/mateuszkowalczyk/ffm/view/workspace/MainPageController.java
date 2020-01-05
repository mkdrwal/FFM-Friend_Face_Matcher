package dev.mateuszkowalczyk.ffm.view.workspace;

import dev.mateuszkowalczyk.ffm.app.WorkspaceService;

public class MainPageController {

    public MainPageController() {
        WorkspaceService.getInstance().refreshWorkspace();
    }
}
