package com.example.musart1;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProximidadActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private View mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proximidad_view);

        Button buttonBackToMainActivity = findViewById(R.id.inicio);
        buttonBackToMainActivity.setOnClickListener(v -> {
            Intent intent = new Intent(ProximidadActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        mainLayout = findViewById(R.id.mainLayout);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            // Obtener la distancia del objeto al sensor
            float distance = event.values[0];
            // Cambiar el color de fondo según la proximidad
            if (distance < 5.0) { // Ajusta este valor según el sensor
                mainLayout.setBackgroundColor(Color.RED); // Color rojo si hay un objeto cerca
            } else {
                mainLayout.setBackgroundColor(Color.GREEN); // Color verde si no hay objeto cerca
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Innecesario de implementar
    }
}

