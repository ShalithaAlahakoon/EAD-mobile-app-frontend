package com.example.eadmobileapp.api;


import com.example.eadmobileapp.models.Station;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    //stations
    @GET("stations")
    Call<List<Station>> getStations();

}





