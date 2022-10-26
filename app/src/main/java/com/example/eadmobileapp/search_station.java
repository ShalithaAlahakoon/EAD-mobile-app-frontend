package com.example.eadmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eadmobileapp.api.API;
import com.example.eadmobileapp.api.RetrofitClient;
import com.example.eadmobileapp.models.Station;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class search_station extends AppCompatActivity {


    private Spinner spinner_area ,spinner_station;
    private Button btn_search ;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_from_home_dashboard);

        //set page title
        setTitle("User from home");
        //get spinner form id
         spinner_area = (Spinner) findViewById(R.id.spinner_area);
        // get search button
         btn_search = (Button) findViewById(R.id.button_search);
         //API interface
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
                int length = (int)stationList[0].size();
                length =  ++length;

                //get all station areas to array
                String[] stationAreas = new String[length];
                stationAreas[0] = "Select Area";

                for (int i = 1; i < length ; i++) {
                    stationAreas[i] = stationList[0].get(i-1).getStationArea();
                }

                //convert array to array adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(search_station.this, android.R.layout.simple_spinner_item, stationAreas);
                //convert array adapter to array
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_area.setAdapter(adapter);

                //on select area get station name list
                spinner_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //set text view
                        String selectedArea = parent.getItemAtPosition(position).toString();
                        //get station name list
                        String[] stationNames = new String[stationList[0].size()];
                        int j = 0;
                        for (int i = 0; i < stationList[0].size(); i++) {
                            if (stationList[0].get(i).getStationArea().equals(selectedArea)) {
                                stationNames[j] = stationList[0].get(i).getStationName()  ;
                                j++;
                            }
                        }
                        //remove null values from array
                        String[] stationNames2 = new String[j];
                        for (int i = 0; i < j; i++) {
                            stationNames2[i] = stationNames[i];
                        }

                        //on btn_search click get selected station name and froward to next activity
                        btn_search.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //forward to next activity
                                Intent intent = new Intent(search_station.this, user_from_home_stations_list.class);
                                intent.putExtra("selectedStation",stationNames2);
                                startActivity(intent);
                            }
                        });
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
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}