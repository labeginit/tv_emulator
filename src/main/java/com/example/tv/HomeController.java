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
        loadChannel();

        Thread t1 = new Thread(() -> {
            while (true) {
                while (Singleton.getInstance().getCommand() != 2 && Singleton.getInstance().getCommand() != 3 && Singleton.getInstance().getCommand() != 4 && Singleton.getInstance().getCommand() != 5) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int a = Singleton.getInstance().getCommand();
                Singleton.getInstance().setCommand(9);

                if (a == 3) {
                    toChannel();
                    break;
                } else if (a == 2) {
                    turnOff();
                    break;
                } else if (a == 4) {
                    goDownChannel();
                } else if (a == 5) {
                    goUpChannel();
                }
            }
        });
        t1.start();
    }

    public void goDownChannel() {
        Platform.runLater(() -> {
            int channel = Singleton.getInstance().getChannel();
            if (channel != 15) {
                Singleton.getInstance().setChannel(channel + 1);
            } else {
                Singleton.getInstance().setChannel(1);
            }
            loadChannel();
        });
    }

    public void goUpChannel() {
        Platform.runLater(() -> {
            int channel = Singleton.getInstance().getChannel();
            if (channel != 1) {
                Singleton.getInstance().setChannel(channel - 1);
            } else {
                Singleton.getInstance().setChannel(15);
            }
            loadChannel();
        });
    }

    public void loadChannel() {

        int newChannel = Singleton.getInstance().getChannel();
        String video = "";
        if (newChannel != 0) {
            if (newChannel == 1) {
                video = "https://www.youtube.com/embed/qBZ_2j_a_1g?autoplay=1";
            } else if (newChannel == 2) {
                video = "https://www.youtube.com/embed/jfzRSpiqtQA?autoplay=1";
            } else if (newChannel == 3) {
                video = "https://www.youtube.com/embed/0FddIVX7qh4?autoplay=1";
            }
        }
        loadWebView(newChannel, video);

    }

    public void loadWebView(int channel, String video) {
        webView.getEngine().load(video);
        webView.setPrefSize(700, 800);
        webView.setDisable(true);
        if (vBox.getChildren().isEmpty()) {
            vBox.getChildren().addAll(webView);
        }

        PauseTransition wait = new PauseTransition(Duration.seconds(1));
        wait.setOnFinished((e) -> {
            welcomeText.setText("Channel " + channel);
            pane.setVisible(true);
            PauseTransition wait2 = new PauseTransition(Duration.seconds(5));
            wait2.setOnFinished((e2) -> pane.setVisible(false));
            wait2.play();
        });
        wait.play();
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
            Singleton.getInstance().setCommand(10);
            Singleton.getInstance().changeScene(anchorPane, "start-view.fxml");
        });
    }
}