package com.example.eadmobileapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eadmobileapp.api.API;
import com.example.eadmobileapp.api.RetrofitClient;
import com.example.eadmobileapp.models.Station;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFuels extends AppCompatActivity {
    Button next;
    Spinner station, type;
    private ArrayList<Station> stationList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfuels);

        next = findViewById(R.id.BtnNext);
        station = findViewById(R.id.spinner_station);
        type = findViewById(R.id.spinner_fuel_type);


        //get extras
        Intent intent1 = getIntent();
        String owner_name = intent1.getStringExtra("owner_name");
//        String owner_name = "owner";
        API api = RetrofitClient.getInstance().getApi();
        //get station list
        Call<List<Station>> call = api.getStations();

        call.enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                //create filtered station array list
                ArrayList<Station> filteredStations = new ArrayList<>();
                if (response.isSuccessful()) {
                    stationList = new ArrayList<>(response.body());
                    for (int i = 0; i < stationList.size(); i++) {
                        if (stationList.get(i).getOwner().equals(owner_name)) {
                            System.out.println("owners station name = " + stationList.get(i).getStationName());
                            filteredStations.add(stationList.get(i));
                        } else {
                            System.out.println("removed station name = " + stationList.get(i).getStationName());
                            stationList.remove(i);
                        }
                    }

                    //get station names into string array
                    String[] stationNames = new String[filteredStations.size()];
//                    stationNames[0] = "Select Station";
                    for (int i = 0; i < filteredStations.size(); i++) {
                        stationNames[i] = filteredStations.get(i).getStationName();
                    }

                    //set spinner
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AddFuels.this, android.R.layout.simple_spinner_item, stationNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    station.setAdapter(adapter);

                    //set spinner on item selected listener
                    station.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String SelectedStation = station.getSelectedItem().toString();

                            System.out.println("selected station = " + SelectedStation);

                            Station selectedStation ;

                            for (int j = 0; j < filteredStations.size(); j++) {
                                if (filteredStations.get(j).getStationName().equals(SelectedStation)) {
                                    selectedStation = filteredStations.get(j);
                                    ArrayList<Station.fuelType> fuelTypes = selectedStation.getFuelTypes();
                                    String[] fuelTypeNames = new String[fuelTypes.size()];
                                    for (int k = 0; k < fuelTypes.size(); k++) {
                                        fuelTypeNames[k] = fuelTypes.get(k).getFuelType();
                                    }

                                    //set spinner
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AddFuels.this, android.R.layout.simple_spinner_item, fuelTypeNames);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    type.setAdapter(adapter);

                                    //set spinner on item selected listener
                                    type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            String SelectedFuelType = type.getSelectedItem().toString();
                                            System.out.println("selected fuel type = " + SelectedFuelType);

                                            next.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent intent = new Intent(AddFuels.this, UpdateFuels.class);
                                                    intent.putExtra("owner_name", owner_name);
                                                    intent.putExtra("station_name", SelectedStation);
                                                    intent.putExtra("fuel_type", SelectedFuelType);
                                                    startActivity(intent);
                                                }
                                            });

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                System.out.println("error = " + t.getMessage());
                //print toast message
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}