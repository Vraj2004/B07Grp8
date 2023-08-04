package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductEditPage extends AppCompatActivity {

    DatabaseReference dbRef;
    String uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_edit_page);

        Button saveButton = findViewById(R.id.save_button);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle back button click here (e.g., finish the activity to go back)
                finish();
            }
        });

        uId = FirebaseAuth.getInstance().getUid();
        dbRef = FirebaseDatabase.getInstance().getReference();
        TextView productNameTextView = findViewById(R.id.product_name_label);
        EditText productPriceEditText = findViewById(R.id.price_label);
        EditText productQuantityEditText = findViewById(R.id.quantity_label);
        EditText productDescriptionEditText = findViewById(R.id.description_label);

        String productName = getIntent().getStringExtra("PRODUCT_NAME");

        // Set the product name to the TextView
        productNameTextView.setText(productName);

        //String productName = productNameTextView.getText().toString();

        // Retrieve the product name from the extras
        dbRef.child("Stores").child(uId).child("Items").child(productName).child("Price").setValue("");
        dbRef.child("Stores").child(uId).child("Items").child(productName).child("Quantity").setValue("");


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String productPrice = productPriceEditText.getText().toString();
                String productQuantity = productQuantityEditText.getText().toString();

                dbRef.child("Stores").child(uId).child("Items").child(productName).child("Price").setValue(productPrice);
                dbRef.child("Stores").child(uId).child("Items").child(productName).child("Quantity").setValue(productQuantity);
                finish();
            }
        });
    }
}