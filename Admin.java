import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents the usage of the Admin class
 * 
 * @author Yusuf Ali Bin Rayney Azmi
 * @author Choo Jun Ian
 */

public class Admin{
    static Scanner input = new Scanner(System.in);
    
    /**
     * The AdminRole displays the Admin Main Menu
     * @param riderList the list of all riders 
     * @param riderQueue the queue of the riders
     * @param orderList the list of all orders
     * @param restaurantList the list of all restaurants
     * @param customerList the list of all customers
     */
    public void AdminRole(ArrayList<Rider> riderList, customQueue<Rider> riderQueue, ArrayList<Order> orderList, ArrayList<Restaurant> restaurantList, CustomerList customerList){        
        int sel = 0;
        do{
            System.out.println("What do u wanna do?");
            System.out.println("1. Add rider");
            System.out.println("2. View riders");
            System.out.println("3. View rider queue");
            System.out.println("4. View all order history and status");
            System.out.println("5. View order statistics");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            sel = input.nextInt();
            input.nextLine();
            System.out.println();

            switch(sel){
                case 1: createRider(riderList, riderQueue); break;

                case 2: printRiders(riderList); break;
                
                case 3: viewRiderQueue(riderQueue); break;

                case 4: printAllOrderHistoryStatus(orderList); break;

                case 5: viewStatistics(riderList, orderList, restaurantList, customerList); break;
            }
        }while(sel != 0);
    }

    /**
     * The viewStatistics displays the menu to view the statistics of the system
     * @param riderList the list of all riders
     * @param orderList the list of all orders
     * @param restaurantList the list of all restaurants 
     * @param customerList the list of all customers
     */
    private static void viewStatistics(ArrayList<Rider> riderList, ArrayList<Order> orderList, ArrayList<Restaurant> restaurantList, CustomerList customerList){
        int sel = 0;
        do{
            System.out.println("Which one would you like to see: ");
            System.out.println("1. Rider with the highest delivery count");
            System.out.println("2. Restaurant with the highest order count");
            System.out.println("3. Customer with the highest order count");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            sel = input.nextInt();
            input.nextLine();
            System.out.println();

            switch(sel){
                case 1: riderStats(riderList, orderList); break;

                case 2: restStats(orderList, restaurantList); break;
                
                case 3: custStats(orderList, customerList); break;
            }
        }while(sel != 0);
    }
    
    /**
     * The createRider creates a new rider and adds it to the Rider List and queue
     * @param riderList the list of all riders 
     * @param riderQueue the queue of the riders 
     */
    public static void createRider(ArrayList<Rider> riderList, customQueue<Rider> riderQueue){
        String riderID, riderName;
        
        System.out.print("Enter Rider ID: ");
        riderID = input.nextLine();
        
        System.out.print("Enter Rider Name: ");
        riderName = input.nextLine();
        
        Rider rider = new Rider();
        rider.setRiderDetails(riderID, riderName, "In queue");
        System.out.println("Successfully added!");
        System.out.println ();
        
        riderList.add(rider);
        riderQueue.add(rider);
    } 
    
    /**
     * The printRiders displays all the current riders 
     * @param riderList the list of all riders
     */
    public static void printRiders(ArrayList<Rider> riderList){
        Rider printedRider = new Rider();
        for(int i = 0; i < riderList.size(); i++){
            printedRider = riderList.get(i);
            printedRider.printDetails();
        } 
    }

    /**
     * The viewRiderQueue shows the current queue of the riders
     * @param riderQueue the queue of the riders
     */
    public static void viewRiderQueue(customQueue<Rider> riderQueue){
        Rider printedRider = new Rider();
		System.out.println ("Rider List");
		for(int i = 0; i < riderQueue.getSize(); i++){
            printedRider = riderQueue.get(i);
            printedRider.printDetails();
        } 
    }

    /**
     * The printAllOrderHistoryStatus displays all the orders that were made
     * @param orderList the list of all orders
     */
    public void printAllOrderHistoryStatus(ArrayList<Order> orderList){
        Order order = new Order();
        for(int i = 0; i < orderList.size(); i++){
            order = orderList.get(i);
            order.printOrder();
        }
    }

    /**
     * The custStats counts the order count for each customer
     * @param orderList the list of all orders
     * @param customerList the list of all customers
     */
    private static void custStats(ArrayList<Order> orderList, CustomerList customerList){
        ArrayList<Customer> sortedCustomers = customerList.getListOfCustomer();
        
        Customer customer = new Customer();
        Order order = new Order();
        
        for(int i = 0; i < sortedCustomers.size(); i++){
            customer = sortedCustomers.get(i);
            int occurence = 0;
            for(int j = 0; j < orderList.size(); j++){
                order = orderList.get(j);
                if(customer.getCustID()  == order.getCustID()){
                    occurence += 1;
                }
            }
            customer.setCustOrderCount(occurence);
        }
        compareCustomers(sortedCustomers);
    }

