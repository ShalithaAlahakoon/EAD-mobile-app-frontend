package com.example.eadmobileapp;

import android.os.Bundle;
import android.widget.Toast;

import com.example.eadmobileapp.api.API;
import com.example.eadmobileapp.api.RetrofitClient;
import com.example.eadmobileapp.models.Station;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class user_from_home_stations_list extends AppCompatActivity {

    private ArrayList<Station> stationList;
    private RecyclerView recyclerView;
    Call<List<Station>> call;

    //    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_from_home_stations_list);
        recyclerView = findViewById(R.id.recycler_view_2);
        //API interface
        API api = RetrofitClient.getInstance().getApi();
        //station list

        //get station list
        call = api.getStations();
        //set station list
        call.enqueue(new retrofit2.Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, retrofit2.Response<List<Station>> response) {
                stationList = (ArrayList<Station>) response.body();
                setStationInfo();
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                System.out.println("t = " + t);
            }
        });

    }

    private void setAdapter() {
        recyclerAdapter adapter = new recyclerAdapter(stationList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setStationInfo() {
        //get data from intent
        String [] selectedStations = getIntent().getStringArrayExtra("selectedStation");
        //create temp list
        ArrayList<Station> temp = new ArrayList<>();
//        loop through the array and add to the list
        for (int i = 0; i < selectedStations.length; i++) {
            Station station = getStationInfo(selectedStations[i]);
            if(station != null) {
                temp.add(new Station(station.getStationName(), station.getStationAddress(), station.getStationArea(),station.getOwner(),station.isOpenClose(),station.getFuelTypes()));
            }else{
                //toast
                Toast.makeText(getApplicationContext(),"station is null",Toast.LENGTH_LONG).show();
            }

        }
        stationList = temp;

    }

    private Station getStationInfo(String s) {
        if (stationList != null) {
            for (int i = 0; i < stationList.size(); i++) {
                if (stationList.get(i).getStationName().equals(s)) {
                    return stationList.get(i);
                }
            }
        } else {
            //toast
            Toast.makeText(getApplicationContext(), "stationList is null", Toast.LENGTH_LONG).show();
        }

        return null;
    }
}