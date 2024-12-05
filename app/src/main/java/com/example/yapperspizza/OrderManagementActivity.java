package com.example.yapperspizza;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * OrderManagementActivity: Activity to manage the current order, including adding/removing pizzas
 * and calculating the total order price.
 * @author Zeel Patel, Sriya Vemuri
 */
public class OrderManagementActivity extends AppCompatActivity {
    // XML elements
    private ListView allPizzas;
    private EditText orderNumber, orderSubtotal, orderSalesTax, orderTotal;

    // Activity variables
    private Order currentOrder = new Order();  // Current order being managed
    private StoreOrders storeOrders = new StoreOrders();  // Store orders collection

    /**
     * This method runs OrderManagementActivity.
     * @param savedInstanceState Used to reload UI state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_management_view);  // Make sure this XML exists

        AlertDialog.Builder alert = new AlertDialog.Builder(this);  // For showing alerts
        buildAlert(alert);

        // Initialize UI components
        allPizzas = findViewById(R.id.placedPizzasListView);
        orderNumber = findViewById(R.id.orderNumberEditText);
        orderSubtotal = findViewById(R.id.subtotalEditText);
        orderSalesTax = findViewById(R.id.taxEditText);
        orderTotal = findViewById(R.id.totalEditText);
        Button clearOrder = findViewById(R.id.clearOrderButton);
        Button placeOrder = findViewById(R.id.placeOrderButton);
        Button backToMain = findViewById(R.id.mainButton);

        // Initial order setup (e.g., load from MainActivity or create a new one)
        // Assuming orders were passed or are being initialized in some way

        // Display current order pizzas
        updatePizzas();

        // Display order number
        orderNumber.setText(String.valueOf(currentOrder.getOrderNumber()));

        // Update price details
        updateOrderPrice();

        // Set on-click listener to remove pizza from order
        allPizzas.setOnItemClickListener((adapterView, view, position, id) -> {
            alert.setMessage("Successfully removed pizza from order!");
            AlertDialog confirmation = alert.create();
            confirmation.show();

            // Remove pizza from current order
            Pizza pizzaToRemove = currentOrder.getPizzas().get(position);
            currentOrder.removePizza(pizzaToRemove);

            // Update price and list view
            updateOrderPrice();
            updatePizzas();
        });

        // Clear current order
        clearOrder.setOnClickListener(view -> {
            alert.setMessage("Successfully cleared order!");
            AlertDialog confirmation = alert.create();
            confirmation.show();

            clearActivity();
        });

        // Place order
        placeOrder.setOnClickListener(view -> {
            alert.setMessage("Successfully placed order! Thank you for ordering from Yappers Pizza!");
            AlertDialog confirmation = alert.create();
            confirmation.show();

            // Add the current order to store orders
            storeOrders.addOrder(currentOrder);

            // Clear the activity and return
            clearActivity();
        });

        // Back to main screen
        backToMain.setOnClickListener(view -> {
            Intent intent = new Intent(OrderManagementActivity.this, MainViewActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Updates the ListView to show all pizzas in the current order.
     */
    private void updatePizzas() {
        ArrayList<String> pizzaDetails = new ArrayList<>();
        for (Pizza pizza : currentOrder.getPizzas()) {
            pizzaDetails.add(pizza.toString());
        }

        ArrayAdapter<String> allPizzasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pizzaDetails);
        allPizzas.setAdapter(allPizzasAdapter);
    }

    /**
     * Updates the UI with the order's subtotal, sales tax, and total price.
     */
    private void updateOrderPrice() {
        double subtotal = 0;
        for (Pizza pizza : currentOrder.getPizzas()) {
            subtotal += pizza.price();
        }

        double tax = subtotal * 0.06625;
        double total = subtotal + tax;

        // Format prices
        orderSubtotal.setText(priceFormatter(subtotal));
        orderSalesTax.setText(priceFormatter(tax));
        orderTotal.setText(priceFormatter(total));
    }

    /**
     * Formats the price to include a dollar sign and two decimal places.
     * @param price the price to format
     * @return the formatted price
     */
    private String priceFormatter(double price) {
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        return String.format("$%s", formatter.format(price));
    }

    /**
     * Clears the current order and resets UI elements.
     */
    private void clearActivity() {
        currentOrder.clearPizzas();
        updatePizzas();
        updateOrderPrice();

        orderNumber.setText("####");
        orderSubtotal.setText("$0.00");
        orderSalesTax.setText("$0.00");
        orderTotal.setText("$0.00");

        Intent intent = new Intent(OrderManagementActivity.this, MainViewActivity.class);
        startActivity(intent);
    }

    /**
     * Builds the alert dialog for showing success messages.
     * @param alert the alert dialog builder
     */
    private void buildAlert(AlertDialog.Builder alert) {
        alert.setCancelable(true);
        alert.setPositiveButton("OK", (dialog, id) -> dialog.cancel());
    }
}
