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
            final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("ws://Ro01.beginit.se:1337/websocket"));

            //Need this to connect to server
            clientEndPoint.sendMessage("getTVStatus");
            // add listener
            clientEndPoint.addMessageHandler(message -> {
                System.out.println(message);
                if (message.contains("on")) {
                    if (message.contains("true")) {
                        Singleton.getInstance().setCommand(1);
                    } else {
                        Singleton.getInstance().setCommand(2);
                    }
                } else if (message.contains("channel")) {
                    JSONObject json = new JSONObject(message);
                    String channel = json.getString("channel");
                    Singleton.getInstance().setCommand(Integer.parseInt(channel));
                }
                // 1 = start tv
                // 2 = turn off
                // 3 = channel list
                // 4 = channel down
                // 5 = channel up
                // 6 = confirm
            });

        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }
    }
}
