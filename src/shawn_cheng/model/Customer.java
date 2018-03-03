package shawn_cheng.model;

import shawn_cheng.controller.CustomerScreenController;

public class Customer {

    private int customerID;
    private String customerName;
    private Address address;
    private int active;

    public Address getAddress() {
        return address;
    }

    public int getActive() {
        return active;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "Customer ID: " + getCustomerID() + " Customer Name: " + getCustomerName();
    }

    public static String validateInput(CustomerScreenController controller) {

        System.out.println("Checking customer input");
        String errorMessage = "";

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
