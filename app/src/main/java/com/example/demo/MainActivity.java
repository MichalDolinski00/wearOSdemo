package com.example.demo;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.databinding.ActivityMainBinding;

public class MainActivity extends Activity {

    private TextView mTextView;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;

    private ActivityMainBinding binding;


    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;

    private SensorEventListener gyroscopeEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //mTextView = binding.text;
        tv2 = findViewById(R.id.textView2);
        tv3 = findViewById(R.id.textView3);
        tv4 = findViewById(R.id.textView4);




        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (gyroscopeSensor == null) {
            Toast.makeText(this, "The device has no Gyroscope!", Toast.LENGTH_SHORT).show();
            finish();
        }



        gyroscopeEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                //z
                if (sensorEvent.values[2] > 0.5f){
                    tv2.setText("turn left");
                } else if (sensorEvent.values[2] < -0.5f){
                    tv2.setText("turn right");
                }
                //y
                if (sensorEvent.values[1] > 0.5f){
                    tv3.setText("roll right");
                } else if (sensorEvent.values[1] < -0.5f){
                    tv3.setText("roll left");
                }
                //x
                if (sensorEvent.values[0] > 0.5f){
                    tv4.setText("pith down");
                } else if (sensorEvent.values[0] < -0.5f){
                    tv4.setText("pith up");
                }


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };


    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroscopeEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gyroscopeEventListener);
    }
}