package com.kansysaea.ind220_tp3s.Controllers;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void switchScene(Parent root){
        primaryStage.setScene(new Scene(root));
    }

    public static Stage getStage() {
        return primaryStage;
    }
}
