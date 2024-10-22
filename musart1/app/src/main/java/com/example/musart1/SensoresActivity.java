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

public class SensoresActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor gyroscope;
    private TextView textView;

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


        Button  buttonToSensoresActivity = findViewById(R.id.Acelerometro);
        buttonToSensoresActivity.setOnClickListener(v -> {
            Intent intent = new Intent(SensoresActivity.this, AcelerometroView.class);
            startActivity(intent);
            finish();
        });

        Button  buttonToProximidadActivity = findViewById(R.id.proximidadbtn);
        buttonToProximidadActivity.setOnClickListener(v -> {
            Intent intent = new Intent(SensoresActivity.this, ProximidadActivity.class);
            startActivity(intent);
            finish();
        });

        Button  buttonToDb = findViewById(R.id.dbbutton);
        buttonToDb.setOnClickListener(v -> {
            Intent intent = new Intent(SensoresActivity.this, DbActivityMain.class);
            startActivity(intent);
            finish();
        });

        textView = findViewById(R.id.gyroscopeTextView);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);




    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Aquí puedes acceder a los datos del giroscopio
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float x = event.values[0]; // Rotación alrededor del eje X
            float y = event.values[1]; // Rotación alrededor del eje Y
            float z = event.values[2]; // Rotación alrededor del eje Z
            textView.setText("Giroscopio:\nX: " + x + "\nY: " + y + "\nZ: " + z);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Innecesario de implementar
    }
}