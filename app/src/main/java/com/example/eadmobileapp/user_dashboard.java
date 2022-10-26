package com.example.eadmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class user_dashboard extends AppCompatActivity {

    Button stations, go_to_queue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        stations = findViewById(R.id.btn_stations);
        go_to_queue = findViewById(R.id.btn_go_to_queue);

        stations.setOnClickListener(v -> {
            Intent intent = new Intent(user_dashboard.this, search_station.class);
            startActivity(intent);
        });
    }
}