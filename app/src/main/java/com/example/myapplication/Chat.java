package com.example.myapplication;

import static com.example.myapplication.MainActivity.clientSocket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.net.Socket;
import java.util.ArrayList;

public class Chat extends AppCompatActivity {
    static ArrayList<String>messages = new ArrayList<>();

    static  ArrayAdapter<String> arrayAdapter;
    EditText inputText;
    String username;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        Bundle args = getIntent().getExtras();
        username = args.get("userName").toString();


        System.out.println(clientSocket.getInetAddress());
        inputText = findViewById(R.id.inputter);
        ListView messagesList = findViewById(R.id.messages);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, messages);
        messagesList.setAdapter(arrayAdapter);
        send = (Button) findViewById(R.id.send);
        new ReadMsg(clientSocket).start();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Clicked");
                String msg = inputText.getText().toString();
                messages.add("You> " + msg);
                arrayAdapter.notifyDataSetChanged();
                new WriteMsg(new Message(username,msg), clientSocket).start();
                inputText.setText("");
            }
        });

    }







}