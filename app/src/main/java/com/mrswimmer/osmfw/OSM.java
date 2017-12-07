package com.mrswimmer.osmfw;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class OSM extends Activity implements SensorEventListener {
    LinearLayout button;
    TextView pulse, text;
    //0 - not fix
    //1 - wait
    //2 - result
    int status=0, P1, P2, zone;
    private SensorManager mSensorManager;
    private Sensor mHeartRateSensor;
    private double point, x = 2.23, y = 0.5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osmt);
        button = findViewById(R.id.osm_but);
        pulse = findViewById(R.id.osm_pulse);
        text = findViewById(R.id.osm_text);
        mSensorManager = ((SensorManager)getSystemService(SENSOR_SERVICE));
        mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (status) {
                    case 0 :
                        status = 1;
                        P1 = Integer.parseInt(pulse.getText().toString());
                        Log.i("osm", "status 0 "+ P1+"");
                        text.setText(R.string.osm_wait);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                P2 = Integer.parseInt(pulse.getText().toString());
                                point = (float) (14.5 - y * ((float) (P1) - 40) / 3.5 - ((float) (P2 - P1)) / x * 0.5);
                                zone = getZone();
                                status = 2;
                                String buf = String.format("%.2f", point);
                                pulse.setText(buf+" б"+"\n"+zone+ "зона");
                                text.setText("Измерить еще раз");
                            }
                        }, 7000);
                        break;
                    case 2 :
                        Log.i("osm", "status 2 ");
                        status = 0;
                        text.setText(R.string.osm_fix);
                }
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_HEART_RATE && (status == 0 || status == 1)) {
            pulse.setText((int) sensorEvent.values[0]+"");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        //Register the listener
        if (mSensorManager != null){
            mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        //Unregister the listener
        if (mSensorManager!=null)
            mSensorManager.unregisterListener(this);
    }
    public int getZone() {
        int n = 0;
        if (point >= 7.5)
            n = 1;
        if (point >= 5 && point < 7.5)
            n = 2;
        if (point >= 2.5 && point < 5)
            n = 3;
        if (point < 2.5)
            n = 4;
        return n;
    }
}
