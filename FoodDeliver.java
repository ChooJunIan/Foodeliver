import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class represents the usage of the FoodDeliver class
 * 
 * @author Yusuf Ali Bin Rayney Azmi
 * @author Choo Jun Ian
 */
public class FoodDeliver {
    public static void main (String[] args) throws IOException {
        ArrayList<Customer> customerListFromFile = readCustomerFromFile();
        ArrayList<Rider> riderList = new ArrayList<>();
        ArrayList<Order> orderList = new ArrayList<>();
        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        CustomerList customerList = new CustomerList(customerListFromFile);
        customQueue<Rider> riderQueue = new customQueue<>();

        preloadOrder(orderList);
        preloadRiders(riderList, riderQueue);
        populateRestaurantArray(restaurantList, orderList);
        preloadRiderQueue(riderQueue);
        
        Scanner input = new Scanner(System.in);
        int roleChoice;

        do {
            saveOrder(orderList);
            saveRiders(riderList);
            saveRiderQueue(riderQueue);
            
            System.out.println ("Please select your role: ");
            System.out.println ("1: Restaurant");
            System.out.println ("2: Customer");
            System.out.println ("3: Rider");
            System.out.println ("4: Admin");
            System.out.println ("0: Exit");
            System.out.print ("Choice: ");
            roleChoice = input.nextInt();
            input.nextLine();
            System.out.println ();
            switch(roleChoice) {
                case 1: restChoiceMenu(orderList); break;
                case 2: custLoginMainMenu(riderQueue,orderList); break;
                case 3: riderMainMenu(riderList, riderQueue, orderList); break;
                case 4: adminMainMenu(riderList, riderQueue, orderList, restaurantList, customerList); break;
            }
        } while (roleChoice != 0);
    }

    /**
     * Displays the Menu to choose between different Restaurants
     * @param orderList the list of all Orders
     * @throws IOException throws an exception if the file containing the Restaurant's Food is not found
     */
    public static void restChoiceMenu (ArrayList<Order> orderList) throws IOException {
        Scanner input = new Scanner(System.in);
        int restChoice;
        do {
            System.out.println ("Select the Restaurant you want to manage: ");
            System.out.println ("1. Malay Restaurant");
            System.out.println ("2. Western Restaurant");
            System.out.println ("3. Chinese Restaurant");
            System.out.println ("0. Exit");
            System.out.print ("Choice: ");
            restChoice = input.nextInt();
            input.nextLine();
            System.out.println ();

            switch(restChoice) {
                case 1: 
                ArrayList<Food> foodR1 = readFoodFromFile(restChoice);
                Menu menuR1 = new Menu(foodR1);
                Restaurant r1 = new Restaurant(1, "Malay Restaurant", menuR1);
                restMainMenu(r1, orderList);
                break;

                case 2: 
                ArrayList<Food> foodR2 = readFoodFromFile(restChoice);
                Menu menuR2 = new Menu(foodR2);
                Restaurant r2 = new Restaurant(2, "Western Restaurant", menuR2);
                restMainMenu(r2, orderList);
                break;

                case 3:
                ArrayList<Food> foodR3 = readFoodFromFile(restChoice);
                Menu menuR3 = new Menu(foodR3);
                Restaurant r3 = new Restaurant(3, "Chinese Restaurant", menuR3);
                restMainMenu(r3, orderList);
                break;
            } 
        }while(restChoice != 0);
    }

    /**
     * Displays the Main Menu for the Restaurant
     * @param r the Restaurant object that was passed in
     * @param orderList the list of all Orders
     * @throws IOException throws an exception if the file containing the Restaurant's Food is not found
     */
    public static void restMainMenu(Restaurant r, ArrayList<Order> orderList) throws IOException {
        Scanner input = new Scanner(System.in);
        int mainMenuChoice;
        do {
            System.out.println ("Select your option (enter '0' to exit this menu): ");
            System.out.println ("1. Manage Food Menu");
            System.out.println ("2. View Food Menu");
            System.out.println ("3. View Order History/Status");
            System.out.println ("4. Update Order Status");
            System.out.println ("0. Exit");
            System.out.print ("Choice: ");
            mainMenuChoice = input.nextInt();
            input.nextLine();
            System.out.println ();
            
            switch(mainMenuChoice) {
                case 1: 
                Restaurant.manageMainMenu(r);
                break;
    
                case 2:
                r.getMenu();
                break;
    
                case 3:
                r.printRestOrderHistoryStatus(orderList);
                break;
                
                case 4:
                r.changeOrderStatus(orderList);
                break;
            
            }
        }while (mainMenuChoice != 0);
    }

