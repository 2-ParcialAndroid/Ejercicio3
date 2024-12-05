package com.example.farmzaragoza;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class CitySelectionActivity extends AppCompatActivity {

    private ListView lvCities;
    private String[] cities = {"Zaragoza", "Madrid", "Barcelona"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);

        lvCities = findViewById(R.id.lv_cities);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cities);
        lvCities.setAdapter(adapter);

        lvCities.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCity = cities[position];
            Intent intent = new Intent(CitySelectionActivity.this, MainActivity.class);
            intent.putExtra("city", selectedCity);
            startActivity(intent);
        });
    }
}