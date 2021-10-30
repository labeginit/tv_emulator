package com.example.tv;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Singleton {
    private int channel;
    private int number;
    private final static Singleton INSTANCE = new Singleton();

    private Singleton() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public static Singleton getInstance() {
        return INSTANCE;
    }

    public void setChannel(int u) {
        this.channel = u;
    }

    public int getChannel() {
        return this.channel;
    }

    public void changeScene(AnchorPane event, String a) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(a));
        Parent mainCallWindowFXML = null;
        try {
            mainCallWindowFXML = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) event.getScene().getWindow();//or use any other component in your controller
        assert mainCallWindowFXML != null;
        Scene mainCallWindow = new Scene(mainCallWindowFXML, 800, 700);
        stage.setScene(mainCallWindow);
        stage.show();
    }
}
