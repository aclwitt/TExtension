package com.example.asa.chrometextension;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ServiceStarterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService();
    }

    private void startService(){
        Intent intent = new Intent(getBaseContext(),Chrome_TExtension_Service.class);
        startService(intent);
    }
}
