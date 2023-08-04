package com.example.myfirstapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class home_customer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_customer);

        final ImageButton home_button = (ImageButton) findViewById(R.id.home_button);

        home_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), home_customer.class);
                startActivity(i);

            }
        });

        final ImageButton cart_button = (ImageButton) findViewById(R.id.orders_button);

        cart_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),item_cart.class);
                startActivity(i);

            }
        });

        final ImageButton my_orders_button = (ImageButton) findViewById(R.id.my_orders_button);

        my_orders_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), MyOrders.class);
                startActivity(i);

            }
        });

        final ImageButton account_button = (ImageButton) findViewById(R.id.account_button);

        account_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),Account.class);
                startActivity(i);

            }
        });
    }
}
