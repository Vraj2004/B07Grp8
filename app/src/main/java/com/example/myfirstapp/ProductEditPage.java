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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductEditPage extends AppCompatActivity {

    TextView price;
    TextView quantity;
    TextView description;
    EditText editPrice;
    EditText editQuantity;
    EditText editDescription;
    Button saveButton;
    DatabaseReference dbRef;
    String uId;
    public static final String SharedPrefs = "sharedPrefs";
    public static final String Text = "text";
    String priceText;
    String quantityText;
    String descriptionText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_edit_page);

        Button saveButton = findViewById(R.id.save_button);

        ImageButton backButton = findViewById(R.id.back_button);

        price = (TextView) findViewById(R.id.price_text);
        quantity = (TextView) findViewById(R.id.quantity_text);
        description = (TextView) findViewById(R.id.description_text);

        editPrice = (EditText) findViewById(R.id.price_label);
        editQuantity = (EditText) findViewById(R.id.quantity_label);
        editDescription = (EditText) findViewById(R.id.description_label);



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

                price.setText(editPrice.getText().toString());
                price.setText(editQuantity.getText().toString());
                price.setText(editDescription.getText().toString());
                saveData();

                String productPrice = productPriceEditText.getText().toString();
                String productQuantity = productQuantityEditText.getText().toString();

                dbRef.child("Stores").child(uId).child("Items").child(productName).child("Price").setValue(productPrice);
                dbRef.child("Stores").child(uId).child("Items").child(productName).child("Quantity").setValue(productQuantity);
                finish();
            }
        });

        loadData();
        updateViews();

    }
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefs, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Text, price.getText().toString());
        editor.putString(Text, quantity.getText().toString());
        editor.putString(Text, description.getText().toString());

        editor.apply();

        Toast.makeText(this, "SAVED", Toast.LENGTH_SHORT).show();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefs, MODE_PRIVATE);
        priceText = sharedPreferences.getString(Text, "Price:");
        quantityText = sharedPreferences.getString(Text, "Quantity");
        descriptionText = sharedPreferences.getString(Text,"Description");

    }

    public void updateViews(){
        price.setText(priceText);
        quantity.setText(quantityText);
        description.setText(descriptionText);
    }
}