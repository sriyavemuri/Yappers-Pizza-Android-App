package com.example.yapperspizza;

import java.util.ArrayList;
/**
 * Represents an order in the pizzeria, containing a list of pizzas and an associated order number.
 * @author Zeel Patel, Sriya Vemuri
 */
public class Order {
    /** Counter to keep track of the total number of orders created. */
    private static int orderCounter = 1;
    /** Unique number assigned to this order. */
    private int orderNumber;
    /** List of pizzas included in this order. */
    private ArrayList<Pizza> pizzas;

    /**
     * Constructs a new Order with a unique order number and an empty list of pizzas.
     */
    public Order() {
        this.orderNumber = orderCounter;
        this.pizzas = new ArrayList<>();
    }

    /**
     * Gets the order number of this order.
     * @return the unique order number
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * Adds a pizza to this order.
     * @param pizza the {@link Pizza} to add
     */
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    /**
     * Removes a pizza from this order.
     * @param pizza the {@link Pizza} to remove
     */
    public void removePizza(Pizza pizza) {
        pizzas.remove(pizza);
    }

    /**
     * Clears all pizzas from this order.
     */
    public void clearPizzas() {
        pizzas.clear();
    }

    /**
     * Gets the list of pizzas in this order.
     * @return an {@link ArrayList} of {@link Pizza} objects
     */
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * Calculates the total cost of the order, including a 6.625% sales tax.
     * @return the total cost of the order
     */
    public double calculateTotal() {
        double total = 0;
        for (Pizza pizza : pizzas) {
            total += pizza.price();
        }
        return total * 1.06625;
    }

    /**
     * Increments the order counter to ensure the next order gets a unique order number.
     */
    public static void incrementOrderCounter() {
        orderCounter++;
    }

    /**
     * Returns a string representation of the Order object.
     * This representation includes the order number.
     *
     * @return A string in the format "Order #<orderNumber>", where <orderNumber> is the
     * unique identifier of the order.
     */
    @Override
    public String toString() {
        return "Order #" + orderNumber; // Ensure orderNumber is a valid field in your Order class
    }

}

