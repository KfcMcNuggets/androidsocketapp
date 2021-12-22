package com.example.myapplication;

import java.io.Serializable;

public class Message implements Serializable {
    String sender;
    String msg;
    String receiver;
    Message (String sender, String msg, String receiver){
        this.sender = sender;
        this.msg = msg;
        this.receiver = receiver;
    }

}
