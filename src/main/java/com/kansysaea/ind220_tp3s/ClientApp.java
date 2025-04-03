package com.kansysaea.ind220_tp3s;

import com.kansysaea.ind220_tp3s.Controllers.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneManager.setPrimaryStage(stage);
        Parent root = FXMLLoader.load(getClass().getResource("Socket_Settings.fxml"));
        stage.setTitle("Socket Settings");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}