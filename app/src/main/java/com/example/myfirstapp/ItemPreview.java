package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myfirstapp.models.CartItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ItemPreview extends AppCompatActivity {

    DatabaseReference dbref;
    Button add_to_cart_button;
    TextView priceText, quantityText, descriptionText;
    EditText quantityEdit;
    String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String storeId, productPrice, productName, storeName, productQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_preview);

        add_to_cart_button = findViewById(R.id.add_to_cart_button);
        priceText = findViewById(R.id.price_text);
        quantityText = findViewById(R.id.quantity_text);
        descriptionText = findViewById(R.id.description_text);
        quantityEdit = findViewById(R.id.quantity_label);
        dbref = FirebaseDatabase.getInstance().getReference();

        productName = getIntent().getStringExtra("PRODUCT_NAME");
        storeName = getIntent().getStringExtra("STORE_NAME");
        productPrice = getIntent().getStringExtra("PRODUCT_PRICE");
        productQuantity = getIntent().getStringExtra("PRODUCT_QUANTITY");
        storeId = getIntent().getStringExtra("STORE_ID");

        priceText.setText(productPrice);
        quantityText.setText(productName);

        add_to_cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
                Intent i = new Intent(ItemPreview.this, StoreHome.class);
                getIntent().putExtra("STORE_ID", storeId);
                startActivity(i);
                finish();
            }
        });
    }

    private void add() {
        CartItem item = new CartItem(storeName, productName, productQuantity, productPrice);
        dbref.child("Users").child(uId).child("Cart").child(storeId).child("Products").child(productName).setValue(item);
    }
}