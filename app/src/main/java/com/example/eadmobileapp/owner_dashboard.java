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

        //get extras
        Intent intent1 = getIntent();
        String owner_name = intent1.getStringExtra("owner_name");

        add_station.setOnClickListener(v -> {
            //set intent to add station activity
            Intent intent = new Intent(owner_dashboard.this, AddStation.class);
            intent.putExtra("owner_name", owner_name);
            startActivity(intent);
        });

        my_stations.setOnClickListener(v -> {
            //set intent to my stations activity
            Intent intent = new Intent(owner_dashboard.this, My_stations.class);
            intent.putExtra("owner_name", owner_name);
            startActivity(intent);
        });


    }
}