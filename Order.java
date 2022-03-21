import java.util.ArrayList;

/**
 * This class represents the usage of the Order class
 * 
 * @author Yusuf Ali Bin Rayney Azmi
 * @author Choo Jun Ian
 */
public class Order {
    private int orderID, custID, restID, isDelivery;
    private String riderID, orderStatus, foodOrdered, foodQuantity;     
    private double totalBill;
    private Restaurant r;
    private ArrayList<Food> foodList = new ArrayList<>();
    private Menu m;

    /**
     * Constructs an empty Order object
     */
    Order () {}

    /**
     * Constructs an Order object
     * @param foodOrdered the ID of the food ordered.
     * @param foodQuantity the quantity of the food ordered.
     * @param orderStatus the status of the order
     * @param restID the ID of the restaurant where the order was made to
     * @param custID the ID of te customer where the order was made from
     * @param isDelivery to check if order is delivery or self collect
     * @param r the Restaurant object where the order was made
     */
    Order (String foodOrdered, String foodQuantity, String orderStatus, int restID, int custID, int isDelivery, Restaurant r) {
        this.foodOrdered = foodOrdered;
        this.foodQuantity = foodQuantity;
        this.orderStatus = orderStatus;
        this.restID = restID;
        this.custID = custID;
        this.isDelivery = isDelivery;
        this.r = r;
        translateFood();
        calcBill();
    }

    /**
     * Assigns the order with an ID
     * @param orderID the order's ID
     */
    public void assignID(int orderID){
        this.orderID = orderID;
    }
    
    /**
     * Convert the Order details into comma-separated values
     * @return the Order details in comma-separated form
     */
    public String toCSVString() {
        if (this.isDelivery == 2)
        return this.foodOrdered + "," + this.foodQuantity + "," + this.orderStatus + "," + this.restID + "," + this.custID + "," + this.isDelivery + "," + this.riderID; 
        return this.foodOrdered + "," + this.foodQuantity + "," + this.orderStatus + "," + this.restID + "," + this.custID + "," + this.isDelivery;
    }

    /**
     * Translates the food String into Food objects
     */
    private void translateFood(){
        this.m = r.passMenu();
        Food food = new Food();
        
        for(int i = 0; i < foodOrdered.length(); i++){
            int foodIndex = Integer.parseInt(String.valueOf(foodOrdered.charAt(i)));
            food = m.passFood(foodIndex-1);
            foodList.add(food);
        }
    }

    /**
     * Calculates the totla bill of an Order object
     */
    private void calcBill(){
        Food food = new Food();

        for(int i = 0; i < foodOrdered.length(); i++){
            food = foodList.get(i);
            int quantity = Integer.parseInt(String.valueOf(foodQuantity.charAt(i)));
            
            for(int j = 0; j < quantity; j++){
                this.totalBill += food.getFoodPrice();
            }
        }
    }

    /**
     * Gets the ID of the Rider assigned to the order
     * @return the riderID of the Order to be delivered
     */
    public String getAssignedRider(){
        return this.riderID;
    }

    /**
     * Gets the order ID
     * @return the Order ID
     */
    public int getOrderID(){
        return this.orderID;
    }

    /**
     * Gets the Restaurant's ID where the order was made to
     * @return the restaurant ID
     */
    public int getRestID(){
        return this.restID;
    }
    
    /**
     * Gets the Customer's ID where the order was made from
     * @return the Customer ID
     */
    public int getCustID(){
        return this.custID;
    }

    /**
     * Gets the status of the order
     * @return the order status
     */
    public String getOrderStatus(){
        return this.orderStatus;
    }

    /**
     * Displays the list of all the Food object in an Order Object
     */
    public void printOutFood(){
        
        Food food = new Food();
        System.out.printf ("%-4s%-20s%-7s%-8s%n","No","Food","Price", "Quantity");
        
        for(int i = 0; i < foodOrdered.length(); i++){
            food = foodList.get(i);
            System.out.printf("%-4d%-20s%-2s%-5.1f%5s%n", i+1, food.getFoodName(), "RM", food.getFoodPrice(), foodQuantity.charAt(i));
        }
        System.out.println ();
        System.out.printf("%31s%-5.1f%n", "Total: RM", this.totalBill);
        System.out.println ();
    }

    /**
     * Assigns a rider to an Order that is to be delivered
     * @param riderID the ID of the Rider
     */
    public void assignRider(String riderID){
        this.riderID = riderID;
    }

    /**
     * Changes the status of an order
     * @param orderStatus the status of an order to be changed
     */
    public void changeOrderStatus(String orderStatus){
        this.orderStatus = orderStatus;
    }

    /**
     * Displays all the order details of an Order object
     */
    public void printOrder(){        
        System.out.println("Order ID: " + this.orderID);
        System.out.println("Customer ID: " + this.custID);
        System.out.println("Restaurant ID: " + this.restID);
        System.out.println("Order status: " + this.orderStatus);
        System.out.print("Delivery method: ");
        if (isDelivery == 2){
            System.out.println("Delivery");
            System.out.println("Assigned rider ID: " + this.riderID);
        }
        else
        System.out.println("Self-collect");
        System.out.println();

        System.out.println("Food ordered: ");
        printOutFood();
    }
}