package com.example.myfirstapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class home_seller extends AppCompatActivity {

    DatabaseReference dbRef;
    Button add;
    AlertDialog dialog;
    LinearLayout layout;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_seller);

        userID = FirebaseAuth.getInstance().getUid();
        dbRef = FirebaseDatabase.getInstance().getReference();
        add = findViewById(R.id.add_button);
        layout = findViewById(R.id.container);

        buildDialog();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        final ImageButton home_button = (ImageButton) findViewById(R.id.home_button);

        home_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), home_customer.class);
                startActivity(i);

            }
        });

        final ImageButton orders_button = (ImageButton) findViewById(R.id.orders_button);

        orders_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),item_cart.class);
                startActivity(i);

            }
        });

        final ImageButton my_orders_button = (ImageButton) findViewById(R.id.my_orders_button);

        my_orders_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), MyOrders.class);
                startActivity(i);

            }
        });

        final ImageButton account_button = (ImageButton) findViewById(R.id.account_button);

        account_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),Account.class);
                startActivity(i);

            }
        });
    }

    private void buildDialog() {
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
                            addCard(productName);
                            String pId = dbRef.push().getKey();
                            dbRef.child("Stores").child(userID).child("Items").child(productName).setValue(productName);
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

    private void addCard(String product) {
        View view_2 = getLayoutInflater().inflate(R.layout.card, null);

        TextView productView = view_2.findViewById(R.id.name);
        Button delete = view_2.findViewById(R.id.delete_button);
        Button edit = view_2.findViewById(R.id.edit_button);

        productView.setText(product);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductEditPage.class);
                intent.putExtra("PRODUCT_NAME", product);
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                layout.removeView(view_2);
            }
        });

        layout.addView(view_2);
    }
}