package com.example.myapplication;

import static com.example.myapplication.SuperUser.myId;
import static com.example.myapplication.SuperUser.mySocket;
import static com.example.myapplication.SuperUser.name;
import static com.example.myapplication.PMList.chatsAdapters;
import static com.example.myapplication.PMList.users;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Chat extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapter;
    EditText inputText;
    Button send;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        this.thread =  Thread.currentThread();
        System.out.println(thread);
        Bundle args = getIntent().getExtras();
        User user = users.get((int) args.get("user"));
        String userId = user.getUserId();
        System.out.println("CREATED USER + " + user.getUsername());
        System.out.println(mySocket.getInetAddress());
        TextView receiver = findViewById(R.id.username);
        receiver.setText(user.getUsername());
        inputText = findViewById(R.id.inputter);
        ListView messagesList = findViewById(R.id.messages);
        System.out.println("PM MESS " + user.getPmMessages().size());
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, user.getPmMessages());
        messagesList.setAdapter(arrayAdapter);
        send = (Button) findViewById(R.id.send);
        Button back = findViewById(R.id.back);
        chatsAdapters.put(userId, this);

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
                new WriteMsg(new Crypter(name, myId, msg, receiver).cryptMessage(), mySocket);
                inputText.setText("");

            }
        });
    }

    public void updateArundapter(String senderId, String message){

        for (User user : users) {
            if (user.getUserId().equals(senderId)) {

                System.out.println(message + "     " + Thread.currentThread());
                user.addMessage(message);
                System.out.println("add message to list = " + user.getUsername() + " length = " + user.getPmMessages().size());

            }
        }
            try {
                arrayAdapter.notifyDataSetChanged();
                System.out.println("adapter updated " + Thread.currentThread());
            }catch(Exception chatNotExist){
                System.out.println("Chat is not exist yet" + chatNotExist);
            }


    }
}