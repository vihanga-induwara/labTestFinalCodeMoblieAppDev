package com.s23010355.induwara; // Replace with your package

import android.media.MediaPlayer;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor temperatureSensor;
    private TextView tempText;
    private TextView statusText;
    private MediaPlayer mediaPlayer;
    private static final float THRESHOLD = 55.0f; // Last two digits of SID: 55

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        // Initialize UI elements
        tempText = findViewById(R.id.tempText);
        statusText = findViewById(R.id.statusText);

        // Initialize SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        // Check if the sensor is available
        if (temperatureSensor == null) {
            statusText.setText("Temperature sensor not available!");
        }

        // Initialize MediaPlayer for audio
        mediaPlayer = MediaPlayer.create(this, R.raw.alert);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register listener for the temperature sensor
        if (temperatureSensor != null) {
            sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the listener to save resources
        sensorManager.unregisterListener(this);
        // Stop and reset the media player if playing
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            float temperature = event.values[0]; // Temperature in Celsius
            tempText.setText(String.format("Temperature: %.1f °C", temperature));

            if (temperature > THRESHOLD) {
                statusText.setText("Status: Alert - Temperature High!");
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
            } else {
                statusText.setText("Status: Normal");
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used in this example
    }
}
