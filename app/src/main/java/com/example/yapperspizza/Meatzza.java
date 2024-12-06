package com.example.yapperspizza;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a Meatzza pizza, which is a predefined pizza type with specific toppings.
 */
public class Meatzza extends Pizza {
    public Meatzza(Crust crust) {
        super(crust);
        ArrayList<Topping> toppingsMeatzza = new ArrayList<>(Arrays.asList(
                new Topping("Sausage", R.drawable.ic_sausage),
                new Topping("Pepperoni", R.drawable.ic_pepperoni),
                new Topping("Beef", R.drawable.ic_beef),
                new Topping("Ham", R.drawable.ic_ham)
        ));
        setToppings(toppingsMeatzza);
    }

    @Override
    public double price() {
        switch (getSize()) {
            case SMALL:
                return 17.99;
            case MEDIUM:
                return 19.99;
            case LARGE:
                return 21.99;
            default:
                return 17.99;
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + getSize() + " " + getStyle() + " Style -> Crust: " +
                getCrust() + " Toppings:" + getToppingNames();
    }
}
