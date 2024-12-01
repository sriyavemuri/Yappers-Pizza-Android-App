package com.example.yapperspizza;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {

    private EditText orderInput;
    private TextView orderResult;
    private Spinner pizzaTypeSpinner, pizzaSizeSpinner, pizzaStyleSpinner;
    private String selectedPizzaType; // To store the selected pizza type
    private String selectedPizzaSize; // To store the selected pizza size
    private String selectedPizzaStyle; // To store the selected pizza style
    private CheckBox pepperoniCheckBox, mushroomsCheckBox, olivesCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_view);

        // Initialize views
        orderInput = findViewById(R.id.orderInput);
        orderResult = findViewById(R.id.orderResult);
        pizzaTypeSpinner = findViewById(R.id.pizzaTypeSpinner);
        pizzaSizeSpinner = findViewById(R.id.pizzaSizeSpinner);
        pizzaStyleSpinner = findViewById(R.id.pizzaStyleSpinner);
        Button placeOrderButton = findViewById(R.id.placeOrderButton);
        Button clearSelectionButton = findViewById(R.id.clearSelectionButton);

        // Setup dropdown (Spinner) for pizza types
        String[] pizzaTypes = getResources().getStringArray(R.array.pizza_types);
        ArrayAdapter<String> pizzaTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pizzaTypes);
        pizzaTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pizzaTypeSpinner.setAdapter(pizzaTypeAdapter);

        // Setup dropdown (Spinner) for pizza styles
        String[] pizzaStyles = getResources().getStringArray(R.array.pizza_styles);
        ArrayAdapter<String> pizzaStyleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pizzaStyles);
        pizzaStyleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pizzaStyleSpinner.setAdapter(pizzaStyleAdapter);

        // Setup dropdown (Spinner) for pizza sizes
        String[] pizzaSizes = getResources().getStringArray(R.array.pizza_sizes);
        ArrayAdapter<String> pizzaSizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pizzaSizes);
        pizzaSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pizzaSizeSpinner.setAdapter(pizzaSizeAdapter);

        // Handle pizza type selection
        pizzaTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPizzaType = pizzaTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedPizzaType = null;
            }
        });

        // Handle pizza style selection
        pizzaStyleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPizzaStyle = pizzaStyles[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedPizzaStyle = null;
            }
        });

        // Handle pizza size selection
        pizzaSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPizzaSize = pizzaSizes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedPizzaSize = null;
            }
        });

        // Set up toppings checkboxes (if needed)
        pepperoniCheckBox = findViewById(R.id.pepperoniCheckBox);
        mushroomsCheckBox = findViewById(R.id.mushroomsCheckBox);
        olivesCheckBox = findViewById(R.id.olivesCheckBox);

        // Handle place order button click
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePlaceOrder();
            }
        });

        // Handle clear selection button click
        clearSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelections();
            }
        });
    }

    private void handlePlaceOrder() {
        String customizations = orderInput.getText().toString().trim();

        // Get selected toppings
        StringBuilder toppings = new StringBuilder();
        if (pepperoniCheckBox.isChecked()) toppings.append("Pepperoni ");
        if (mushroomsCheckBox.isChecked()) toppings.append("Mushrooms ");
        if (olivesCheckBox.isChecked()) toppings.append("Olives ");

        // Validate selections
        if (selectedPizzaType == null || selectedPizzaSize == null || selectedPizzaStyle == null) {
            orderResult.setText("Please select pizza type, size, and style!");
            orderResult.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        } else {
            // Construct order summary
            String orderSummary = "Order placed: " + selectedPizzaType + " (" + selectedPizzaSize + ", " + selectedPizzaStyle + ")";
            if (toppings.length() > 0) {
                orderSummary += " with toppings: " + toppings.toString();
            }
            if (!customizations.isEmpty()) {
                orderSummary += " and customizations: " + customizations + ".";
            } else {
                orderSummary += " with no customizations.";
            }

            orderResult.setText(orderSummary);
            orderResult.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        }
    }

    private void clearSelections() {
        pizzaTypeSpinner.setSelection(0);
        pizzaStyleSpinner.setSelection(0);
        pizzaSizeSpinner.setSelection(0);
        orderInput.setText("");
        orderResult.setText("");

        // Reset checkbox states
        pepperoniCheckBox.setChecked(false);
        mushroomsCheckBox.setChecked(false);
        olivesCheckBox.setChecked(false);
    }
}
