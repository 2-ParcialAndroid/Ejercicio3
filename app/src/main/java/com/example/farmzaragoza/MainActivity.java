package com.example.farmzaragoza;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvPharmacies;
    private ArrayList<Pharmacy> pharmacyList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvPharmacies = findViewById(R.id.lv_pharmacies);
        pharmacyList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        lvPharmacies.setAdapter(adapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pharmacies");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                pharmacyList.clear();
                ArrayList<String> names = new ArrayList<>();

                for (DataSnapshot data : snapshot.getChildren()) {
                    Pharmacy pharmacy = data.getValue(Pharmacy.class);
                    if (pharmacy != null) {
                        pharmacyList.add(pharmacy);
                        names.add(pharmacy.getName() + " - " + pharmacy.getPhone());
                    }
                }

                adapter.clear();
                adapter.addAll(names);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Log the error
            }
        });

        lvPharmacies.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Pharmacy selectedPharmacy = pharmacyList.get(position);
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            intent.putExtra("name", selectedPharmacy.getName());
            intent.putExtra("latitude", selectedPharmacy.getLatitude());
            intent.putExtra("longitude", selectedPharmacy.getLongitude());
            startActivity(intent);
        });
    }
}
