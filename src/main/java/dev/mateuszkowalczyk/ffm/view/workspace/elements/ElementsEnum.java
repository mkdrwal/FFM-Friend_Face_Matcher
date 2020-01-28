package dev.mateuszkowalczyk.ffm.view.workspace.elements;

public enum ElementsEnum {
    ImagesContainer("templates/workspace/elements/image_container.fxml"),
    People("templates/workspace/elements/people.fxml");

    private String path;

    ElementsEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
