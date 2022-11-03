package com.example.eadmobileapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eadmobileapp.api.API;
import com.example.eadmobileapp.api.RetrofitClient;
import com.example.eadmobileapp.models.Queue;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class timer_screen extends AppCompatActivity {

    Button fuelBefore, fuelAfter;
    Chronometer timers;
    boolean isRunning = false;

    String area, station, fuel;

    //API interface
    API api = RetrofitClient.getInstance().getApi();

    long saveTime ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_screen);

        Intent intent = getIntent();
        area = intent.getStringExtra("area");
        station = intent.getStringExtra("station");
        fuel = intent.getStringExtra("fuel");

        fuelAfter = findViewById(R.id.btnAfter);
        fuelBefore = findViewById(R.id.btnBefore);
        timers = findViewById(R.id.textView3);

        if(!isRunning){
            isRunning = true;
            timers.start();
        }

        fuelBefore.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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
                Log.i("time", Long.toString(saveTime));
                long seconds = Duration.ofMillis(saveTime).getSeconds();
                long HH = seconds / 3600;
                long MM = (seconds % 3600) / 60;
                long SS = seconds % 60;
                String timeElapsed = String.format("%02d:%02d:%02d", HH, MM, SS);
                getDataSave(area, station, fuel,currentDate+" "+currentTime,timeElapsed+"",false);

                Toast toast = Toast.makeText(getApplicationContext(), "You are exited before pump fuel", Toast.LENGTH_SHORT);
                toast.show();
                //navigate back to user dashboard
                Intent intent = new Intent(timer_screen.this, user_dashboard.class);
                startActivity(intent);

            }
        });

        fuelAfter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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
                long seconds = Duration.ofMillis(saveTime).getSeconds();
                long HH = seconds / 3600;
                long MM = (seconds % 3600) / 60;
                long SS = seconds % 60;
                String timeElapsed = String.format("%02d:%02d:%02d", HH, MM, SS);
                getDataSave(area, station, fuel,currentDate+" "+currentTime,timeElapsed+"",true);



                Toast toast = Toast.makeText(getApplicationContext(), "You are exited after pump fuel", Toast.LENGTH_SHORT);
                toast.show();
                //navigate back to user dashboard
                Intent intent = new Intent(timer_screen.this, user_dashboard.class);
                startActivity(intent);

            }
        });

    }

    public void getDataSave(String area, String station, String fuel, String arrvie, String duration, Boolean fuelSt){
        try{
            RequestQueue queue = Volley.newRequestQueue(this);
            String URL = "http://192.168.2.1/filling/add";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("area",area);
            jsonObject.put("station",station);
            jsonObject.put("fuel",fuel);
            jsonObject.put("arrivel",arrvie);
            jsonObject.put("fillSt",fuelSt);
            jsonObject.put("wating",duration);

            //reduce count of queue
            Call queueExit = api.exit(new Queue(station,fuel,null));
            queueExit.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, retrofit2.Response response) {
                    System.out.println("response = " + response.body());
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    System.out.println("error = " + t.getMessage());
                }
            });


            final String reqString = jsonObject.toString();
            Log.i("reqL", reqString);

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
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return reqString == null ? null : reqString.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", reqString, "utf-8");
                        return null;
                    }
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