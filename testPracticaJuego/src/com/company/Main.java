package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
	// write your code here

        HashMap<String, String> capitalCities = new HashMap<String, String>();

        // Add keys and values (Country, City)
        capitalCities.put("England", "London");
        capitalCities.put("Germany", "Berlin");
        capitalCities.put("Norway", "Oslo");
        capitalCities.put("USA", "Washington");


        List<String> valuesList = new ArrayList<String>(capitalCities.keySet());
        int randomIndex = new Random().nextInt(valuesList.size());
        String randomValue = valuesList.get(randomIndex);

        System.out.println(randomValue);

    }
}
