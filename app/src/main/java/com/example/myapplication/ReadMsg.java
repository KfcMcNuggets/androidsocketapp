package com.example.myapplication;




import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

class ReadMsg extends Thread {

    Socket clientSocket;
    User user;
    ArrayAdapter arrayAdapter;
    ReadMsg(Socket clientSocket, User user, ArrayAdapter arrayAdapter) {
        this.clientSocket = clientSocket;
        this.user = user;
        this.arrayAdapter = arrayAdapter;
        start();
    }

    @Override
    public void run() {
        System.out.println("Im reading only from" + user.getUserId());


        while (true) {
            try {

                InputStream inputStream = clientSocket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                Message messagein = (Message) objectInputStream.readObject();
                String message = messagein.sender + "> " + messagein.msg;
                System.out.println(message);
                if (!message.isEmpty()) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {

                        @Override
                        public void run() {
                            user.addMessage(message);
                            arrayAdapter.notifyDataSetChanged();
                        }
                    });
//
                }
            } catch (Exception e) {
                System.out.println("ERROR IN READING, e =  " + e);
                e.printStackTrace();
                System.out.println("MESSAGE = " + e.getMessage());
            }
        }


    }
}