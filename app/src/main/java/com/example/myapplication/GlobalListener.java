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
import java.util.concurrent.locks.ReentrantLock;

public class GlobalListener extends Thread implements Serializable {
    ArrayList<User> users;
    ReentrantLock users_mutex = new ReentrantLock();
    ReentrantLock usersAdapter_mutex = new ReentrantLock();
    UserAdapter userAdapter;
    PMList pmlist;

    GlobalListener(ArrayList<User> users, UserAdapter userAdapter, PMList pmList) {
        this.users = users;
        this.userAdapter = userAdapter;
        this.pmlist = pmList;
        start();
    }

    int i = 6;

    @Override
    public void run() {
        while (true) {
            try {
                InputStream inputStream = mySocket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

                Object object = (Object) objectInputStream.readObject();

                System.out.println("NEW TRED ==== " + i++);
                Thread thread = new Thread() {

                    @Override
                    public void run() {

                        System.out.println("TRED " + Thread.currentThread());
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                readObject(object);
                            }
                        });
}
                };
                thread.start();


            } catch (Exception a) {
                System.out.println("Global problem with input" + a);
            } catch (Error e) {
                e.printStackTrace();
            }
        }
    }

    public void readMessage(Object object) {
        try {
            Message messagein = (Message) object;

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

    public synchronized void readObject(Object object) {
        try {
            ArrayList<User> downloadedUsers = (ArrayList<User>) object;
            users_mutex.lock();
            users.clear();
            users.addAll(downloadedUsers);
            users_mutex.unlock();

            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    usersAdapter_mutex.lock();
                    System.out.println("SIZE old" + userAdapter.getCount());
                    userAdapter.notifyDataSetChanged();
                    System.out.println("SIZE " + userAdapter.getCount());
                    System.out.println("Rewrite adapter");
                    usersAdapter_mutex.unlock();
                }
            });

        } catch (Exception e) {
            System.out.println("THI IS MESSAGE");
            readMessage(object);
        }
    }
}


