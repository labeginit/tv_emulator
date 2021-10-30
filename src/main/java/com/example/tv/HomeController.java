package com.example.tv;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    AnchorPane anchorPane;

    @FXML
    Button startButton, channelButton;

    @FXML
    Label welcomeText;

    @FXML
    Pane pane;

    @FXML
    VBox vBox;

    @FXML
    WebView webView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webView = new WebView();
        String video = "https://www.youtube.com/embed/SJUS2ZuZ7go?autoplay=1";

        int channel = Singleton.getInstance().getChannel();
        if (channel != 0) {
            if (channel == 2) {
                video = "https://www.youtube.com/embed/jfzRSpiqtQA?autoplay=1";
            } else if (channel == 3) {
                video = "https://www.youtube.com/embed/qBZ_2j_a_1g?autoplay=1";
            }
        } else {
            channel = 1;
        }

        webView.getEngine().load(video);
        webView.setPrefSize(700, 800);
        webView.setDisable(true);
        vBox.getChildren().addAll(webView);

        PauseTransition wait = new PauseTransition(Duration.seconds(1));
        int finalChannel = channel;
        wait.setOnFinished((e) -> {
            welcomeText.setText("Channel " + finalChannel);
            pane.setVisible(true);
            PauseTransition wait2 = new PauseTransition(Duration.seconds(5));
            wait2.setOnFinished((e2) -> pane.setVisible(false));
            wait2.play();
        });
        wait.play();

        Thread t1 = new Thread(() -> {

            while (Singleton.getInstance().getNumber() != 2 && Singleton.getInstance().getNumber() != 3) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (Singleton.getInstance().getNumber() == 3) {
                toChannel();
            } else {
                turnOff();
            }
        });
        t1.start();

    }

    public void toChannel() {
        Platform.runLater(() -> {
            webView.getEngine().load(null);
            Singleton.getInstance().changeScene(anchorPane, "channels-view.fxml");
        });
    }

    public void turnOff() {
        Platform.runLater(() -> {
            webView.getEngine().load(null);
            Singleton.getInstance().setNumber(10);
            Singleton.getInstance().changeScene(anchorPane, "start-view.fxml");
        });
    }
}