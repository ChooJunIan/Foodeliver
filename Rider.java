import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents the usage of the Rider class
 * 
 * @author Yusuf Ali Bin Rayney Azmi
 * @author Choo Jun Ian
 */
public class Rider{
    String riderID, riderName, riderStatus;
    int deliveryCount;
    
    /**
     * Displays the details of a Rider object
     */
    public void printDetails(){
        System.out.println("Rider ID: " + this.riderID);
        System.out.println("Rider name: " + this.riderName);
        System.out.println("Rider status: " + this.riderStatus);
        System.out.println("");
    }

    /**
     * Constructs a Rider object with an ID, Name, and Status
     * @param riderID the Rider ID
     * @param riderName the Rider name
     * @param riderStatus the Rider status
     */
    public void setRiderDetails(String riderID, String riderName, String riderStatus){
        this.riderID = riderID;
        this.riderName = riderName;
        this.riderStatus = riderStatus;
    }

    /**
     * Sets the number of deliveries that were made by a Rider
     * @param deliveryCount the number of deliveries of the Rider
     */
    public void setDeliveryCount(int deliveryCount){
        this.deliveryCount = deliveryCount;
    }

    /**
     * Gets the ID of the rider
     * @return the Rider ID
     */
    public String getID(){
        return this.riderID;
    }

    /**
     * Gets the Status of the Rider 
     * @return the Rider Status
     */
    public String getRiderStatus(){
        return this.riderStatus;
    }

    /**
     * Gets the number of deliveries made by a Rider
     * @return the Rider delivery count
     */
    public int getDeliveryCount(){
        return this.deliveryCount;
    }

    /**
     * Changes the status of an order
     * @param status the status of an Order to be changed
     */
    public void changeStatus(String status){
        this.riderStatus = status;
    }

    /**
     * Displays the Rider Main Menu
     * @param riderList the list of all Riders
     * @param riderQueue the current queue of the Riders
     * @param orderList the list of all Orders
     */
    public void riderRole(ArrayList<Rider> riderList, customQueue<Rider> riderQueue, ArrayList<Order> orderList){
        int sel = 0;
        Scanner input = new Scanner(System.in);

        do{
            System.out.println("What do u wanna do?");
            System.out.println("1. View turn");
            System.out.println("2. Head out for delivery");
            System.out.println("3. Complete order");
            System.out.println("4. View order history and status");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            System.out.println ();
            sel = input.nextInt();
            input.nextLine();

            switch(sel){
                case 1:
                showTurn(riderQueue);
                break;

                case 2:
                deliver(riderList, orderList);
                break;
                
                case 3:
                completeOrder(riderList, riderQueue, orderList);
                break;

                case 4:
                printRiderOrderHistoryStatus(orderList);
                break;
            }
        }while(sel != 0);
    }

    /**
     * Displays the number of turns left before the current Rider
     * @param riderQueue the current queue of the Riders
     */
    public void showTurn(customQueue<Rider> riderQueue){
        Rider loopedRider = new Rider();
        int turn = 0;
        for(int i = 0; i < riderQueue.getSize(); i++){
            loopedRider = riderQueue.get(i);
            if(this.riderID == loopedRider.getID()){
                turn = i;
            }
        }

        if (turn == 0)
        System.out.println("Your turn is next! Get ready. ");

        else
        System.out.println("You have " + turn + " more rider(s) before you get your turn.");
    }

    /**
     * Sets the Order status to delivering
     * @param riderList the list of all Riders
     * @param orderList the list of all Orders
     */
    public void deliver(ArrayList<Rider> riderList, ArrayList<Order> orderList){
        Order order = new Order();
        Order loopedOrder = new Order();
        boolean orderFound = false;

        for(int i = 0; i < orderList.size(); i++){
            loopedOrder = orderList.get(i);
            if(this.riderID.equals(loopedOrder.getAssignedRider())){
                if( loopedOrder.getOrderStatus().equals("Preparing") || loopedOrder.getOrderStatus().equals("Ready")){
                    order = loopedOrder;
                    orderFound = true;
                }
            }
        }

        if (orderFound != true){
            System.out.println("You do not have any orders.");
            System.out.println();
        }
        
        if(orderFound){
            if(order.getOrderStatus().equals("Ready")){
                changeStatus("Delivering");
                order.changeOrderStatus("Delivering");
                System.out.println("Order set to delivering. Be safe!");
                System.out.println();
            }
            else
            System.out.println("The order is not ready!");
            System.out.println();
        }
    }
    
    /**
     * Convert the fields of the Rider to comma-separated values
     * @return the fields of the Rider in comma-separated form
     */
    public String toCSVString() {
        return this.riderID + "," + this.riderName + "," + this.riderStatus;
    }

    /**
     * Change the order status to 'Collected' after the Rider delivers the Order
     * @param riderList the list of all Riders 
     * @param riderQueue the current queue of the Riders
     * @param orderList the list of all orders
     */
    public void completeOrder(ArrayList<Rider> riderList, customQueue<Rider> riderQueue, ArrayList<Order> orderList){
        Order order = new Order();
        Order loopedOrder = new Order();
        Rider loopedRider = new Rider();
        boolean orderFound = false;
        if (this.riderStatus.equals("Delivering")){
            for(int i = 0; i < orderList.size(); i++){
                loopedOrder = orderList.get(i);
                if(this.riderID.equals(loopedOrder.getAssignedRider())){
                    if( loopedOrder.getOrderStatus().equals("Delivering")){
                        order = loopedOrder;
                        orderFound = true;
                    }
                }
            }
            if (orderFound != true){
                System.out.println("You do not have any orders/orders ready.");
                System.out.println();
            }
            
            if(orderFound){
                order.changeOrderStatus("Collected");
                this.riderStatus = "In queue";
                for(int i = 0; i < riderList.size(); i++){
                    loopedRider = riderList.get(i);
                    if(this.riderID.equals(loopedRider.getID()))
                    riderQueue.add(loopedRider);
                }
                System.out.println("Order delivered. Thank you.");
                System.out.println();
            }
        }
        else 
        System.out.println("You are not currently out on delivery.");
        System.out.println();
    }

    /**
     * Prints the Order that were delivered by a Rider
     * @param orderList the list of all Orders
     */
    public void printRiderOrderHistoryStatus(ArrayList<Order> orderList){
        Order order = new Order();
        
        for(int i = 0; i < orderList.size(); i++){
        order = orderList.get(i);
            if(this.riderID.equals(order.getAssignedRider())){
                order.printOrder();
            }
        }
    }
}





