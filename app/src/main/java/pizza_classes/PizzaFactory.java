package pizza_classes;

/**
 * Represents a factory for creating different types of pizzas.
 * Classes implementing this interface provide specific implementations for
 * creating various pizza types with predefined configurations.
 * @author Zeel Patel, Sriya Vemuri
 */
public interface PizzaFactory {
    /**
     * Creates a Deluxe pizza with predefined crust and toppings.
     * @return a {@link Pizza} object representing a Deluxe pizza
     */
    Pizza createDeluxe();

    /**
     * Creates a BBQ Chicken pizza with predefined crust and toppings.
     * @return a {@link Pizza} object representing a BBQ Chicken pizza
     */
    Pizza createBBQChicken();

    /**
     * Creates a Meatzza pizza with predefined crust and toppings.
     * @return a {@link Pizza} object representing a Meatzza pizza
     */
    Pizza createMeatzza();

    /**
     * Creates a Build Your Own pizza with a customizable crust and toppings.
     * @return a {@link Pizza} object representing a Build Your Own pizza
     */
    Pizza createBuildYourOwn();
}

