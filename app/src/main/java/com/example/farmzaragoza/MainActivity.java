package com.example.farmzaragoza;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvPharmacies;
    private ArrayList<Pharmacy> pharmacyList;
    private ArrayAdapter<String> adapter;
    private String selectedCity;
    private double cityLatitude;
    private double cityLongitude;
    private String apiKey = "YOUR_API_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedCity = getIntent().getStringExtra("city");

        // Set city coordinates based on the selected city
        switch (selectedCity) {
            case "Zaragoza":
                cityLatitude = 41.6488;
                cityLongitude = -0.8891;
                break;
            case "Madrid":
                cityLatitude = 40.4168;
                cityLongitude = -3.7038;
                break;
            case "Barcelona":
                cityLatitude = 41.3851;
                cityLongitude = 2.1734;
                break;
        }

        lvPharmacies = findViewById(R.id.lv_pharmacies);
        pharmacyList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        lvPharmacies.setAdapter(adapter);

        fetchPharmacies();

        lvPharmacies.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            intent.putExtra("cityLatitude", cityLatitude);
            intent.putExtra("cityLongitude", cityLongitude);
            intent.putParcelableArrayListExtra("pharmacies", pharmacyList);
            startActivity(intent);
        });
    }

    private void fetchPharmacies() {
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + cityLatitude + "," + cityLongitude + "&radius=5000&type=pharmacy&key=" + apiKey;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        ArrayList<String> names = new ArrayList<>();

                        for (int i = 0; i < results.length(); i++) {
                            JSONObject place = results.getJSONObject(i);
                            String name = place.getString("name");
                            String phone = place.has("formatted_phone_number") ? place.getString("formatted_phone_number") : "N/A";
                            double latitude = place.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                            double longitude = place.getJSONObject("geometry").getJSONObject("location").getDouble("lng");

                            Pharmacy pharmacy = new Pharmacy(name, phone, latitude, longitude);
                            pharmacyList.add(pharmacy);
                            names.add(name + " - " + phone);
                        }

                        adapter.clear();
                        adapter.addAll(names);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace()
        );

        queue.add(request);
    }
}