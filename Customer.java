import java.util.Scanner;
import java.util.ArrayList;

/**
 * This class represents the usage of the Customer class
 * 
 * @author Yusuf Ali Bin Rayney Azmi
 * @author Choo Jun Ian
 */

public class Customer {

    private int custID, custOrderCount;
    private String custName;
     
    /**
     * Constructs a Customer with no ID and Name
     */
    Customer () {}

    /**
     * Constructs a Customer with a his own ID and Name
     * @param custID the ID of the customer
     * @param custName the Name of the customer
     */
    Customer (int custID, String custName) {
        this.custID = custID;
        this.custName = custName; 
    }

    /**
     * Gets the customer's ID 
     * @return the ID of the Customer
     */
    public int getCustID () {
        return custID;
    }

    /**
     * Gets the customer's Name
     * @return the Name of the Customer
     */
    public String getCustName () {
        return custName;
    }

    /**
     * Sets the customer's order count.
     * @param custOrderCount the order count of the Customer
     */
    public void setCustOrderCount(int custOrderCount){
        this.custOrderCount = custOrderCount;
    }

    /**
     * Get the customer's order count
     * @return the customer's order count
     */
    public int getCustOrderCount(){
        return this.custOrderCount;
    }

    /**
     * Convert the customer's ID and Name to comma-separated values
     * @return the custID and custName in comma-separated form
     */
    public String toCSVString() {
        return custID + "," + custName;
    }

    /**
     * Displays a String representation of the custID and custName
     * @return custID and custName in String form
     */
    public String toString() {
        return custID + "  \t" + custName;
    }  

    /**
     * Prints the customer's order list 
     * @param orderList the list of all orders
     */
    public void printCustOrderHistoryStatus(ArrayList<Order> orderList){
        Order order = new Order();
        
        for(int i = 0; i < orderList.size(); i++){
        order = orderList.get(i);
            if(this.custID == order.getCustID()){
                order.printOrder();
            }
        }
    }

    /**
     * Changes the order status to 'Collected' when the customer collect's the food when the order is self-collect
     * @param orderList the list of all Orders
     */
    public void collectFood(ArrayList<Order> orderList){
        Order order = new Order();
        Order loopedOrder = new Order();
        Scanner input = new Scanner(System.in);
        boolean orderFound = false;

        for(int i = 0; i < orderList.size(); i++){
            loopedOrder = orderList.get(i);
            if(this.custID == loopedOrder.getCustID()){
                if(loopedOrder.getOrderStatus().equals("Ready")){
                    loopedOrder.printOrder();
                    orderFound = true;
                }
            }
        }

        if (orderFound != true){
            System.out.println("You do not have any orders/orders ready.");
            System.out.println();
        }
        
        if (orderFound){
            System.out.println("Please choose an order ID to collect: ");
            int orderID = input.nextInt();
            input.nextLine();
            order = orderList.get(orderID-1);
            order.changeOrderStatus("Collected");
            System.out.println("Order number " + orderID + " is collected, thank you. ");
            System.out.println();
        }
    }
}