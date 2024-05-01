package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

<<<<<<< HEAD
    public static final String host = "http://localhost:20000/api";
=======
    //public static final String ip = "http://90.94.101.49:20000/api";
    public static final String ip = "http://localhost:20000/api";
    public static Usuario usuario;
>>>>>>> 2bc811439eb36e2622af1f3b30f75941e990aa9a

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("/com/example/vistas/login"), 640, 480);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/imgs/logo.jpg")));
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}