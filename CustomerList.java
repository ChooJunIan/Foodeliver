import java.util.ArrayList;

/**
 * The class represents the usage of the list of customers.
 * 
 * @author Yusuf Ali Bin Rayney Azmi
 * @author Choo Jun Ian
 */

public class CustomerList {
    private ArrayList<Customer> customerList;

    /**
     * Constructs a customerList with no customers
     */
    CustomerList () {}

    /**
     * Constructs a customerList with a list of customer
     * @param customerList
     */
    CustomerList (ArrayList<Customer> customerList) {
        this.customerList = customerList;
    }

    /**
     * Adds a customer to the list of customers
     * @param customer a customer object
     */
    public void addCustomerToList (Customer customer) {
        customerList.add(customer);
    }

    /**
     * Gets the list of customer
     * @return the list of customer
     */
    public ArrayList<Customer> getListOfCustomer () {
        return customerList;     
    }

    /**
     * Gets a customer object from the list
     * @param i the index of the customer in the list
     * @return the Customer oject
     */
    public Customer getCustomer (int i) {
        return customerList.get(i);
    }

    /**
     * Checks if a customer exist in the list
     * @param id the customer's ID
     * @param name the customer's name
     * @param customerList the list of all customers
     * @return a boolean: true if customer exist; false if customer does not exist
     */
    public boolean checkCustomerExist (int id, String name, CustomerList customerList) {
        Customer checkCustomer = new Customer();
        for (int i = 0; i < customerList.getListOfCustomer().size(); i++) {
            checkCustomer = customerList.getListOfCustomer().get(i);
            if (checkCustomer.getCustID() == id && checkCustomer.getCustName().equals(name)) {
                System.out.println ("Customer Login Successful!");
                System.out.println ();
                return true;
            }
        }
        return false;
    }
}