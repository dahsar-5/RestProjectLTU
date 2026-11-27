
package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/GUI.fxml")
        );

        Scene scene = new Scene(loader.load());

        stage.setTitle("Register Study Results");
        stage.setScene(scene);
        stage.show();
    }
}

