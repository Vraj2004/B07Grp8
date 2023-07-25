package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WhoAreYouPage extends AppCompatActivity {

    Button shopper;
    Button seller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_are_you_page);
        shopper = findViewById(R.id.ShopperButton);
        seller = findViewById(R.id.SellerButton);

        shopper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shopper_intent = new Intent(getApplicationContext(), SignUp_Customer.class);
                startActivity(shopper_intent);
            }
        });

        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent seller_intent = new Intent(getApplicationContext(), SignUp_Owner.class);
                startActivity(seller_intent);
            }
        });
    }
}