    /**
     * Displays the customer with the highest order count
     * @param sortedCustomers the array to compare the customers
     */
    private static void compareCustomers(ArrayList<Customer> sortedCustomers){
        Customer c1 = new Customer();
        Customer c2 = new Customer();
        boolean sorted = false;
        
        while(!sorted){
            sorted = true;
            for(int i = 0; i < sortedCustomers.size() - 1; i++){
                c1 = sortedCustomers.get(i);
                c2 = sortedCustomers.get(i+1);
                if(c1.getCustOrderCount() < c2.getCustOrderCount()){
                    sortedCustomers.set(i, c2);
                    sortedCustomers.set(i+1, c1);
                    sorted = false;
                }
            }
        }
        
        c1 = sortedCustomers.get(0);
        System.out.println("The customer with the highest order count is: ");
        System.out.println("Order count: " + c1.getCustOrderCount());
        System.out.println("Customer ID: " + c1.getCustID());        
        System.out.println("Customer name: " + c1.getCustName());
        System.out.println();
    }

    /**
     * The restStats counts the order count for each restaurants
     * @param orderList the list of all orders
     * @param restaurantList the list of all restaurants
     */
    private static void restStats(ArrayList<Order> orderList, ArrayList<Restaurant> restaurantList){
        ArrayList<Restaurant> sortedRestaurants = restaurantList;
        
        Restaurant restaurant = new Restaurant();
        Order order = new Order();
        
        for(int i = 0; i < sortedRestaurants.size(); i++){
            restaurant = sortedRestaurants.get(i);
            int occurence = 0;
            for(int j = 0; j < orderList.size(); j++){
                order = orderList.get(j);
                if(restaurant.getRestID() == order.getRestID() ){
                    occurence += 1;
                }
            }
            restaurant.setOrderCount(occurence);
        }
        compareRestaurants(sortedRestaurants);
    }

    /**
     * Displays the restaurant with the highest order count.
     * @param sortedRestaurants the array to compare the restaurants
     */
    private static void compareRestaurants(ArrayList<Restaurant> sortedRestaurants){
        Restaurant r1 = new Restaurant();
        Restaurant r2 = new Restaurant();
        boolean sorted = false;
        
        while(!sorted){
            sorted = true;
            for(int i = 0; i < sortedRestaurants.size() - 1; i++){
                r1 = sortedRestaurants.get(i);
                r2 = sortedRestaurants.get(i+1);
                if(r1.getOrderCount() < r2.getOrderCount()){
                    sortedRestaurants.set(i, r2);
                    sortedRestaurants.set(i+1, r1);
                    sorted = false;
                }
            }
        }
        
        r1 = sortedRestaurants.get(0);
        System.out.println("Restaurant with the highest order count is: ");
        System.out.println("Order count: " + r1.getOrderCount());
        System.out.println("Restaurant ID: " + r1.getRestID());        
        System.out.println("Restaurant name: " + r1.getRestName());
        System.out.println();
    }

    /**
     * The riderStats counts the delivery count
     * @param riderList the list of all riders
     * @param orderList the list of all orders
     */
    private static void riderStats(ArrayList<Rider> riderList, ArrayList<Order> orderList){
        ArrayList<Rider> sortedRiders = riderList;
        
        Rider rider = new Rider();
        Order order = new Order();
        
        for(int i = 0; i < sortedRiders.size(); i++){
            rider = sortedRiders.get(i);
            int occurence = 0;
            for(int j = 0; j < orderList.size(); j++){
                order = orderList.get(j);
                if(rider.getID().equals(order.getAssignedRider())){
                    occurence += 1;
                }
            }
            rider.setDeliveryCount(occurence);
        }
        compareRiders(sortedRiders);
    }

    /**
     * Displays the rider with the highest delivery count
     * @param sortedRiders the array to compare the riders
     */
    private static void compareRiders(ArrayList<Rider> sortedRiders){
        Rider rider1 = new Rider();
        Rider rider2 = new Rider();
        boolean sorted = false;
        
        while(!sorted){
            sorted = true;
            for(int i = 0; i < sortedRiders.size() - 1; i++){
                rider1 = sortedRiders.get(i);
                rider2 = sortedRiders.get(i+1);
                if(rider1.getDeliveryCount() < rider2.getDeliveryCount()){
                    sortedRiders.set(i, rider2);
                    sortedRiders.set(i+1, rider1);
                    sorted = false;
                }
            }
        }
        
        rider1 = sortedRiders.get(0);
        System.out.println("Rider with the highest delivery count is: ");
        System.out.println("Delivery count: " + rider1.getDeliveryCount());        
        rider1.printDetails();
        System.out.println();
    }
}
