package com.example.tv;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Singleton {
    private int channel;
    private final static Singleton INSTANCE = new Singleton();

    private Singleton() {}

    public static Singleton getInstance() {
        return INSTANCE;
    }

    public void setChannel(int u) {
        this.channel = u;
    }

    public int getChannel() {
        return this.channel;
    }

    public void changeScene(ActionEvent event, String a) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(a));
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root,800,700));
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
