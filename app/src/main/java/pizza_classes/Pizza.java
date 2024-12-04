package pizza_classes;


import java.util.ArrayList;
/**
 * Represents an abstract base class for different types of pizzas.
 * Contains shared attributes and behaviors for all pizzas.
 * @author Zeel Patel, Sriya Vemuri
 */
public abstract class Pizza {
    /** List of toppings added to the pizza. */
    private ArrayList<Topping> toppings;
    /** Crust type of the pizza. */
    private Crust crust;
    /** Size of the pizza (e.g., Small, Medium, Large). */
    private Size size;
    /** Style of the pizza (e.g., New York, Chicago). */
    private String style;

    /**
     * Calculates the price of the pizza. Implemented by subclasses.
     * @return the price of the pizza
     */
    public abstract double price();

    /**
     * Constructs a pizza with the specified crust type.
     * @param crust the {@link Crust} type for the pizza
     */
    public Pizza(Crust crust) {
        this.crust = crust;
        this.toppings = new ArrayList<>();
    }

    /**
     * Gets the crust type of the pizza.
     * @return the {@link Crust} type
     */
    public Crust getCrust() {
        return crust;
    }

    /**
     * Gets the size of the pizza.
     * @return the {@link Size} of the pizza
     */
    public Size getSize() {
        return size;
    }

    /**
     * Sets the size of the pizza.
     * @param size the {@link Size} to set
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Gets the list of toppings on the pizza.
     * @return an {@link ArrayList} of {@link Topping} objects
     */
    public ArrayList<Topping> getToppings() {
        return toppings;
    }

    /**
     * Sets the list of toppings on the pizza.
     * @param toppings an {@link ArrayList} of {@link Topping} objects to set
     */
    protected void setToppings(ArrayList<Topping> toppings) {
        this.toppings = toppings;
    }

    /**
     * Gets the style of the pizza.
     * @return the style of the pizza
     */
    public String getStyle() {
        return style;
    }

    /**
     * Sets the style of the pizza.
     * @param style the style to set (e.g., "New York", "Chicago")
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * Adds a topping to the pizza.
     * @param topping the {@link Topping} to add
     */
    public void addTopping(Topping topping) {
        toppings.add(topping);
    }

    /**
     * Returns a string representation of the pizza. Implemented by subclasses.
     * @return a string describing the pizza
     */
    @Override
    public abstract String toString();
}

