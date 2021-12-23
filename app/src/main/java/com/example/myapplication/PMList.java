package com.example.myapplication;


import static com.example.myapplication.MainActivity.clientSocket;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class PMList extends AppCompatActivity {

    static ArrayList<User> users = new ArrayList<>();
    private UserAdapter userAdapter;
    static HashMap<String, Chat> chatsAdapters = new HashMap<>();

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       System.out.println(Thread.currentThread());
        setContentView(R.layout.pm_list);
        TextView username = findViewById(R.id.username);
        Bundle args = getIntent().getExtras();
        String you = args.get("userName").toString();
        username.setText("You: " + you);
        System.out.println("im here");
        ListView usersList = findViewById(R.id.users);
        userAdapter = new UserAdapter(this, R.layout.user, users);
        usersList.setAdapter(userAdapter);
        new GlobalListener(users, userAdapter, this);
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(PMList.this, Chat.class);
                intent.putExtra("user", position);
                intent.putExtra("userName", you);
                startActivity(intent);
            }
        });


    }


    public void updateAdapter(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userAdapter.notifyDataSetChanged();
            }
        });
    }




}
