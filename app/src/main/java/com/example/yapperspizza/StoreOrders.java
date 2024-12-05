package com.example.yapperspizza;


import java.util.ArrayList;
import java.util.List;
/**
 * Represents the store-wide collection of all orders.
 * Provides functionality to manage and retrieve orders.
 * @author Zeel Patel, Sriya Vemuri
 */
public class StoreOrders {
    /**
     * A list containing all the orders placed at the store.
     */
    private static StoreOrders instance;
    private List<Order> orders;

    /**
     * Constructs a new {@code StoreOrders} object with an empty list of orders.
     */
    public StoreOrders() {
        this.orders = new ArrayList<>();
    }

    /**
     * Converting Store Orders to a singleton.
     * @return StoreOrders as a singleton
     */
    public static synchronized StoreOrders getInstance() {
        if (instance == null) {
            instance = new StoreOrders();
        }
        return instance;
    }

    /**
     * Adds a new order to the list of store orders.
     * @param order the {@code Order} to be added
     */
    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * Retrieves the list of all orders placed at the store.
     * @return a list of {@code Order} objects
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Retrieves an order by its order number.
     * @param orderNumber the unique number of the order to retrieve
     * @return the {@code Order} object with the specified order number, or {@code null} if no such order exists
     */
    public Order getOrderByNumber(int orderNumber) {
        for (Order order : orders) {
            if (order.getOrderNumber() == orderNumber) {
                return order;
            }
        }
        return null;
    }
}

