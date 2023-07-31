package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class home_seller extends AppCompatActivity {

    DatabaseReference dbRef;
    Button add;
    AlertDialog dialog;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_seller);

        dbRef = FirebaseDatabase.getInstance().getReference().child("Products");
        add = findViewById(R.id.add_button);
        layout = findViewById(R.id.container);

        buildDialog();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
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
                            // Save the product to the database
                            // Generate a unique key for the new product
                            String productId = dbRef.push().getKey();
                            // Save the product to the Firebase database using the generated key
                            dbRef.child(productName).child("Product id").setValue(productId);
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

        productView.setText(product);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                layout.removeView(view_2);
            }
        });

        layout.addView(view_2);
    }
}

