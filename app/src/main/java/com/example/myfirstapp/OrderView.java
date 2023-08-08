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

public class OrderView extends AppCompatActivity {

    ImageButton home, orders, account, cart;
    DatabaseReference dbRef;
    LinearLayout layout;
    List<CartItem> stores;
    String userID;
    String orderID;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_view);

        home = findViewById(R.id.homeButton);
        orders = findViewById(R.id.ordersButton);
        account = findViewById(R.id.accountButton);
        cart = findViewById(R.id.cart_button);

        dbRef = FirebaseDatabase.getInstance().getReference();
        layout = findViewById(R.id.container);
        stores = new ArrayList<>();
        userID = FirebaseAuth.getInstance().getUid();
        orderID = getIntent().getStringExtra("ORDER_ID");
        status = null;

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

    private void addCard(String name, String quantity, String price) {
        View view_2 = getLayoutInflater().inflate(R.layout.order_view_card, null);

        TextView productView = view_2.findViewById(R.id.product_name);
        TextView priceView = view_2.findViewById(R.id.product_price);
        TextView quantView = view_2.findViewById(R.id.product_quantity);
        productView.setText(name);
        priceView.setText(price);
        quantView.setText(quantity);
        layout.addView(view_2);
    }
    public void loadStores()
    {
        DatabaseReference storeRef = dbRef.child("Orders");

        storeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stores.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(status == null) {
                        status = snapshot.child(orderID).child("status").getValue(String.class);
                        TextView stat = findViewById(R.id.status_text);
                        stat.setText(status);
                    }
                    System.out.println(status);
                    DataSnapshot products = snapshot.child(orderID).child("Products");
                    for (DataSnapshot product : products.getChildren()) {
                        CartItem store = product.getValue(CartItem.class);
                        if (store != null) {
                            stores.add(store);
                        }
                    }
                }
                for(CartItem i : stores)
                {
                    addCard(i.getProduct_name(),  i.getQuantity(), i.getPrice());
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