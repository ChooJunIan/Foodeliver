import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class represents the usage of the Restaurant class
 * 
 * @author Yusuf Ali Bin Rayney Azmi
 * @author Choo Jun Ian
 */

public class Restaurant {
    private int restID, orderCount;
    private String restName;
    private Menu menu;
    Scanner input = new Scanner(System.in);

    /**
     * Constructs an empty Restaurant object
     */
    Restaurant () {}
    
    /**
     * Constructs a Restaurant object with an ID, name, and Menu 
     * @param restID
     * @param restName
     * @param thisMenu
     */
    Restaurant (int restID, String restName, Menu thisMenu) {
        this.restID = restID;
        this.restName = restName;
        this.menu = thisMenu;
    }

    /**
     * Gets the name of the Restaurant object
     * @return the Restaurant name
     */
    public String getRestName () {
        return restName;
    }

    /**
     * Gets the ID of the Restaurant object
     * @return the Restaurant ID
     */
    public int getRestID () {
        return restID;
    }

    /**
     * Gets the number of orders made to this Restaurant
     * @return the number of orders of the Restaurant
     */
    public int getOrderCount(){
        return orderCount;
    }

    /**
     * Sets the number of orders made to this Restaurant
     * @param orderCount the number of orders made to this restaurant
     */
    public void setOrderCount(int orderCount){
        this.orderCount = orderCount;
    }

    /**
     * Displays the menu to manage the Menu
     * @param r the Restaurant object
     * @throws IOException if the file containing the food for the restaurant is not found, it throws an exception
     */
    public static void manageMainMenu (Restaurant r) throws IOException {
        Scanner input = new Scanner(System.in);
        int manageFoodChoice;
        do {
            try {
                
                System.out.println ("Select your option (enter '0' to exit this menu): ");
                System.out.println ("1. Add Food to Menu");
                System.out.println ("2. Update Food Menu");
                System.out.println ("3. Remove Food from Menu");
                System.out.println ("0. Exit");
                System.out.print ("Choice: ");
                manageFoodChoice = input.nextInt();
                input.nextLine();
                System.out.println ();
                
                switch(manageFoodChoice) {
                    case 1: 
                    System.out.print ("Please enter the id of the food: ");
                    int id = input.nextInt();
                    input.nextLine();
                    System.out.print ("Please enter the name of the food: ");
                    String name = input.nextLine();
                    System.out.print ("Please enter the price of the food: ");
                    double price = input.nextDouble();
                    r.addFood(id, name, price, r.restID);
                    break;
                    
                    case 2: 
                    r.getMenu();
                    System.out.print ("Please select the index of the food to be edited: ");
                    int foodIndex = input.nextInt();
                    input.nextLine();
                    System.out.print ("Please enter the name of the food: ");
                    String newName = input.nextLine();
                    System.out.print ("Please enter the price of the food: ");
                    double newPrice = input.nextDouble();
                    r.updateFood(foodIndex, newName, newPrice, r.restID);
                    break;
                    
                    case 3: 
                    r.getMenu();
                    System.out.print ("Please select the index of the food to be removed: ");
                    foodIndex = input.nextInt();
                    input.nextLine();
                    r.removeFood(foodIndex, r.restID);
                    break;
                    
                }
            } catch (IOException ex) {
                System.out.println ("File not found");
                System.out.println ();
                manageFoodChoice = -1;
            }
        } while (manageFoodChoice != 0);
    }

    /**
     * Adds a Food object to the Menu of the restaurant
     * @param foodID the ID of the Food
     * @param fName the Name of the Food
     * @param fPrice the Price of the Food
     * @param restID the Restaurant where the food is to be added
     * @throws IOException if the file containing the food for the restaurant is not found, it throws an exception
     */
    private void addFood (int foodID, String fName, double fPrice, int restID) throws IOException {
        menu.addFoodtoMenu(new Food(foodID, fName,fPrice));
        saveFoodToFile(menu.getListOfFood(), restID);
        System.out.println ("Food successfully added!");
        System.out.println ();
    }

