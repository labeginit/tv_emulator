package com.example.tv;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingletonTest {

    @Test
    void setCommand() {

        Singleton.getInstance().setCommand(2);
        int tempInt = Singleton.getInstance().getCommand();
        Assertions.assertEquals(2,tempInt);


    }


    @Test
    void getCommand() {
        Singleton.getInstance().setCommand(1);
        int tempInt = Singleton.getInstance().getCommand();
        Assertions.assertEquals(1,tempInt);

    }


    @Test
    void getInstance() {
    }

    @Test
    void setChannel() {
    }

    @Test
    void getChannel() {

        Singleton.getInstance().getChannel();


    }
}