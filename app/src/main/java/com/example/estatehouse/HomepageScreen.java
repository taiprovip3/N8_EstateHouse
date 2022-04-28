package com.example.estatehouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class HomepageScreen extends AppCompatActivity {
    FirebaseFirestore db;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_screen);

        btnSave = (Button) findViewById(R.id.btnSave);
        db = FirebaseFirestore.getInstance();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewDocument("phan tan", "duy");
            }
        });
    }

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