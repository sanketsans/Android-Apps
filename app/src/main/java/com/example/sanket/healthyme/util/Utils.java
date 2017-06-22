package com.example.sanket.healthyme.util;

import java.text.DecimalFormat;

//static method to format numbers with commas//
public class Utils {

    public static String formatNumber(int value){
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formatted = formatter.format(value);

        return formatted;
    }
}
