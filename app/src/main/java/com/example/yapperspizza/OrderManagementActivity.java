package com.example.yapperspizza;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderManagementActivity extends AppCompatActivity {

    private ListView orderDetailsListView;
    private TextView subtotalLabel, taxLabel, totalCostLabel;
    private Button removeSelectedPizzaButton, placeOrderButton, clearOrderButton, addAPizzaButton, pastOrdersButton;
    private ArrayList<String> orderDetails;
    private ArrayAdapter<String> orderDetailsAdapter;
    private double subtotal = 0.0;
    private static final double TAX_RATE = 0.06625;
    private DecimalFormat df = new DecimalFormat("#,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_management_view);

        // Initialize Views
        orderDetailsListView = findViewById(R.id.orderDetailsListView);
        subtotalLabel = findViewById(R.id.subtotalLabel);
        taxLabel = findViewById(R.id.taxLabel);
        totalCostLabel = findViewById(R.id.totalCostLabel);
        removeSelectedPizzaButton = findViewById(R.id.removeSelectedPizzaButton);
        placeOrderButton = findViewById(R.id.placeOrderButton);
        clearOrderButton = findViewById(R.id.clearOrderButton);
        addAPizzaButton = findViewById(R.id.addAPizzaButton);
        pastOrdersButton = findViewById(R.id.pastOrdersButton);

        // Initialize Order Details
        orderDetails = new ArrayList<>();
        orderDetailsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, orderDetails);
        orderDetailsListView.setAdapter(orderDetailsAdapter);

        // Set Listeners
        orderDetailsListView.setOnItemClickListener((parent, view, position, id) -> {
            removeSelectedPizzaButton.setEnabled(true);
        });

        removeSelectedPizzaButton.setOnClickListener(v -> removeSelectedPizza());
        placeOrderButton.setOnClickListener(v -> placeOrder());
        clearOrderButton.setOnClickListener(v -> clearOrder());
        addAPizzaButton.setOnClickListener(v -> addPizza());
        pastOrdersButton.setOnClickListener(v -> viewPastOrders());

        updateCosts();
    }

    private void removeSelectedPizza() {
        int position = orderDetailsListView.getCheckedItemPosition();
        if (position != ListView.INVALID_POSITION) {
            String removedPizza = orderDetails.remove(position);
            Toast.makeText(this, "Removed: " + removedPizza, Toast.LENGTH_SHORT).show();
            updateCosts();
            orderDetailsAdapter.notifyDataSetChanged();
            removeSelectedPizzaButton.setEnabled(false);
        }
    }

    private void placeOrder() {
        if (orderDetails.isEmpty()) {
            showAlert("Error", "Cannot place empty order", "Please add at least one pizza.");
        } else {
            showAlert("Success", "Order Placed", "Your order has been placed!");
            orderDetails.clear();
            updateCosts();
            orderDetailsAdapter.notifyDataSetChanged();
        }
    }

    private void clearOrder() {
        orderDetails.clear();
        updateCosts();
        orderDetailsAdapter.notifyDataSetChanged();
    }

    private void addPizza() {
        // Example for adding a new pizza
        String newPizza = "BBQ Chicken SMALL Chicago Style -> Crust: PAN Toppings: [BBQ_CHICKEN, GREEN_PEPPER]";
        orderDetails.add(newPizza);
        Toast.makeText(this, "Added: " + newPizza, Toast.LENGTH_SHORT).show();
        updateCosts();
        orderDetailsAdapter.notifyDataSetChanged();
    }

    private void viewPastOrders() {
        showAlert("Past Orders", "Feature Not Implemented", "This is a placeholder for viewing past orders.");
    }

    private void updateCosts() {
        subtotal = orderDetails.size() * 14.99; // Example: $14.99 per pizza
        double tax = subtotal * TAX_RATE;
        double total = subtotal + tax;

        subtotalLabel.setText("Subtotal: $" + df.format(subtotal));
        taxLabel.setText("Tax: $" + df.format(tax));
        totalCostLabel.setText("Total: $" + df.format(total));
    }

    private void showAlert(String title, String header, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}