package com.example.tv;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ChannelsController implements Initializable {
    @FXML
    ListView<String> list;

    @FXML
    AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int a = 0; a < 15; a++) {
            list.getItems().add("Channel " + (a + 1));
        }
        int channel = Singleton.getInstance().getChannel() - 1;
        list.getSelectionModel().select(channel);
        list.scrollTo(channel);

        Thread t1 = new Thread(() -> {
            while (true) {
                while (Singleton.getInstance().getCommand() != 2 && Singleton.getInstance().getCommand() != 4 && Singleton.getInstance().getCommand() != 5 && Singleton.getInstance().getCommand() != 6) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int a = Singleton.getInstance().getCommand();
                Singleton.getInstance().setCommand(9);
                if (a == 4) {
                    goDown();
                } else if (a == 5) {
                    goUp();
                } else if (a == 2) {
                    turnOff();
                    break;
                } else {
                    confirm();
                    break;
                }
            }
        });
        t1.start();

    }

    public void goUp() {
        Platform.runLater(() -> {
            int a = list.getSelectionModel().getSelectedIndex();
            int newIndex = a - 1;
            if (newIndex == -1) {
                newIndex = list.getItems().size() - 1;
            }
            list.getSelectionModel().select(newIndex);
            list.scrollTo(newIndex);
        });
    }

    public void goDown() {
        Platform.runLater(() -> {
            int a = list.getSelectionModel().getSelectedIndex();
            int newIndex = a + 1;
            if (newIndex == list.getItems().size()) {
                newIndex = 0;
            }
            list.getSelectionModel().select(newIndex);
            list.scrollTo(newIndex);
        });
    }

    public void confirm() {
        Platform.runLater(() -> {
            int a = list.getSelectionModel().getSelectedIndex();
            Singleton.getInstance().setChannel((a + 1));
            Singleton.getInstance().setCommand(0);
            Singleton.getInstance().changeScene(anchorPane, "home-view.fxml");
        });
    }

    public void turnOff() {
        Platform.runLater(() -> {
            Singleton.getInstance().setCommand(10);
            Singleton.getInstance().changeScene(anchorPane, "start-view.fxml");
        });
    }
}