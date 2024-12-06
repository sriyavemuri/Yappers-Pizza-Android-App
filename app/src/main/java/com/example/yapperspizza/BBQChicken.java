package com.example.yapperspizza;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a BBQ Chicken pizza, which is a predefined pizza type with specific toppings.
 */
public class BBQChicken extends Pizza {
    public BBQChicken(Crust crust) {
        super(crust);
        ArrayList<Topping> toppingsBBQ = new ArrayList<>(Arrays.asList(
                new Topping("BBQ Chicken", R.drawable.ic_bbq_chicken),
                new Topping("Green Pepper", R.drawable.ic_green_pepper),
                new Topping("Provolone", R.drawable.ic_provolone),
                new Topping("Onion", R.drawable.ic_onion)
        ));
        setToppings(toppingsBBQ);
    }

    @Override
    public double price() {
        switch (getSize()) {
            case SMALL:
                return 14.99;
            case MEDIUM:
                return 16.99;
            case LARGE:
                return 19.99;
            default:
                return 14.99;
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + getSize() + " " + getStyle() + " Style -> Crust: " +
                getCrust() + " Toppings:" + getToppingNames();
    }
}
