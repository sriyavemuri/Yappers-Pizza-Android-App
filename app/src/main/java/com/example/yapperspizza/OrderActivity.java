package com.example.yapperspizza;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Activity for managing pizza orders.
 * Handles pizza creation, toppings selection, and order placement.
 * @author Sriya Vemuri
 */
public class OrderActivity extends AppCompatActivity {
    private Spinner styleSpinner, sizeSpinner, typeSpinner;
    private ListView availableToppingsListView, selectedToppingsListView;
    private TextView currentPizzaCost, totalOrderCost, footerText;
    private Button addToppingButton, removeToppingButton, addToOrderButton, placeOrderButton;

    private StoreOrders storeOrders;
    private Order currentOrder;
    private PizzaFactory pizzaFactory;
    private ArrayList<String> availableToppings;
    private ArrayList<String> selectedToppings;

    private static final double TOPPING_COST = 1.69;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_view);

        // Initialize views
        styleSpinner = findViewById(R.id.styleSpinner);
        sizeSpinner = findViewById(R.id.sizeSpinner);
        typeSpinner = findViewById(R.id.typeSpinner);
        availableToppingsListView = findViewById(R.id.availableToppingsListView);
        selectedToppingsListView = findViewById(R.id.selectedToppingsListView);
        currentPizzaCost = findViewById(R.id.currentPizzaCost);
        totalOrderCost = findViewById(R.id.totalOrderCost);
        footerText = findViewById(R.id.footerText);
        addToppingButton = findViewById(R.id.addToppingButton);
        removeToppingButton = findViewById(R.id.removeToppingButton);
        addToOrderButton = findViewById(R.id.addToOrderButton);
        placeOrderButton = findViewById(R.id.placeOrderButton);

        // Initialize order and toppings
        storeOrders = StoreOrders.getInstance();
        currentOrder = new Order();
        availableToppings = new ArrayList<>();
        selectedToppings = new ArrayList<>();
        initializeToppings();

        // Set up spinners
        setupSpinners();

        // Add listeners
        addListeners();
    }

    private void initializeToppings() {
        String[] toppingsArray = {"SAUSAGE", "PEPPERONI", "GREEN_PEPPER", "ONION", "MUSHROOM", "BBQ_CHICKEN",
                "PROVOLONE", "CHEDDAR", "BEEF", "HAM", "PINEAPPLE", "JALAPENO", "SPINACH"};
        for (String topping : toppingsArray) {
            availableToppings.add(topping);
        }

        ArrayAdapter<String> availableAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, availableToppings);
        availableToppingsListView.setAdapter(availableAdapter);

        ArrayAdapter<String> selectedAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedToppings);
        selectedToppingsListView.setAdapter(selectedAdapter);
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

    private void addListeners() {
        styleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String style = (String) parent.getItemAtPosition(position);
                pizzaFactory = style.equals("New York Style") ? new NYPizza() : new ChicagoPizza();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = (String) parent.getItemAtPosition(position);
                boolean isBuildYourOwn = type.equals("Build Your Own");
                availableToppingsListView.setEnabled(isBuildYourOwn);
                selectedToppingsListView.setEnabled(isBuildYourOwn);
                addToppingButton.setEnabled(isBuildYourOwn);
                removeToppingButton.setEnabled(isBuildYourOwn);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

//        addToppingButton.setOnClickListener(v -> handleAddTopping());
//        removeToppingButton.setOnClickListener(v -> handleRemoveTopping());
        addToOrderButton.setOnClickListener(v -> handleAddPizzaToOrder());
        placeOrderButton.setOnClickListener(v -> handlePlaceOrder());

        availableToppingsListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTopping = availableToppings.get(position);
            if (selectedToppings.size() < 7 && !selectedToppings.contains(selectedTopping)) {
                selectedToppings.add(selectedTopping);
                availableToppings.remove(selectedTopping);
                updateToppingsList();
                updateCurrentPizzaCost(TOPPING_COST);
            } else {
                Toast.makeText(this, "Cannot add more than 7 toppings or duplicate toppings.", Toast.LENGTH_SHORT).show();
            }
        });

        selectedToppingsListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTopping = selectedToppings.get(position);
            selectedToppings.remove(selectedTopping);
            availableToppings.add(selectedTopping);
            updateToppingsList();
            updateCurrentPizzaCost(-TOPPING_COST);
        });

    }

    private void handleAddPizzaToOrder() {
        Pizza pizza = createPizza();
        if (pizza != null) {
            currentOrder.addPizza(pizza);
            updateTotalOrderCost();
            footerText.setText("Pizza added to order!");
        } else {
            footerText.setText("Error: Invalid pizza configuration.");
        }
    }

    private void handlePlaceOrder() {
        if (currentOrder.getPizzas().isEmpty()) {
            footerText.setText("Error: No pizzas in order.");
            return;
        }
        storeOrders.addOrder(currentOrder);
        currentOrder = new Order();
        updateTotalOrderCost();
        footerText.setText("Order placed successfully!");
    }

    private Pizza createPizza() {
        if (pizzaFactory == null || typeSpinner.getSelectedItem() == null || sizeSpinner.getSelectedItem() == null) {
            return null;
        }

        String type = (String) typeSpinner.getSelectedItem();
        String size = (String) sizeSpinner.getSelectedItem();
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
                for (String topping : selectedToppings) {
                    Topping toppingEnum = Topping.valueOf(topping.toUpperCase());
                    pizza.addTopping(toppingEnum);
                }
                break;
            default:
                footerText.setText("Error: Invalid pizza type selected.");
                return null;
        }

        if (pizza != null) {
            pizza.setSize(pizzaSize);
        }
        return pizza;
    }


    private void updateToppingsList() {
        ((ArrayAdapter<String>) availableToppingsListView.getAdapter()).notifyDataSetChanged();
        ((ArrayAdapter<String>) selectedToppingsListView.getAdapter()).notifyDataSetChanged();
    }

    private void updateCurrentPizzaCost(double costChange) {
        String currentCostText = currentPizzaCost.getText().toString(); // Convert to String
        double currentCost = Double.parseDouble(currentCostText.replace("$", ""));
        currentCost += costChange;
        currentPizzaCost.setText(String.format(Locale.US, "$%.2f", currentCost));
    }


    private void updateTotalOrderCost() {
        double total = currentOrder.calculateTotal();
        totalOrderCost.setText(String.format(Locale.US, "$%.2f", total));
    }

    private void addToppingToSelected() {
        int selectedIndex = availableToppingsListView.getCheckedItemPosition();
        if (selectedIndex >= 0 && selectedToppings.size() < 7) {
            String topping = availableToppings.get(selectedIndex);
            availableToppings.remove(topping);
            selectedToppings.add(topping);
            updateToppingsList();
            updateCurrentPizzaCost(TOPPING_COST);
        } else if (selectedToppings.size() >= 7) {
            Toast.makeText(this, "Maximum 7 toppings allowed.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please select a topping to add.", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeToppingFromSelected() {
        int selectedIndex = selectedToppingsListView.getCheckedItemPosition();
        if (selectedIndex >= 0) {
            String topping = selectedToppings.get(selectedIndex);
            selectedToppings.remove(topping);
            availableToppings.add(topping);
            updateToppingsList();
            updateCurrentPizzaCost(-TOPPING_COST);
        } else {
            Toast.makeText(this, "Please select a topping to remove.", Toast.LENGTH_SHORT).show();
        }
    }

    private void enableToppingControls(boolean isBuildYourOwn) {
        availableToppingsListView.setEnabled(isBuildYourOwn);
        selectedToppingsListView.setEnabled(isBuildYourOwn);
        addToppingButton.setEnabled(isBuildYourOwn);
        removeToppingButton.setEnabled(isBuildYourOwn);
    }


}
