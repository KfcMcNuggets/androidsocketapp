package com.example.myapplication;
import static com.example.myapplication.SuperUser.mySocket;

import java.net.Socket;

public class SocketConnecter extends Thread {

    @Override
    public void run() {
        try {
            mySocket = new Socket("10.0.2.2", 4004);
            System.out.println("SUCCES" + mySocket.getKeepAlive());
        } catch (Exception e) {
        System.out.println("ERRROR" + e);}
    }
}