package com.example.tv;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    Button startButton, channelButton;

    @FXML
    Label welcomeText;

    @FXML ImageView gif;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image(new File("giphy.gif").toURI().toString());
        int channel = Singleton.getInstance().getChannel();
        if (channel != 0) {
            if (channel == 5) {
                gif.setImage(image);
            } else {
                welcomeText.setText("Channel " + channel);
            }
        } else {
            welcomeText.setText("Channel " + 1);
        }
    }

    public void toChannel(ActionEvent event) {
        Singleton.getInstance().changeScene(event, "channels-view.fxml");
    }

    public void turnOff(ActionEvent event) {
        Singleton.getInstance().changeScene(event, "start-view.fxml");
    }
}