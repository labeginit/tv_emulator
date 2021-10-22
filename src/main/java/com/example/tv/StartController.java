package com.example.tv;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.ResourceBundle;

public class StartController implements Initializable {

    @FXML
    Button startButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Task task = new Task() {
            @Override
            protected Object call() {
                FileHandler fileHandler = new FileHandler();
                int a = fileHandler.test();
                if (a == 1) {
                    startTV();
                }
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();

    }

    public void toHome(ActionEvent event) {
        Singleton.getInstance().changeScene(event, "home-view.fxml");
    }
    public void startTV() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("home-view.fxml"));
                Parent mainCallWindowFXML = null;
                try {
                    mainCallWindowFXML = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Stage stage = (Stage)startButton.getScene().getWindow();//or use any other component in your controller
                Scene mainCallWindow = new Scene (mainCallWindowFXML, 800, 600);
                stage.setScene(mainCallWindow);
                stage.show();
            }
        });
    }
}