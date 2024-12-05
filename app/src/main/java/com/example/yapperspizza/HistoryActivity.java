package com.example.yapperspizza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ListView orderHistoryListView;
    private TextView orderDetailsLabel, statusLabel;
    private Button backToOrderViewButton, cancelOrderButton;

    private ArrayList<Order> orderHistory; // Change to Order list, not String list
    private ArrayAdapter<Order> orderHistoryAdapter; // Change to Order Adapter

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


        // Initialize mock order history
        orderHistory = new ArrayList<>();
        populateOrderHistory(); // Add mock orders for testing

        // Set up adapter for ListView
        orderHistoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, orderHistory);
        orderHistoryListView.setAdapter(orderHistoryAdapter);

        // Handle ListView item selection
        orderHistoryListView.setOnItemClickListener((parent, view, position, id) -> {
            Order selectedOrder = orderHistory.get(position);
            displayOrderDetails(selectedOrder);
        });

        // Handle button clicks
        backToOrderViewButton.setOnClickListener(v -> backToOrderMenu());
        cancelOrderButton.setOnClickListener(v -> cancelSelectedOrder());
    }

    private void populateOrderHistory() {
        // Create pizzas for mock orders
        Pizza bbqChicken = new BBQChicken(Crust.THIN);
        bbqChicken.setSize(Size.MEDIUM);
        Pizza buildYourOwn = new BuildYourOwn(Crust.PAN);
        buildYourOwn.setSize(Size.LARGE);
        ((BuildYourOwn) buildYourOwn).addTopping(Topping.PEPPERONI);
        Pizza deluxe = new Deluxe(Crust.DEEP_DISH);
        deluxe.setSize(Size.SMALL);

        // Create orders
        Order order1 = new Order();
        order1.addPizza(bbqChicken);

        Order order2 = new Order();
        order2.addPizza(buildYourOwn);
        order2.addPizza(deluxe);

        // Add orders to order history
        orderHistory.add(order1);
        orderHistory.add(order2);
    }

    private void displayOrderDetails(Order selectedOrder) {
        if (selectedOrder != null) {
            StringBuilder orderDetails = new StringBuilder("Order #" + selectedOrder.getOrderNumber() + "\n\n");
            for (Pizza pizza : selectedOrder.getPizzas()) {
                orderDetails.append(pizza.toString()).append("\n\n");
            }
            orderDetailsLabel.setText(orderDetails.toString());
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

    private void backToOrderMenu() {
        // Navigate back to Order Menu screen
        Intent intent = new Intent(this, MainViewActivity.class);
        startActivity(intent);
    }
}
