package com.example.asa.chrometextension;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class Chrome_TExtension_Service extends Service {
    //Log tag
    private static final String LOG_TAG = "TExtension_Service";
    //The socket we'll be opening to connect to our server
    private Socket socket = null;
    //Our Server information
    final String IP = "159.203.25.93";
    final int PORT = 1026;

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        final Context context = this;
        new Thread() {
            @Override
            public void run() {
                connectToServer();
                writeToServer("Hey bb");
                closeConnection();
            }
        }.start();
        //Starts a new thread for network-connections
        return START_STICKY;
    }
    //Writes to the socket we've connected to.
    private void writeToServer(String s){
        if (socket==null) {
           Log.e(LOG_TAG,"Not connected to the server, connect first!");
           return;
        }
        OutputStream out;
        try{
            out = socket.getOutputStream();

        } catch (IOException e){
            Log.e(LOG_TAG, "IOException when getting socket's OutputStream: " + e.getMessage());
            return;
        }
        PrintWriter writer = new PrintWriter(out,true);
        writer.println(s);
        writer.close();
    }
    //Opens a socket to our server
    private void connectToServer(){
        try {
            InetAddress IA = InetAddress.getByName(IP);
            socket = new Socket(IA,PORT);
        } catch (UnknownHostException e) {
            Log.e(LOG_TAG, "UnknownHostException when attempting to create a socket: " + e.getMessage());
        } catch (IOException e) {
            Log.e(LOG_TAG, "IO Exception when attempting to create a socket: " + e.getMessage());
        }
    }

    //Closes socket
    private void closeConnection(){
        try {
            socket.close();
        } catch (IOException e){
            Log.e(LOG_TAG, "Couldn't close the socket : " + e.getMessage());
        }
    }




    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
