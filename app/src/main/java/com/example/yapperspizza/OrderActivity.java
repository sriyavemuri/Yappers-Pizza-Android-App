package com.example.yapperspizza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderActivity extends AppCompatActivity {
    private Spinner styleSpinner, sizeSpinner, typeSpinner;
    private RecyclerView toppingsRecyclerView, selectedToppingsRecyclerView;
    private TextView currentPizzaCost, totalOrderCost, footerText;
    private Button addToOrderButton, confirmOrderButton, backToMainMenuButton;
    private ToppingAdapter availableToppingsAdapter, selectedToppingsAdapter;
    private EditText currentPizzaCostEditText, totalOrderCostEditText;
    private StoreOrders storeOrders;
    private Order currentOrder;
    private PizzaFactory pizzaFactory;
    private List<Topping> availableToppings;
    private List<Topping> selectedToppings;
    private Pizza currentPizza;
    private static final double TOPPING_COST = 1.69;
    private static final int MAX_TOPPINGS = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_view);

        // Initialize views
        styleSpinner = findViewById(R.id.styleSpinner);
        sizeSpinner = findViewById(R.id.sizeSpinner);
        typeSpinner = findViewById(R.id.typeSpinner);
        toppingsRecyclerView = findViewById(R.id.availableToppingsRecyclerView);
        selectedToppingsRecyclerView = findViewById(R.id.selectedToppingsRecyclerView);
        currentPizzaCostEditText = findViewById(R.id.currentPizzaCostEditText);
        totalOrderCostEditText = findViewById(R.id.totalOrderCostEditText);
        footerText = findViewById(R.id.footerText);
        addToOrderButton = findViewById(R.id.addToOrderButton);
        confirmOrderButton = findViewById(R.id.confirmOrderButton);
        backToMainMenuButton = findViewById(R.id.backToMainMenuButton);

        // Initialize order and toppings
        storeOrders = StoreOrders.getInstance();
        currentOrder = new Order();
        availableToppings = new ArrayList<>();
        selectedToppings = new ArrayList<>();

        initializeToppings();
        setupSpinners();
        setupRecyclerViews();
        addListeners();
    }

    private void initializeToppings() {
        availableToppings.add(new Topping("Sausage", R.drawable.ic_sausage));
        availableToppings.add(new Topping("Pepperoni", R.drawable.ic_pepperoni));
        availableToppings.add(new Topping("Green Pepper", R.drawable.ic_green_pepper));
        availableToppings.add(new Topping("Onion", R.drawable.ic_onion));
        availableToppings.add(new Topping("Mushroom", R.drawable.ic_mushroom));
        availableToppings.add(new Topping("BBQ Chicken", R.drawable.ic_bbq_chicken));
        availableToppings.add(new Topping("Provolone", R.drawable.ic_provolone));
        availableToppings.add(new Topping("Cheddar", R.drawable.ic_cheddar));
        availableToppings.add(new Topping("Beef", R.drawable.ic_beef));
        availableToppings.add(new Topping("Ham", R.drawable.ic_ham));
        availableToppings.add(new Topping("Pineapple", R.drawable.ic_pineapple));
        availableToppings.add(new Topping("Jalapeno", R.drawable.ic_jalapeno));
        availableToppings.add(new Topping("Spinach", R.drawable.ic_spinach));
    }

    private void setupSpinners() {
        ArrayAdapter<String> styleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                new String[]{"New York Style", "Chicago Style"});
        styleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        styleSpinner.setAdapter(styleAdapter);

        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                new String[]{"Small", "Medium", "Large"});
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeAdapter);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                new String[]{"Deluxe", "BBQ Chicken", "Meatzza", "Build Your Own"});
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
    }

    private void setupRecyclerViews() {
        availableToppingsAdapter = new ToppingAdapter(availableToppings, position -> {
            if (selectedToppings.size() < MAX_TOPPINGS) {
                Topping selected = availableToppings.remove(position);
                selectedToppings.add(selected);
                updateRecyclerViews();
                updateCurrentPizzaCost(TOPPING_COST);
            } else {
                Toast.makeText(this, "Maximum of 7 toppings allowed.", Toast.LENGTH_SHORT).show();
            }
        });

        toppingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        toppingsRecyclerView.setAdapter(availableToppingsAdapter);

        selectedToppingsAdapter = new ToppingAdapter(selectedToppings, position -> {
            Topping removed = selectedToppings.remove(position);
            availableToppings.add(removed);
            updateRecyclerViews();
            updateCurrentPizzaCost(-TOPPING_COST);
        });

        selectedToppingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectedToppingsRecyclerView.setAdapter(selectedToppingsAdapter);
    }
    private void addListeners() {
        // Set Pizza Style and Update Pizza Factory
        styleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String style = (String) parent.getItemAtPosition(position);
                pizzaFactory = "New York Style".equals(style) ? new NYPizza() : new ChicagoPizza();
                updateCurrentPizza(); // Recalculate pizza whenever style changes
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String type = (String) adapterView.getItemAtPosition(i);
                boolean isBuildYourOwn = "Build Your Own".equals(type);
                enableToppingControls(isBuildYourOwn);

                if (!isBuildYourOwn) {
                    selectedToppings.clear(); // Clear toppings if switching away from Build Your Own
                    updateRecyclerViews();
                }

                updateCurrentPizza(); // Update pizza whenever type changes
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateCurrentPizza(); // Update pizza whenever size changes
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        addToOrderButton.setOnClickListener(v -> handleAddPizzaToOrder());
        confirmOrderButton.setOnClickListener(v -> handleConfirmOrder());
        backToMainMenuButton.setOnClickListener(v -> handleBackToMain());
    }

    private void updateCurrentPizza() {
        String type = (String) typeSpinner.getSelectedItem();
        String size = (String) sizeSpinner.getSelectedItem();

        if (pizzaFactory != null && type != null && size != null) {
            currentPizza = createPizza();
            updateCurrentPizzaCost(); // Update the cost display
        } else {
            currentPizza = null;
            currentPizzaCostEditText.setText(getString(R.string.default_price));

        }
    }

    private void updateTotalOrderCost() {
        double total = currentOrder.calculateTotal();
        totalOrderCostEditText.setText(String.format(Locale.US, "$%.2f", total));
    }
    private void handleAddPizzaToOrder() {
        Pizza pizza = createPizza();
        if (pizza != null) {
            currentOrder.addPizza(pizza);
            updateTotalOrderCost();
            footerText.setText(getString(R.string.pizza_added_to_order));
        } else {
            footerText.setText(getString(R.string.error_invalid_pizza_configuration));

        }
    }


    private void handleConfirmOrder() {
        if (currentOrder.getPizzas().isEmpty()) {
            footerText.setText(getString(R.string.error_invalid_pizza_configuration));
            return;
        }

        // Add the order to the StoreOrders singleton only once
        StoreOrders storeOrders = StoreOrders.getInstance();
        if (!storeOrders.getOrders().contains(currentOrder)) {
            storeOrders.addOrder(currentOrder);
        }

        // Navigate to Order Management screen
        Intent intent = new Intent(this, OrderManagementActivity.class);
        startActivity(intent);
        finish();
    }

    private Pizza createPizza() {
        if (pizzaFactory == null || typeSpinner.getSelectedItem() == null || sizeSpinner.getSelectedItem() == null) {
            return null;
        }

        String type = (String) typeSpinner.getSelectedItem();
        String size = (String) sizeSpinner.getSelectedItem();
        String style = (String) styleSpinner.getSelectedItem();
        Size pizzaSize = Size.valueOf(size.toUpperCase(Locale.ROOT));
        Pizza pizza = null;

        switch (type) {
            case "Deluxe":
                pizza = pizzaFactory.createDeluxe();
                break;
            case "BBQ Chicken":
                pizza = pizzaFactory.createBBQChicken();
                break;
            case "Meatzza":
                pizza = pizzaFactory.createMeatzza();
                break;
            case "Build Your Own":
                pizza = pizzaFactory.createBuildYourOwn();
                for (Topping topping : selectedToppings) {
                    pizza.addTopping(topping);
                }
                break;
            default:
                footerText.setText(getString(R.string.error_invalid_pizza_configuration));
                return null;
        }

        if (pizza != null) {
            pizza.setSize(pizzaSize);
            pizza.setStyle(style); // Set the style of the pizza
        }

        return pizza;
    }

    private void updateRecyclerViews() {
        availableToppingsAdapter.notifyDataSetChanged();
        selectedToppingsAdapter.notifyDataSetChanged();
    }

    private void updateCurrentPizzaCost(double costChange) {
        String currentCostText = currentPizzaCostEditText.getText().toString();
        String numericPart = currentCostText.replaceAll("[^0-9.]", ""); // Extract numeric part
        try {
            double currentCost = Double.parseDouble(numericPart);
            currentCost += costChange;
            currentPizzaCostEditText.setText(String.format(Locale.US, "$%.2f", currentCost));
        } catch (NumberFormatException e) {
            currentPizzaCostEditText.setText(getString(R.string.default_price)); // Reset if parsing fails
        }
    }

    private void updateCurrentPizzaCost() {
        if (currentPizza != null) {
            double baseCost = currentPizza.price();
            double toppingsCost = selectedToppings.size() * TOPPING_COST;
            double totalCost = baseCost + toppingsCost;
            currentPizzaCostEditText.setText(String.format(Locale.US, "$%.2f", totalCost));
        } else {
            currentPizzaCostEditText.setText(getString(R.string.default_price));
        }
    }
    private void enableToppingControls(boolean isBuildYourOwn) {
        int visibility = isBuildYourOwn ? View.VISIBLE : View.GONE;
        toppingsRecyclerView.setVisibility(visibility);
        selectedToppingsRecyclerView.setVisibility(visibility);
    }

    /**
     * Navigates back to the main menu.
     */
    private void handleBackToMain() {
        Intent intent = new Intent(this, MainViewActivity.class);
        startActivity(intent);
    }

}