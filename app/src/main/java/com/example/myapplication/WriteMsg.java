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

    int counter = 0;
//        for (Integer i = 0; i<100  ; i++) {
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
               // msg.msg = "ddos" + i + (new String(new char[100 - i]).replace("\0", "-"));
                objectOutputStream.writeObject(msg);
                System.out.println("Plez send " + msg.msg + " to " + msg.receiver);
                counter++;

            } catch (IOException e) {
            }
        System.out.println("SENDED MESSAGES:  " + counter);
//        }
    }
}