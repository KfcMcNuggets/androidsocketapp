package com.example.myapplication;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

public class User implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private String username;
    private String userId;
    private Socket socket;
  private  ArrayList<String>pmMessages = new ArrayList<>();
    User (Socket socket, String username, String userId){
        this.socket = socket;
        this.userId = userId;
        this.username =username;

    }


    public String getUsername(){
        return username;
    }

    public String getUserId(){
        return userId;
    }

    public void addMessage(String msg){
        pmMessages.add(msg);
    }

    public ArrayList<String> getPmMessages(){
        return pmMessages;
    }





}
