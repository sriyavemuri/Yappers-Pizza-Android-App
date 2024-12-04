package pizza_classes;
/**
 * Represents a factory for creating Chicago-style pizzas.
 * Implements the {@link PizzaFactory} interface to provide various types of pizzas with Chicago-specific crusts.
 * @author Zeel Patel, Sriya Vemuri
 */
public class ChicagoPizza implements PizzaFactory {
    /**
     * Creates a Deluxe pizza with a deep-dish crust.
     * @return a new {@link Deluxe} pizza with {@link Crust#DEEP_DISH}
     */
    @Override
    public Pizza createDeluxe() {
        return new Deluxe(Crust.DEEP_DISH);
    }

    /**
     * Creates a BBQ Chicken pizza with a pan crust.
     * @return a new {@link BBQChicken} pizza with {@link Crust#PAN}
     */
    @Override
    public Pizza createBBQChicken() {
        return new BBQChicken(Crust.PAN);
    }

    /**
     * Creates a Meatzza pizza with a stuffed crust.
     * @return a new {@link Meatzza} pizza with {@link Crust#STUFFED}
     */
    @Override
    public Pizza createMeatzza() {
        return new Meatzza(Crust.STUFFED);
    }

    /**
     * Creates a "Build Your Own" pizza with a pan crust.
     * @return a new {@link BuildYourOwn} pizza with {@link Crust#PAN}
     */
    @Override
    public Pizza createBuildYourOwn() {
        return new BuildYourOwn(Crust.PAN);
    }
}

