package pizza_classes;

/**
 * Represents a factory for creating various types of pizzas in the New York style.
 * Implements the {@link PizzaFactory} interface.
 * @author Zeel Patel, Sriya Vemuri
 */
public class NYPizza implements PizzaFactory {
    /**
     * Creates a Deluxe pizza with a Brooklyn-style crust.
     * @return a new {@link Deluxe} pizza instance
     */
    @Override
    public Pizza createDeluxe() {
        return new Deluxe(Crust.BROOKLYN);
    }

    /**
     * Creates a BBQ Chicken pizza with a thin crust.
     * @return a new {@link BBQChicken} pizza instance
     */
    @Override
    public Pizza createBBQChicken() {
        return new BBQChicken(Crust.THIN);
    }

    /**
     * Creates a Meatzza pizza with a hand-tossed crust.
     * @return a new {@link Meatzza} pizza instance
     */
    @Override
    public Pizza createMeatzza() {
        return new Meatzza(Crust.HAND_TOSSED);
    }

    /**
     * Creates a Build Your Own pizza with a hand-tossed crust.
     * @return a new {@link BuildYourOwn} pizza instance
     */
    @Override
    public Pizza createBuildYourOwn() {
        return new BuildYourOwn(Crust.HAND_TOSSED);
    }
}
