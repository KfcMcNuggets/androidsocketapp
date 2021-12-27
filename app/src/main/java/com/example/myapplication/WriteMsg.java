package com.example.myapplication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class WriteMsg extends Thread {

    Message msg;
    Socket clientSocket;

    WriteMsg(Message msg, Socket clientSocket){
        this.msg = msg;
        this.clientSocket = clientSocket;
        System.out.println("created");
        start();
    }

    @Override
    public void run() {

        System.out.println("Started sending");

        try {
            System.out.println("tryin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            System.out.println("sendin");
            objectOutputStream.writeObject(msg);

            System.out.println("Plez send " + msg.msg + " to " + msg.receiver);

        } catch (IOException e) {
            System.out.println("AAAAAAAAAAAA" + e);
        }
    }
}