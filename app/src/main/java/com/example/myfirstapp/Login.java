package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class Login extends AppCompatActivity {

    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://b07-final-project-dbff5-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText editEmail = findViewById(R.id.email);
        final EditText editPassword = findViewById(R.id.password);
        final Button btn = findViewById(R.id.login_btn);
        final TextView textView = findViewById(R.id.new_sign_up);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = String.valueOf(editEmail.getText().toString());
                password = editPassword.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ) {
                    Toast.makeText(Login.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                    dbRef.child("Users").addListenerForSingleValueEvent(new ValueEventListener(){

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(email))
                            {
                                String storedPassword = snapshot.child(email).child("password").getValue(String.class);
                                if(storedPassword.equals(password))
                                {
                                    Toast.makeText(Login.this, "Login Successful!",
                                            Toast.LENGTH_SHORT).show();
                                    Intent customer_intent = new Intent(getApplicationContext(), Home_Customer.class);
                                    startActivity(customer_intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(Login.this, "Incorrect email or password",
                                            Toast.LENGTH_SHORT).show();
                                    Intent customer_intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(customer_intent);
                                    finish();
                                }
                            }
                            else
                            {
                                Toast.makeText(Login.this, "No such account exists",
                                        Toast.LENGTH_SHORT).show();
                                Intent customer_intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(customer_intent);
                                finish();

                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        textView.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), WhoAreYouPage.class);
            startActivity(intent);
            finish();
        });
    }
}