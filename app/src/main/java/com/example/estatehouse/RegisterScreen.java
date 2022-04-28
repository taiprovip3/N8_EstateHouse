package com.example.estatehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import javax.annotation.Nonnull;

public class RegisterScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnRegister;
    private EditText txtEmail, txtPassword, txtRePassword;
    private String email;
    private String password;
    private String rePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        txtEmail=(EditText) findViewById(R.id.txtEmail);
        txtPassword=(EditText) findViewById(R.id.txtPassword);
        txtRePassword=(EditText) findViewById(R.id.txtRePassword);
        btnRegister=(Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=txtEmail.getText().toString();
                password=txtPassword.getText().toString();
                rePassword=txtRePassword.getText().toString();
                if(password.equals(rePassword)){
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@Nonnull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterScreen.this, "Register success",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(RegisterScreen.this, "Register failed",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(RegisterScreen.this, "Password and Repassword are not the same",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}