    /**
     * Displays the Login Menu to the Customer
     * @param riderQueue the current queue of the Riders
     * @param orderList the list of all Orders
     * @throws IOException throws an exception if the file containing the list of Customers is not found
     */
    public static void custLoginMainMenu(customQueue<Rider> riderQueue, ArrayList<Order> orderList) throws IOException {
        ArrayList<Customer> customerListFromFile = readCustomerFromFile();
        CustomerList customerList = new CustomerList(customerListFromFile);
        Scanner input = new Scanner(System.in);
        int mainMenuChoice;

        do {
            System.out.println ("Select your option (Enter '0' to exit this menu): ");
            System.out.println ("1. Register as new Customer");
            System.out.println ("2. Log in as Customer");
            System.out.println ("0. Exit");
            System.out.print ("Choice: ");
            mainMenuChoice = input.nextInt();
            input.nextLine();
            System.out.println ();

            switch(mainMenuChoice) {
                case 1: 
                System.out.print ("Enter your id: ");
                int id = input.nextInt();
                input.nextLine();
                System.out.print ("Enter your name: ");
                String name = input.nextLine();
                registerCustomer(id, name, customerList);
                break; 

                case 2: 
                System.out.print ("Enter your id: ");
                id = input.nextInt();
                input.nextLine();
                System.out.print ("Enter your name: ");
                name = input.nextLine();
                if (customerList.checkCustomerExist(id, name, customerList)) {
                    Customer customer = new Customer(id, name);
                    customerMainMenu(customer, riderQueue, orderList);
                }
                else {
                    System.out.println ("Customer does not exist! Please register!");
                    System.out.println ();
                }
                break;
            }
        } while (mainMenuChoice != 0);
    }

    /**
     * Displays the Customer's Main Menu after successfully logging in
     * @param customer the Customer object that was passed in
     * @param riderQueue the current queue of the Riders
     * @param orderList the list of all Orders
     * @throws IOException throws an exception if the file containing the Restaurant's Food is not found
     */
    public static void customerMainMenu (Customer customer, customQueue<Rider> riderQueue, ArrayList<Order> orderList) throws IOException {
        Scanner input = new Scanner(System.in);
        int mainMenuChoice;
        do {
            System.out.println ("Select your option (enter '0' to exit this menu): ");
            System.out.println ("1. Make an Order");
            System.out.println ("2. View Order History/Status");
            System.out.println ("3. Collect Food");
            System.out.println ("0. Exit");
            System.out.print ("Choice: ");
            mainMenuChoice = input.nextInt();
            input.nextLine();
            System.out.println ();
            
            switch(mainMenuChoice) {
                case 1:
                int restChoice;
                do {
                    System.out.println ("Restaurant");
                    System.out.println ("1. Malay Restaurant");
                    System.out.println ("2. Western Restaurant");
                    System.out.println ("3. Chinese Restaurant");
                    System.out.println ("0. Exit");
                    System.out.print ("Please select a restaurant (Enter '0' to exit): ");
                    restChoice = input.nextInt();
                    input.nextLine();
                    System.out.println ();

                    switch (restChoice) {
                        case 1: 
                        ArrayList<Food> foodR1 = readFoodFromFile(restChoice);
                        Menu menuR1 = new Menu(foodR1);
                        Restaurant r1 = new Restaurant(1, "Malay Restaurant", menuR1);
                        customerOrderMenu(r1, customer, riderQueue, orderList);
                        break;

                        case 2: 
                        ArrayList<Food> foodR2 = readFoodFromFile(restChoice);
                        Menu menuR2 = new Menu(foodR2);
                        Restaurant r2 = new Restaurant(2, "Western Restaurant", menuR2);
                        customerOrderMenu(r2, customer, riderQueue, orderList);
                        break;

                        case 3: 
                        ArrayList<Food> foodR3 = readFoodFromFile(restChoice);
                        Menu menuR3 = new Menu(foodR3);
                        Restaurant r3 = new Restaurant(3, "Chinese Restaurant", menuR3);
                        customerOrderMenu(r3, customer, riderQueue, orderList);
                        break;

                    }

                }while (restChoice != 0 );

                break;
    
                case 2:
                customer.printCustOrderHistoryStatus(orderList);
                break;
    
                case 3:
                customer.collectFood(orderList);
                break;
                
            }
        }while (mainMenuChoice != 0);
    }

