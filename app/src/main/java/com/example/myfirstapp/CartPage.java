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

import com.example.myfirstapp.models.CartItem;
import com.example.myfirstapp.models.StoreModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends AppCompatActivity {

    DatabaseReference dbRef;
    ImageButton home;
    ImageButton account;
    ImageButton orders;
    LinearLayout layout;
    String userID;
    List<CartItem> cart;
    String store_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);
        userID = FirebaseAuth.getInstance().getUid();
        dbRef = FirebaseDatabase.getInstance().getReference();
        layout = findViewById(R.id.container);
        home = findViewById(R.id.home_button);
        orders = findViewById(R.id.orders_button);
        account = findViewById(R.id.account_button);
        store_id = getIntent().getStringExtra("STORE_ID");
        cart = new ArrayList<>();
        loadProducts();
    }

    private void addCard(String name, String quantity, String price) {
        View view_2 = getLayoutInflater().inflate(R.layout.cart_item, null);

        TextView productView = view_2.findViewById(R.id.product_name);
        TextView priceView = view_2.findViewById(R.id.product_price);
        TextView quantView = view_2.findViewById(R.id.product_quantity);
        productView.setText(name);
        priceView.setText(price);
        quantView.setText(quantity);
        Button delete = view_2.findViewById(R.id.delete_btn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        layout.addView(view_2);
    }
    public void loadProducts()
    {
        dbRef.child("Users").child(userID).child("Cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cart.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot child : snapshot.child("Products").getChildren()) {
                        CartItem product = child.getValue(CartItem.class);
                        if (product != null) {
                            cart.add(product);
                        }
                    }

                }
                for(CartItem i : cart)
                {
                    addCard(i.getProduct_name(), i.getQuantity(), i.getPrice());
                }
                Log.d("ProductList", "Loaded " + cart.size() + " products from Firebase");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Error loading products from Firebase: " + error.getMessage());
            }
        });
    }
}