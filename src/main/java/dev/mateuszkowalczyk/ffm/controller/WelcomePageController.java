package dev.mateuszkowalczyk.ffm.controller;

import javafx.fxml.FXML;

public class WelcomePageController {
    public WelcomePageController() {
        System.out.println("Controller work");
    }

    @FXML
    public void startNewApp(){
        System.out.println("Button work");;
    }
}