    /**
     * Displays the menu for the Customer to place their order
     * @param r the Restaurant object where the Order is made to
     * @param c the Customer object where the Order is made from
     * @param riderQueue the current queue of the Riders
     * @param orderList the list of all Orders
     */
    public static void customerOrderMenu (Restaurant r, Customer c, customQueue<Rider> riderQueue, ArrayList<Order> orderList) {
        r.getMenu();
        Menu menu = r.passMenu();
        ArrayList<Food> foodList = menu.getListOfFood();
        int lFoodList = foodList.size();
        Rider assignedRider = new Rider();
        Scanner input = new Scanner(System.in);
        int foodID;
        int foodQuantity;
        StringBuilder sbFoodID = new StringBuilder();
        StringBuilder sbFoodQuantity = new StringBuilder();
        
        
        System.out.println ("Place your orders here. Enter '0' to complete your order!");
        do {
            boolean fiNotInRange = false;
            do{
                fiNotInRange = false;
                System.out.print ("Enter food ID: ");
                foodID = input.nextInt();
                input.nextLine();
                if (foodID > lFoodList  || foodID < 0){
                    fiNotInRange = true;
                }
            }while(fiNotInRange);
        
            if (foodID != 0) {
                sbFoodID.append(Integer.toString(foodID));
                boolean notInRange = false;
                do{
                    notInRange = false;
                    System.out.print ("Enter quantity (Between 1-9): ");
                    foodQuantity = input.nextInt();
                    input.nextLine();
                    if (foodQuantity > 9 || foodQuantity < 1 ){
                        notInRange = true;
                    }
                }while(notInRange);
                sbFoodQuantity.append(Integer.toString(foodQuantity));
            }
        } while (foodID != 0);
        
        System.out.println ("Please choose the delivery method: ");
        System.out.println ("1. Self-collect");
        System.out.println ("2. Delivery");
        int isDelivery = input.nextInt();
        input.nextLine();
        System.out.println ("Order Completed!");
        System.out.println();

        Order newOrder = new Order(sbFoodID.toString(), sbFoodQuantity.toString(), "Preparing", r.getRestID(), c.getCustID(), isDelivery, r);
        
        if (isDelivery == 2) {            
            assignedRider = riderQueue.get(0);
            newOrder.assignRider(assignedRider.getID());
            
            assignedRider.changeStatus("Delivering");
            riderQueue.removeFirst();
        }
        newOrder.assignID(orderList.size() + 1);
        orderList.add(newOrder);
    }

    /**
     * Displays the menu to register a new Customer
     * @param id the ID of the new Customer
     * @param name the name of the new Customer
     * @param customerList the list of all Customers
     * @throws IOException throws an exception if the file containing the list of Customers is not found
     */
    public static void registerCustomer (int id, String name, CustomerList customerList) throws IOException {
        Customer newCustomer = new Customer(id, name);
        customerList.addCustomerToList(newCustomer);
        saveCustomerToFile(customerList);
        System.out.println ("Customer registered successfully!");
        System.out.println ();
    }

    /**
     * Displays the main menu of the Rider
     * @param riderList the list of all Riders
     * @param riderQueue the current queue of the Riders
     * @param orderList the list of all orders
     */
    public static void riderMainMenu (ArrayList<Rider> riderList, customQueue<Rider> riderQueue, ArrayList<Order> orderList) {
        Scanner input = new Scanner(System.in);
        int selectedRider = 0;
        Rider rider = new Rider();
        System.out.println("Who would you like to log in as?");
        
        for(int i = 0; i < riderList.size(); i++){
            rider = riderList.get(i);
            rider.printDetails();
        }

        selectedRider = input.nextInt();
        input.nextLine();
        rider = riderList.get(selectedRider-1);
        rider.riderRole(riderList, riderQueue, orderList);
    }

