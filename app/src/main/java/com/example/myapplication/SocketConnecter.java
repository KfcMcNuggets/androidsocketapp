package com.example.myapplication;
import static com.example.myapplication.MainActivity.clientSocket;

import java.net.Socket;

public class SocketConnecter extends Thread {

    @Override
    public void run() {
        try {
            clientSocket = new Socket("10.0.2.2", 4004);
            System.out.println("SUCCES" + clientSocket.getKeepAlive());
        } catch (Exception e) {
        System.out.println("ERRROR" + e);}
    }
}