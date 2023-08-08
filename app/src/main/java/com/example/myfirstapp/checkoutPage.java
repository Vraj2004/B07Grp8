package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.myfirstapp.models.CartItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.List;
import java.util.UUID;

public class checkoutPage extends AppCompatActivity {

    Button order;
    EditText firstName, lastName, Address;
    EditText postalCode, creditCardNumber, csv, expiryDate;
    ImageButton home;
    ImageButton account;
    ImageButton orders, cart;
    DatabaseReference dbRef;
    String userID;
    List<String> store_ids;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_page);

        home = findViewById(R.id.home_button);
        orders = findViewById(R.id.orders_button);
        account = findViewById(R.id.account_button);
        cart = findViewById(R.id.cart_button);
        dbRef = FirebaseDatabase.getInstance().getReference();
        userID = FirebaseAuth.getInstance().getUid();
        store_ids = getIntent().getStringArrayListExtra("STORE_IDS");

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Home_Customer.class);
                startActivity(i);
                finish();
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),OrdersCustomer.class);
                startActivity(i);
                finish();
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),AccountShopper.class);
                startActivity(i);
                finish();
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),CartPage.class);
                startActivity(i);
                finish();
            }
        });

        order = findViewById(R.id.finalOrderButton);

        order.setOnClickListener(view -> {
            makeOrderFunc();
            Intent intent= new Intent(getApplicationContext(), Home_Customer.class);
            startActivity(intent);
            finish();
        });

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        Address = findViewById(R.id.address);
        postalCode = findViewById(R.id.postalCode);
        creditCardNumber = findViewById(R.id.cardInfo);
        csv = findViewById(R.id.csv);
        expiryDate = findViewById(R.id.expiryDate);

        order.setEnabled(false);

        firstName.addTextChangedListener(textWatcher);
        lastName.addTextChangedListener(textWatcher);
        Address.addTextChangedListener(textWatcher);
        postalCode.addTextChangedListener(textWatcher);
        creditCardNumber.addTextChangedListener(textWatcher);
        csv.addTextChangedListener(textWatcher);
        expiryDate.addTextChangedListener(textWatcher);

    }

    private void makeOrderFunc()
    {
        dbRef.child("Users").child(userID).child("Cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                for (int a = 0; a < count; a++){
                    String orderId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                    String currentStore = store_ids.get(a);

                    dbRef.child("Users").child(userID).child("Cart").child(currentStore).child("Products").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                CartItem item = snap.getValue(CartItem.class);
                                dbRef.child("Orders").child(currentStore).child(orderId).child("orderId").setValue(orderId);
                                dbRef.child("Orders").child(currentStore).child(orderId).child("status").setValue("Pending");
                                dbRef.child("Orders").child(currentStore).child(orderId).child("userId").setValue(userID);
                                dbRef.child("Orders").child(currentStore).child(orderId).child("Products").child(item.getProduct_name()).setValue(item);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    dbRef.child("Users").child(userID).child("Orders").child(orderId).setValue(orderId);
                    dbRef.child("Users").child(currentStore).child("Orders").child(orderId).setValue(orderId);
                }
                dbRef.child("Users").child(userID).child("Cart").removeValue();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Error loading products from Firebase: " + error.getMessage());
            }
        });

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String first = firstName.getText().toString();
            String last = lastName.getText().toString();
            String addy = Address.getText().toString();
            String postal = postalCode.getText().toString();
            String card = creditCardNumber.getText().toString();
            String CSV = csv.getText().toString();
            String expiry = expiryDate.getText().toString();

            if (!first.isEmpty() && !last.isEmpty() && !addy.isEmpty() && !postal.isEmpty() && !card.isEmpty() &&
                    !CSV.isEmpty() && !expiry.isEmpty()) {
                order.setEnabled(true);
            } else {
                order.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}