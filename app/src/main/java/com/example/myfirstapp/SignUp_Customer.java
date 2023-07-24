package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp_Customer extends AppCompatActivity {

    TextInputEditText editEmail, editPassword, editUser;
    Button btn;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_shopper);

        mAuth = FirebaseAuth.getInstance();
        editEmail = findViewById(R.id.cust_email);
        editPassword = findViewById(R.id.cust_password);
        editUser = findViewById(R.id.username_cust);
        btn = findViewById(R.id.sign_up_btn_cust);

        btn.setOnClickListener(view -> {
            String email, password, username;
            email = String.valueOf(editEmail.getText());
            password = String.valueOf(editPassword.getText());
            username = String.valueOf(editUser.getText());

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(SignUp_Customer.this, "Enter Email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(SignUp_Customer.this, "Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(username)) {
                Toast.makeText(SignUp_Customer.this, "Enter Username", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    CustomerInfo new_cust = new CustomerInfo(username, password, email);
                    db = FirebaseDatabase.getInstance();
                    ref = db.getReference().child("Customers");
                    ref.child(email).setValue(new_cust).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(SignUp_Customer.this, "Account Created!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(SignUp_Customer.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}