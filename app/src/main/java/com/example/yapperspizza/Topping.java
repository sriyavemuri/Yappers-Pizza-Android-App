package com.example.yapperspizza;

/**
 * Represents a topping for a pizza, with a name and associated image.
 * Used for dynamically handling toppings in the application.
 * @author Sriya Vemuri
 */
public class Topping {
    private String name;      // The name of the topping
    private int imageResId;   // The resource ID for the topping's image

    /**
     * Constructs a new Topping with the given name and image resource ID.
     * @param name the name of the topping
     * @param imageResId the resource ID for the topping's image
     */
    public Topping(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    /**
     * Gets the name of the topping.
     * @return the name of the topping
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the topping.
     * @param name the new name of the topping
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the image resource ID for the topping.
     * @return the image resource ID for the topping
     */
    public int getImageResId() {
        return imageResId;
    }

    /**
     * Sets the image resource ID for the topping.
     * @param imageResId the new image resource ID for the topping
     */
    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
