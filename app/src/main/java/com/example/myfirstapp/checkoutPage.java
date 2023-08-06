package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class checkoutPage extends AppCompatActivity {

    Button order;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_page);

        order = findViewById(R.id.finalOrderButton);

        order.setOnClickListener(view -> {
            Intent intent= new Intent(getApplicationContext(), Home_Customer.class);
            startActivity(intent);
            finish();
        });
    }
}