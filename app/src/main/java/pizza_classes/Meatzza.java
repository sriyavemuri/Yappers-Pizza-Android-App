package pizza_classes;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * Represents a Meatzza pizza, which is a predefined pizza type with specific toppings.
 * @author Zeel Patel, Sriya Vemuri
 */
public class Meatzza extends Pizza {
    /**
     * Constructs a Meatzza pizza with a specific crust type.
     * @param crust the type of crust for the Meatzza pizza
     */
    public Meatzza(Crust crust) {
        super(crust);
        ArrayList<Topping> toppingsMeatzza = new ArrayList<>(Arrays.asList(Topping.SAUSAGE, Topping.PEPPERONI, Topping.BEEF, Topping.HAM));
        setToppings(toppingsMeatzza);
    }

    /**
     * Calculates the price of the Meatzza pizza based on its size.
     * @return the price of the pizza
     */
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

    /**
     * Provides a string representation of the Meatzza pizza, including its size,
     * style, crust type, and toppings.
     * @return a string describing the Meatzza pizza
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + getSize() + " " + getStyle() + " Style -> Crust: " +
                getCrust() + " Toppings:" + getToppings();
    }
}
