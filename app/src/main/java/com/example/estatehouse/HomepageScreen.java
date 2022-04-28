package com.example.estatehouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.estatehouse.adapter.HomepageAdapter;
import com.example.estatehouse.entity.House;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomepageScreen extends AppCompatActivity {

    FirebaseFirestore db;
    Spinner spinnerCountries, spinnerCities;
    RadioGroup rdGroupType;
    List<House> houses;
    ListView listView;
    HomepageAdapter homepageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_screen);

        //Ánh xạ
        spinnerCountries = findViewById(R.id.spinner_countries);
        spinnerCities = findViewById(R.id.spinner_cities);
        rdGroupType = findViewById(R.id.rdGroupType);
        houses = new ArrayList<House>();
        listView = (ListView) findViewById(R.id.cart_listView);

        ArrayAdapter<CharSequence> adapterCountry = ArrayAdapter.createFromResource(this, R.array.countries, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterCity = ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_item);
        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_item);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCountries.setAdapter(adapterCountry);
        spinnerCities.setAdapter(adapterCity);

        rdGroupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioButtonBuy:
                        Toast.makeText(HomepageScreen.this, "Selected Buy", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.radioButtonRent:
                        Toast.makeText(HomepageScreen.this, "Selected Rent", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

        House h1 = new House("", 45000, "311, COLOMBIA, NEWYORK, street 12", 1.0, Arrays.asList("colombia", "newyork"), 5, 5, 460, R.drawable.house_description_1);
        House h2 = new House("", 50000, "312, DOMINICA, LOSANGELES, street 13", 2.0, Arrays.asList("dominica", "losangeles"), 4, 6, 480, R.drawable.house_description_1);
        houses.add(h1);
        houses.add(h2);
        homepageAdapter = new HomepageAdapter(houses, HomepageScreen.this);
        listView.setAdapter(homepageAdapter);
    }//end onCreate

    private void addNewDocument(String fn, String ln) {
        Map<String, Object> user = new HashMap<>();
        user.put("first_name", fn);
        user.put("last_name", ln);
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("SUCCESS FIRESTORE", "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(HomepageScreen.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FAILED FIRESTORE", "Error adding document", e);
                        Toast.makeText(HomepageScreen.this, "Error adding document" + e, Toast.LENGTH_LONG).show();
                    }
                });
    }
}