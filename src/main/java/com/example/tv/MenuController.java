package com.example.tv;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MenuController implements Initializable {

    @FXML
    AnchorPane anchorPane;

    @FXML
    TextField channel, sleep1, sleep2, sleep3, hdmi1, hdmi2;

    int marked = 1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Thread t1 = new Thread(() -> {
            while (true) {
                int a = lookForChange();
                if (a == 3) {
                    goDown();
                } else if (a == 4) {
                    goUp();
                } else if (a == 1) {
                    turnOff();
                    break;
                } else if (a == 2) {
                    confirm();
                    break;
                }
            }
        });
        t1.start();
    }

    static int lookForChange() {
        while (Singleton.getInstance().getCommand() != 1 && Singleton.getInstance().getCommand() != 2 && Singleton.getInstance().getCommand() != 3 && Singleton.getInstance().getCommand() != 4) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int a = Singleton.getInstance().getCommand();
        Singleton.getInstance().setCommand(9);
        return a;
    }

    public void goDown() {
        Platform.runLater(() -> {
            if (marked == 1) {
                channel.setStyle("-fx-background-color: white;");
                sleep1.setStyle("-fx-background-color: #5c61fa;");
            } else if (marked == 2) {
                sleep1.setStyle("-fx-background-color: white;");
                sleep2.setStyle("-fx-background-color: #5c61fa;");
            } else if (marked == 3) {
                sleep2.setStyle("-fx-background-color: white;");
                sleep3.setStyle("-fx-background-color: #5c61fa;");
            } else if (marked == 4) {
                sleep3.setStyle("-fx-background-color: white;");
                hdmi1.setStyle("-fx-background-color: #5c61fa;");
            } else if (marked == 5) {
                hdmi1.setStyle("-fx-background-color: white;");
                hdmi2.setStyle("-fx-background-color: #5c61fa;");
            } else if (marked == 6) {
                hdmi2.setStyle("-fx-background-color: white;");
                channel.setStyle("-fx-background-color: #5c61fa;");
            }
            marked++;
            if (marked == 7) {
                marked = 1;
            }
        });
    }

    public void goUp() {
        Platform.runLater(() -> {
            if (marked == 1) {
                channel.setStyle("-fx-background-color: white;");
                hdmi2.setStyle("-fx-background-color: #5c61fa;");
            } else if (marked == 2) {
                sleep1.setStyle("-fx-background-color: white;");
                channel.setStyle("-fx-background-color: #5c61fa;");
            } else if (marked == 3) {
                sleep2.setStyle("-fx-background-color: white;");
                sleep1.setStyle("-fx-background-color: #5c61fa;");
            } else if (marked == 4) {
                sleep3.setStyle("-fx-background-color: white;");
                sleep2.setStyle("-fx-background-color: #5c61fa;");
            } else if (marked == 5) {
                hdmi1.setStyle("-fx-background-color: white;");
                sleep3.setStyle("-fx-background-color: #5c61fa;");
            } else if (marked == 6) {
                hdmi2.setStyle("-fx-background-color: white;");
                hdmi1.setStyle("-fx-background-color: #5c61fa;");
            }
            marked--;
            if (marked == 0) {
                marked = 6;
            }
        });
    }

    public void confirm() {
        Platform.runLater(() -> {
            if (marked == 5 || marked == 6) {
                // Change source if necessary
                int temp = Singleton.getInstance().getHdmi();
                if (temp == 1 && marked == 6) {
                    Singleton.getInstance().setHdmi(20);
                } else if (temp == 2 && marked == 5) {
                    Singleton.getInstance().setHdmi(10);
                }
                Singleton.getInstance().changeScene(anchorPane, "home-view.fxml");
            } else if (marked == 1) {
                //Go to channel list
                Singleton.getInstance().changeScene(anchorPane, "channels-view.fxml");
            } else if (marked == 2 || marked == 3 || marked == 4) {
                //Set a timer and remove if there already is one
                // 15sec
                int time = 15000;
                if (marked == 3) {
                    // 30sec
                    time = 30000;
                } else if (marked == 4) {
                    // 60sec
                    time = 60000;
                }
                Timer previousTimer = Singleton.getInstance().getTimer();
                if (previousTimer != null) {
                    previousTimer.cancel();
                }
                Timer timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                Singleton.getInstance().setCommand(1);
                            }
                        },
                        time
                );
                Singleton.getInstance().setTimer(timer);
                Singleton.getInstance().changeScene(anchorPane, "home-view.fxml");
            }

        });
    }

    public void turnOff() {
        Platform.runLater(() -> Singleton.getInstance().changeScene(anchorPane, "start-view.fxml"));
    }
}