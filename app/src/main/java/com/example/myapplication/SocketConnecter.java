package com.example.myapplication;
import static com.example.myapplication.SuperUser.mySocket;
import static com.example.myapplication.SuperUser.name;

import android.app.Activity;

import java.net.Socket;

public class SocketConnecter extends Thread {
    ConnectCallback connectCallback;
    SocketConnecter(ConnectCallback connectCallback){
        this.connectCallback = connectCallback;
        start();
    }




    @Override
    public void run(){
        try {
            mySocket = new Socket("10.0.2.2", 4004);
            System.out.println("SUCCES" + mySocket);
            this.connectCallback.succesfullyConnected();


        } catch (Exception e) {
            System.out.println("ERRROR IN CONNECTION" + e);
            this.connectCallback.connectionFailed();
        }
    }

}