package shawn_cheng.model;

import shawn_cheng.controller.ManageCustomerScreenController;

/**
 * Object to house customer information
 *
 * @author Shawn Cheng
 */
public class Customer {
    // Instance variables to house customer information
    private int customerID;
    private String customerName;
    private Address address;
    // Set 0 for inactive and 1 for active
    private int active;

    /**
     * Gets the address
     * @return
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Gets whether customer is active
     * @return
     */
    public int getActive() {
        return active;
    }

    /**
     * Gets the customer name
     * @return
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Gets the customer ID
     * @return
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Set active value, 0 for inactive and 1 for active
     * @param active
     */
    public void setActive(int active) {
        this.active = active;
    }

    /**
     * Sets the address
     * @param address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Sets the customer ID
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Sets the customer name
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Override of toString, returns customer name
     * @return
     */
    @Override
    public String toString() {
        return getCustomerName();
    }

    /**
     * Validates input before creating customer object
     * @param controller
     * @return
     */
    public static String validateInput(ManageCustomerScreenController controller) {
        // This string will contain what the error is (if empty, no error)
        String errorMessage = "";
        // Check each field to see if there was a value entered
        if (controller.nameField.getText().equals("")) {
            errorMessage += "Invalid name\n";
        }
        if (controller.address1Field.getText().equals("")) {
            errorMessage += "Invalid address\n";
        }
        if (controller.cityField.getText().equals("")) {
            errorMessage += "Invalid city\n";
        }
        if (controller.countryField.getText().equals("")) {
            errorMessage += "Invalid country\n";
        }
        if (controller.postalField.getText().equals("")) {
            errorMessage += "Invalid postal code\n";
        }
        if (controller.phoneField.getText().equals("")) {
            errorMessage += "Invalid Phone Number\n";
        }
        return errorMessage;
    }
}
