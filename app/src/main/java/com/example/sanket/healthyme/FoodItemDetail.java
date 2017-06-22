package com.example.sanket.healthyme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanket.healthyme.data.DatabaseHandler;

public class FoodItemDetail extends AppCompatActivity{

        private TextView foodName, calories, dateTaken, foodTime, foodQty;
        private int foodId;
    private Button deleteButton, continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item_detail);

        final int calBurnt = getIntent().getIntExtra("CalBurnt",0);

        foodName = (TextView) findViewById(R.id.detsFoodName);
        calories = (TextView) findViewById(R.id.detsCaloriesValue);
        dateTaken = (TextView) findViewById(R.id.detsDateText);
        foodTime = (TextView) findViewById(R.id.detsFoodTime);
        foodQty = (TextView) findViewById(R.id.detsfoodQty);
        continueButton = (Button) findViewById(R.id.continueButton);

        //has serializable food object - all details//
        //pass in key "userObj" from CustomListViewAdaptor//
        Food food = (Food) getIntent().getSerializableExtra("userObj");

        foodName.setText(food.getFoodName());
        calories.setText(String.valueOf(food.getCalories()));
        dateTaken.setText(food.getRecordDate());
        foodTime.setText(food.getFoodTime());
        foodQty.setText(String.valueOf(food.getFoodQuantity()));
        deleteButton = (Button) findViewById(R.id.deleteButton);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodItemDetail.this, DisplayFoodActivity.class);
                intent.putExtra("CalBurnt", calBurnt);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(FoodItemDetail.this);
                alert.setTitle("Delete?");
                alert.setMessage("Are you sure you want to delete this item?");
                alert.setNegativeButton("No", null);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DatabaseHandler dba = new DatabaseHandler(getApplicationContext());
                        dba.deleteFood(foodId);

                        Toast.makeText(getApplicationContext(), "Food Item Deleted!", Toast.LENGTH_LONG)
                                .show();

                        Intent i = new Intent(FoodItemDetail.this, DisplayFoodActivity.class);
                        i.putExtra("CalBurnt", calBurnt);
                        startActivity(i);
                        //remove this activity from activity stack//
                        FoodItemDetail.this.finish();
                    }
                });
                alert.show();
            }
        });

        //getting food id so we are able to delete it//
        foodId = food.getFoodId();
    }
}
