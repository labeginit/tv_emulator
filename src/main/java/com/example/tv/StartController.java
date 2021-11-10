package com.example.tv;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {

    @FXML
    AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Singleton.getInstance().getCommand() == 0) {
            System.out.println("enter");
            ServerHandler serverHandler = new ServerHandler();
            serverHandler.start();
        }

        Thread t1 = new Thread(() -> {
            while (Singleton.getInstance().getCommand() != 1) {
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