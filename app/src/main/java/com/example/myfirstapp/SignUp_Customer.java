package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
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

                            if (snapshot.child(email).exists())
                            {
                                Toast.makeText(SignUp_Customer.this, "SHOULD NOT BE HERE",Toast.LENGTH_SHORT).show();
                                Toast.makeText(SignUp_Customer.this, "This email is already registered",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(SignUp_Customer.this, "REACHED",Toast.LENGTH_SHORT).show();
                                dbRef.child("Customers").child(email).child("username").setValue(username);
                                dbRef.child("Customers").child(email).child("password").setValue(password);
                                dbRef.child("Customers").child(email).child("personality").setValue("Customer");
                                Toast.makeText(SignUp_Customer.this, "Account Created!",
                                        Toast.LENGTH_SHORT).show();
                                Intent customer_intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(customer_intent);
                                //finish();
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