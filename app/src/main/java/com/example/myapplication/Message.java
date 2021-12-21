package com.example.myapplication;

import java.io.Serializable;

public class Message implements Serializable {
    String sender;
    String msg;

    Message (String sender, String msg){
        this.sender = sender;
        this.msg = msg;

    }

}
