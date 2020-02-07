package com.firni.zado;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements SensorEventListener {

    private Felulet FView;
    private int interval = 16;

    private SensorManager sManager;
    private Sensor mSensorOrientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorOrientation = sManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sManager.registerListener(this, mSensorOrientation, SensorManager.SENSOR_DELAY_UI);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        FView = findViewById(R.id.FView);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        FView.invalidate();
                        if (FView.isGameOver()) {
                            cancel();
                            Intent IttGameOver = new Intent();
                            IttGameOver.setClass(MainActivity.this, JatekVege.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("SCORE", FView.getScore());
                            IttGameOver.putExtras(bundle);
                            startActivity(IttGameOver);
                            MainActivity.this.onDestroy();
                        }
                    }
                });
            }
        };


        timer.schedule(task, 0, interval);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        FView.setDoodleVx((float) (Math.round(event.values[2] * 100)) / 100);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
