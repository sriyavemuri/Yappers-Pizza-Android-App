package com.example.yapperspizza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    private ListView orderHistoryListView;
    private TextView orderDetailsLabel, statusLabel;
    private Button backToOrderViewButton, cancelOrderButton, exportOrdersButton;

    private ArrayList<String> orderHistory; // Mock data for orders
    private ArrayAdapter<String> orderHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        // Initialize views
        orderHistoryListView = findViewById(R.id.orderHistoryListView);
        orderDetailsLabel = findViewById(R.id.orderDetailsLabel);
        statusLabel = findViewById(R.id.statusLabel);
        backToOrderViewButton = findViewById(R.id.backToOrderViewButton);
        cancelOrderButton = findViewById(R.id.cancelOrderButton);
        exportOrdersButton = findViewById(R.id.exportOrdersButton);

        // Initialize mock order history
        orderHistory = new ArrayList<>();
        populateOrderHistory(); // Add mock orders for testing

        orderHistoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, orderHistory);
        orderHistoryListView.setAdapter(orderHistoryAdapter);

        // Handle ListView item selection
        orderHistoryListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedOrder = orderHistory.get(position);
            displayOrderDetails(selectedOrder);
        });

        // Handle button clicks
        backToOrderViewButton.setOnClickListener(v -> backToOrderMenu());
        cancelOrderButton.setOnClickListener(v -> cancelSelectedOrder());
        exportOrdersButton.setOnClickListener(v -> exportOrders());
    }

    private void populateOrderHistory() {
        // Mock data
        orderHistory.add("Order #1: 2 pizzas");
        orderHistory.add("Order #2: 3 pizzas");
        orderHistory.add("Order #3: 1 pizza");
    }

    private void displayOrderDetails(String selectedOrder) {
        if (selectedOrder != null) {
            // Mock details based on selected order
            orderDetailsLabel.setText("Details for " + selectedOrder + "\n\n- Pizza 1: Cheese\n- Pizza 2: Pepperoni");
        } else {
            orderDetailsLabel.setText("Select an order to view details.");
        }
    }

    private void cancelSelectedOrder() {
        int selectedIndex = orderHistoryListView.getCheckedItemPosition();
        if (selectedIndex != ListView.INVALID_POSITION) {
            orderHistory.remove(selectedIndex);
            orderHistoryAdapter.notifyDataSetChanged();
            orderDetailsLabel.setText("Order canceled.");
            Toast.makeText(this, "Order canceled.", Toast.LENGTH_SHORT).show();
        } else {
            statusLabel.setText("Select an order to cancel.");
        }
    }

    private void exportOrders() {
        // Example of exporting orders (mock implementation)
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Export Orders")
                .setMessage("Orders exported successfully.")
                .setPositiveButton("OK", null)
                .show();
    }

    private void backToOrderMenu() {
        // Navigate back to Order Menu screen
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}