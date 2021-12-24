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
        start();
    }
    @Override
    public void run() {


        for (Integer i = 0; i<1000; i++) {
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                msg.msg = "ddos" + i  ;
                objectOutputStream.writeObject(msg);
                System.out.println("Plez send " + msg.msg + " to " + msg.receiver);


            } catch (IOException e) {
            }
        }
    }
}