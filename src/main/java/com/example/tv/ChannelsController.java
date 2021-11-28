package com.example.tv;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.tv.HomeController.mediationInfo;

public class ChannelsController implements Initializable {
    @FXML
    ListView<String> list;

    @FXML
    AnchorPane anchorPane;

    @FXML
    Pane mediation;

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
                int a = MenuController.lookForChange();
                if (a == 2) {
                    confirm();
                    break;
                } else if (a == 1) {
                    turnOff();
                    break;
                } else if (a == 3) {
                    goDown();
                } else if (a == 4) {
                    goUp();
                } else if (a == 5) {
                    mediationInfo(mediation, 1);
                } else if (a == 6) {
                    mediationInfo(mediation, 0);
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