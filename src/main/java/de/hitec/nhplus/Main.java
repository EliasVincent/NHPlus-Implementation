package de.hitec.nhplus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class is the main class of the application. It starts the application and loads the main window.
 */
public class Main extends Application {

    /**
     * This method starts the application and loads the main window.
     * @param primaryStage The primary stage of the application.
     * @throws Exception if an error occurs while loading the main window.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/hitec/nhplus/Views/MainWindowView.fxml"));
            BorderPane root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method starts the application.
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
