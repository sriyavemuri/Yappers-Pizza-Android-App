package com.example.yapperspizza;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a BBQ Chicken pizza, which is a specialized type of {@link Pizza}.
 * It comes with predefined toppings and varying prices based on size.
 * @author Zeel Patel, Sriya Vemuri
 */
public class BBQChicken extends Pizza {
    /**
     * Constructs a BBQ Chicken pizza with a specified crust.
     * The pizza is initialized with predefined toppings: BBQ Chicken, Green Pepper, Provolone, and Cheddar.
     * @param crust the crust for the BBQ Chicken pizza
     */
    public BBQChicken(Crust crust) {
        super(crust);
        ArrayList<Topping> toppingsBBQChicken = new ArrayList<>(Arrays.asList(Topping.BBQ_CHICKEN, Topping.GREEN_PEPPER, Topping.PROVOLONE, Topping.CHEDDAR));
        setToppings(toppingsBBQChicken);
    }

    /**
     * Calculates the price of the BBQ Chicken pizza based on its size.
     * @return the price of the pizza, determined by its size
     */
    @Override
    public double price() {
        switch (getSize()) {
            case SMALL: return 14.99;
            case MEDIUM: return 16.99;
            case LARGE: return 19.99;
            default: return 14.99;
        }
    }

    /**
     * Returns a string representation of the BBQ Chicken pizza.
     * The string includes the pizza's class name, size, style, crust, and toppings.
     * @return a string representation of the BBQ Chicken pizza
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + getSize() + " " + getStyle() + " Style -> Crust: " +
                getCrust() + " Toppings:" + getToppings();
    }
}
