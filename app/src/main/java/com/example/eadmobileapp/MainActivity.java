package com.example.eadmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eadmobileapp.api.API;
import com.example.eadmobileapp.api.RetrofitClient;
import com.example.eadmobileapp.models.Station;
import com.example.eadmobileapp.models.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView register;
    Button login;
    EditText txt_username, txt_password;

//    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = findViewById(R.id.textView4);
        login = findViewById(R.id.login);
        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_password = (EditText) findViewById(R.id.txt_password);

        //user list
         List<User>[] userList = new List[]{null};
        //API interface
        API api = RetrofitClient.getInstance().getApi();
        //get station list
        Call<List<User>> call = api.getUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                System.out.println("user name = " + response.body().get(0).getUsername());
                userList[0] = response.body();






                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String username = txt_username.getText().toString();
                        System.out.println("username = " + username);
                        String password = txt_password.getText().toString();
                        System.out.println("password = " + password);
                        boolean isSuccessful = false;
                        for (int i = 0; i < userList[0].size(); i++) {
                            if (username.equals(userList[0].get(i).getUsername()) && password.equals(userList[0].get(i).getPassword())) {
                                isSuccessful = true;
                                //if user type is owner
                                if (userList[0].get(i).getType().equals("owner")) {
                                    Intent intent = new Intent(MainActivity.this, owner_dashboard.class);
                                    startActivity(intent);
                                }else{
                                    Intent intent = new Intent(MainActivity.this, user_dashboard.class);
                                    startActivity(intent);
                                }

                            } else {
                                isSuccessful = false;
                            }
                        }
                        if (isSuccessful == false) {
                            Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        }

                    }




//                        Intent intent = new Intent(MainActivity.this, search_station.class);
//                        startActivity(intent);
//                        Toast.makeText(getApplicationContext(), "Logged in Successfully...!", Toast.LENGTH_LONG).show();
//                    }
                });

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });



    }

    protected void onResume() {
        super.onResume();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

    }
}