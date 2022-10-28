package com.example.eadmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eadmobileapp.api.API;
import com.example.eadmobileapp.api.RetrofitClient;
import com.example.eadmobileapp.models.Station;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStation extends AppCompatActivity {
    EditText sName, sDistrict, sAddress, sOwner;
    Button addStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_station);

        addStation = findViewById(R.id.button5);

        sName = findViewById(R.id.stName);
        sDistrict = findViewById(R.id.stDistrict);
        sAddress = findViewById(R.id.stAddress);
        sOwner = findViewById(R.id.stOwner);

        addStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stationName = sName.getText().toString();
                String stationArea = sDistrict.getText().toString();
                String stationAddress = sAddress.getText().toString();
                String owner = sOwner.getText().toString();


                if (stationName.isEmpty() || stationArea.isEmpty() || stationAddress.isEmpty() || owner.isEmpty()) {
                    Toast.makeText(AddStation.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else{
                    //API interface
                    API api = RetrofitClient.getInstance().getApi();
                    //create user
                    Station station = new Station(stationName,stationArea,stationAddress,owner);
                    Call<Station> call = api.createStation(station);

                    call.enqueue(new Callback<Station>() {
                        @Override
                        public void onResponse(Call<Station> call, Response<Station> response) {
                            if(response.isSuccessful()) {
                                Toast.makeText(AddStation.this, "Station added successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddStation.this, StationOwner_StationDetails.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(AddStation.this, "Station adding failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Station> call, Throwable t) {
                            Toast.makeText(AddStation.this, "Station adding failed", Toast.LENGTH_SHORT).show();
                        }
                    })
                    ;
                }
            }
        });
    }

}