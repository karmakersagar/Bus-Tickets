package com.example.bus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splass);
        getSupportActionBar().hide();
        Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    sleep(1500);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();

                }
            }
        };thread.start();
    }
}