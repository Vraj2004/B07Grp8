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

import com.example.myfirstapp.models.ItemModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_seller);

        userID = FirebaseAuth.getInstance().getUid();
        dbRef = FirebaseDatabase.getInstance().getReference();
        add = findViewById(R.id.add_button);
        layout = findViewById(R.id.container);
        home = findViewById(R.id.home_button);
        orders = findViewById(R.id.orders_button);
        account = findViewById(R.id.account_button);

        buildDialog();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home_intent = new Intent(getApplicationContext(), home_seller.class);
                startActivity(home_intent);
                finish();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home_intent = new Intent(getApplicationContext(), Account.class);
                startActivity(home_intent);
                finish();
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
                            ItemModel itemModel = new ItemModel(productName, "", "", "");
                            db.getReference().child("Stores").child(userID).child("Items").child(productName).setValue(itemModel);
                            //dbRef.child("Stores").child(userID).child("Items").setValue(productName);
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