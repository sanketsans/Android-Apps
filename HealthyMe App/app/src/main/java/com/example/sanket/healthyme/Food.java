package com.example.sanket.healthyme;

import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;


//implementing serializable saves/freezes our food object with all states & behaviours so we can use later//
    //these can then be as extras between intents with all properties intact//
    //giving it an id lets us know the serialize version//

public class Food extends AppCompatActivity implements Serializable {

    private static final long serialVersionUID = 10L;

    //instance variables//
    private String foodName;
    private int calories;
//    private String mealName;
    private int foodId;

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    private String recordDate;
    private int foodQuantity;


    private String foodTime;


    //constructor//
    public Food(String food, int cals, int id, String date, String foodTym, int qty){
        foodName = food;
        calories = cals;
//        mealName = meal;
        foodId = id;
        recordDate = date;
        foodTime = foodTym;
        foodQuantity = qty;

    }

    //default constructor - in case we dont want to add all variables to food object//
    public Food(){
    //left empty//
    }

    //getters & setters for food object//

    //ID//
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    //Food name//
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    //Calories//
    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    //Date//
    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getFoodTime() {
        return foodTime;
    }

    public void setFoodTime(String foodTime) {
        this.foodTime = foodTime;
    }


}
