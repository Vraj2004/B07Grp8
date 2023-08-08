package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.models.ItemModel;
import com.example.myfirstapp.models.StoreModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StoreHome extends AppCompatActivity {

    DatabaseReference dbRef;
    LinearLayout layout;
    ImageButton home;
    ImageButton account;
    ImageButton orders;
    ImageButton cart_button;

    String userID;
    ArrayList<ItemModel> products;

    String storeID;
    String storeName;
    TextView name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_home);
        storeID = getIntent().getStringExtra("STORE_ID");
        storeName = getIntent().getStringExtra("STORE_NAME");
        home = findViewById(R.id.home_button);
        orders = findViewById(R.id.orders_button);
        account = findViewById(R.id.account_button);
        cart_button = findViewById(R.id.cart_button);
        products = new ArrayList<>();
        name = findViewById(R.id.store_name);
        dbRef = FirebaseDatabase.getInstance().getReference();
        layout = findViewById(R.id.container);

        name.setText(storeName);
        loadProducts();

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
        cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),CartPage.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void addCard(String item, String price, String quantity) {
        View view_2 = getLayoutInflater().inflate(R.layout.product_card, null);

        TextView productView = view_2.findViewById(R.id.product_name);
        TextView priceView = view_2.findViewById(R.id.product_price);
        productView.setText(item);
        priceView.setText(price);
        Button preview = view_2.findViewById(R.id.preview_btn);

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ItemPreview.class);
                intent.putExtra("STORE_NAME", storeName);
                intent.putExtra("PRODUCT_NAME", item);
                intent.putExtra("PRODUCT_PRICE", price);
                intent.putExtra("STORE_ID", storeID);
                intent.putExtra("PRODUCT_QUANTITY", quantity);
                startActivity(intent);
                finish();
            }
        });
        layout.addView(view_2);
    }
    public void loadProducts()
    {
        DatabaseReference storeRef = dbRef.child("Stores").child(storeID).child("Items");

        storeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                products.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ItemModel product = snapshot.getValue(ItemModel.class);
                    if (product != null) {
                        products.add(product);
                    }
                }
                for(ItemModel i : products)
                {
                    addCard(i.getName(), i.getPrice(), i.getQuantity());
                }
                Log.d("ProductList", "Loaded " + products.size() + " products from Firebase");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Error loading products from Firebase: " + error.getMessage());
            }
        });
    }
}