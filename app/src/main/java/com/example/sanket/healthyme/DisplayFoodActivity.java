package com.example.sanket.healthyme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sanket.healthyme.data.CustomListViewAdaptor;
import com.example.sanket.healthyme.data.DatabaseHandler;
import com.example.sanket.healthyme.util.Utils;

import java.util.ArrayList;

public class DisplayFoodActivity extends AppCompatActivity {
    private DatabaseHandler dba;
    private ArrayList<Food> dbFoods = new ArrayList<>();
    private CustomListViewAdaptor foodAdapter;
    private ListView listView;

    private Food myFood;
    private TextView totalCals, totalFoods, caloBurn;
    Button addMoreFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_food);

        listView = (ListView) findViewById(R.id.list_breakfast);
        totalCals = (TextView) findViewById(R.id.totalAmountTextView);
        totalFoods = (TextView) findViewById(R.id.totalItemsTextView);
        addMoreFood = (Button) findViewById(R.id.addFood);
        caloBurn = (TextView) findViewById(R.id.bmr);
        final int calBurnt = getIntent().getIntExtra("CalBurnt",0);
        String calo = String.valueOf(calBurnt);
        caloBurn.setText("Calories Burnt Today: "+ calo);
        addMoreFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayFoodActivity.this, selectFood.class);
                intent.putExtra("CalBurnt", calBurnt);
                startActivity(intent);
            }
        });

        refreshData();
    }
    private void refreshData() {
        //clear ArrayList//
        dbFoods.clear();

        dba = new DatabaseHandler(getApplicationContext());

        ArrayList<Food> foodsFromDB = dba.getFoods();

        int calsValue = dba.totalCalories();
        int totalItems = dba.getTotalItems();

        //formatting the numbers//
        String formattedValue = Utils.formatNumber(calsValue);
        String formattedItems = Utils.formatNumber(totalItems);

        totalCals.setText("Total Calories: " + formattedValue);
        totalFoods.setText("Total Foods: " + formattedItems);

        //loop through all db foods//
        for (int i = 0; i < foodsFromDB.size(); i++){

            String name = foodsFromDB.get(i).getFoodName();
            String dateText = foodsFromDB.get(i).getRecordDate();
            String foodTime = foodsFromDB.get(i).getFoodTime();
            int foodQty = foodsFromDB.get(i).getFoodQuantity();
            int cals = foodsFromDB.get(i).getCalories();
            int foodId = foodsFromDB.get(i).getFoodId();

            Log.v("FOOD IDS: ", String.valueOf(foodId));

            //new instance of food//
            myFood = new Food();
            myFood.setFoodName(name);
            myFood.setRecordDate(dateText);
            myFood.setCalories(cals);
            myFood.setFoodId(foodId);
            myFood.setFoodTime(foodTime);
            myFood.setFoodQuantity(foodQty);

            //add myFood object to dbFoods arrayList//
            dbFoods.add(myFood);

        }
        dba.close();

        //setup adapter//
        //adaptor takes in Display Food and XML file we want to inflate - list_row & data//
        foodAdapter = new CustomListViewAdaptor(DisplayFoodActivity.this, R.layout.list_row, dbFoods);
        listView.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();

    }
}
