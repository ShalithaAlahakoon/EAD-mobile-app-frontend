package com.example.eadmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    Button signUp;
    TextView logIn;
    Spinner spinner2;
    String[] user = {"Station Owner","Customer"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signUp = findViewById(R.id.btn_signup);
        logIn = findViewById(R.id.log);

        spinner2=findViewById(R.id.spinner_user_type);

        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(Register.this, R.layout.item_file,user);
        adapter2.setDropDownViewResource(R.layout.item_file);
        spinner2.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                Toast.makeText(Register.this, value, Toast.LENGTH_SHORT).show();
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

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, UpdateFuels.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Registered Successfully...!", Toast.LENGTH_LONG).show();
            }
        });
    }
}