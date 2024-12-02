package com.example.yapperspizza;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    private Spinner styleSpinner, sizeSpinner, typeSpinner;
    private ListView availableToppingsListView, selectedToppingsListView;
    private ArrayAdapter<String> availableToppingsAdapter, selectedToppingsAdapter;
    private ArrayList<String> availableToppings, selectedToppings;
    private TextView currentPizzaCost, totalOrderCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_view);

        // Initialize components
        styleSpinner = findViewById(R.id.styleSpinner);
        sizeSpinner = findViewById(R.id.sizeSpinner);
        typeSpinner = findViewById(R.id.typeSpinner);
        availableToppingsListView = findViewById(R.id.availableToppingsListView);
        selectedToppingsListView = findViewById(R.id.selectedToppingsListView);
        currentPizzaCost = findViewById(R.id.currentPizzaCost);
        totalOrderCost = findViewById(R.id.totalOrderCost);

        setupSpinners();
        setupToppingsLists();

        // Add functionality to buttons
        findViewById(R.id.addToppingButton).setOnClickListener(v -> addSelectedTopping());
        findViewById(R.id.removeToppingButton).setOnClickListener(v -> removeSelectedTopping());
    }

    private void setupSpinners() {
        // Dropdown data
        ArrayAdapter<String> styleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"New York Style", "Chicago Style"});
        styleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        styleSpinner.setAdapter(styleAdapter);

        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Small", "Medium", "Large"});
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeAdapter);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Deluxe", "BBQ Chicken", "Meatzza", "Build Your Own"});
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        // Handle type selection
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = typeSpinner.getSelectedItem().toString();
                toggleToppingsSelection("Build Your Own".equals(selectedType));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupToppingsLists() {
        availableToppings = new ArrayList<>();
        selectedToppings = new ArrayList<>();
        populateAvailableToppings();

        availableToppingsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, availableToppings);
        availableToppingsListView.setAdapter(availableToppingsAdapter);

        selectedToppingsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedToppings);
        selectedToppingsListView.setAdapter(selectedToppingsAdapter);
    }

    private void populateAvailableToppings() {
        String[] toppings = {"SAUSAGE", "PEPPERONI", "GREEN_PEPPER", "ONION", "MUSHROOM",
                "BBQ_CHICKEN", "PROVOLONE", "CHEDDAR", "BEEF", "HAM",
                "PINEAPPLE", "JALAPENO", "SPINACH"};
        availableToppings.clear();
        availableToppings.addAll(java.util.Arrays.asList(toppings));
    }

    private void toggleToppingsSelection(boolean enable) {
        availableToppingsListView.setEnabled(enable);
        selectedToppingsListView.setEnabled(enable);
        findViewById(R.id.addToppingButton).setEnabled(enable);
        findViewById(R.id.removeToppingButton).setEnabled(enable);
    }

    private void addSelectedTopping() {
        int position = availableToppingsListView.getCheckedItemPosition();
        if (position >= 0) {
            String topping = availableToppings.remove(position);
            selectedToppings.add(topping);
            refreshToppingsLists();
            updateCurrentPizzaCost(1.69); // Example cost per topping
        }
    }

    private void removeSelectedTopping() {
        int position = selectedToppingsListView.getCheckedItemPosition();
        if (position >= 0) {
            String topping = selectedToppings.remove(position);
            availableToppings.add(topping);
            refreshToppingsLists();
            updateCurrentPizzaCost(-1.69); // Example cost adjustment
        }
    }

    private void refreshToppingsLists() {
        availableToppingsAdapter.notifyDataSetChanged();
        selectedToppingsAdapter.notifyDataSetChanged();
    }

    private void updateCurrentPizzaCost(double amount) {
        String currentText = currentPizzaCost.getText().toString().replace("$", "");
        double currentCost = Double.parseDouble(currentText);
        currentCost += amount;
        currentPizzaCost.setText(String.format("$%.2f", currentCost));
    }
}