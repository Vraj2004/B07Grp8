package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {

    ImageButton home;
    ImageButton account;
    ImageButton orders;

    private Button signOut;
    FirebaseAuth mAuth;
    DatabaseReference dbRef;
    TextView showUser, showPass, showEmail;
    String email, password, username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        home = findViewById(R.id.home_button);
        orders = findViewById(R.id.orders_button);
        account = findViewById(R.id.account_button);

        showEmail = findViewById(R.id.email_content);
        showUser = findViewById(R.id.username_content);
        showPass = findViewById(R.id.password_content);
        dbRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        showContent();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), home_seller.class);
                startActivity(i);
                finish();
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),OrdersOwner.class);
                startActivity(i);
                finish();
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), Account.class);
                startActivity(i);
                finish();
            }
        });

        signOut = findViewById(R.id.signout_button);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

    }

    private void showContent() {

        dbRef.child("Users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                password = snapshot.child("password").getValue(String.class);
                email = snapshot.child("email").getValue(String.class);
                username = snapshot.child("username").getValue(String.class);
                showEmail.setText(email);
                showPass.setText(password);
                showUser.setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
