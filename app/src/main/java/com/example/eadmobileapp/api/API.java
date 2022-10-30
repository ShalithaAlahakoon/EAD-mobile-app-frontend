package com.example.eadmobileapp.api;


import com.example.eadmobileapp.models.Queue;
import com.example.eadmobileapp.models.Station;
import com.example.eadmobileapp.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {

    //stations
    @GET("stations")
    Call<List<Station>> getStations();

    @GET("users")
    Call<List<User>> getUsers();

    @POST("users")
    Call<User> createUser(@Body User user);
    
    @POST("stations")
    Call<Station> createStation(@Body Station station);

    @GET("queues")
    Call<List<Queue>> getQueues();

    @POST("queues/arrived")
    Call<Queue> arrived(@Body Queue queue);

    @POST("queues/exit")
    Call<Queue> exit(@Body Queue queue);
    @PUT("api/stations/2")

        // on below line we are creating a method to put our data.
    Call<Station> updateData(@Body Station station);

}





