package shawn_cheng.model;

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
        return getCustomerName();
    }
}
