package com.example.myapplication;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.net.*;
import java.io.*;
public class Regist extends Thread{
    private String account;
    private String pwd;
    private String echo=null;
    private Handler handler;
    public Regist(String str1,String str2,Handler hand){
        account=str1;
        pwd=str2;
        handler=hand;
    }
    public void run(){
        try
        {
            Socket client = new Socket("116.62.206.214", 9527);
            OutputStream out=client.getOutputStream();
            String message="reg#"+account+"#"+pwd;
            client.getOutputStream().write(message.getBytes("Utf-8"));
            client.shutdownOutput();

            InputStream in=client.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(in));
            echo=br.readLine();

            out.close();
            client.close();
        }catch(IOException e)
        {
            e.printStackTrace();

        }
        if (echo.equals("pass")){
            Message msg=new Message();
            msg.what=1;
            handler.sendMessage(msg);

        }
        else {
            Message msg=new Message();
            msg.what=2;
            handler.sendMessage(msg);


        }

    }




}

