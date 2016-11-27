package com.example.oko.lab07;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sManager;
    Sensor sens;
    TextView recordTextView;
    TextView currentTextView;
    Button resetButton;
    double recordX;
    double recordY;
    double recordZ;
    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public final void onSensorChanged(SensorEvent event) {
        double x, y, z;
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];
        currentTextView.setText(Double.toString(x) + ", " + Double.toString(y) + ", " + Double.toString(z));
        if((Math.abs(x) + Math.abs(y) + Math.abs(z)) > (recordX + recordY + recordZ)){
            recordTextView.setText(currentTextView.getText().toString());
            recordX = Math.abs(x);
            recordY = Math.abs(y);
            recordZ = Math.abs(z);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        sManager.registerListener(this, sens, SensorManager.SENSOR_DELAY_NORMAL);
    }
    View.OnClickListener resetButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            recordTextView.setText("0, 0, 0");
            recordX = 0;
            recordY = 0;
            recordZ = 0;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recordX = 0;
        recordY = 0;
        recordZ = 0;
        sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sens = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        recordTextView = (TextView)findViewById(R.id.recordTextView);
        currentTextView = (TextView)findViewById(R.id.currentTextView);
        resetButton = (Button)findViewById(R.id.resetButton);
        resetButton.setOnClickListener(resetButtonClick);
    }
}
