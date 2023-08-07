package com.example.myfirstapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myfirstapp.models.ItemModel;
import com.example.myfirstapp.models.StoreModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home_Customer extends AppCompatActivity {

    DatabaseReference dbRef;
    ImageButton home;
    ImageButton account;
    ImageButton orders;
    LinearLayout layout;
    String userID;
    List<StoreModel> stores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_customer);
        userID = FirebaseAuth.getInstance().getUid();
        dbRef = FirebaseDatabase.getInstance().getReference();
        layout = findViewById(R.id.container);
        home = findViewById(R.id.home_button);
        orders = findViewById(R.id.orders_button);
        account = findViewById(R.id.account_button);
        stores = new ArrayList<>();
        loadStores();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), home_seller.class);
                startActivity(i);
                finish();
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),CartPage.class);
                startActivity(i);
                finish();
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),Account.class);
                startActivity(i);
                finish();
            }
        });
    }
    private void addCard(String store, String id) {
        View view_2 = getLayoutInflater().inflate(R.layout.card2, null);

        TextView productView = view_2.findViewById(R.id.name);
        productView.setText(store);
        Button go_to_store = view_2.findViewById(R.id.go_to_store_button);
        go_to_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StoreHome.class);
                intent.putExtra("STORE_NAME", store);
                intent.putExtra("STORE_ID", id);
                startActivity(intent);
            }
        });
        layout.addView(view_2);
    }
    public void loadStores()
    {
        DatabaseReference storeRef = dbRef.child("Stores");

        storeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stores.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    StoreModel store = snapshot.getValue(StoreModel.class);
                    if (store != null) {
                        stores.add(store);
                    }
                }
                for(StoreModel i : stores)
                {
                    addCard(i.getStore_name(), i.getId());
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