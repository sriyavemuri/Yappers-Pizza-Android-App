package com.example.yapperspizza;


import java.util.ArrayList;
import java.util.Arrays;
/**
 * Represents a Deluxe pizza, which is a predefined pizza type with specific toppings.
 * @author Zeel Patel, Sriya Vemuri
 */
public class Deluxe extends Pizza {
    /**
     * Constructs a Deluxe pizza with a specific crust type.
     * @param crust the type of crust for the Deluxe pizza
     */
    public Deluxe(Crust crust) {
        super(crust);
        ArrayList<Topping> toppingsDeluxe = new ArrayList<>(Arrays.asList(Topping.SAUSAGE, Topping.PEPPERONI,
                Topping.GREEN_PEPPER, Topping.ONION, Topping.MUSHROOM));
        setToppings(toppingsDeluxe);
    }

    /**
     * Calculates the price of the Deluxe pizza based on its size.
     * @return the price of the pizza
     */
    @Override
    public double price() {
        switch (getSize()) {
            case SMALL: return 16.99;
            case MEDIUM: return 18.99;
            case LARGE: return 20.99;
            default: return 16.99;
        }
    }

    /**
     * Provides a string representation of the Deluxe pizza, including its size,
     * style, crust type, and toppings.
     * @return a string describing the Deluxe pizza
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + getSize() + " " + getStyle() + " Style -> Crust: " +
                getCrust() + " Toppings:" + getToppings();
    }
}