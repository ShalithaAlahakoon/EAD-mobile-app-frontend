package com.example.eadmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eadmobileapp.api.API;
import com.example.eadmobileapp.api.RetrofitClient;
import com.example.eadmobileapp.models.Station;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class join_queue extends AppCompatActivity {

    Button scanQR, arriveQueue;
    Spinner fuel_dropDown, station_dropDown, area_dropDown;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://192.168.42.1:3000/stations";
    private JSONArray jsonArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_queue);

        scanQR = findViewById(R.id.qrBtn);
        arriveQueue = findViewById(R.id.arriveBtn);
        fuel_dropDown = findViewById(R.id.spinnerFuel);
        station_dropDown = findViewById(R.id.spinnerStation);
        area_dropDown = findViewById(R.id.spinnerArea);


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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(join_queue.this, android.R.layout.simple_spinner_item, stationAreas);
                //convert array adapter to array
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                area_dropDown.setAdapter(adapter);

                //on select area get station name list
                area_dropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        //set text view
                        String selectedArea = parent.getItemAtPosition(position).toString();

                        if (selectedArea == "Select Area") {
                            station_dropDown.setEnabled(false);
                            fuel_dropDown.setEnabled(false);
                        } else {
                            station_dropDown.setEnabled(true);
                            fuel_dropDown.setEnabled(true);
                        }

                        //get station name list
                        String[] stationNames = new String[stationList[0].size()];
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
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(join_queue.this, android.R.layout.simple_spinner_item, stationNames2);
                            //convert array adapter to array
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            station_dropDown.setAdapter(adapter);


                            //Get fuel
                            station_dropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    //set text view
                                    String selectedStation = parent.getItemAtPosition(position).toString();

                                    if (selectedStation == "Select Station") {
                                        fuel_dropDown.setEnabled(false);
                                    } else {
                                        fuel_dropDown.setEnabled(true);
                                    }


                                    //get station name list
                                    String[] fuelNames = new String[stationList[0].size()];

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
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(join_queue.this, android.R.layout.simple_spinner_item, fuelNames2);
                                        //convert array adapter to array
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        fuel_dropDown.setAdapter(adapter);
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

                if (selectArea == "Select Area" || selectStation == "Select Station" || selectFuel == "Select Fuel") {
                    Toast.makeText(join_queue.this, "Please Select Values", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent(view.getContext(), timer_screen.class);
                    intent.putExtra("area", selectArea);
                    intent.putExtra("station", selectStation);
                    intent.putExtra("fuel", selectFuel);
                    startActivity(intent);
                }

            }
        });
    }

}