package com.example.eadmobileapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eadmobileapp.api.API;
import com.example.eadmobileapp.api.RetrofitClient;
import com.example.eadmobileapp.models.Station;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddStation extends AppCompatActivity {
    EditText sName, sDistrict, sAddress, sOwner;
    Button addStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_station);

        addStation = findViewById(R.id.button5);

        sName = findViewById(R.id.stName);
        sDistrict = findViewById(R.id.stDistrict);
        sAddress = findViewById(R.id.stAddress);
        sOwner = findViewById(R.id.stOwner);

        addStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (sName.getText().toString().isEmpty() && sDistrict.getText().toString().isEmpty() && sAddress.getText().toString().isEmpty() && sOwner.getText().toString().isEmpty()) {
                    Toast.makeText(AddStation.this, "Please enter all the values", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to post the data and passing our name and job.
                postData(sName.getText().toString(), sDistrict.getText().toString(), sAddress.getText().toString(), sOwner.getText().toString());
            }
        });
    }

    private void postData(String stationName, String stationArea, String stationAddress, String owner) {


        // on below line we are creating a retrofit
        // builder and passing our base url
        RetrofitClient retrofitClient = new RetrofitClient().Builder()
                .baseUrl("https://reqres.in/api/")
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
        // below line is to create an instance for our retrofit api class.
        API api = retrofitClient.create(API.class);

        // passing data from our text fields to our modal class.
        Station station = new Station(stationName, stationArea, stationAddress, owner);

        // calling a method to create a post and passing our modal class.
        Call<Station> call = api.createStation(station);

        // on below line we are executing our method.
        call.enqueue(new Callback<Station>() {
            @Override
            public void onResponse(Call<Station> call, Response<Station> response) {
                // this method is called when we get response from our api.
                Toast.makeText(AddStation.this, "Data added to API", Toast.LENGTH_SHORT).show();

                // on below line we are setting empty text
                // to our both edit text.
                sName.setText("");
                sDistrict.setText("");
                sAddress.setText("");
                sOwner.setText("");

                // we are getting response from our body
                // and passing it to our modal class.
                Station responseFromAPI = response.body();

                // on below line we are getting our data from modal class and adding it to our string.
                String responseString = "Response Code : " + response.code() + "\nName : " + responseFromAPI.getStationName() + "\n" + "Area : " + responseFromAPI.getStationArea() + "\n" + "Address : " + responseFromAPI.getStationAddress() + "\n" + "Owner : " + responseFromAPI.getOwner();

                // below line we are setting our
                // string to our text view.
                responseTV.setText(responseString);
            }

            @Override
            public void onFailure(Call<Station> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                responseTV.setText("Error found is : " + t.getMessage());
            }
        });
    }
}