package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class home_seller extends AppCompatActivity {

    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://b07-final-project-dbff5-default-rtdb.firebaseio.com/");
    Button add;
    AlertDialog dialog;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_seller);

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
                        addCard(product.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

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
