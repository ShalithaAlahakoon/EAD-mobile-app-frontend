package com.example.eadmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eadmobileapp.api.API;
import com.example.eadmobileapp.api.RetrofitClient;
import com.example.eadmobileapp.models.User;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    TextView register;
    Button login;
    EditText txt_username, txt_password;

//    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                        //check if username and password is empty
                        if (username.isEmpty() || password.isEmpty()) {
                            Toast.makeText(Login.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                        }

                         boolean isSuccessful = true;
                        int count = 0;
                        for (int i = 0; i < userList[0].size(); i++) {
                            if (username.equals(userList[0].get(i).getUsername()) && password.equals(userList[0].get(i).getPassword())) {

                                //if user type is owner
                                if (userList[0].get(i).getType().equals("Station Owner")) {
                                    Intent intent = new Intent(Login.this, owner_dashboard.class);
                                    //set owner details as extras
                                    intent.putExtra("owner_name", userList[0].get(i).getUsername());
                                    System.out.println("owner name = " + userList[0].get(i).getUsername());
                                    startActivity(intent);
                                }
                                else if (userList[0].get(i).getType().equals("Customer")) {
                                    Intent intent = new Intent(Login.this, user_dashboard.class);
                                    startActivity(intent);
                                }

                            }
                            else {
                                count++;
                            }
                        }

                        if(count == userList[0].size()){
                            Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        }


                    }
//                    }
                });

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });



    }

    protected void onResume() {
        super.onResume();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, AddFuels.class);
                startActivity(intent);
            }
        });

    }
}