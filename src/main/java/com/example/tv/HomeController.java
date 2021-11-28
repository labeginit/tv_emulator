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
import java.util.Timer;
import java.util.TimerTask;

public class HomeController implements Initializable {


    @FXML
    AnchorPane anchorPane;

    @FXML
    VBox vBox;

    @FXML
    WebView webView;

    @FXML
    Label volumeLabel, sourceLabel, welcomeText;

    @FXML
    Pane pane, sourcePane, volumePane, mediation;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webView = new WebView();

        //Check if a new source was chosen
        int hdmi = Singleton.getInstance().getHdmi();
        String temp = String.valueOf(hdmi);
        if (temp.contains("0")) {
            hdmi = Integer.parseInt(String.valueOf(temp.charAt(0)));
            Singleton.getInstance().setHdmi(hdmi);
            sourcePane.setVisible(true);
            sourceLabel.setText("HDMI " + hdmi);
            PauseTransition show = new PauseTransition(Duration.seconds(5));
            show.setOnFinished((E) -> sourcePane.setVisible(false));
            show.play();
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
                } else if (a == 5) {
                    mediationInfo(mediation, 1);
                } else if (a == 6) {
                    mediationInfo(mediation, 0);
                }
            }
        });
        t1.start();

    }

    static void mediationInfo(Pane mediations, int status) {
        if (status == 1) {
            mediations.setVisible(true);
            Timer previousTimer = Singleton.getInstance().getTimer();
            if (previousTimer != null) {
                previousTimer.cancel();
            }
            Timer timer = new Timer();
            timer.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            if (Singleton.getInstance().isOn()) {
                                Singleton.getInstance().setCommand(1);
                            }
                        }
                    },
                    20000
            );
            Singleton.getInstance().setMeditationTimer(timer);
        } else {
            mediations.setVisible(false);
            Singleton.getInstance().getMeditationTimer().cancel();
        }

    }

    public void volumeDown() {
        Platform.runLater(() -> {
            int tempVolume = Singleton.getInstance().getVolume() - 5;

            if (tempVolume < 0) {
                Singleton.getInstance().setVolume(0);
            } else {
                Singleton.getInstance().setVolume(tempVolume);
            }

            volumeLabel.setText("+\n" + (Singleton.getInstance().getVolume()) + "\n-");
            volumePane.setVisible(true);
            PauseTransition show = new PauseTransition(Duration.seconds(5));
            show.setOnFinished((E) -> volumePane.setVisible(false));
            show.play();
        });


    }

    public void volumeUP() {
        Platform.runLater(() -> {
            int tempVolume = Singleton.getInstance().getVolume() + 5;

            if (tempVolume > 100) {
                Singleton.getInstance().setVolume(100);
            } else {
                Singleton.getInstance().setVolume(tempVolume);
            }
            volumeLabel.setText("+\n" + (Singleton.getInstance().getVolume()) + "\n-");
            volumePane.setVisible(true);
            PauseTransition show = new PauseTransition(Duration.seconds(5));
            show.setOnFinished((E) -> volumePane.setVisible(false));
            show.play();

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