package com.example.yapperspizza;

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
        orders.add(order);
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order newOrder) {
        this.currentOrder = newOrder;
    }
}
