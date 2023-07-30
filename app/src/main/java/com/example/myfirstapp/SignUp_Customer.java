package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp_Customer extends AppCompatActivity {
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://b07-final-project-dbff5-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_shopper);

        final EditText editEmail = findViewById(R.id.cust_email);
        final EditText editPassword = findViewById(R.id.cust_password);
        final EditText editUser = findViewById(R.id.username_cust);
        final EditText editPasswordConfirm = findViewById(R.id.confirm_password_cust);
        final Button btn = findViewById(R.id.sign_up_btn_cust);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();



        btn.setOnClickListener(view -> {
                String email, password, username, passwordConfirm;
                email = String.valueOf(editEmail.getText().toString());
                password = String.valueOf(editPassword.getText().toString());
                username = String.valueOf(editUser.getText().toString());
                passwordConfirm = String.valueOf(editPasswordConfirm.getText().toString());

                if( TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username) || TextUtils.isEmpty(passwordConfirm))
                {
                    Toast.makeText(SignUp_Customer.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(passwordConfirm)){
                    Toast.makeText(SignUp_Customer.this, "Password does not match", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dbRef.child("Customers").addListenerForSingleValueEvent(new ValueEventListener(){

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(email))
                            {
                                Toast.makeText(SignUp_Customer.this, "This email is already registered",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                dbRef.child("Customers").child(email).child("username").setValue(username);
                                dbRef.child("Customers").child(email).child("password").setValue(password);
                                mAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(SignUp_Customer.this, "Account Created!",
                                                        Toast.LENGTH_SHORT).show();
                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Toast.makeText(SignUp_Customer.this, "Authentication failed.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                Intent customer_intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(customer_intent);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
        });
    }
}