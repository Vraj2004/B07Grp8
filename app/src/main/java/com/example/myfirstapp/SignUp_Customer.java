package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import com.example.myfirstapp.models.StoreModel;
import com.example.myfirstapp.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.ChildEventRegistration;

import java.util.HashMap;

public class SignUp_Customer extends AppCompatActivity {
    //DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://b07-final-project-dbff5-default-rtdb.firebaseio.com/");

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_shopper);

        final EditText editEmail = findViewById(R.id.cust_email);
        final EditText editPassword = findViewById(R.id.cust_password);
        final EditText editUser = findViewById(R.id.username_cust);
        final EditText editPasswordConfirm = findViewById(R.id.confirm_password_cust);
        final Button btn = findViewById(R.id.sign_up_btn_cust);
        FirebaseAuth fAuth = FirebaseAuth.getInstance();



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password, username, passwordConfirm;
                email = editEmail.getText().toString();
                password = editPassword.getText().toString();
                username = editUser.getText().toString();
                passwordConfirm = editPasswordConfirm.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUp_Customer.this, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(passwordConfirm)){
                    Toast.makeText(SignUp_Customer.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            UserModel userModel = new UserModel(username, email, password, 0);
                            String id = fAuth.getUid();
                            db.getReference().child("Users").child(id).setValue(userModel);
                            Toast.makeText(SignUp_Customer.this, "Account Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), home_customer.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(SignUp_Customer.this, "Failed to create account!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                            finish();
                        }
                    }
                });

//                fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        Toast.makeText(SignUp_Customer.this, "Account Created", Toast.LENGTH_SHORT).show();
//                        HashMap<String, Object> add = new HashMap<>();
//                        add.put("Username", username);
//                        add.put("Email", email);
//                        add.put("isOwner", 0);
//                        dbRef.child("Users").push().setValue(add);
//                        startActivity(new Intent(getApplicationContext(), Login.class));
//                        finish();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(SignUp_Customer.this, "Failed to Create Account!", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(getApplicationContext(), SignUp_Customer.class));
//                        finish();
//                    }
//                });
            }

        });
    }
}

