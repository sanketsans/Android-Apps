package com.example.sanket.healthyme;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.sanket.healthyme.R.id.textView;

public class HealthyActivity extends Activity implements SensorEventListener, StepListener {
    final Context context = this;
    Button connect, disconnect, continueButton;

    TextView command, data, link;

    ProgressBar progressBar;

    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthy);

        connect = (Button) findViewById(R.id.connect);
        disconnect = (Button) findViewById(R.id.disconnect);
        continueButton = (Button) findViewById(R.id.continueButton);

        command = (TextView) findViewById(R.id.commandRecieved);
        data = (TextView) findViewById(R.id.dataRecieved);
        link = (TextView) findViewById(R.id.linkStatus);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom);
                dialog.setTitle("Calorie Burnt");

                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.text);
                final int calBurnt = 50;
                String total = String.valueOf(calBurnt);
                text.setText(total);
                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //dialog.dismiss();
                        Intent intent1 = new Intent(HealthyActivity.this, DetailActivity.class);
                        intent1.putExtra("CalBurnt", calBurnt);
                        startActivity(intent1);
                    }
                });

                dialog.show();
            }
        });

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(HealthyActivity.this);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                command.setText(" TRUE ");
                link.setText( " TRUE ");
                numSteps = 0;
                sensorManager.registerListener(HealthyActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
            }
        });

        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                command.setText(" FALSE ");
                link.setText(" FALSE ");
                sensorManager.unregisterListener(HealthyActivity.this);
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    sensorEvent.timestamp, sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(HealthyActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.registerListener(HealthyActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.registerListener(HealthyActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        data.setText(TEXT_NUM_STEPS + numSteps);
    }
}