    /**
     * Display the main menu for the Riders
     * @param riderList the list of all Riders
     * @param riderQueue the current queue of the Riders
     * @param orderList the list of all Orders
     * @param restaurantList the list of all Restaurants
     * @param customerList the list of all Customers
     */
    public static void adminMainMenu (ArrayList<Rider> riderList, customQueue<Rider> riderQueue, ArrayList<Order> orderList, ArrayList<Restaurant> restaurantList, CustomerList customerList) {
        Admin admin = new Admin();
        admin.AdminRole(riderList, riderQueue, orderList, restaurantList, customerList);
    }

    /**
     * Reads the food from the file for each respective Restaurant
     * @param restChoice the Restaurant ID
     * @return the list of Food of the Restaurant
     * @throws IOException throws an exception when the list of Food file of a Restaurant is not found
     */
    private static ArrayList<Food> readFoodFromFile(int restChoice) throws IOException {
        ArrayList<Food> foodInFile = new ArrayList<>();
        
        // read Restaurant.csv into a list of lines.
        if (restChoice == 1) {            
            List<String> lines = Files.readAllLines(Paths.get("Restaurant1Food.csv"));
            for (int i = 0; i < lines.size(); i++) {
                String[] items = lines.get(i).split(",");
                int foodID = Integer.parseInt(items[0]);
                double price = Double.parseDouble(items[2]);
                foodInFile.add(new Food(foodID, items[1], price));
            }
            return foodInFile;
        } else if (restChoice == 2) {
            List<String> lines = Files.readAllLines(Paths.get("Restaurant2Food.csv"));
            for (int i = 0; i < lines.size(); i++) {
                String[] items = lines.get(i).split(",");
                int foodID = Integer.parseInt(items[0]);
                double price = Double.parseDouble(items[2]);
                foodInFile.add(new Food(foodID, items[1], price));
            }
            return foodInFile;
        } else if (restChoice == 3) {
            List<String> lines = Files.readAllLines(Paths.get("Restaurant3Food.csv"));
            for (int i = 0; i < lines.size(); i++) {
                String[] items = lines.get(i).split(",");
                int foodID = Integer.parseInt(items[0]);
                double price = Double.parseDouble(items[2]);
                foodInFile.add(new Food(foodID, items[1], price));
            }
            return foodInFile;
        } else return null;
    }

    /**
     * Reads the list of Customers from file
     * @return the list of all Customers
     * @throws IOException throws an exception when the list of Customers file is not found
     */
    private static ArrayList<Customer> readCustomerFromFile() throws IOException {
        ArrayList<Customer> customerInFile = new ArrayList<>();
            // read CustomerList.csv into a list of lines.
            List<String> lines = Files.readAllLines(Paths.get("CustomerList.csv"));
            for (int i = 0; i < lines.size(); i++) {
                String[] items = lines.get(i).split(",");
                int id = Integer.parseInt(items[0]);
                customerInFile.add(new Customer(id, items[1]));
            }
            return customerInFile;
    }

