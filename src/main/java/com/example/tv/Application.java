package com.example.tv;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("start-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 700);
            stage.setTitle("Start");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() {
        System.exit(0);
    }
}