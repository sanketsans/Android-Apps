package com.example.sanket.healthyme;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sanket.healthyme.data.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class selectFood extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    final Context context = this;
    Button button, addButton, subButton,total;
    EditText searchFood;
    TextView dateTV, calIntake,qty;
    Spinner foodTime;
    final String appId = "f3ae615e";
    final String appKey = "20a929e7e7f39ec5e0bfa373cc4c9c1f";
    int quantity = 1;

    String itemName, item;
    double calo, finalcalo = 0.0;
    RequestQueue requestQueue;

    private DatabaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_food);
        //searchView = (SearchView) findViewById(R.id.searchFood);
        dateTV = (TextView) findViewById(R.id.date);
        foodTime = (Spinner) findViewById(R.id.foodTime);
        searchFood = (EditText) findViewById(R.id.searchFood);
        calIntake = (TextView) findViewById(R.id.calIntake);
        button = (Button) findViewById(R.id.calculateCalorie);
        requestQueue = Volley.newRequestQueue(this);
        qty = (TextView) findViewById(R.id.qty);
        addButton = (Button) findViewById(R.id.addButton);
        subButton = (Button) findViewById(R.id.subButton);
        total = (Button) findViewById(R.id.totalCalculateCalorie);

        final int calBurnt = getIntent().getIntExtra("CalBurnt",0);

        dba = new DatabaseHandler(selectFood.this);
        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
        String dateString = sdf.format(date);
        dateTV.setText(dateString);

        foodTime.setOnItemSelectedListener(this);

        final List categories = new ArrayList();
        categories.add("Breakfast");
        categories.add("Lunch");
        categories.add("Dinner");
        categories.add("Extras");

        // Creating adapter for spinner
        final ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        foodTime.setAdapter(dataAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // categories.remove(item);
               // foodTime.setAdapter(dataAdapter);

            String foodSearch = searchFood.getText().toString();
                String fieldSearch = "item_name,nf_calories,nf_total_fat";
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https")
                        .authority("api.nutritionix.com")
                        .appendPath("v1_1")
                        .appendPath("search")
                        .appendPath(foodSearch)
                        .appendQueryParameter("fields",fieldSearch)
                        .appendQueryParameter("appId",appId)
                        .appendQueryParameter("appKey",appKey);

                String myUrl = builder.build().toString();

                //Toast.makeText(getApplicationContext(),myUrl,Toast.LENGTH_SHORT).show();

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myUrl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray jsonArray = response.getJSONArray("hits");
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                    JSONObject jsonObject2 = jsonObject1.getJSONObject("fields");
                                    itemName = jsonObject2.getString("item_name");
                                    calo = jsonObject2.getDouble("nf_calories");

                                    String calStr = String.valueOf(calo);
                                    calIntake.setText(itemName + " \n"+ calStr);

                                    qty.setText(String.valueOf(quantity));
                                    addButton.setEnabled(true);
                                    total.setEnabled(true);
                                    addButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            quantity ++ ;
                                            qty.setText(String.valueOf(quantity));
                                            if(quantity > 1){
                                                subButton.setEnabled(true);
                                            }
                                            finalcalo = calo * quantity;
                                            Toast.makeText(getApplicationContext(),"Total Calo: "+ finalcalo, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    subButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if(quantity > 1){
                                                quantity -- ;
                                                qty.setText(String.valueOf(quantity));
                                            }
                                            else if( quantity == 1){
                                                Toast.makeText(getApplicationContext(),"Cannot go less than 1", Toast.LENGTH_SHORT).show();
                                                subButton.setEnabled(false);
                                            }
                                            finalcalo = calo * quantity;
                                            Toast.makeText(getApplicationContext(),"Total Calo: "+ finalcalo, Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();
                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(),"Can't connect ",Toast.LENGTH_SHORT).show();
                            }
                        });

                requestQueue.add(jsonObjectRequest);


            }
        });

        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom);
                dialog.setTitle("Calories in "+ searchFood.getText().toString());

                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.text);
                if(finalcalo == 0.0){
                    finalcalo = calo;
                    text.setText("Total Calories : "+ finalcalo);
                }
                else{
                    text.setText("Total Calories : "+ finalcalo);
                }
                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //dialog.dismiss();
                        saveDataToDB();
                        Intent intent1 = new Intent(selectFood.this, DisplayFoodActivity.class);
                        intent1.putExtra("CalBurnt", calBurnt);
                        startActivity(intent1);
                    }
                });

                dialog.show();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void saveDataToDB(){

        Food food = new Food();
        String name = calIntake.getText().toString().trim();
        int qty = quantity;
       // String calsString = String.valueOf(finalcalo);
        int cals = (int) finalcalo;

            food.setFoodName(name);
            food.setCalories(cals);
            food.setFoodTime(item);
            food.setFoodQuantity(qty);

            dba.addFood(food);
            dba.close();

            //clear the form for next session//
            calIntake.setText("");

            //take users to next screen (display all entered items)//
            startActivity(new Intent(selectFood.this, DisplayFoodActivity.class));

    }

}
