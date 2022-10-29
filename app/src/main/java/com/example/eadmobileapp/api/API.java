package com.example.eadmobileapp.api;


import com.example.eadmobileapp.models.Queue;
import com.example.eadmobileapp.models.Station;
import com.example.eadmobileapp.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {

    //stations
    @GET("stations")
    Call<List<Station>> getStations();

    @GET("users")
    Call<List<User>> getUsers();

    @GET("queues/{station}")
    Call <List<Queue>> getQueue(@Path("station") String station);

    @POST("users")
    Call<User> createUser(@Body User user);
    
    @POST("stations")
    Call<Station> createStation(@Body Station station);

}