    /**
     * Saves the list of Customers to file
     * @param customerList the list of all Customers
     * @throws IOException throws an exception when the list of Customers file is not found
     */
    private static void saveCustomerToFile(CustomerList customerList) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < customerList.getListOfCustomer().size(); i++) {
            sb.append (customerList.getCustomer(i).toCSVString() + "\n");
        }
        Files.write(Paths.get("CustomerList.csv"), sb.toString().getBytes());
    }

    /**
     * Reads the list of Riders from file
     * @param riderList the list of all Riders 
     * @param riderQueue the current queue of the Riders
     * @throws IOException throws an exception when the list of Riders file is not found
     */
    public static void preloadRiders(ArrayList<Rider> riderList, customQueue<Rider> riderQueue) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("Riders.csv"));

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            Rider rider = new Rider();
            rider.setRiderDetails(items[0], items[1], items[2]);
            riderList.add(rider);
        }
    }

    /**
     * Saves a new Rider into the file
     * @param riderList the list of all Riders
     * @throws IOException throws an exception when the list of Riders file is not found
     */
    public static void saveRiders(ArrayList<Rider> riderList) throws IOException{
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < riderList.size(); i++) {
            sb.append (riderList.get(i).toCSVString() + "\n");
        }
        Files.write(Paths.get("Riders.csv"), sb.toString().getBytes());
    }

    /**
     * Read the Rider Queue from file
     * @param riderQueue the current queue of the Riders 
     * @throws IOException throws an exception when the Rider Queue file is not found
     */
    public static void preloadRiderQueue(customQueue<Rider> riderQueue) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("RiderQueue.csv"));

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            Rider rider = new Rider();
            rider.setRiderDetails(items[0], items[1], items[2]);
            riderQueue.add(rider);
            
        }
    }

    /**
     * Saves the Rider Queue into a file
     * @param riderQueue the current Queue of the Riders 
     * @throws IOException throws an exception when the Rider Queue file is not found
     */
    public static void saveRiderQueue(customQueue<Rider> riderQueue) throws IOException{
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < riderQueue.getSize(); i++) {
            sb.append (riderQueue.get(i).toCSVString() + "\n");
        }
        Files.write(Paths.get("RiderQueue.csv"), sb.toString().getBytes());
    }

    /**
     * Reads the list of Orders from file
     * @param orderList the list of all orders
     * @throws IOException throws an exception when the Orders file is not found
     */
    public static void preloadOrder(ArrayList<Order> orderList) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("Orders.csv"));
        Order order = new Order();
        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            int restID = Integer.parseInt(items[3]);
            int custID = Integer.parseInt(items[4]);
            int isDelivery = Integer.parseInt(items[5]);
            
            switch(restID){
                case 1: 
                ArrayList<Food> foodR1 = readFoodFromFile(restID);
                Menu menuR1 = new Menu(foodR1);
                Restaurant r1 = new Restaurant(1, "Malay Restaurant", menuR1);
                order = new Order(items[0], items[1], items[2], restID, custID, isDelivery, r1);
                order.assignID(orderList.size()+1);
                if(isDelivery == 2){
                    order.assignRider(items[6]);
                }
                break;

                case 2: 
                ArrayList<Food> foodR2 = readFoodFromFile(restID);
                Menu menuR2 = new Menu(foodR2);
                Restaurant r2 = new Restaurant(2, "Western Restaurant", menuR2);
                order = new Order(items[0], items[1], items[2], restID, custID, isDelivery, r2);
                order.assignID(orderList.size()+1);
                if(isDelivery == 2){
                    order.assignRider(items[6]);
                }
                break;

                case 3:
                ArrayList<Food> foodR3 = readFoodFromFile(restID);
                Menu menuR3 = new Menu(foodR3);
                Restaurant r3 = new Restaurant(3, "Chinese Restaurant", menuR3);
                order = new Order(items[0], items[1], items[2], restID, custID, isDelivery, r3);
                order.assignID(orderList.size()+1);
                if(isDelivery == 2){
                    order.assignRider(items[6]);
                }
                break;
            }
            orderList.add(order);
        }
    }

    /**
     * Saves the Orders into file
     * @param orderList the list of all Orders
     * @throws IOException throws an exception when the list of Orders file is not found
     */
    public static void saveOrder(ArrayList<Order> orderList) throws IOException{
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < orderList.size(); i++) {
            sb.append (orderList.get(i).toCSVString() + "\n");
        }
        Files.write(Paths.get("Orders.csv"), sb.toString().getBytes());
    }

    /**
     * Populates the Restaurant's list of Food 
     * @param restaurantList the list of all Restaurants
     * @param orderList the list of all Orders  
     * @throws IOException throws an exception when the list of Food of a Restaurant is not found
     */
    public static void populateRestaurantArray(ArrayList<Restaurant> restaurantList, ArrayList<Order> orderList) throws IOException{
        ArrayList<Food> foodR1 = readFoodFromFile(1);
        Menu menuR1 = new Menu(foodR1);
        Restaurant r1 = new Restaurant(1, "Malay Restaurant", menuR1);
        restaurantList.add(r1);

        ArrayList<Food> foodR2 = readFoodFromFile(2);
        Menu menuR2 = new Menu(foodR2);
        Restaurant r2 = new Restaurant(2, "Western Restaurant", menuR2);
        restaurantList.add(r2);
        
        ArrayList<Food> foodR3 = readFoodFromFile(3);
        Menu menuR3 = new Menu(foodR3);
        Restaurant r3 = new Restaurant(3, "Chinese Restaurant", menuR3);
        restaurantList.add(r3);
    }
}
