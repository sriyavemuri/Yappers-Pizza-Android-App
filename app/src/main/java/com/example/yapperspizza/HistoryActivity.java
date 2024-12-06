package com.example.yapperspizza;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ListView orderHistoryListView;
    private TextView orderDetailsLabel, statusLabel;
    private Button backToOrderViewButton, cancelOrderButton, backToMainMenuButton;
    private List<Order> orderHistory;
    private ArrayAdapter<String> orderHistoryAdapter;
    private StoreOrders storeOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_view);

        // Initialize views
        orderHistoryListView = findViewById(R.id.orderHistoryListView);
        orderDetailsLabel = findViewById(R.id.orderDetailsLabel);
        statusLabel = findViewById(R.id.statusLabel);
        backToOrderViewButton = findViewById(R.id.backToOrderViewButton);
        cancelOrderButton = findViewById(R.id.cancelOrderButton);

        // Retrieve the shared StoreOrders singleton
        storeOrders = StoreOrders.getInstance();
        orderHistory = storeOrders.getOrders(); // Get the List<Order>

        // Set up adapter for ListView
        orderHistoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, formatOrders(orderHistory));
        orderHistoryListView.setAdapter(orderHistoryAdapter);
        // Display the order history
        displayOrderHistory();

        // Handle ListView item selection
        orderHistoryListView.setOnItemClickListener((parent, view, position, id) -> {
            Order selectedOrder = orderHistory.get(position);
            displayOrderDetails(selectedOrder);
        });

        // Handle button clicks
        backToOrderViewButton.setOnClickListener(v -> backToOrderMenu());
        cancelOrderButton.setOnClickListener(v -> cancelSelectedOrder());
        backToMainMenuButton.setOnClickListener(v -> handleBackToMain());
    }

    /**
     * Formats orders for display in the ListView.
     *
     * @param orders The list of orders.
     * @return A list of formatted strings for display.
     */
    private List<String> formatOrders(List<Order> orders) {
        List<String> formattedOrders = new ArrayList<>();
        for (Order order : orders) {
            formattedOrders.add("Order #" + order.getOrderNumber() + ": " + order.getPizzas().size() + " pizzas");
        }
        return formattedOrders;
    }

    /**
     * Populates the history list with all orders or shows a message if no orders exist.
     */
    private void displayOrderHistory() {
        List<Order> allOrders = StoreOrders.getInstance().getOrders();

        if (allOrders.isEmpty()) {
            // If no orders, display an empty message
            ArrayAdapter<String> emptyAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    new String[]{"No orders available."}
            );
            orderHistoryListView.setAdapter(emptyAdapter); // Use your actual ListView or RecyclerView here
        } else {
            // Populate with orders
            ArrayAdapter<Order> ordersAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    allOrders
            );
            orderHistoryListView.setAdapter(ordersAdapter); // Use your actual ListView or RecyclerView here
        }
    }



    /**
     * Displays the details of the selected order in the TextView.
     *
     * @param selectedOrder The selected order.
     */
    private void displayOrderDetails(Order selectedOrder) {
        if (selectedOrder != null) {
            StringBuilder orderDetails = new StringBuilder("Order #" + selectedOrder.getOrderNumber() + "\n\n");
            for (Pizza pizza : selectedOrder.getPizzas()) {
                orderDetails.append(pizza.toString()).append("\n");
            }
            orderDetails.append("\nOrder Total: $").append(String.format("%.2f", selectedOrder.calculateTotal()));
            orderDetailsLabel.setText(orderDetails.toString());
        } else {
            orderDetailsLabel.setText("Select an order to view details.");
        }
    }

    /**
     * Cancels the selected order and updates the ListView.
     */
    private void cancelSelectedOrder() {
        int selectedIndex = orderHistoryListView.getCheckedItemPosition();
        if (selectedIndex != ListView.INVALID_POSITION) {
            Order orderToRemove = orderHistory.get(selectedIndex);
            storeOrders.getOrders().remove(orderToRemove); // Remove from StoreOrders
            orderHistoryAdapter.clear();
            orderHistoryAdapter.addAll(formatOrders(orderHistory)); // Refresh the adapter
            orderDetailsLabel.setText("Order canceled.");
            Toast.makeText(this, "Order canceled.", Toast.LENGTH_SHORT).show();
        } else {
            statusLabel.setText("Select an order to cancel.");
        }
    }

    /**
     * Navigates back to the main order view.
     */
    private void backToOrderMenu() {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Navigates back to the main menu.
     */
    private void handleBackToMain() {
        Intent intent = new Intent(this, MainViewActivity.class);
        startActivity(intent);
    }


}