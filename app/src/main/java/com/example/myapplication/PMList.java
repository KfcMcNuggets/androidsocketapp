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

public class PMList extends AppCompatActivity {

    static ArrayList<User> users = new ArrayList<>();
    static UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pm_list);
        TextView username = findViewById(R.id.username);
        Bundle args = getIntent().getExtras();
        String you = args.get("userName").toString();
        username.setText("You: " + you);
        System.out.println("im here");
        ListView usersList = findViewById(R.id.users);
        userAdapter = new UserAdapter(this, R.layout.user, users);
        usersList.setAdapter(userAdapter);
        new UsersListReader(users, userAdapter);
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PMList.this, Chat.class);
                intent.putExtra("user", users.get(position));
//                intent.putExtra("reader", reader);
                intent.putExtra("userName", you);
                startActivity(intent);
            }
        });


    }







}
