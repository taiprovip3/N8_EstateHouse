package com.example.estatehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.estatehouse.adapter.CartAdapter;
import com.example.estatehouse.entity.HouseCart;

import java.util.ArrayList;
import java.util.List;

public class CartScreen extends AppCompatActivity {

    List<HouseCart> houseCarts;
    ListView listView;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_screen);

        //Ánh xạ
        houseCarts = new ArrayList<HouseCart>();
        listView = (ListView) findViewById(R.id.cart_listView);

        HouseCart h1 = new HouseCart(39000, "Yankol1995", 4,5,380, R.drawable.image_26);
        HouseCart h2 = new HouseCart(41000, "IllusiousMagic", 5,6,385, R.drawable.image_26);
        HouseCart h3 = new HouseCart(39000, "Navigator_Anaconda", 3,3,390, R.drawable.image_26);
        houseCarts.add(h1);
        houseCarts.add(h2);
        houseCarts.add(h3);
        cartAdapter = new CartAdapter(houseCarts, CartScreen.this);
        listView.setAdapter(cartAdapter);
    }
}