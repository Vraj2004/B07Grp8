package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    public static final String SharedPrefs = "sharedPrefs";


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

        dbRef.child("Stores").child(uId).child("Items").child(productName).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists()) {
                        ItemModel itemModel = dataSnapshot.getValue(ItemModel.class);
                        price.setText(itemModel.getPrice());
                        quantity.setText(itemModel.getQuantity());
                        description.setText(itemModel.getDescription());
                    }
                }
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
                if (!newPrice.isEmpty()) {
                    price.setText(newPrice);
                    priceText = newPrice;
                } else {
                    newPrice = "";
                    priceText="";
                }

                if (!newQuantity.isEmpty()) {
                    quantity.setText(newQuantity);
                    quantityText = newQuantity;
                } else {
                    newQuantity = "";
                    quantityText="";
                }

                if (!newDescription.isEmpty()) {
                    description.setText(newDescription);
                    descriptionText = newDescription;
                } else {
                    newDescription = "";
                    descriptionText="";
                }

                // Save non-empty values to SharedPreferences
                if (!newPrice.isEmpty() || !newQuantity.isEmpty() || !newDescription.isEmpty()) {
                    saveData();
                }

                ItemModel itemModel = new ItemModel(productName, priceText, quantityText, descriptionText);
                dbRef.child("Stores").child(uId).child("Items").child(productName).setValue(itemModel);

                finish();
            }
        });

        loadData();
        updateViews();

    }
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefs, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!editPrice.getText().toString().isEmpty()) {
            editor.putString("Price", editPrice.getText().toString());
        }
        if (!editQuantity.getText().toString().isEmpty()) {
            editor.putString("Quantity", editQuantity.getText().toString());
        }
        if (!editDescription.getText().toString().isEmpty()) {
            editor.putString("Description", editDescription.getText().toString());
        }

        editor.apply();

        Toast.makeText(this, "SAVED", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefs, MODE_PRIVATE);
        priceText = sharedPreferences.getString("Price", "Price: ");
        quantityText = sharedPreferences.getString("Quantity", "Quantity: ");
        descriptionText = sharedPreferences.getString("Description", "Description: ");

    }

    public void updateViews() {
        price.setText(priceText);
        quantity.setText(quantityText);
        description.setText(descriptionText);
    }

}