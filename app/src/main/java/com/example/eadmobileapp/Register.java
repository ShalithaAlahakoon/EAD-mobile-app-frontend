package com.example.eadmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eadmobileapp.api.API;
import com.example.eadmobileapp.api.RetrofitClient;
import com.example.eadmobileapp.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    Button signUp;
    TextView logIn;
    Spinner type_spinner;
    String[] user = {"Station Owner","Customer"};
    EditText txt_email, txt_username, txt_nic, txt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txt_email = findViewById(R.id.txt_register_email);
        txt_username = findViewById(R.id.txt_register_username);
        txt_nic = findViewById(R.id.txt_register_nic);
        txt_password = findViewById(R.id.txt_register_password);
        signUp = findViewById(R.id.btn_signup);
        logIn = findViewById(R.id.log);
        type_spinner =findViewById(R.id.spinner_user_type);
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(Register.this, R.layout.item_file,user);
        adapter2.setDropDownViewResource(R.layout.item_file);
        type_spinner.setAdapter(adapter2);

        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = parent.getItemAtPosition(position).toString();
                String email = txt_email.getText().toString();
                String username =  txt_username.getText().toString();
                String password = txt_password.getText().toString();
                String nic = txt_nic.getText().toString();

                signUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //check if all fields are filled
                        if (email.isEmpty() || username.isEmpty() || password.isEmpty() || nic.isEmpty()) {
                            Toast.makeText(Register.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                        } else {
                            //API interface
                            API api = RetrofitClient.getInstance().getApi();
                            //create user
                            User user = new User(username,password,email,nic,type);
                            Call<User> call = api.createUser(user);

                            call.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(Register.this, "User created successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Register.this, Login.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(Register.this, "User creation failed", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    Toast.makeText(Register.this, "User creation failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    protected void onResume() {
        super.onResume();

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

    }
}