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
    }
    @Override
    public void run() {


            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                objectOutputStream.writeObject(msg);
                objectOutputStream.flush();
                System.out.println("Send + " + msg.msg);
            } catch (IOException e) {
            }
    }
}