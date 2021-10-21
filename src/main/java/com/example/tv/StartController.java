package com.example.tv;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {

    @FXML
    Button startButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void toHome(ActionEvent event) {
        Singleton.getInstance().changeScene(event, "home-view.fxml");
    }
}