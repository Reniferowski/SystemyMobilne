package com.example.sensorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SensorDetailsActivity extends AppCompatActivity implements SensorEventListener {
    public static final String SENSOR_TYPE = "Sensor Type";
    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView sensorDetailNameTextView;
    private TextView sensorDetailValueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_details);

        sensorDetailNameTextView = findViewById(R.id.sensor_detail_name);
        sensorDetailValueTextView = findViewById(R.id.sensor_detail_value);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(getIntent().getIntExtra(SENSOR_TYPE, Sensor.TYPE_LIGHT));

        if(sensor == null)
            sensorDetailNameTextView.setText(R.string.missing_sensor);
        else
            sensorDetailNameTextView.setText(sensor.getName());

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(sensor != null){
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];

        switch (sensorType){
            case Sensor.TYPE_LIGHT:
                sensorDetailValueTextView.setText(getResources().getString(R.string.sensor_detail_value_light, currentValue));
                break;
            case Sensor.TYPE_GYROSCOPE:
                sensorDetailValueTextView.setText(getResources().getString(R.string.sensor_detail_value_gyro, sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]));
                break;
            default:
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Log.d("onAccuracyChangedMethod", "WYWOŁANO");
    }
}