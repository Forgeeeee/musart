package com.example.musart1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AcelerometroView extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acelerometro_view);

        Button buttonBackToMainActivity = findViewById(R.id.inicio);
        buttonBackToMainActivity.setOnClickListener(v -> {
            Intent intent = new Intent(AcelerometroView.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        imageView = findViewById(R.id.imageView);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Obtener los valores del acelerómetro
            float x = event.values[0]; // Inclinación en el eje X
            float y = event.values[1]; // Inclinación en el eje Y

            // Mover la imagen en función de la inclinación
            float translationX = -x * 50; // Escala el movimiento
            float translationY = y * 50; // Escala el movimiento

            imageView.setTranslationX(translationX);
            imageView.setTranslationY(translationY);
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Innecesario de implementar
    }
}

