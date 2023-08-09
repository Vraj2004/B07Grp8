package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrdersCustomer extends AppCompatActivity {

    ImageButton home, orders, account, cart;
    DatabaseReference dbRef;
    LinearLayout layout;
    List<String> stores;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_customer);

        home = findViewById(R.id.home_button);
        orders = findViewById(R.id.orders_button);
        account = findViewById(R.id.account_button);
        cart = findViewById(R.id.cart_button);

        dbRef = FirebaseDatabase.getInstance().getReference();
        layout = findViewById(R.id.container);
        stores = new ArrayList<>();
        userID = FirebaseAuth.getInstance().getUid();

        loadStores();


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Home_Customer.class);
                startActivity(i);
                finish();
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CartPage.class);
                startActivity(i);
                finish();
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), OrdersCustomer.class);
                startActivity(i);
                finish();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AccountShopper.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void addCard(String order) {
        View view_2 = getLayoutInflater().inflate(R.layout.orders_customer_card, null);

        TextView productView = view_2.findViewById(R.id.name);
        productView.setText(order);

        Button go_to_store = view_2.findViewById(R.id.view);
        go_to_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OrderView.class);
                intent.putExtra("ORDER_ID", order);
                startActivity(intent);
                finish();
            }
        });

        layout.addView(view_2);
    }
    public void loadStores()
    {
        DatabaseReference storeRef = dbRef.child("Users").child(userID).child("Orders");

        storeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String order = snapshot.getValue(String.class);
                    if (order != null) {
                        stores.add(order);
                    }
                }
                for(String i : stores)
                {
                    addCard(i);
                }
                Log.d("ProductList", "Loaded " + stores.size() + " products from Firebase");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Error loading products from Firebase: " + error.getMessage());
            }
        });
    }
}