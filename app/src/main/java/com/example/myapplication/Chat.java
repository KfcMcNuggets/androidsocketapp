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

public class Chat extends AppCompatActivity implements ItsTimeToUpdateAdapter{
    //ArrayList<String>messages = new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;
    EditText inputText;
    String username;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        Bundle args = getIntent().getExtras();
        username = args.get("userName").toString();
        User user = (User) args.getSerializable("user");
//        UsersListReader reader = (UsersListReader) args.getSerializable("reader");

        System.out.println("CREATED USER + " + user.getUsername());
        System.out.println(clientSocket.getInetAddress());
        TextView receiver = findViewById(R.id.username);
        receiver.setText(user.getUsername());
        inputText = findViewById(R.id.inputter);
        ListView messagesList = findViewById(R.id.messages);
        System.out.println("PM MESS " + user.getPmMessages());
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, user.getPmMessages());
        messagesList.setAdapter(arrayAdapter);
        send = (Button) findViewById(R.id.send);
        Button back = findViewById(R.id.back);
        new UsersListReader().setTimer(this, arrayAdapter, user);
        //reader.setTimer(this, arrayAdapter, user);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Clicked");
                String msg = inputText.getText().toString();
                user.addMessage("You> " + msg);
                String receiver = user.getUserId();
                arrayAdapter.notifyDataSetChanged();
                new WriteMsg(new Message(username,msg, receiver), clientSocket);
                inputText.setText("");
            }
        });

    }


    public void timeIsCome(){
        System.out.println("update messageadapter");
        arrayAdapter.notifyDataSetChanged();
    }




}