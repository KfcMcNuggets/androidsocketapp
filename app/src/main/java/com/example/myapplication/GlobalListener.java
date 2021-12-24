package com.example.myapplication;

import static com.example.myapplication.SuperUser.myId;
import static com.example.myapplication.SuperUser.mySocket;
import static com.example.myapplication.SuperUser.name;
import static com.example.myapplication.PMList.chatsAdapters;
import static com.example.myapplication.PMList.users;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class GlobalListener extends Thread implements Serializable {

    ArrayList<User> users;
    UserAdapter userAdapter;
    PMList pmlist;

    GlobalListener(ArrayList<User> users, UserAdapter userAdapter, PMList pmList) {
        this.users = users;
        this.userAdapter = userAdapter;
        this.pmlist = pmList;
        start();
    }




    @Override
    public void run() {
        while (true) {
            try {
                InputStream inputStream = mySocket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                Object object = objectInputStream.readObject();

                try {
                    ArrayList<User> downloadedUsers = (ArrayList<User>) object;
                    users.clear();
                    users.addAll(downloadedUsers);

                    new Handler(Looper.getMainLooper()).post(new Runnable() {

                        @Override
                        public void run() {
                            System.out.println("SIZE old" + userAdapter.getCount());
                            userAdapter.notifyDataSetChanged();
                            System.out.println("SIZE " + userAdapter.getCount());
                            System.out.println("Rewrite adapter");
                        }
                    });
                    //

                } catch (Exception e) {
    System.out.println("THI IS MESSAGE");
                    try {

                        Message messagein = (Message) object;

                        String message = messagein.sender + "> " + messagein.msg;
                        String senderId = messagein.senderId;

                        if (!message.isEmpty()) {


                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (chatsAdapters.get(senderId) != null) {
                                            chatsAdapters.get(senderId).updateArundapter(senderId, message);
                                        }
                                    }
                                });

                        }





                    } catch (Exception g) {
                        System.out.println("ERROR IN READING, e =  " + g);
                        e.printStackTrace();
                        System.out.println("MESSAGE = " + g.getMessage());
                    }
                }
            } catch (Exception a) {
                System.out.println("all is in cunt");
            }
        }
    }
}


