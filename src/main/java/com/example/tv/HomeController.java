package com.example.tv;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

        //Check if a new source was chosen
        int hdmi = Singleton.getInstance().getHdmi();
        String temp = String.valueOf(hdmi);
        if (temp.contains("0")) {
            hdmi = Integer.parseInt(String.valueOf(temp.charAt(0)));
            Singleton.getInstance().setHdmi(hdmi);
        }
        if (hdmi == 1) {
            loadChannel();
        } else {
            //Load HDMI 2
        }

        Thread t1 = new Thread(() -> {
            while (true) {
                int a = MenuController.lookForChange();
                if (a == 2) {
                    toMenu();
                    break;
                } else if (a == 1) {
                    turnOff();
                    break;
                } else if (a == 3) {
                    volumeDown();
                } else if (a == 4) {
                    volumeUP();
                }
            }
        });
        t1.start();

    }

    public void volumeDown() {
    }

    public void volumeUP() {
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

    public void toMenu() {
        Platform.runLater(() -> {
            webView.getEngine().load(null);
            Singleton.getInstance().changeScene(anchorPane, "menu-view.fxml");
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