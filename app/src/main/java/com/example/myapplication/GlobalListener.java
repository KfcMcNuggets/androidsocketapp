package com.example.myapplication;

import static androidx.core.content.ContextCompat.startActivity;
import static com.example.myapplication.SuperUser.myId;
import static com.example.myapplication.SuperUser.mySocket;
import static com.example.myapplication.PMList.chatsAdapters;
import static com.example.myapplication.SuperUser.name;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class GlobalListener extends Thread implements Serializable {

    private ArrayList<User> users;
    private UserAdapter userAdapter;
    private Activity pmList;
    private static Chat chat;
    private PMList pmListClass;
    GlobalListener(PMList pmListClass, Activity activity, ArrayList<User> users, UserAdapter userAdapter) {
        this.pmList = activity;
        System.out.println("LISTENER CREATED");
        this.users = users;
        this.userAdapter = userAdapter;
        this.pmList = pmList;
        this.chat = null;
        this.pmListClass = pmListClass;
        start();
    }



    @Override
    public void run() {
        while (true) {

            try {
                InputStream inputStream = mySocket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                Object object = (Object) objectInputStream.readObject();

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        readObject(object);
                    }
                });

            } catch (Exception a) {
                System.out.println("Global problem with input" + a);
               try{
                   mySocket.close();

               }catch (Exception ex){
                System.out.println("CANT CLOSE + "+  ex);
               }
                if(chat !=null){
                    System.out.println(chat + "ready to be killed");
                    chat.quit();
                    System.out.println(chat + "already killed");


                }

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast welcome = Toast.makeText(pmList, "Server disconnected", Toast.LENGTH_LONG);
                        welcome.show();
                        pmListClass.quit();
                        pmList = null;
                        pmListClass = null;
                    }
                });

                break;
            }
        }
    }

    public void readMessage(Object object) {
        try {

            Message cryptedMessage = (Message) object;
            Message messagein = new Crypter(cryptedMessage.sender, cryptedMessage.senderId, cryptedMessage.msg, cryptedMessage.receiver).decryptMessage();
            String message = messagein.sender + "> " + messagein.msg;
            String senderId = messagein.senderId;
            System.out.println("THe message is = " + message);

            if (!message.isEmpty()) {

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public synchronized void run() {
                        if (chatsAdapters.get(senderId) != null) {
                            chatsAdapters.get(senderId).updateArundapter(senderId, message);
                        } else {
                            System.out.println("adapter is null, just add message without updating adapter");
                            for (User user : users) {
                                if (user.getUserId().equals(senderId)) {
                                    System.out.println(message + "     " + Thread.currentThread());
                                    user.addMessage(message);
                                    System.out.println("add message to list = " + user.getUsername());
                                }
                            }
                        }
                    }
                });

            } else {
                System.out.println("SRY ITS EMPTY");
            }


        } catch (Exception g) {
            System.out.println("ERROR IN READING, e =  " + g);
            g.printStackTrace();
            System.out.println("MESSAGE = " + g.getMessage());
        }
    }

    public void readObject(Object object) {
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

        } catch (Exception e) {
            System.out.println("THI IS MESSAGE");
            readMessage(object);
        }
    }

    public void getActivity(Chat chat){
        this.chat = chat;
    }
    public void killChat(){
        this.chat = null;
    }

}


