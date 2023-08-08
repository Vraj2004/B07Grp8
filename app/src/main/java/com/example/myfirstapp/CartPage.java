package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myfirstapp.models.CartItem;
import com.example.myfirstapp.models.OrderModel;
import com.example.myfirstapp.models.StoreModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CartPage extends AppCompatActivity {

    DatabaseReference dbRef;
    ImageButton home;
    ImageButton account;
    Button makeOrder;
    ImageButton cart_button;
    ImageButton orders;
    LinearLayout layout;
    String userID;
    List<CartItem> cart;
    String store_id;
    List<String> store_ids;
    List<CartItem> currentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);
        userID = FirebaseAuth.getInstance().getUid();
        dbRef = FirebaseDatabase.getInstance().getReference();
        layout = findViewById(R.id.container);
        home = findViewById(R.id.home_button);
        makeOrder = findViewById(R.id.order);
        orders = findViewById(R.id.orders_button);
        account = findViewById(R.id.account_button);
        cart_button = findViewById(R.id.cart_button);
        cart = new ArrayList<>();
        store_ids = new ArrayList<>();
        currentList = new ArrayList<>();
        loadProducts();

        makeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartPage.this, checkoutPage.class);
                intent.putStringArrayListExtra("STORE_IDS", (ArrayList<String>) store_ids);
                startActivity(intent);
                finish();
            }
        });

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
                Intent i = new Intent(getApplicationContext(),MyOrders.class);
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

    private void addCard(String name, String quantity, String price, String store_id) {
        View view_2 = getLayoutInflater().inflate(R.layout.cart_item, null);

        TextView productView = view_2.findViewById(R.id.product_name);
        TextView priceView = view_2.findViewById(R.id.product_price);
        TextView quantView = view_2.findViewById(R.id.product_quantity);
        productView.setText(name);
        priceView.setText(price);
        quantView.setText(quantity);
        Button delete = view_2.findViewById(R.id.delete_btn);
        layout.addView(view_2);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef.child("Users").child(userID).child("Cart").child(store_id).child("Products").child(name)
                        .removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError == null) {
                                    //Toast.makeText(home_seller.this, "Item removed!", Toast.LENGTH_SHORT).show();
                                    dbRef.child("Users").child(userID).child("Cart").child(store_id).child("Products").child(name).removeValue();
                                    layout.removeView(view_2);
                                } else {
                                    // An error occurred
                                    Log.e("Firebase", "Error removing item: " + databaseError.getMessage());
                                }
                            }
                        });
            }
        });
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
                    addCard(i.getProduct_name(), i.getQuantity(), i.getPrice(), i.getStore_name());
                    store_ids.add(i.getStore_name());
                }
                Log.d("ProductList", "Loaded " + cart.size() + " products from Firebase");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Error loading products from Firebase: " + error.getMessage());
            }
        });

        //DatabaseReference itemsRef = dbRef.child("Stores").child(userID).child("Items");
//        System.out.println("LOADED DB STUFF");
//
//        itemsRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
//                ItemModel item = dataSnapshot.getValue(ItemModel.class);
//                if (item != null) {
//                    items.add(item);
//                    System.out.println("ADDED " + item.getName() + " to list");
//                    addCard(item);
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
//                // Handle changes to existing items if needed
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//                // Handle item removal if needed
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
//                // Handle item reordering if needed
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                System.err.println("Error loading products from Firebase: " + databaseError.getMessage());
//            }
//        });
    }
}