package com.example.estatehouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.estatehouse.entity.House;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerScreen extends AppCompatActivity {

    Button btnReset, btnRegister;
    EditText edType, edPrice, edAddress, edSale, edTags, edBedroom, edBathroom, edLivingArea;
    ImageView homePage, imageChosen;
    TextView btnChooseImage, imageNameView;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    String imageChosenName = "can_ho_nha_mat_dat_1_1";
    FirebaseFirestore db;
    CollectionReference houseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_screen);

        db = FirebaseFirestore.getInstance();
        houseRef = db.collection("House");
        btnChooseImage = findViewById(R.id.sll_btnChooseImage);
        btnReset = findViewById(R.id.sll_btnReset);
        btnRegister = findViewById(R.id.sll_btnRegister);
        edType = findViewById(R.id.sll_edType);
        edPrice = findViewById(R.id.sll_edPrice);
        edAddress = findViewById(R.id.sll_edAddress);
        edSale = findViewById(R.id.sll_edSale);
        edTags = findViewById(R.id.sll_edTags);
        edBedroom = findViewById(R.id.sll_edBedroom);
        edBathroom = findViewById(R.id.sll_edBathroom);
        edLivingArea = findViewById(R.id.sll_edLivingArea);
        homePage = findViewById(R.id.sll_homePageIcon);
        imageChosen = findViewById(R.id.sll_imageChosen);
        imageNameView = findViewById(R.id.sll_imageName);

        //setOnClickListenter
        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyField();
            }
        });
        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerScreen.this, HomepageScreen.class);
                startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = edType.getText().toString();
                double price = Double.parseDouble(edPrice.getText().toString());
                String address = edAddress.getText().toString();
                int sale = Integer.parseInt(edSale.getText().toString());
                List<String> tags = Arrays.asList("NEW", edTags.getText().toString());
                int bedroom = Integer.parseInt(edBedroom.getText().toString());
                int bathroom = Integer.parseInt(edBathroom.getText().toString());
                int livingArea = Integer.parseInt(edLivingArea.getText().toString());
                if(dataIsValid(type, price, address, sale, tags, bedroom, bathroom, livingArea)){
                    Map<String, Object> data = new HashMap<>();
                    data.put("type", type);
                    data.put("cost", price);
                    data.put("address", address);
                    data.put("sale", sale);
                    data.put("tags", tags);
                    data.put("bedrooms", bedroom);
                    data.put("bathrooms", bathroom);
                    data.put("livingarea", livingArea);
                    data.put("image", imageChosenName);
                    houseRef.add(data)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(SellerScreen.this, "Register product successfully", Toast.LENGTH_LONG).show();

                                    emptyField();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SellerScreen.this, "Register product failed", Toast.LENGTH_LONG).show();
                                }
                            });
                } else Toast.makeText(SellerScreen.this, "Please fill out fields required!", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null ) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageChosen.setImageBitmap(bitmap);
                imageChosenName = getImageName(filePath);
                imageNameView.setText(imageChosenName);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getImageName(Uri filePath){
        try {
            String path = filePath.getLastPathSegment();
            return path != null ? path.substring(path.lastIndexOf("/") + 1) : "house10";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "house10";
    }
    private boolean dataIsValid(String type, double price, String address, int sale, List<String> tags, int bedroom, int bathroom, int livingArea) {
        if(price <= 0){
            Toast.makeText(this, "Price must > 0", Toast.LENGTH_LONG).show();
            return false;
        }
        if(address.equals(" ")){
            Toast.makeText(this, "Address must not empty", Toast.LENGTH_LONG).show();
            return false;
        }
        if(sale < 0 || sale >= 100){
            Toast.makeText(this, "0 < Sale < 100", Toast.LENGTH_LONG).show();
            return false;
        }
        if(bedroom <= 0 || bathroom <= 0 || livingArea <= 0){
            Toast.makeText(this, "Bed, bath, area must not be negative", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void emptyField() {
        edType.setText("");
        edPrice.setText("");
        edAddress.setText("");
        edSale.setText("");
        edTags.setText("");
        edBedroom.setText("");
        edBathroom.setText("");
        edLivingArea.setText("");
    }
}