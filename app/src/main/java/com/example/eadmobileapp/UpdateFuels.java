package com.example.eadmobileapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eadmobileapp.models.Station;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UpdateFuels extends AppCompatActivity {

    String url = "http://192.168.42.1:3000/stations";

    // creating our variables for our views such
    // as text view, button and progress
    // bar and response text view.
    private EditText fuelTime, fuelLitres;
    private Button updateBtn;
    private ToggleButton finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatefuels);

        // initializing our views with their ids.
        fuelTime = findViewById(R.id.etTime2);
        fuelLitres = findViewById(R.id.etLitres2);
        updateBtn = findViewById(R.id.button);
        finish = findViewById(R.id.toggleButton);

        // adding on click listener for our button.
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking if the edit text is empty or not.
                if (TextUtils.isEmpty(fuelTime.getText().toString()) && TextUtils.isEmpty(fuelLitres.getText().toString())) {

                    // displaying a toast message if the edit text is empty.
                    Toast.makeText(UpdateFuels.this, "Please enter your data..", Toast.LENGTH_SHORT).show();
                    return;
                }

                // calling a method to update data in our API.
                callPUTDataMethod(fuelTime.getText().toString(), fuelLitres.getText().toString());
            }
        });
    }

    private void callPUTDataMethod(String nextArrival, String litres) {



        // on below line we are creating a retrofit
        // builder and passing our base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)

                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())

                // at last we are building our retrofit builder.
                .build();

        // below the line is to create an instance for our retrofit api class.
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        // passing data from our text fields to our modal class.
        Station station = new Station(nextArrival, litres);

        // calling a method to create an update and passing our modal class.
        Call<Station> call = retrofitAPI.updateData(station);

        // on below line we are executing our method.
        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                // this method is called when we get response from our api.
                Toast.makeText(MainActivity.this, "Data updated to API", Toast.LENGTH_SHORT).show();

                // on below line we are setting empty
                // text to our both edit text.
                jobEdt.setText("");
                userNameEdt.setText("");

                // we are getting a response from our body and
                // passing it to our modal class.
                DataModal responseFromAPI = response.body();

                // on below line we are getting our data from modal class
                // and adding it to our string.
                String responseString = "Response Code : " + response.code() + "\nName : " + responseFromAPI.getName() + "\n" + "Job : " + responseFromAPI.getJob();

                // below line we are setting our string to our text view.
                responseTV.setText(responseString);
            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {

                // setting text to our text view when
                // we get error response from API.
                responseTV.setText("Error found is : " + t.getMessage());
            }
        });
    }
}
