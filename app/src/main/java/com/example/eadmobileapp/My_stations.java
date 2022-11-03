package com.example.eadmobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.eadmobileapp.api.API;
import com.example.eadmobileapp.api.RetrofitClient;
import com.example.eadmobileapp.models.Station;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class My_stations extends AppCompatActivity {
    private ArrayList<Station> stationList;
    private RecyclerView recyclerView;
    Call<List<Station>> call;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stations);
        recyclerView = findViewById(R.id.recycler_view_2);
        //API interface
        API api = RetrofitClient.getInstance().getApi();
        //station list

        //get extras
        Intent intent1 = getIntent();
        String owner_name = intent1.getStringExtra("owner_name");

        //get station list
        call = api.getStations();

        //fill station list owner name
        call.enqueue(new retrofit2.Callback<List<Station>>() {
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

                    recyclerAdapter adapter = new recyclerAdapter(filteredStations);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                Toast.makeText(My_stations.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    };
    }
