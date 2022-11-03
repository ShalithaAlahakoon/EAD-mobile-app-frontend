package com.example.eadmobileapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eadmobileapp.api.API;
import com.example.eadmobileapp.api.RetrofitClient;
import com.example.eadmobileapp.models.Station;

import retrofit2.Call;

public class UpdateFuels extends AppCompatActivity {

    // creating our variables for our views such
    // as text view, button and progress
    // bar and response text view.
    private EditText arrivalTime, fuelLitres;
    private Button updateBtn;
    private TextView stationName,fuelType;
    private Switch fuelStatus;


    //get API interface
    API api = RetrofitClient.getInstance().getApi();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatefuels);

        // initializing our views with their ids.
        arrivalTime = findViewById(R.id.editTextTime);
        fuelLitres = findViewById(R.id.etLitres2);
        updateBtn = findViewById(R.id.button);
        stationName = findViewById(R.id.txt_station_name);
        fuelType = findViewById(R.id.txt_fuel_type);
        fuelStatus = findViewById(R.id.switch1);

        //get extras
        Intent intent1 = getIntent();
        String owner_name = intent1.getStringExtra("owner_name");
        String station_name = intent1.getStringExtra("station_name");
        String fuel_type = intent1.getStringExtra("fuel_type");

        //set text
        stationName.setText(station_name);
        fuelType.setText(fuel_type);

        updateBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String arrival_time = arrivalTime.getText().toString();
                        String fuel_litres = fuelLitres.getText().toString();
                        boolean fuel_status = fuelStatus.isChecked();







                        Station station = new Station(station_name,fuel_type, arrival_time, fuel_litres, fuel_status);

                        Call<Station> call = api.updateStation(station);

                        call.enqueue(
                                new retrofit2.Callback<Station>() {
                                    @Override
                                    public void onResponse(Call<Station> call, retrofit2.Response<Station> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(UpdateFuels.this, "Station Updated", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(UpdateFuels.this, owner_dashboard.class);
                                            intent.putExtra("owner_name", owner_name);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(UpdateFuels.this, "Station Not Updated", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Station> call, Throwable t) {
                                        Toast.makeText(UpdateFuels.this, "Station Not Updated", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );

                    }
                });

    }
}
