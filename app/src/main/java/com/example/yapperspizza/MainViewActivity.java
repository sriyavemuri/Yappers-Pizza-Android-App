package com.example.yapperspizza;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Buttons
        Button orderMenuButton = findViewById(R.id.orderMenuButton);
        Button orderManagementButton = findViewById(R.id.orderManagementButton);
        Button pastOrdersButton = findViewById(R.id.pastOrdersButton);

        // Navigate to Order Menu Screen
        orderMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, OrderMenuActivity.class);
            startActivity(intent);
        });

        // Navigate to Order Management Screen
        orderManagementButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, OrderManagementActivity.class);
            startActivity(intent);
        });

        // Navigate to Past Orders Screen
        pastOrdersButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PastOrdersActivity.class);
            startActivity(intent);
        });
    }
}