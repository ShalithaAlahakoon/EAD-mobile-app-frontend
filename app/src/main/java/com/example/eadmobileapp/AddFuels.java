package com.example.eadmobileapp;

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
    Button update;
    Spinner station, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_queue);

        update = findViewById(R.id.updateBtn);
        station = findViewById(R.id.spinner_station);
        type = findViewById(R.id.spinner_fuel_type);


        API api = RetrofitClient.getInstance().getApi();
        //station list
        final List<Station>[] stationList = new List[]{null};

        //get station list
        Call<List<Station>> call = api.getStations();

        call.enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                stationList[0] = response.body();


                //get station list length
                int length = (int) stationList[0].size();
                length = ++length;

                //get all station areas to array
                String[] stationAreas = new String[length];
                stationAreas[0] = "Select Area";

                for (int i = 1; i < length; i++) {
                    stationAreas[i] = stationList[0].get(i - 1).getStationArea();
                }

                //convert array to array adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddFuels.this, android.R.layout.simple_spinner_item, stationAreas);
                //convert array adapter to array
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                station.setAdapter(adapter);

                //on select area get station name list
                station.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        //set text view
                        String selectedArea = parent.getItemAtPosition(position).toString();


                        //get station name list
                        String[] stationNames = new String[stationList[0].size()+1];
                        stationNames[0] = "Select Station";
                        int j = 1;
                        for (int i = 0; i < stationList[0].size(); i++) {
                            if (stationList[0].get(i).getStationArea().equals(selectedArea)) {
                                stationNames[j] = stationList[0].get(i).getStationName();
                                j++;
                            }
                        }
                        //remove null values from array
                        String[] stationNames2 = new String[j];
                        for (int i = 0; i < j; i++) {
                            stationNames2[i] = stationNames[i];
                        }

                        if (stationNames2.length != 0) {

                            //convert array to array adapter
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddFuels.this, android.R.layout.simple_spinner_item, stationNames2);
                            //convert array adapter to array
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            type.setAdapter(adapter);


                            //Get fuel
                            type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    //set text view
                                    String selectedStation = parent.getItemAtPosition(position).toString();


                                    //get station name list
                                    String[] fuelNames = new String[stationList[0].size()+1];

                                    fuelNames[0] = "Select Fuel";

                                    int j = 1;
                                    for (int i = 0; i < stationList[0].size(); i++) {
                                        if (stationList[0].get(i).getStationName().equals(selectedStation)) {

                                            ArrayList<Station.fuelType> lst = stationList[0].get(i).getFuelTypes();

                                            for (int c = 0; c < lst.size(); c++) {
                                                fuelNames[j] = lst.get(c).fuelType;
                                                j++;
                                            }
                                        }
                                    }
                                    //remove null values from array
                                    String[] fuelNames2 = new String[j];

                                    for (int i = 0; i < j; i++) {
                                        fuelNames2[i] = fuelNames[i];
                                    }


                                    if (fuelNames2.length != 0) {
                                        //convert array to array adapter
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddFuels.this, android.R.layout.simple_spinner_item, fuelNames2);
                                        //convert array adapter to array
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        type.setAdapter(adapter);
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                System.out.println("error = " + t.getMessage());
                //print toast message
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String selectStation = station.getSelectedItem().toString();
                String selectFuel = type.getSelectedItem().toString();

                if (selectStation == "Select Station" || selectFuel == "Select Fuel") {
                    Toast.makeText(AddFuels.this, "Please Select Values", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent(view.getContext(), timer_screen.class);
                    intent.putExtra("station", selectStation);
                    intent.putExtra("fuel", selectFuel);
                    startActivity(intent);
                }
            }
        });
    }
}