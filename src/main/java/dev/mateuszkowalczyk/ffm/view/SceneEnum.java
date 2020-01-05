package dev.mateuszkowalczyk.ffm.view;

public enum SceneEnum {
    MainPage("templates/workspace/main_page.fxml"),
    WelcomePage("templates/workspace/welcome_page.fxml");

    private String path;

    SceneEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
