package com.encircleapp.ensensortest;

import java.util.List;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private SensorManager mSensorManager = null;
    private Sensor mGravitySensor = null;
    private Sensor mAccelSensor = null;
    private TextView mTextView = null;
    private ImageView mImageView = null;
    private SensorEventListener mSensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(final SensorEvent event) {
            if (mTextView != null && mImageView != null) {
                // do stuff
                final String val = String.format(
                        "%s %s %d %f %f %f",
                        event.sensor.getName(),
                        event.sensor.getVendor(),
                        event.sensor.getType(),
                        event.values[0],
                        event.values[1],
                        event.values[2]);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText(val);

                        if (event.values[0] < -4.5) {
                            // left side up
                            mImageView.setImageResource(R.drawable.left);
                        }
                        if (event.values[1] < -4.5) {
                            // down side up
                            mImageView.setImageResource(R.drawable.down);
                        }
                        if (event.values[0] > 4.5) {
                            // right side up
                            mImageView.setImageResource(R.drawable.right);
                        }
                        if (event.values[1] > 4.5) {
                            // up
                            mImageView.setImageResource(R.drawable.up);
                        }
                    }
                });
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView en_tv = (TextView) findViewById(R.id.en_tv);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        String sensor_list = "";
        for (Sensor sensor : deviceSensors) {
            sensor_list += String.format("%s %s %d\n", sensor.getName(), sensor.getVendor(), sensor.getType());
        }
        en_tv.setText(sensor_list);

        mGravitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mAccelSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(
                mSensorEventListener,
                mGravitySensor == null ? mAccelSensor : mGravitySensor,
                SensorManager.SENSOR_DELAY_NORMAL);

        mTextView = (TextView) findViewById(R.id.textView1);
        mImageView = (ImageView) findViewById(R.id.imageView1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
