package com.example.estatehouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.estatehouse.adapter.CartAdapter;
import com.example.estatehouse.entity.HouseCart;
import com.example.estatehouse.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartScreen extends AppCompatActivity {

    List<HouseCart> houseCarts;
    ListView listView;
    CartAdapter cartAdapter;
    FirebaseFirestore db;
    CollectionReference cartReference, userReference;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseStorage storage;
    StorageReference storageReference, imageReference;
    private ImageView btnBack, avatarView;
    private Button btnBalance, btnSettings;
    private TextView fullnameView, roleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_screen);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://estatehouse-4ee84.appspot.com");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        cartReference = db.collection("carts");
        userReference = db.collection("users");
        houseCarts = new ArrayList<HouseCart>();
        listView = (ListView) findViewById(R.id.cart_listView);
        btnBack = findViewById(R.id.cart_btnBack);
        avatarView = findViewById(R.id.cart_avatarView);
        btnBalance = findViewById(R.id.cart_btnBalance);
        btnSettings = findViewById(R.id.cart_btnSettings);
        fullnameView = findViewById(R.id.cart_fullNameView);
        roleView = findViewById(R.id.cart_roleView);

        btnBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartScreen.this, ProfieScreen.class);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartScreen.this, ProfieScreen.class);
                startActivity(intent);
            }
        });

        cartReference
                .whereEqualTo("email", currentUser.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                HouseCart cart = documentSnapshot.toObject(HouseCart.class);
                                houseCarts.add(cart);
                            }
                            cartAdapter = new CartAdapter(houseCarts, CartScreen.this);
                            listView.setAdapter(cartAdapter);
                        }else
                            Log.w("ERROR-GET-FIRESTORE", "Error getting documents.", task.getException());
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        userReference.whereEqualTo("email", currentUser.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = new User();
                                user = document.toObject(User.class);
                                user.setDocumentId(document.getId());
                                imageReference = storageReference.child("images/"+user.getAvatar());
                                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Picasso.get()
                                                .load(uri)
                                                .into(avatarView);
                                    }
                                });
                                fullnameView.setText(user.getFirstName() + " " + user.getLastName());
                                roleView.setText(user.getRole());
                            }
                        } else
                            Toast.makeText(CartScreen.this, "GET DOCUMENT FAILED", Toast.LENGTH_LONG).show();
                    }
                });
    }
}