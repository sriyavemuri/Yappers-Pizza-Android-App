package pizza_classes;

import java.util.ArrayList;

/**
 * Represents a "Build Your Own" pizza, allowing customers to customize toppings.
 * Inherits from the {@link Pizza} class and provides methods for adding toppings and calculating price.
 * @author Zeel Patel, Sriya Vemuri
 */
public class BuildYourOwn extends Pizza {
    /**
     * Constructs a "Build Your Own" pizza with a specified crust.
     *
     * @param crust the crust for the "Build Your Own" pizza
     */
    public BuildYourOwn(Crust crust) {
        super(crust);
    }

    /**
     * Adds a topping to the pizza.
     * Limits the number of toppings to a maximum of 7.
     * @param topping the topping to be added to the pizza
     */
    public void addTopping(Topping topping) {
        ArrayList<Topping> toppings = getToppings();
        if (toppings.size() < 7) {
            toppings.add(topping);
        } else {
            System.out.println("You can only add up to 7 toppings.");
        }
    }

    /**
     * Calculates the price of the "Build Your Own" pizza.
     * The price includes a base price based on size and an additional charge of $1.69 per topping.
     * A sales tax of 6.625% is applied to the subtotal.
     * @return the total price of the pizza
     */
    @Override
    public double price() {
        ArrayList<Topping> toppings = getToppings();
        double basePrice = 8.99;
        switch (getSize()) {
            case MEDIUM:
                basePrice = 10.99;
                break;
            case LARGE:
                basePrice = 12.99;
                break;
            case SMALL:
            default:
                break;
        }
        double subtotal = basePrice + (toppings.size() * 1.69);
        return subtotal * (1 + 0.06625);
    }

    /**
     * Returns a string representation of the "Build Your Own" pizza.
     * Includes details about size, style, crust, and selected toppings.
     * @return a string representation of the "Build Your Own" pizza
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Build Your Own " + getSize() + " " + getStyle() + " Style -> Crust: " +
                getCrust());
        ArrayList<Topping> toppings = getToppings();
        if (!toppings.isEmpty()) {
            sb.append("  Toppings: ");
            for (Topping topping : toppings) {
                sb.append(topping).append(", ");
            }
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }
}