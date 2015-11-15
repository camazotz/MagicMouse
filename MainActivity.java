package com.example.nav.magic_mouse;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.*;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    SensorManager mSensorManager;
    Sensor senAccelerometer, senGyro;

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 500) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                last_x = x;
                last_y = y;
                last_z = z;
                System.out.println("x: " + last_x);// + "\ny: " + y + "\nz: " + z);
            }
        }

        if (mySensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 500) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                last_x = x;
                last_y = y;
                last_z = z;
                System.out.println("Linearx: " + last_x + "\ny: " + y + "\nz: " + z);
            }
        }

        if (mySensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 500) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                last_x = x;
                last_y = y;
                last_z = z;
                System.out.println("Gyrox: " + last_x + "\ny: " + y + "\nz: " + z);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //senAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        senGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        //mSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, senGyro , SensorManager.SENSOR_DELAY_NORMAL);
//        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
//            System.out.println("accelerometer");
//        }
//        if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null){
//            System.out.println("Type gyroscope");
//        }
//        if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null){
//            System.out.println("Linear acc");
//        }
//        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null){
//            System.out.println("Rotation vector");
//        }
//        if (mSensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION) != null){
//            System.out.println("Significant motion");
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
