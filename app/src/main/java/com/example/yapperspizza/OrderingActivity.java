package com.example.yapperspizza;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OrderingActivity extends AppCompatActivity {

    private Spinner styleSpinner, sizeSpinner, typeSpinner;
    private Button addToOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_view);  // Ensure this is your OrderActivity layout

        // Initialize Spinners by finding them in the layout
        styleSpinner = findViewById(R.id.styleComboBox);
        sizeSpinner = findViewById(R.id.sizeComboBox);
        typeSpinner = findViewById(R.id.typeComboBox);

        // Initialize the buttons
        addToOrderButton = findViewById(R.id.addToOrderButton);

        // Create and set the adapter for Style Spinner
        ArrayAdapter<CharSequence> styleAdapter = ArrayAdapter.createFromResource(this,
                R.array.pizza_styles, android.R.layout.simple_spinner_item);
        styleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        styleSpinner.setAdapter(styleAdapter);

        // Create and set the adapter for Size Spinner
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(this,
                R.array.pizza_sizes, android.R.layout.simple_spinner_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeAdapter);

        // Create and set the adapter for Type Spinner
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.pizza_types, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        // Optional: Set listeners for handling selection events
        styleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedStyle = parentView.getItemAtPosition(position).toString();
                Toast.makeText(OrderingActivity.this, "Selected Style: " + selectedStyle, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if no item is selected
            }
        });

        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedSize = parentView.getItemAtPosition(position).toString();
                Toast.makeText(OrderingActivity.this, "Selected Size: " + selectedSize, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if no item is selected
            }
        });

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedType = parentView.getItemAtPosition(position).toString();
                Toast.makeText(OrderingActivity.this, "Selected Type: " + selectedType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if no item is selected
            }
        });

        // Handle the button click event for adding the pizza to the order
        addToOrderButton.setOnClickListener(v -> {
            String selectedStyle = styleSpinner.getSelectedItem().toString();
            String selectedSize = sizeSpinner.getSelectedItem().toString();
            String selectedType = typeSpinner.getSelectedItem().toString();

            // You can perform any action here like adding to a cart, etc.
            Toast.makeText(OrderingActivity.this, "Pizza Added: " + selectedStyle + " " + selectedSize + " " + selectedType, Toast.LENGTH_LONG).show();
        });
    }
}
