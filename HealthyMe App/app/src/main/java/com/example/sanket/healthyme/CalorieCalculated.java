package com.example.sanket.healthyme;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CalorieCalculated extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    final Context context = this;
    TextView  bmrRatio;

    Button eat, back;

    String item, calNeedStr;
    double bmr;
    int calNeed;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_calculated);
        final Intent intent = getIntent();
        bmr = intent.getDoubleExtra("BMRValue", 0);
        bmrRatio = (TextView) findViewById(R.id.bmr);
        String bmrRatioVal = String.valueOf(bmr);
        bmrRatio.setText("BMR Value: "+ bmrRatioVal);
        Spinner spinner = (Spinner) findViewById(R.id.chooseFit);

        final int calBurnt = getIntent().getIntExtra("CalBurnt",0);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        List categories = new ArrayList();
        categories.add("Little or no Exercise");
        categories.add("Light exercise(1-3 days/week)");
        categories.add("Moderate exercise(3-5 days/week)");
        categories.add("Hard exercise(6-7 days/week)");
        categories.add("Sports or 2x training");

        // Creating adapter for spinner
        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        eat = (Button) findViewById(R.id.eatButton);

        eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.equals("Little or no Exercise")){
                    calNeed = (int) (bmr * 1.2);
                    calNeedStr = String.valueOf(calNeed);
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.custom);
                    dialog.setTitle("Daily Calorie Requirement...");

                    // set the custom dialog components - text, image and button
                    TextView text = (TextView) dialog.findViewById(R.id.text);
                    text.setText(calNeedStr);
                    ImageView image = (ImageView) dialog.findViewById(R.id.image);
                    Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //dialog.dismiss();
                            Intent intent1 = new Intent(CalorieCalculated.this, selectFood.class);
                            intent1.putExtra("CalBurnt", calBurnt);
                            startActivity(intent1);
                        }
                    });

                    dialog.show();
                }
                else if(item.equals("Light exercise(1-3 days/week)")){
                    calNeed = (int) (bmr * 1.375);
                    calNeedStr = String.valueOf(calNeed);
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.custom);
                    dialog.setTitle("Daily Calorie Requirement...");

                    // set the custom dialog components - text, image and button
                    TextView text = (TextView) dialog.findViewById(R.id.text);
                    text.setText(calNeedStr);
                    ImageView image = (ImageView) dialog.findViewById(R.id.image);
                    Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //dialog.dismiss();
                            Intent intent1 = new Intent(CalorieCalculated.this, selectFood.class);
                            intent1.putExtra("CalBurnt", calBurnt);
                            startActivity(intent1);
                        }
                    });

                    dialog.show();
                }
                else if(item.equals("Moderate exercise(3-5 days/week)")){
                    calNeed = (int) (bmr * 1.55);
                    calNeedStr = String.valueOf(calNeed);
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.custom);
                    dialog.setTitle("Daily Calorie Requirement...");

                    // set the custom dialog components - text, image and button
                    TextView text = (TextView) dialog.findViewById(R.id.text);
                    text.setText(calNeedStr);
                    ImageView image = (ImageView) dialog.findViewById(R.id.image);
                    Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //dialog.dismiss();
                            Intent intent1 = new Intent(CalorieCalculated.this, selectFood.class);
                            intent1.putExtra("CalBurnt", calBurnt);
                            startActivity(intent1);
                        }
                    });

                    dialog.show();
                }
                else if(item.equals("Hard exercise(6-7 days/week)")){
                    calNeed = (int) (bmr * 1.725);
                    calNeedStr = String.valueOf(calNeed);
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.custom);
                    dialog.setTitle("Daily Calorie Requirement...");

                    // set the custom dialog components - text, image and button
                    TextView text = (TextView) dialog.findViewById(R.id.text);
                    text.setText(calNeedStr);
                    ImageView image = (ImageView) dialog.findViewById(R.id.image);
                    Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //dialog.dismiss();
                            Intent intent1 = new Intent(CalorieCalculated.this, selectFood.class);
                            intent1.putExtra("CalBurnt", calBurnt);
                            startActivity(intent1);
                        }
                    });

                    dialog.show();
                }
                else if(item.equals("Sports or 2x training")){
                    calNeed = (int) (bmr * 1.9);
                    calNeedStr = String.valueOf(calNeed);
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.custom);
                    dialog.setTitle("Daily Calorie Requirement...");

                    // set the custom dialog components - text, image and button
                    TextView text = (TextView) dialog.findViewById(R.id.text);
                    text.setText(calNeedStr);
                    ImageView image = (ImageView) dialog.findViewById(R.id.image);
                    Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //dialog.dismiss();
                            Intent intent1 = new Intent(CalorieCalculated.this, selectFood.class);
                            intent1.putExtra("CalBurnt", calBurnt);
                            startActivity(intent1);
                        }
                    });

                    dialog.show();
                }
            }
        });
        back = (Button) findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalorieCalculated.this, DetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
         item = adapterView.getItemAtPosition(i).toString();


        // Showing selected spinner item
        //Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
