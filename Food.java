/**
 * This class represents the usage of the Food class
 * 
 * @author Yusuf Ali Bin Rayney Azmi
 * @author Choo Jun Ian
 */

public class Food {
    private int foodID;
    private String foodName;
    private double price;   

    /**
     * Constructs a Food object with no fields.
     */
    Food () {}

    /**
     * Constructs a Food object with its respective fields
     * @param foodID the ID of the food 
     * @param foodName the name of the food
     * @param price the price of the food
     */
    Food (int foodID, String foodName, double price) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.price = price;
    }

    /**
     * Gets the ID of the food
     * @return the foodID
     */
    public int getFoodID () {
        return foodID;
    }

    /**
     * Gets the name of the food
     * @return the foodName
     */
    public String getFoodName () {
        return foodName;
    }

    /**
     * Gets the price of the food
     * @return the price
     */
    public double getFoodPrice () {
        return price;
    }

    /**
     * Displays a String representation of the foodID, foodName and price
     * @return foodID, foodName and price in String form
     */
    public String toString () {
        return foodID + "   " + foodName + " \tRM" + price;
    }

    /**
     * Convert the food's ID, Name and price to comma-separated values
     * @return the foodID, foodName and price in comma-separated form
     */
    public String toCSVString() {
        return foodID + "," + foodName + "," + price;
    }
    
}