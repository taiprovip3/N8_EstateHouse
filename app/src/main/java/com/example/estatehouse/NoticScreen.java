package com.example.estatehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class NoticScreen extends AppCompatActivity {
    ImageView btn_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notic_screen);
        btn_img=findViewById(R.id.btn_img);
        btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NoticScreen.this,HomepageScreen.class);
                startActivity(intent);
            }
        });
    }
}