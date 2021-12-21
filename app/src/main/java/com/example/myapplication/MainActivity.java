package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    static Socket clientSocket;
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
                if (!name.isEmpty()) {

                    Toast welcome = Toast.makeText(MainActivity.this, "Hello " + name + "!", Toast.LENGTH_LONG);
                    welcome.show();
                    Intent intent = new Intent(MainActivity.this, Chat.class);
                    intent.putExtra("userName", name);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast error = Toast.makeText(MainActivity.this, "Name can't be empty! Please enter you're name", Toast.LENGTH_LONG);
                    error.show();
                }




            }



        });

    }
}
