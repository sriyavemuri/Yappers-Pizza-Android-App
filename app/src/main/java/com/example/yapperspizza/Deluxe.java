package com.example.yapperspizza;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a Deluxe pizza, which is a predefined pizza type with specific toppings.
 */
public class Deluxe extends Pizza {
    /**
     * Constructs a Deluxe pizza with a specific crust type.
     * @param crust the type of crust for the Deluxe pizza
     */
    public Deluxe(Crust crust) {
        super(crust);
        ArrayList<Topping> toppingsDeluxe = new ArrayList<>(Arrays.asList(
                new Topping("Sausage", R.drawable.ic_sausage),
                new Topping("Pepperoni", R.drawable.ic_pepperoni),
                new Topping("Green Pepper", R.drawable.ic_green_pepper),
                new Topping("Onion", R.drawable.ic_onion),
                new Topping("Mushroom", R.drawable.ic_mushroom)
        ));
        setToppings(toppingsDeluxe);
    }

    @Override
    public double price() {
        switch (getSize()) {
            case SMALL:
                return 16.99;
            case MEDIUM:
                return 18.99;
            case LARGE:
                return 20.99;
            default:
                return 16.99;
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + getSize() + " " + getStyle() + " Style -> Crust: " +
                getCrust() + " Toppings:" + getToppings();
    }
}
