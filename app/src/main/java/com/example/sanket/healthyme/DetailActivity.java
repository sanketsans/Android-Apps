package com.example.sanket.healthyme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    EditText weight, height, age;
    CheckBox male, female, external, sensor;
     Button calcualte;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final int calBurnt = getIntent().getIntExtra("CalBurnt",0);

        weight = (EditText) findViewById(R.id.weight);

        height = (EditText) findViewById(R.id.height);

        age = (EditText) findViewById(R.id.age);


        male = (CheckBox) findViewById(R.id.maleCheckbox);
        female = (CheckBox) findViewById(R.id.femaleCheckbox);
        external = (CheckBox) findViewById(R.id.externalCheckbox);
        sensor = (CheckBox) findViewById(R.id.mobileSensorCheckbox);

        calcualte = (Button) findViewById(R.id.calculateCalorie);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        /*

Women: BMR = 655 + (9.6 x weight in kg) + (1.8 x height in cm) - (4.7 x age in years)

Men: BMR = 66 + (13.7 x weight in kg) + (5 x height in cm) - (6.8 x age in years)


         */

        calcualte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wStr = weight.getText().toString();
                String hStr = height.getText().toString();
                String aStr = age.getText().toString();
                double mBMR, wBMR;
                if(wStr.isEmpty() || hStr.isEmpty() || aStr.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter weight, height and age", Toast.LENGTH_SHORT).show();
                }
                else{
                     int weightInt = Integer.parseInt((weight.getText().toString()));
                     int heightInt = Integer.parseInt((height.getText().toString()));
                     int ageInt = Integer.parseInt((age.getText().toString()));
                    if(male.isChecked()){
                        mBMR = 655 + (9.6 * weightInt) + (1.8 * heightInt) - (4.7 * ageInt);
                        Toast.makeText(getApplicationContext(), "BMR Index: "+ mBMR, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailActivity.this, CalorieCalculated.class);
                        intent.putExtra("BMRValue",mBMR);
                        intent.putExtra("CalBurnt",calBurnt);
                        startActivity(intent);
                    }
                    else if(female.isChecked()){
                        wBMR = 66 + (13.7 * weightInt) + (5 * heightInt) - (6.8 * ageInt);
                        Toast.makeText(getApplicationContext(), "BMR Index: "+ wBMR, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailActivity.this, CalorieCalculated.class);
                        intent.putExtra("BMRValue",wBMR);
                        intent.putExtra("CalBurnt",calBurnt);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Select a Gender", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });




    }
}
