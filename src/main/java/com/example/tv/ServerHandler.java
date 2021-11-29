package com.example.tv;

import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class ServerHandler extends Thread {
    @Override
    public void run() {
        super.run();
        try {
            // open websocket
            //local
            //final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("ws://localhost:8080/websocket"));
            //extern
            final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("ws://ro01.beginit.se:1337/websocket"));

            //Need this to connect to server
            clientEndPoint.sendMessage("getTVStatus");
            // add listener
            clientEndPoint.addMessageHandler(message -> {
                System.out.println(message);
                if (message.contains("on")) {
                    boolean status = Singleton.getInstance().isOn();
                    Singleton.getInstance().setOn(!status);
                    Singleton.getInstance().setCommand(1);
                } else if (message.contains("channel")) {
                    JSONObject json = new JSONObject(message);
                    String channel = json.getString("channel");
                    if (!Singleton.getInstance().isOn() && channel.equals("7")) {
                        Singleton.getInstance().setOn(true);
                    }
                    Singleton.getInstance().setCommand(Integer.parseInt(channel));
                }
                // 1 = start tv
                // 2 = confirm/go to list
                // 3 = channel down
                // 4 = channel up
                // 5 = meditation pop up
                // 6 = cancel meditation pop up
                // 7 = Turn on TV with attention
            });

        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }
    }
}
