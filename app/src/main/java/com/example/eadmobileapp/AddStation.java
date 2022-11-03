package com.example.eadmobileapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eadmobileapp.api.API;
import com.example.eadmobileapp.api.RetrofitClient;
import com.example.eadmobileapp.models.Queue;
import com.example.eadmobileapp.models.Station;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddStation extends AppCompatActivity {

    //get API interface
    API api = RetrofitClient.getInstance().getApi();

    EditText stationName, stationAddress, stationArea ;
    CheckBox octane92, octane95,diesel, superDiesel;
    Button addStation;
    TextView owner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_station);

        stationName = findViewById(R.id.stName);
        stationAddress = findViewById(R.id.stAddress);
        stationArea = findViewById(R.id.stArea);
        octane92 = findViewById(R.id.checkBox92Octane);
        octane95 = findViewById(R.id.checkBox95Octane);
        diesel = findViewById(R.id.checkBoxDiesel);
        superDiesel = findViewById(R.id.checkBoxSuperDiesel);
        addStation = findViewById(R.id.addStation);
        owner = findViewById(R.id.stOwner);

        //get extras
        Intent intent1 = getIntent();
        String owner_name = intent1.getStringExtra("owner_name");
        owner.setText(owner_name);


        addStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if fields are empty
                if (stationName.getText().toString().isEmpty() || stationAddress.getText().toString().isEmpty() || stationArea.getText().toString().isEmpty() || owner.getText().toString().isEmpty()) {
                    Toast.makeText(AddStation.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    //according to selected fuel type create fuel type array list
                    ArrayList<Station.fuelType> fuelTypes = new ArrayList<>();
                    if(octane92.isChecked()){
                        fuelTypes.add(new Station.fuelType("92 Octane",true,null));
                    }
                    if(octane95.isChecked()){
                        fuelTypes.add(new Station.fuelType("95 Octane",true,null));
                    }
                    if(diesel.isChecked()){
                        fuelTypes.add(new Station.fuelType("Diesel",true,null));
                    }
                    if(superDiesel.isChecked()){
                        fuelTypes.add(new Station.fuelType("Super Diesel",true,null));
                    }


                    Station station = new Station(stationName.getText().toString(),  stationArea.getText().toString(),stationAddress.getText().toString(), owner.getText().toString(),true,fuelTypes);


                    //get queue list
                    Call<Station> call = api.createStation(station);

                    call.enqueue(new Callback<Station>() {
                        @Override
                        public void onResponse(Call<Station> call, Response<Station> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(AddStation.this, "Station added successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddStation.this, owner_dashboard.class);
                                intent.putExtra("owner_name", owner_name);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<Station> call, Throwable t) {
                            Toast.makeText(AddStation.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    );
                }



        }
    });
    }

}