package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

public class checkoutPage extends AppCompatActivity {

    Button order;
    EditText firstName, lastName, emailAddress, Address;
    EditText postalCode, creditCardNumber, csv, expiryDate;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_page);

        order = findViewById(R.id.finalOrderButton);

        order.setOnClickListener(view -> {
            Intent intent= new Intent(getApplicationContext(), Home_Customer.class);
            startActivity(intent);
            finish();
        });

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        Address = findViewById(R.id.address);
        postalCode = findViewById(R.id.postalCode);
        creditCardNumber = findViewById(R.id.cardInfo);
        csv = findViewById(R.id.csv);
        expiryDate = findViewById(R.id.expiryDate);

        order.setEnabled(false);

        firstName.addTextChangedListener(textWatcher);
        lastName.addTextChangedListener(textWatcher);
        Address.addTextChangedListener(textWatcher);
        postalCode.addTextChangedListener(textWatcher);
        creditCardNumber.addTextChangedListener(textWatcher);
        csv.addTextChangedListener(textWatcher);
        expiryDate.addTextChangedListener(textWatcher);

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String first = firstName.getText().toString();
            String last = lastName.getText().toString();
            String addy = Address.getText().toString();
            String postal = postalCode.getText().toString();
            String card = creditCardNumber.getText().toString();
            String CSV = csv.getText().toString();
            String expiry = expiryDate.getText().toString();

            if (!first.isEmpty() && !last.isEmpty() && !addy.isEmpty() && !postal.isEmpty() && !card.isEmpty() &&
                    !CSV.isEmpty() && !expiry.isEmpty()) {
                order.setEnabled(true);
            } else {
                order.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}