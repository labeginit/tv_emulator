package com.example.tv;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {

    @FXML
    Button startButton;

    @FXML
    AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Singleton.getInstance().getNumber() == 0) {
            ServerHandler fileHandler = new ServerHandler();
            fileHandler.start();
        }


        Thread t1 = new Thread(() -> {
            while (Singleton.getInstance().getNumber() != 1) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            startTV();
        });
        t1.start();
    }

    public void startTV() {
        Platform.runLater(() -> Singleton.getInstance().changeScene(anchorPane, "home-view.fxml"));
    }
}