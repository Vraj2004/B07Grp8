//package com.example.myfirstapp;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.graphics.drawable.AnimationDrawable;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.textfield.TextInputEditText;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//public class Login extends AppCompatActivity {
//
//    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://b07-final-project-dbff5-default-rtdb.firebaseio.com/");
//    FirebaseAuth mAuth = FirebaseAuth.getInstance();
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        final EditText editEmail = findViewById(R.id.email);
//        final EditText editPassword = findViewById(R.id.password);
//        final Button btn = findViewById(R.id.login_btn);
//        final TextView textView = findViewById(R.id.new_sign_up);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String email, password;
//                email = editEmail.getText().toString();
//                password = editPassword.getText().toString();
//
//                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ) {
//                    Toast.makeText(Login.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                        String id = mAuth.getUid();
//                        DatabaseReference userRef = dbRef.child("Users").child(id);
//                        userRef.child("isOwner").addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                if (dataSnapshot.exists() && dataSnapshot.getValue(Integer.class) == 1) {
//                                    startActivity(new Intent(getApplicationContext(), home_seller.class));
//                                    finish();
//                                } else {
//                                    startActivity(new Intent(getApplicationContext(), Home_Customer.class));
//                                    finish();
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//                                // Handle error case here
//                            }
//                        });
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(Login.this, "Failed to Login", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(getApplicationContext(), Login.class));
//                        finish();
//                    }
//                });
//            }
//        });
//
//        textView.setOnClickListener(view -> {
//            Intent intent = new Intent(getApplicationContext(), WhoAreYouPage.class);
//            startActivity(intent);
//        });
//    }
//}
package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity implements Contract.View{

    TextInputEditText editEmail, editPassword;
    Button btn;
    TextView signUp;
    String email, password;
    Contract.Model model;
    Contract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        btn = findViewById(R.id.login_btn);
        signUp = findViewById(R.id.new_sign_up);
        model = new LoginModel(this);
        presenter = new LoginPresenter(this, model);

        btn.setOnClickListener(view -> presenter.login());
        signUp.setOnClickListener(view -> {
            signUp();
        });

    }
    public void makeToast(String toast){
        Toast.makeText(Login.this, toast, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getEmail() {
        email = editEmail.getText().toString();
        return email;

    }

    @Override
    public String getPassword() {
        password = editPassword.getText().toString();
        return password;
    }
    @Override
    public void custLogin() {
        startActivity(new Intent(Login.this, Home_Customer.class));
            }

    @Override
    public void storeLogin() {
        startActivity(new Intent(Login.this, home_seller.class));

    }
    @Override
    public void handleError(String error){
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ) {
            makeToast(error);
        }
    }

    @Override
    public void failure(){
        makeToast("Login Failed");
        startActivity(new Intent(this, Login.class));
        finish();
    }
    @Override
    public void signUp()
    {
        startActivity(new Intent(this, WhoAreYouPage.class));
        finish();
    }

    @Override
    public void login(){
        model.login();
    }
}
