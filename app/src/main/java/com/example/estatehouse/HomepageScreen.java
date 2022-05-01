package com.example.estatehouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.estatehouse.adapter.HomepageAdapter;
import com.example.estatehouse.entity.House;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomepageScreen extends AppCompatActivity {

    FirebaseFirestore db;
    Spinner spinnerCountries, spinnerCities;
    RadioGroup rdGroupType;
    RadioButton rdBuy, rdRent;
    List<House> houses;
    ListView listView;
    HomepageAdapter homepageAdapter;
    ImageView helpView, languageView, sellerView, loginView, homePageView, notiView, profileView;
    Button btnFilter;
    ImageView hp_notiIcon;
    private int typeSelectedDefault = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_screen);

        //Ánh xạ
        db = FirebaseFirestore.getInstance();
        CollectionReference houseRef = db.collection("House");
        helpView = findViewById(R.id.hp_helpIcon);
        languageView = findViewById(R.id.hp_languageIcon);
        sellerView = findViewById(R.id.hp_sellerIcon);
        loginView = findViewById(R.id.hp_loginIcon);
        homePageView = findViewById(R.id.hp_homePageIcon);
        notiView = findViewById(R.id.hp_notiIcon);
        profileView = findViewById(R.id.hp_profileIcon);
        spinnerCountries = findViewById(R.id.spinner_countries);
        spinnerCities = findViewById(R.id.spinner_cities);
        rdGroupType = findViewById(R.id.rdGroupType);
        btnFilter = findViewById(R.id.hp_btnFilter);
        rdBuy = findViewById(R.id.radioButtonBuy);
        rdRent = findViewById(R.id.radioButtonRent);
        listView = findViewById(R.id.cart_listView);
        hp_notiIcon=findViewById(R.id.hp_notiIcon);
        hp_notiIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomepageScreen.this,NoticScreen.class);
                startActivity(intent);
            }
        });

        ArrayAdapter<CharSequence> adapterCountry = ArrayAdapter.createFromResource(this, R.array.countries, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterCity = ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_item);
        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_item);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCountries.setAdapter(adapterCountry);
        spinnerCities.setAdapter(adapterCity);

        //setOnClick
        helpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomepageScreen.this, "Change intent to helpScreen", Toast.LENGTH_LONG).show();
            }
        });
        languageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomepageScreen.this, "Language avaiable: US, VN", Toast.LENGTH_LONG).show();
            }
        });
        sellerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomepageScreen.this, "Change to 'SellerScreen.java'", Toast.LENGTH_SHORT).show();
            }
        });
        loginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomepageScreen.this, LoginScreen.class);
                startActivity(intent);
            }
        });
        homePageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomepageScreen.this, "You're here now!", Toast.LENGTH_LONG).show();
            }
        });
        notiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomepageScreen.this, "Notes: some new estate house has just added recently. Check it now", Toast.LENGTH_LONG).show();
            }
        });
        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomepageScreen.this, "Change to ProfileScreen", Toast.LENGTH_LONG).show();
            }
        });
        rdGroupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioButtonBuy:
                        typeSelectedDefault = 0;
                        break;
                    case R.id.radioButtonRent:
                        typeSelectedDefault = 1;
                        break;
                }
            }
        });
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String countrySelected = spinnerCountries.getSelectedItem().toString();
                String citySelected = spinnerCities.getSelectedItem().toString();
                String typeSelected = "";
                if(typeSelectedDefault == 0)
                    typeSelected = "BUY";
                else
                    typeSelected = "RENT";
                if(!countrySelected.equalsIgnoreCase("Select country") && !citySelected.equalsIgnoreCase("Select city")){
                    Toast.makeText(HomepageScreen.this, "Querying...", Toast.LENGTH_LONG).show();
                    houseRef.whereArrayContainsAny("tags", Arrays.asList(countrySelected, citySelected))
                            .whereEqualTo("type", typeSelected)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        houses.clear();
                                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                            House house = documentSnapshot.toObject(House.class);
                                            houses.add(house);
                                            Log.d("FILTERRED", house.toString());
                                        }
                                        homepageAdapter = new HomepageAdapter(houses, HomepageScreen.this);
                                        listView.setAdapter(homepageAdapter);
                                    } else Log.w("ERROR-FIRE", "Error getting documents.", task.getException());
                                }
                            });
                }//end if
            }
        });
        //Get recommended for new
        houses = new ArrayList<House>();
        House h1 = new House("BUY", 45000, "311, COLOMBIA, NEWYORK, Street 12", 1.0, Arrays.asList("COLOMBIA", "NEWYORK", "NEW"), 5, 5, 460, "house_description_1");
        House h2 = new House("RENT", 50000, "312, DOMINICA, LOSANGELES, Street 13", 2.0, Arrays.asList("DOMINICA", "LOSANGELES", "NEW"), 4, 6, 480, "house_started_1");
//        houses.add(h1);
//        houses.add(h2);
        houseRef.whereArrayContains("tags", "NEW")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                House house = documentSnapshot.toObject(House.class);
                                houses.add(house);
                            }
                            homepageAdapter = new HomepageAdapter(houses, HomepageScreen.this);
                            listView.setAdapter(homepageAdapter);
                        } else
                            Log.w("ERROR-FIRE", "Error getting documents.", task.getException());
                    }
                });
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