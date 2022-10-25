package com.example.eadmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    Button add;
    EditText name, type, time, litres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        add = findViewById(R.id.button2);
    }

    public void addFuels(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        name = findViewById(R.id.etName);
        type = findViewById(R.id.etType);
        time = findViewById(R.id.etTime);
        litres = findViewById(R.id.etLitres);

        String n1 = name.getText().toString();
        String n2 = type.getText().toString();
        String n3 = time.getText().toString();
        String n4 = litres.getText().toString();

        intent.putExtra("EXTRA_MESSAGE1",n1);
        intent.putExtra("EXTRA_MESSAGE2",n2);
        intent.putExtra("EXTRA_MESSAGE3",n3);
        intent.putExtra("EXTRA_MESSAGE4",n4);

        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Data added Successfully..!", Toast.LENGTH_LONG).show();
    }
}