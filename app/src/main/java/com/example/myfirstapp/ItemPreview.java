package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myfirstapp.models.CartItem;
import com.example.myfirstapp.models.ItemModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ItemPreview extends AppCompatActivity {

    DatabaseReference dbref;
    Button add_to_cart_button;
    ImageButton back_button;
    TextView priceText, descriptionText, productText;
    EditText quantityEdit;
    String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String storeId, productPrice, productName, storeName, productQuantity;

    String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_preview);

        add_to_cart_button = findViewById(R.id.add_to_cart_button);
        priceText = findViewById(R.id.priceNumber);
        descriptionText = findViewById(R.id.descriptionStuff);
        quantityEdit = findViewById(R.id.quantity_label);
        dbref = FirebaseDatabase.getInstance().getReference();
        productText = findViewById(R.id.product_name_label);
        back_button = findViewById(R.id.back_button);
        productName = getIntent().getStringExtra("PRODUCT_NAME");
        storeName = getIntent().getStringExtra("STORE_NAME");
        productPrice = getIntent().getStringExtra("PRODUCT_PRICE");
        storeId = getIntent().getStringExtra("STORE_ID");
        productText.setText(productName);
        desc="";

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivity(new Intent(ItemPreview.this, StoreHome.class));
                finish();
            }
        });
        dbref.child("Stores").child(storeId).child("Items").child(productName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ItemModel model = snapshot.getValue(ItemModel.class);
                desc = model.getDescription();
                descriptionText.setText(desc);
                System.out.println(desc);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        priceText.setText("$" + productPrice);

        add_to_cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productQuantity = quantityEdit.getText().toString();
                add();
                Intent i = new Intent(ItemPreview.this, Home_Customer.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void add() {
        CartItem item = new CartItem(storeId, productName, productQuantity, productPrice);
        dbref.child("Users").child(uId).child("Cart").child(storeId).child("Products").child(productName).setValue(item);
    }
}