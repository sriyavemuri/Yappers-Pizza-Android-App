package com.example.yapperspizza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderManagementActivity extends AppCompatActivity {

    private ListView placedPizzasListView;
    private EditText subtotalEditText, taxEditText, totalEditText;
    private Button clearOrderButton, placeOrderButton, mainButton;
    private ArrayAdapter<String> pizzaAdapter;

    private Order currentOrder;
    private StoreOrders storeOrders;
    private DecimalFormat df = new DecimalFormat("#,##0.00");
    private static final double TAX_RATE = 0.06625;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_management_view);

        // Initialize views
        placedPizzasListView = findViewById(R.id.placedPizzasListView);
        subtotalEditText = findViewById(R.id.subtotalEditText);
        taxEditText = findViewById(R.id.taxEditText);
        totalEditText = findViewById(R.id.totalEditText);
        clearOrderButton = findViewById(R.id.clearOrderButton);
        placeOrderButton = findViewById(R.id.placeOrderButton);
        mainButton = findViewById(R.id.mainButton);

        // Initialize data
        currentOrder = new Order();
        storeOrders = StoreOrders.getInstance();

        // Set up ListView adapter
        pizzaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        placedPizzasListView.setAdapter(pizzaAdapter);

        // Add listeners
        clearOrderButton.setOnClickListener(v -> handleClearOrder());
        placeOrderButton.setOnClickListener(v -> handlePlaceOrder());
        mainButton.setOnClickListener(v -> handleBackToMain());

        // Update the view
        updateOrderView();
    }

    /**
     * Updates the ListView and cost fields based on the current order.
     */
    private void updateOrderView() {
        pizzaAdapter.clear();
        for (Pizza pizza : currentOrder.getPizzas()) {
            pizzaAdapter.add(pizza.toString());
        }
        updateCosts();
    }

    /**
     * Updates the subtotal, tax, and total cost fields.
     */
    private void updateCosts() {
        double subtotal = calculateSubtotal();
        double tax = subtotal * TAX_RATE;
        double total = subtotal + tax;

        subtotalEditText.setText("$" + df.format(subtotal));
        taxEditText.setText("$" + df.format(tax));
        totalEditText.setText("$" + df.format(total));
    }

    /**
     * Calculates the subtotal cost of all pizzas in the order.
     *
     * @return the subtotal
     */
    private double calculateSubtotal() {
        double subtotal = 0;
        for (Pizza pizza : currentOrder.getPizzas()) {
            subtotal += pizza.price();
        }
        return subtotal;
    }

    /**
     * Clears the current order.
     */
    private void handleClearOrder() {
        currentOrder.clearPizzas();
        updateOrderView();
        Toast.makeText(this, "Order cleared.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Places the current order and adds it to the store-wide orders.
     */
    private void handlePlaceOrder() {
        if (currentOrder.getPizzas().isEmpty()) {
            Toast.makeText(this, "Cannot place an empty order.", Toast.LENGTH_SHORT).show();
            return;
        }

        storeOrders.addOrder(currentOrder);
        currentOrder = new Order(); // Reset the current order
        updateOrderView();
        Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Navigates back to the main menu.
     */
    private void handleBackToMain() {
        Intent intent = new Intent(this, MainViewActivity.class);
        startActivity(intent);
    }
}
