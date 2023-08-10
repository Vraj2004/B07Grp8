package com.example.myfirstapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginModel extends AppCompatActivity implements Contract.Model{
    DatabaseReference dbRef;
    FirebaseAuth mAuth;
    Contract.View view;
    public LoginModel(Contract.View view)
    {
        this.view = view;
        dbRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public String getId()
    {
        return mAuth.getUid();
    }

    @Override
    public void login() {
        mAuth.signInWithEmailAndPassword(view.getEmail(), view.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DatabaseReference userRef = dbRef.child("Users").child(getId());
                        userRef.child("isOwner").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int value = 0;
                                if (dataSnapshot.exists() && dataSnapshot.getValue(Integer.class) == 1) {
                                    value = 1;
                                }
                                if (value == 1) {
                                    view.storeLogin();
                                } else {
                                    view.custLogin();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                view.failure();
                            }
                        });
                    } else {
                        view.failure();
                    }
                });
    }

//    public int login(){
//        // view.handleError("Please fill all fields");
//        mAuth = FirebaseAuth.getInstance();
//        final AtomicInteger value = new AtomicInteger(0);
//        mAuth.signInWithEmailAndPassword(view.getEmail(),view.getPassword()).addOnSuccessListener(authResult -> {
//            // view.makeToast("Login Successful");
//            DatabaseReference userRef = dbRef.child("Users").child(getId());
//            System.out.println("YAYYY");
//            userRef.child("isOwner").addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    System.out.println("VALUE BEFORE" + value);
//                    if (dataSnapshot.exists() && dataSnapshot.getValue(Integer.class) == 1) {
//                        value.set(1);
//                    }
//                    System.out.println("VALUE AFTER" + value);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    // Handle error case here
//                    System.out.println("NOOO");
//                }
//            });
//
//        }).addOnFailureListener(e -> {
//            System.out.println("REAHCED FAILURE");
//            view.failure();
//            value.set(2);
//        });
//
//        return value.get();
//    }
}
