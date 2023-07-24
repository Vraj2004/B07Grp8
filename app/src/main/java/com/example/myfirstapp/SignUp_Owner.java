package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp_Owner extends AppCompatActivity {

    TextInputEditText editEmail, editPassword, editUser, editStoreName;
    Button btn;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_seller);

        mAuth = FirebaseAuth.getInstance();
        editStoreName = findViewById(R.id.Store_Name);
        editEmail = findViewById(R.id.owner_email);
        editPassword = findViewById(R.id.owner_password);
        editUser = findViewById(R.id.username_owner);
        btn = findViewById(R.id.sign_up_btn_owner);

        btn.setOnClickListener(view -> {
            String email, password, username, storeName;
            email = String.valueOf(editEmail.getText());
            password = String.valueOf(editPassword.getText());
            username = String.valueOf(editUser.getText());
            storeName = String.valueOf(editStoreName.getText());

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(SignUp_Owner.this, "Enter Email!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(SignUp_Owner.this, "Enter Password!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(username)) {
                Toast.makeText(SignUp_Owner.this, "Enter Username!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(storeName)) {
                Toast.makeText(SignUp_Owner.this, "Enter Store Name!", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            OwnerInfo new_cust = new OwnerInfo(email, password, storeName, username);
                            db = FirebaseDatabase.getInstance();
                            ref = db.getReference().child("Owners");
                            ref.child(email).setValue(new_cust).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(SignUp_Owner.this, "Account Created!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUp_Owner.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        finish();
    }
}