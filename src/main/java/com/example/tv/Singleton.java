package com.example.tv;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;

public class Singleton {
    private int channel = 1;
    private int command;
    private int hdmi = 1;
    private Timer timer;
    private final static Singleton INSTANCE = new Singleton();

    private Singleton() {
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public int getHdmi() {
        return hdmi;
    }

    public void setHdmi(int hdmi) {
        this.hdmi = hdmi;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int Command) {
        this.command = Command;
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
