package com.example.eadmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class user_from_home_dashboard extends AppCompatActivity {


    private Spinner spinner_area ,spinner_station;
    private Button btn_search ;
    private TextView txt_view2 ;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_from_home_dashboard);

        //get spinner form id
         spinner_area = (Spinner) findViewById(R.id.spinner_area);
        // station spinner
         spinner_station = (Spinner) findViewById(R.id.spinner_station);
        // get search button
         btn_search = (Button) findViewById(R.id.button_search);
        // get text view
         txt_view2 = (TextView) findViewById(R.id.textView2);

         //sample array
        String[] area = {"Colombo","Gampaha","Kaluthara"};

        //set area2 array
        spinner_area.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,area));

        //display all area values in text view
        txt_view2.setText(area[0]);






    }

    
}