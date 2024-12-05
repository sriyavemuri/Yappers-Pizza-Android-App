package com.example.yapperspizza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainViewActivity extends AppCompatActivity {

    private Button orderMenuButton, orderManagementButton, pastOrdersButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);  // This is the layout you provided

        // Initialize buttons
        orderMenuButton = findViewById(R.id.orderMenuButton);
        orderManagementButton = findViewById(R.id.orderManagementButton);
        pastOrdersButton = findViewById(R.id.pastOrdersButton);

        // Set up button listeners for navigation
        orderMenuButton.setOnClickListener(v -> navigateToOrderMenu());
        orderManagementButton.setOnClickListener(v -> navigateToOrderManagement());
        pastOrdersButton.setOnClickListener(v -> navigateToPastOrders());
    }

    // Navigate to the Order Menu screen (example with intent)
    private void navigateToOrderMenu() {
        Intent intent = new Intent(MainViewActivity.this, OrderActivity.class);  // Assuming you have OrderMenuActivity
        startActivity(intent);
    }

    // Navigate to the Order Management screen (example with intent)
    private void navigateToOrderManagement() {
        Intent intent = new Intent(MainViewActivity.this, OrderManagementActivity.class);  // Assuming you have OrderManagementActivity
        startActivity(intent);
    }

    // Navigate to the Past Orders screen (example with intent)
    private void navigateToPastOrders() {
        Intent intent = new Intent(MainViewActivity.this, HistoryActivity.class);  // Assuming you have PastOrdersActivity
        startActivity(intent);
    }
}
