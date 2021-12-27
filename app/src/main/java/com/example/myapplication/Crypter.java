package com.example.myapplication;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import android.util.Base64;
import javax.crypto.spec.SecretKeySpec;



public class Crypter {

    private String keyForClient;
    private String keyForServer;
    private String msg;
    private String sender;
    private String senderId;
    private String receiver;

    Crypter(String name, String myId, String msg, String receiver){
        this.keyForClient = "123456789clientk";
        this.keyForServer = "123456789serverk";
        this.msg = msg;
        this.sender = name;
        this.receiver = receiver;
        this. senderId = myId;
    }


    private String encrypted(String encryptionKey, String str){
      try {

          Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
          byte[] key = encryptionKey.getBytes("UTF-8");
          SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
          IvParameterSpec ivparameterspec = new IvParameterSpec(key);
          cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivparameterspec);
          byte[] cipherText = cipher.doFinal(str.getBytes("UTF8"));

          return new String(Base64.encode(cipherText, Base64.DEFAULT));

      } catch (Exception ex) {
          ex.printStackTrace();
      }

      return null;
    }

    private String decrypted(String encryptionKey, String str){
        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            byte[] key= encryptionKey.getBytes("UTF-8");
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            IvParameterSpec ivparameterspec = new IvParameterSpec(key);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivparameterspec);
            byte[] cipherText = Base64.decode(str, Base64.DEFAULT);

            return new String(cipher.doFinal(cipherText), "UTF-8");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public Message cryptMessage (){
    String receiver = encrypted(this.keyForServer, this.receiver);
    String message = encrypted(this.keyForClient, this.msg);
    String sender = encrypted(this.keyForClient, this.sender);
    String senderId = encrypted(this.keyForClient, this.senderId);

    return new Message(sender, senderId, message, receiver);

    }

    public Message decryptMessage (){
        String receiver = decrypted(this.keyForServer, this.receiver);
        String message = decrypted(this.keyForClient, this.msg);
        String sender = decrypted(this.keyForClient, this.sender);
        String senderId = decrypted(this.keyForClient, this.senderId);

        return new Message(sender, senderId, message, receiver);

    }
}
