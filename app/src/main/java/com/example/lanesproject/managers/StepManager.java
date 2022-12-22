package com.example.lanesproject.managers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.lanesproject.callbacks.Callback_steps;

public class StepManager {

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    private Callback_steps cbSteps;

    private long timestamp = 0;
    
    private static StepManager _instance = null;

    private StepManager(Context context, Callback_steps cbSteps) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.cbSteps = cbSteps;
        initEventListener();
    }

    public static synchronized void init(Context context, Callback_steps cbSteps){
        if ( _instance == null ) _instance = new StepManager(context, cbSteps);
    }

    public static synchronized StepManager getInstance(){
        return _instance;
    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];

                calculateStep(x, y);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    private void calculateStep(float x, float y) {
        if (System.currentTimeMillis() - timestamp > 500) {
            System.out.println("y: " + y);
            System.out.println("x: " + x);
            timestamp = System.currentTimeMillis();
            if (x > 6.0) {
                // move right
                if (cbSteps != null)
                    cbSteps.stepX(1);
            }else if ( x < -6.0){
                // move left
                if (cbSteps != null)
                    cbSteps.stepX(-1);
            }
            if (y > 6.0) {
                // go fast forward
                cbSteps.stepY();
            }
        }
    }

    public void start() {
        if ( _instance == null ) return;
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop() {
        if ( _instance == null ) return;
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }
}
