package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ItemPreview extends AppCompatActivity {

    Button add_to_cart_button;
    TextView priceText, quantityText, descriptionText;
    EditText quantityEdit;
    FirebaseDatabase db = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_preview);

        add_to_cart_button = findViewById(R.id.add_to_cart_button);
        priceText = findViewById(R.id.price_text);
        quantityText = findViewById(R.id.quantity_text);
        descriptionText = findViewById(R.id.description_text);
        quantityEdit = findViewById(R.id.quantity_label);

        String productName = getIntent().getStringExtra("PRODUCT_NAME");
        String storeName = getIntent().getStringExtra("STORE_NAME");
        String productPrice = getIntent().getStringExtra("PRODUCT_PRICE");

        priceText.setText(productPrice);
        quantityText.setText(productName);

        add_to_cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}