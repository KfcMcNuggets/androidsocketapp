package com.example.myapplication;

import static com.example.myapplication.Chat.arrayAdapter;


import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

class ReadMsg extends Thread {

    Socket clientSocket;

    ReadMsg(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        System.out.println("Im reading");
        String str;

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
                            Chat.messages.add(message);
                            arrayAdapter.notifyDataSetChanged();
                        }
                    });
//
                }
            } catch (Exception e) {
                System.out.println("ERROR IN READING " + e);
            }
        }


    }
}