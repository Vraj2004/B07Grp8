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

public class ProductEditPage extends AppCompatActivity {

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
        TextView productNameTextView = findViewById(R.id.product_name_label);

        // Retrieve the product name from the extras
        String productName = getIntent().getStringExtra("PRODUCT_NAME");

        // Set the product name to the TextView
        productNameTextView.setText(productName);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}