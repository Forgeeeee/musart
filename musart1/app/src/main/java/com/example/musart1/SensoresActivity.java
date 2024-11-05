package com.example.musart1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SensoresActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensores_view);

        Button  buttonBackToMainActivity = findViewById(R.id.Inicio);
        buttonBackToMainActivity.setOnClickListener(v -> {
            Intent intent = new Intent(SensoresActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });




        Button  buttonToDb = findViewById(R.id.dbbutton);
        buttonToDb.setOnClickListener(v -> {
            Intent intent = new Intent(SensoresActivity.this, DbActivityMain.class);
            startActivity(intent);
            finish();
        });





    }






}