    /**
     * Edits a Food object already present in the Menu
     * @param foodIndex the index of the food to be edited
     * @param newName the new Name of the Food
     * @param newPrice the new Price of the Food
     * @param restID the Restaurant ID where the Food is edited
     * @throws IOException if the file containing the food for the restaurant is not found, it throws an exception
     */
    private void updateFood (int foodIndex, String newName, double newPrice, int restID) throws IOException {
        Food newFood = new Food(foodIndex, newName, newPrice);
        menu.editFoodInMenu(foodIndex, newFood);
        saveFoodToFile(menu.getListOfFood(), restID);
        System.out.println ("Food successfully edited!");
        System.out.println ();
    }

    /**
     * Removes a Food object from the menu
     * @param foodIndex the index of the Food to be removed
     * @param restChoice the Restaurant ID where the food is removed
     * @throws IOException if the file containing the food for the restaurant is not found, it throws an exception
     */
    private void removeFood (int foodIndex, int restChoice) throws IOException { 
        foodIndex--;
        menu.removeFoodfromMenu(foodIndex);
        saveFoodToFile(menu.getListOfFood(), restChoice);
        System.out.println ("Food successfully removed!");
        System.out.println ();
    }

    /**
     * Displays the list of Food in the Menu
     */
    public void getMenu () {
        System.out.println ("Food Menu");
        System.out.printf ("%-4s%-20s%-5s%n","ID","Food","Price");
        for (int i = 0; i < menu.getListOfFood().size(); i++) {
            menu.getFood(i);     
        }
        System.out.println ();
    }

    /**
     * Changes the status of an Order object
     * @param orderList the list of all orders
     */
    public void changeOrderStatus(ArrayList<Order> orderList){
        Order order = new Order();
        for(int i = 0; i < orderList.size(); i++){
            order = orderList.get(i);
            if (this.restID == order.getRestID())
            order.printOrder();
        }

        System.out.println("Please choose an id to change status: ");
        int selID = input.nextInt();
        input.nextLine();
        order = orderList.get(selID-1);

        if(order.getOrderStatus().equals("Collected"))
        {
            System.out.println("Order has already been completed, please choose another one. ");    
        }
        else{
            System.out.println("What would you like to change it to?");
            System.out.println("1. Preparing");
            System.out.println("2. Ready");
            System.out.println("3. Delivering");
            System.out.println("4. Collected");
            System.out.print ("Choice: ");
            int selStatus = input.nextInt();
            input.nextLine();
            System.out.println ();
            
            String status = "";
    
            switch(selStatus){
                case 1: status = "Preparing"; break;
                case 2: status = "Ready"; break;
                case 3: status = "Delivering"; break;
                case 4: status = "Collected"; break;
            }
    
            
            if(status.equals(order.getOrderStatus()))
            System.out.println("The status is already set to " + status);
            else
            order.changeOrderStatus(status);
        }
    }

    /**
     * Returns the Menu object
     * @return the Menu object of the Restaurant
     */
    public Menu passMenu () {
        return menu;
    }

    /**
     * Saves the Food object to a file
     * @param foods the list of all food
     * @param restID the Restaurant ID
     * @throws IOException if the file containing the food for the restaurant is not found, it throws an exception
     */
    private static void saveFoodToFile(List<Food> foods, int restID) throws IOException {
        // write a list of lines into Restaurant1.csv
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < foods.size(); i++) {
            sb.append (foods.get(i).toCSVString() + "\n");
        }
        if (restID == 1 ) 
            Files.write(Paths.get("Restaurant1Food.csv"), sb.toString().getBytes());
        else if (restID == 2)
            Files.write(Paths.get("Restaurant2Food.csv"), sb.toString().getBytes());
        else if (restID == 3)
            Files.write(Paths.get("Restaurant3Food.csv"), sb.toString().getBytes());
    }

    /**
     * Displays the order history of a Restaurant 
     * @param orderList the list of all Orders
     */
    public void printRestOrderHistoryStatus(ArrayList<Order> orderList){
        Order order = new Order();
        
        for(int i = 0; i < orderList.size(); i++){
        order = orderList.get(i);
            if(this.restID == order.getRestID()){
                order.printOrder();
            }
        }
    }
    
}