package com.example.yapperspizza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class OrderManagementActivity extends AppCompatActivity {

    private ListView placedPizzasListView;
    private EditText subtotalEditText, taxEditText, totalEditText;
    private Button clearOrderButton, placeOrderButton, mainButton, removePizzaButton;
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
        removePizzaButton = findViewById(R.id.removePizzaButton);

        storeOrders = StoreOrders.getInstance();
        currentOrder = storeOrders.getLatestOrder();

        // Set up ListView adapter
        pizzaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        placedPizzasListView.setAdapter(pizzaAdapter);
        placedPizzasListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Add listeners
        clearOrderButton.setOnClickListener(v -> handleClearOrder());
        placeOrderButton.setOnClickListener(v -> handlePlaceOrder());
        mainButton.setOnClickListener(v -> handleBackToMain());
        removePizzaButton.setOnClickListener(v -> handleRemovePizza());

        // Update the view
        updateOrderView();
        updateOrderNumber(); // Add this call
    }

    /**
     * Updates the ListView and cost fields based on the current order.
     */
    private void updateOrderView() {
        pizzaAdapter.clear(); // Clear the current items in the adapter
        if (currentOrder != null) {
            for (Pizza pizza : currentOrder.getPizzas()) {
                pizzaAdapter.add(pizza.toString()); // Add pizzas with proper toString output
            }
        }
        pizzaAdapter.notifyDataSetChanged(); // Notify the adapter of changes
        updateCosts(); // Update the cost fields
    }

    /**
     * Updates the subtotal, tax, and total cost fields.
     */
    private void updateCosts() {
        double subtotal = calculateSubtotal();
        double tax = subtotal * TAX_RATE;
        double total = subtotal + tax;


        String formattedSubtotal = "$" + df.format(subtotal);
        String formattedTax = "$" + df.format(tax);
        String formattedTotal = "$" + df.format(total);


        subtotalEditText.setText(getString(R.string.subtotal_label, formattedSubtotal));
        taxEditText.setText(getString(R.string.tax_label, formattedTax));
        totalEditText.setText(getString(R.string.total_label, formattedTotal));

    }

    private void updateOrderNumber() {
        int orderIndex = storeOrders.getOrders().indexOf(currentOrder);
        int orderNumber = orderIndex + 1; // Index starts at 0, so add 1
        EditText orderNumberEditText = findViewById(R.id.orderNumberEditText);
        orderNumberEditText.setText(String.valueOf(orderNumber));
    }

    private void handleRemovePizza() {
        int selectedPosition = placedPizzasListView.getCheckedItemPosition(); // Get the selected item position
        if (selectedPosition == ListView.INVALID_POSITION) {
            Toast.makeText(this, "Please select a pizza to remove.", Toast.LENGTH_SHORT).show();
            return;
        }

        Pizza pizzaToRemove = currentOrder.getPizzas().get(selectedPosition); // Get the selected pizza
        currentOrder.removePizza(pizzaToRemove); // Remove the pizza from the order

        updateOrderView(); // Update the ListView and cost fields
        Toast.makeText(this, "Pizza removed successfully.", Toast.LENGTH_SHORT).show();
    }


    /**
     * Calculates the subtotal cost of all pizzas in the order.
     *
     * @return the subtotal
     */
    private double calculateSubtotal() {
        double subtotal = 0;
        if (currentOrder != null) {
            for (Pizza pizza : currentOrder.getPizzas()) {
                subtotal += pizza.price();
            }
        }
        return subtotal;
    }

    /**
     * Clears the current order.
     */
    private void handleClearOrder() {
        if (currentOrder == null || currentOrder.getPizzas().isEmpty()) {
            Toast.makeText(this, "Nothing to be cleared.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (currentOrder != null) {
            Iterator<Pizza> iterator = currentOrder.getPizzas().iterator();
            while (iterator.hasNext()) {
                iterator.next();
                iterator.remove(); // Safely remove the current pizza
            }
        }
        updateOrderView();
        Toast.makeText(this, "Order cleared.", Toast.LENGTH_SHORT).show();
    }


    /**
     * Places the current order and adds it to the store-wide orders.
     */
    private void handlePlaceOrder() {
        if (currentOrder == null || currentOrder.getPizzas().isEmpty()) {
            Toast.makeText(this, "Cannot place an empty order.", Toast.LENGTH_SHORT).show();
            return;
        }

        // storeOrders.addOrder(currentOrder);
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
