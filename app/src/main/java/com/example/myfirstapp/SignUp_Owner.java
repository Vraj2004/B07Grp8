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

import com.example.myfirstapp.models.StoreModel;
import com.example.myfirstapp.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.util.ArrayList;
import java.util.HashMap;

public class SignUp_Owner extends AppCompatActivity {

    //DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://b07-final-project-dbff5-default-rtdb.firebaseio.com/");
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    String id;
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
        final FirebaseAuth fAuth = FirebaseAuth.getInstance();


        btn.setOnClickListener(view -> {
            String email, password, username, storeName, passwordConfirm;
            email = editEmail.getText().toString();
            password = editPassword.getText().toString();
            passwordConfirm = editPasswordConfirm.getText().toString();
            username = editUser.getText().toString();
            storeName = editStoreName.getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username)
                    || TextUtils.isEmpty(storeName) || TextUtils.isEmpty(passwordConfirm)) {
                Toast.makeText(SignUp_Owner.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }

            if (!password.equals(passwordConfirm)){
                Toast.makeText(SignUp_Owner.this, "Password does not match", Toast.LENGTH_SHORT).show();
            }

            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        id = fAuth.getCurrentUser().getUid();
                        UserModel userModel = new UserModel(username, email, password, 1);
                        StoreModel storeModel = new StoreModel(storeName,id);
                        db.getReference().child("Users").child(id).setValue(userModel);
                        db.getReference().child("Stores").child(id).setValue(storeModel);
                        Toast.makeText(SignUp_Owner.this, "Account Created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), home_seller.class));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(SignUp_Owner.this, "Failed to create account!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), SignUp_Owner.class));
                        finish();
                    }
                }
            });
        });
    }
}