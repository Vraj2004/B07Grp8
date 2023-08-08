package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.models.ItemModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class home_seller extends AppCompatActivity {

    DatabaseReference dbRef;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    Button add;
    ImageButton home;
    ImageButton account;
    ImageButton orders;
    AlertDialog dialog;
    LinearLayout layout;
    String userID;
    HashSet<ItemModel> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_seller);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbRef = FirebaseDatabase.getInstance().getReference();
        add = findViewById(R.id.add_button);
        layout = findViewById(R.id.container);
        home = findViewById(R.id.home_button);
        orders = findViewById(R.id.orders_button);
        account = findViewById(R.id.account_button);
        items = new HashSet<>();
        loadItems();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("ADD BUTTON CLICKED");
                buildDialog();
                dialog.show();
            }
        });

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

                Intent i = new Intent(getApplicationContext(),OrdersOwner.class);
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

    private void buildDialog() {
        System.out.println("BUILD DIALOG HERE");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog, null);

        EditText product = view.findViewById(R.id.edit_product_name);

        builder.setView(view);
        builder.setTitle("Enter Product Name")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String productName = product.getText().toString().trim();
                        if (!productName.isEmpty()) {
                            ItemModel itemModel = new ItemModel(productName, "", "", "");
                            db.getReference().child("Stores").child(userID).child("Items").child(productName).setValue(itemModel);
                            product.setText("");
                        } else {
                            Toast.makeText(home_seller.this, "Product name cannot be empty.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle cancel action if needed
                    }
                });

        dialog = builder.create();
    }

    public void addCard(ItemModel item) {
        String product = item.getName();
        String price = item.getPrice();
        String quantity = item.getQuantity();
        String description = item.getDescription();

        View view_2 = getLayoutInflater().inflate(R.layout.card, null);
        TextView productView = view_2.findViewById(R.id.name);
        Button delete = view_2.findViewById(R.id.delete_button);
        Button edit = view_2.findViewById(R.id.edit_button);
        productView.setText(product);
        layout.addView(view_2);
        System.out.println("ADDING CARD FOR " + item.getName());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductEditPage.class);
                intent.putExtra("PRODUCT_NAME", product);
//                intent.putExtra("PRODUCT_PRICE", price);
//                intent.putExtra("PRODUCT_QUANTITY", quantity);
//                intent.putExtra("PRODUCT_DESCRIPTION", description);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef.child("Stores").child(userID).child("Items").child(product)
                        .removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError == null) {
                                    //Toast.makeText(home_seller.this, "Item removed!", Toast.LENGTH_SHORT).show();
                                    dbRef.child("Stores").child(userID).child("Items").child(product).removeValue();
                                    layout.removeView(view_2);
                                    System.out.println("removed" + product + " from db and view");
                                } else {
                                    // An error occurred
                                    Log.e("Firebase", "Error removing item: " + databaseError.getMessage());
                                }
                            }
                        });
            }
        });

    }
    private void loadItems() {
        DatabaseReference itemsRef = dbRef.child("Stores").child(userID).child("Items");
        System.out.println("LOADED DB STUFF");

        itemsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                ItemModel item = dataSnapshot.getValue(ItemModel.class);
                if (item != null) {
                    items.add(item);
                    System.out.println("ADDED " + item.getName() + " to list");
                    addCard(item);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Handle changes to existing items if needed
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                // Handle item removal if needed
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Handle item reordering if needed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.err.println("Error loading products from Firebase: " + databaseError.getMessage());
            }
        });
    }

//    private void loadItems() {
//        DatabaseReference itemsRef = dbRef.child("Stores").child(userID).child("Items");
//        System.out.println("LOADED DB STUFF");
//
//        itemsRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    ItemModel item = snapshot.getValue(ItemModel.class);
//                        items.add(item);
//                        System.out.println("ADDED " + item.getName() + "to list");
//                }
//                for(ItemModel i : items)
//                {
//                    addCard(i);
//                }
////                Log.d("ProductList", "Loaded " + items.size() + " products from Firebase");
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                System.err.println("Error loading products from Firebase: " + error.getMessage());
//            }
//        });
//    }
}