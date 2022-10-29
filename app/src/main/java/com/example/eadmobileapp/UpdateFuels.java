package com.example.eadmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class UpdateFuels extends AppCompatActivity {
Spinner spinner;
String[] type = {"Petrol-95","Petrol-92","Diesel","Super Diesel"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatefuels);


    }
}