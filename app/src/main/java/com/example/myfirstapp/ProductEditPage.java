package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.models.ItemModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductEditPage extends AppCompatActivity {

    TextView prodName, price, quantity, description;
    EditText editPrice, editQuantity, editDescription;
    String priceText, quantityText, descriptionText;
    Button saveButton;
    ImageButton backButton;
    DatabaseReference dbRef;
    String uId;

    String newPrice, newQuantity, newDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_edit_page);

        saveButton = findViewById(R.id.save_button);
        backButton = findViewById(R.id.back_button);

        price = findViewById(R.id.price_text);
        quantity = findViewById(R.id.quantity_text);
        description = findViewById(R.id.description_text);
        prodName = findViewById(R.id.product_name_label);

        editPrice =findViewById(R.id.price_label);
        editQuantity =  findViewById(R.id.quantity_label);
        editDescription =  findViewById(R.id.description_label);

        uId = FirebaseAuth.getInstance().getUid();
        dbRef = FirebaseDatabase.getInstance().getReference();
        String productName = getIntent().getStringExtra("PRODUCT_NAME");
        prodName.setText(productName);
        DatabaseReference ref = dbRef.child("Stores").child(uId).child("Items").child(productName);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    priceText = dataSnapshot.child("price").getValue(String.class);
                    quantityText = dataSnapshot.child("quantity").getValue(String.class);
                    descriptionText = dataSnapshot.child("description").getValue(String.class);
                    price.setText("Current Price: $" + priceText);
                    quantity.setText("Current Stock: " +quantityText);
                    description.setText("Current Description: " +descriptionText);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error case
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle back button click here (e.g., finish the activity to go back)
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newPrice = editPrice.getText().toString();
                newQuantity = editQuantity.getText().toString();
                newDescription = editDescription.getText().toString();

                // Check if any of the EditText fields are empty
                if (TextUtils.isEmpty(editPrice.getText().toString().trim())) {
                    newPrice = priceText;
                }

                if (TextUtils.isEmpty(editQuantity.getText().toString().trim())) {
                    newQuantity = quantityText;
                }
                if (TextUtils.isEmpty(editDescription.getText().toString().trim())) {
                    newDescription = descriptionText;
                }

                price.setText(newPrice);
                quantity.setText(newQuantity);
                description.setText(newDescription);


                ItemModel itemModel = new ItemModel(productName, newPrice, newQuantity, newDescription);
                dbRef.child("Stores").child(uId).child("Items").child(productName).setValue(itemModel);
                Intent intent = new Intent(getApplicationContext(), Home_Customer.class);
                finish();
            }
        });
    }
}