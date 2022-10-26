package com.example.eadmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class timer_screen extends AppCompatActivity {

    Button fuelBefore, fuelAfter;
    Chronometer timers;
    boolean isRunning = false;

    long saveTime ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_screen);

        fuelAfter = findViewById(R.id.btnAfter);
        fuelBefore = findViewById(R.id.btnBefore);
        timers = findViewById(R.id.textView3);

        if(!isRunning){
            isRunning = true;
            timers.start();
        }


        fuelBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calling api and send data
                // ontime
                // duration
                // fuel fill or not
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                timers.stop();
                saveTime = SystemClock.elapsedRealtime() - timers.getBase();
                int second = (int) (saveTime/1000 % 60);
                getDataSave(currentDate+" "+currentTime,second+"",false);
            }
        });

        fuelAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calling api and send data
                // ontime
                // duration
                // fuel fill or not
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                timers.stop();
                saveTime = SystemClock.elapsedRealtime() - timers.getBase();
                int second = (int) (saveTime/1000 % 60);
                getDataSave(currentDate+" "+currentTime,second+"",true);
            }
        });

    }

    public void getDataSave(String arrvie, String duration, Boolean fuelSt){
        try{
            RequestQueue queue = Volley.newRequestQueue(this);
            String URL = "http://localhost:3000/filling/add";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("arrivel",arrvie);
            jsonObject.put("fillSt",fuelSt);
            jsonObject.put("wating",duration);

            final String reqString = jsonObject.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    Toast.makeText(getApplicationContext(),"SuccessFully Added Filling Info",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(),join_queue.class);
                    startActivity(i);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }){
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            queue.add(stringRequest);

        }catch (Error e){
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}