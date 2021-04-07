package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {

//    @Override
//    public void start(Stage primaryStage) {
//        try {
//            //Loads the homepage
//            Parent homepageRoot = FXMLLoader.load(getClass().getResource("homepage"));
//            Scene homepage = new Scene(homepageRoot);
//            primaryStage.setScene(homepage);
//            primaryStage.show();
//
//
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("homepage"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        //Starts the program
        launch(args);

    }

}