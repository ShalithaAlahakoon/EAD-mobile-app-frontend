package com.example.eadmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class owner_dashboard extends AppCompatActivity {

    Button add_station,my_stations;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_dashboard);

        add_station = findViewById(R.id.btn_add_stations);
        my_stations = findViewById(R.id.btn_my_stations);

        add_station.setOnClickListener(v -> {
            //set intent to add station activity
            Intent intent = new Intent(owner_dashboard.this, AddStation.class);
            startActivity(intent);
        });


    }
}