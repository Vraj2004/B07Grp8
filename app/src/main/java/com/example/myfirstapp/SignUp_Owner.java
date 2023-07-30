package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp_Owner extends AppCompatActivity {

    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://b07-final-project-dbff5-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_seller);

        final EditText editEmail = findViewById(R.id.owner_email);
        final EditText editPassword = findViewById(R.id.owner_password);
        final EditText editUser = findViewById(R.id.username_owner);
        final EditText editStoreName = findViewById(R.id.Store_Name);
        final EditText editPasswordConfirm = findViewById(R.id.confirm_password_owner);
        final Button btn = findViewById(R.id.sign_up_btn_owner);


        btn.setOnClickListener(view -> {
                String email, password, username, storeName, passwordConfirm;
                email = String.valueOf(editEmail.getText().toString());
                password = String.valueOf(editPassword.getText().toString());
                passwordConfirm = String.valueOf(editPasswordConfirm.getText().toString());
                username = String.valueOf(editUser.getText().toString());
                storeName = String.valueOf(editStoreName.getText().toString());

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username)
                || TextUtils.isEmpty(storeName) || TextUtils.isEmpty(passwordConfirm)) {
                    Toast.makeText(SignUp_Owner.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(passwordConfirm)){
                    Toast.makeText(SignUp_Owner.this, "Password does not match", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dbRef.child("Users").addListenerForSingleValueEvent(new ValueEventListener(){

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(email))
                            {
                                Toast.makeText(SignUp_Owner.this, "This email is already registered",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                dbRef.child("Users").child(email).child("username").setValue(username);
                                dbRef.child("Users").child(email).child("password").setValue(password);
                                dbRef.child("Users").child(email).child("store name").setValue(storeName);
                                dbRef.child("Users").child(email).child("personality").setValue("Seller");
                                Toast.makeText(SignUp_Owner.this, "Account created successfully!",
                                        Toast.LENGTH_SHORT).show();

                                Intent seller_intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(seller_intent);
                                finish();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
        });
    }
}