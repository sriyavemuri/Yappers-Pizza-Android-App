package com.example.yapperspizza;

import android.util.Log;

import java.util.ArrayList;

public class StoreOrders {
    private static StoreOrders instance;
    private ArrayList<Order> orders;
    private Order currentOrder;

    private StoreOrders() {
        orders = new ArrayList<>();
        currentOrder = new Order(); // Start with a new order
    }

    public static StoreOrders getInstance() {
        if (instance == null) {
            instance = new StoreOrders();
        }
        return instance;
    }

    public void addOrder(Order order) {
        if (!orders.contains(order)) {
            orders.add(order);
            order.setOrderNumber(orders.size()); // Dynamically set order number
        }
    }


    public ArrayList<Order> getOrders() {
        Log.d("StoreOrders", "Current orders: " + orders.size());
        return orders;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order newOrder) {
        this.currentOrder = newOrder;
    }

    public void clearOrders() {
        orders.clear();
    }

    public Order getLatestOrder() {
        if (orders.isEmpty()) {
            return new Order(); // Return a new empty order if no orders exist
        }
        return orders.get(orders.size() - 1); // Return the most recent order
    }

}
