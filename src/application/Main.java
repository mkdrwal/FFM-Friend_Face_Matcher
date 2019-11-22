package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL welcomePageUrl = ClassLoader.getSystemClassLoader().
                getResource("welcomePage.fxml");

        Parent welcomePage = FXMLLoader.load(welcomePageUrl);
        primaryStage.setTitle("Friend Face Matcher");
        primaryStage.setScene(new Scene(welcomePage));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
