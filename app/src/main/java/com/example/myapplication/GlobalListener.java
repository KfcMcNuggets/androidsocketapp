package com.example.myapplication;

import static com.example.myapplication.MainActivity.clientSocket;
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
                InputStream inputStream = clientSocket.getInputStream();
                System.out.println("Handle new object");
                System.out.println("SIZE at begin " + userAdapter.getCount());
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

                Object object = objectInputStream.readObject();
                //
                try {
                    ArrayList<User> downloadedUsers = (ArrayList<User>) object;
                    System.out.println("this is userlist");
                    System.out.println("SIZE superold " + userAdapter.getCount());
                    users.clear();
                    users.addAll(downloadedUsers);
                    System.out.println("Get user list " + users);

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
                    System.out.println("ITS NOT USERSLIST" + e);
                    try {
                        System.out.println("SIZE before creating message" + userAdapter.getCount());
                        System.out.println("Im in message writer inside reader ");
                        System.out.println("stream" + objectInputStream);
                        Message messagein = (Message) object;
                        System.out.println("SIZE after creating message" + userAdapter.getCount());
                        System.out.println("streamread" + messagein);
                        System.out.println("after new object message");
                        String message = messagein.sender + "> " + messagein.msg;
                        String userId = messagein.receiver;
                        System.out.println(message);
                        if (!message.isEmpty()) {
                            System.out.println("He-he gotcha message to " + userId);
                            System.out.println("SIZE before add message" + userAdapter.getCount());
                            for (User user : users) {
                                if (user.getUserId().equals(userId)) {
                                    user.addMessage(message);
                                    System.out.println("SIZE after creating message" + userAdapter.getCount());
                                }
                            }

                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (chatsAdapters.get(userId) != null) {
                                            chatsAdapters.get(userId).updateArundapter();
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


