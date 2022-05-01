package com.example.estatehouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.estatehouse.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nonnull;

public class RegisterScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnRegister;
    private EditText txtEmail, txtPassword, txtRePassword;
    private TextView txtLoginHere;
    private String email;
    private String password;
    private String rePassword;
    private FirebaseFirestore db;
    CollectionReference userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        db = FirebaseFirestore.getInstance();
        userReference = db.collection("users");
        txtEmail=(EditText) findViewById(R.id.txtEmail);
        txtPassword=(EditText) findViewById(R.id.txtPassword);
        txtRePassword=(EditText) findViewById(R.id.txtRePassword);
        txtLoginHere=(TextView)findViewById(R.id.hp_txtViewLogin);
        txtLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterScreen.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });
        btnRegister=(Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=txtEmail.getText().toString();
                password=txtPassword.getText().toString();
                rePassword=txtRePassword.getText().toString();
                if(email.equals("")){
                    Toast.makeText(RegisterScreen.this, "Enter email",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if(password.equals("")){
                    Toast.makeText(RegisterScreen.this, "Enter password",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if(rePassword.equals("")){
                    Toast.makeText(RegisterScreen.this, "Enter re-enter password",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if(password.equals(rePassword)){
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@Nonnull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        String documentId = UUID.randomUUID().toString();
                                        User user = new User();
                                        user.setDocumentId(documentId);
                                        user.setEmail(email);
                                        user.setAvatar("image_6.png");
                                        user.setBalance(0.0);
                                        user.setFirstName("Un");
                                        user.setLastName("know");
                                        user.setLocation("VN");
                                        user.setPassword(password);
                                        user.setPhonenumber("+84");
                                        user.setRole("member");
                                        userReference.document(documentId).set(user);
                                        Toast.makeText(RegisterScreen.this, "Register success",
                                                Toast.LENGTH_LONG).show();
                                        emptyField();
                                    } else {
                                        Toast.makeText(RegisterScreen.this, "Register failed",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(RegisterScreen.this, "Password and Repassword are not the same",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void emptyField() {
        txtEmail.setText("");
        txtPassword.setText("");
        txtRePassword.setText("");
    }
}