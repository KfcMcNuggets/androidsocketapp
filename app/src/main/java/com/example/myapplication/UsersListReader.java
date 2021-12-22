package com.example.myapplication;

import static com.example.myapplication.MainActivity.clientSocket;

import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class UsersListReader extends Thread implements Serializable {

    ArrayList<User> users;
    UserAdapter userAdapter;
    ItsTimeToUpdateAdapter time;
    ArrayAdapter arrayAdapter;
    User user;

    UsersListReader(ArrayList<User> users, UserAdapter userAdapter) {
        this.users = users;
        this.userAdapter = userAdapter;
        start();
    }

    UsersListReader() {

    }

    public void setTimer(ItsTimeToUpdateAdapter time, ArrayAdapter arrayAdapter, User user) {
        this.time = time;
        this.arrayAdapter = arrayAdapter;
        this.user = user;
    }

    @Override
    public void run() {
        while (true) {
            try {
                InputStream inputStream = clientSocket.getInputStream();
                System.out.println("Handle new userlist");
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                users.clear();
                Object object = objectInputStream.readObject();
                try {
                    ArrayList<User> downloadedUsers = (ArrayList<User>) object;
                    users.addAll(downloadedUsers);
                    System.out.println("Get user list " + users);

                    new Handler(Looper.getMainLooper()).post(new Runnable() {

                        @Override
                        public void run() {

                            userAdapter.notifyDataSetChanged();
                            System.out.println("Rewrite adapter");
                        }
                    });
                    //

                } catch (Exception e) {
                    System.out.println("ITS NOT USERSLIST" + e);
                    try {
                        System.out.println("Im in message writer inside reader ");
                        System.out.println("stream" + objectInputStream);
                        Message messagein = (Message) object;
                        System.out.println("streamread" + messagein);
                        System.out.println("after new object message");
                        String message = messagein.sender + "> " + messagein.msg;
                        System.out.println(message);
                        if (!message.isEmpty()) {
                            System.out.println("He he message");
                            user.addMessage(message);
                            time.timeIsCome();
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


