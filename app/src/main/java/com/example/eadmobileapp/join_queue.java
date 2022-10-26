package com.example.eadmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class join_queue extends AppCompatActivity {

    Button scanQR, arriveQueue;
    Spinner fuel_dropDown, station_dropDown, area_dropDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_queue);

        scanQR = findViewById(R.id.qrBtn);
        arriveQueue = findViewById(R.id.arriveBtn);
        fuel_dropDown = findViewById(R.id.spinnerFuel);
        station_dropDown = findViewById(R.id.spinnerStation);
        area_dropDown = findViewById(R.id.spinnerArea);

        scanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
            }
        });

        arriveQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String selectArea = area_dropDown.getSelectedItem().toString();
                String selectStation = station_dropDown.getSelectedItem().toString();
                String selectFuel = fuel_dropDown.getSelectedItem().toString();

                System.out.println(" selected area : "+selectArea+" station : "+selectStation+" fuel : "+selectFuel);

                Intent intent = new Intent(view.getContext(), timer_screen.class);
                intent.putExtra("area", selectArea);
                intent.putExtra("station", selectStation);
                intent.putExtra("fuel", selectFuel);
                startActivity(intent);

            }
        });
    }
}