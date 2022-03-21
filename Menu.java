import java.util.ArrayList;

/**
 * This class represents the usage of the Menu class
 * 
 * @author Yusuf Ali Bin Rayney Azmi
 * @author Choo Jun Ian
 */
public class Menu {
    private ArrayList<Food> menuList;

    /**
     * Constructs an empty Menu class
     */
    Menu () {}

    /**
     * Constructs a Menu class with a list of Food objects
     * @param menuList the list of Food objects
     */
    Menu (ArrayList<Food> menuList) {
        this.menuList = menuList;
    }

    /**
     * Adds a Food object to the Menu
     * @param food the Food object to be added
     */
    public void addFoodtoMenu (Food food) {
        menuList.add(food);
    }

    /**
     * Edits a Food object in the Menu
     * @param foodIndex the ID of the food to be edited
     * @param food the Food object to be edited
     */
    public void editFoodInMenu (int foodIndex, Food food) {
        foodIndex--;
        menuList.set(foodIndex, food);
    }

    /**
     * Removes the Food object from the Menu
     * @param foodIndex index of the food to be removed 
     */
    public void removeFoodfromMenu (int foodIndex) {
        menuList.remove(foodIndex);
    }

    /**
     * Gets the list of all the Food in the Menu
     * @return the list of Food objects
     */
    public ArrayList<Food> getListOfFood () {
        return menuList;     
    }

    /**
     * Displays a Food object in the Menu
     * @param i the index of the Food object in the menu     
     */
    public void getFood (int i) {
        System.out.println (menuList.get(i)); 
    }

    /**
     * Returns the Food object 
     * @param i the index of the food to be returned
     * @return the Food object
     */
    public Food passFood (int i) {
        return menuList.get(i); 
    }
}

