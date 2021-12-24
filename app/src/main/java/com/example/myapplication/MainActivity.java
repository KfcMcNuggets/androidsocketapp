package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.myapplication.SuperUser.myId;
import static com.example.myapplication.SuperUser.mySocket;
import static com.example.myapplication.SuperUser.name;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.security.spec.ECField;

public class MainActivity extends AppCompatActivity implements GetterId {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        new SocketConnecter().start();
        EditText nameSetter = findViewById(R.id.nameSetter);
        Button setName =findViewById(R.id.setName);
        setName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameSetter.getText().toString();
                System.out.println("SOCKET    " + mySocket  + name);
                if (!name.isEmpty()) {
                    SuperUser.name = name;
                    new WriteMsg(new Message(name, "i ain't got some yet", "hello server", "server"), mySocket);
                    Thread idGetter = new Thread(){

                        @Override
                        public void run() {

                            try {
                                InputStream inputStream = mySocket.getInputStream();
                                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                                myId = (String) objectInputStream.readObject();
                                MainActivity.this.registrate();
                            }catch (Exception e){
                                System.out.println("FAILED IN GETTING ID " + e);
                            }

                        }
                    };
                    idGetter.start();

                }
                else{
                    Toast error = Toast.makeText(MainActivity.this, "Name can't be empty! Please enter you're name", Toast.LENGTH_LONG);
                    error.show();
                }




            }



        });

    }

    @Override
    public void registrate() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast welcome = Toast.makeText(MainActivity.this, "Hello " + name + "!\n" + "You're id is " + myId, Toast.LENGTH_LONG);
                welcome.show();
                Intent intent = new Intent(MainActivity.this, PMList.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }
}