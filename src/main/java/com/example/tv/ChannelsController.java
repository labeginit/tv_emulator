package com.example.tv;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ChannelsController implements Initializable {
    @FXML
    ListView list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int a = 0; a < 15; a++) {
            list.getItems().add("Channel " + (a + 1));
        }
        list.getSelectionModel().select(0);

    }

    public void goUp(){
        int a = list.getSelectionModel().getSelectedIndex();
        int newIndex = a - 1;
        if (newIndex == -1) {
            newIndex = list.getItems().size() - 1;
        }
        list.getSelectionModel().select(newIndex);
    }
    public void goDown(){
        int a = list.getSelectionModel().getSelectedIndex();
        int newIndex = a + 1;
        if (newIndex == list.getItems().size()) {
            newIndex = 0;
        }
        list.getSelectionModel().select(newIndex);
    }
    public void confirm(ActionEvent event){
        int a = list.getSelectionModel().getSelectedIndex();
        Singleton.getInstance().setChannel((a+1));
        Singleton.getInstance().changeScene(event, "home-view.fxml");
    }
}