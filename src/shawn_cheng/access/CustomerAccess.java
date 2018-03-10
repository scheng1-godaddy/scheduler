package shawn_cheng.access;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import shawn_cheng.MainApp;
import shawn_cheng.model.*;

/**
 * Access Object for Customer Table
 * @author Shawn Cheng
 */
public class CustomerAccess {

    // Get database connection
    private Connection conn = MainApp.getDBConnection();

    /**
     * Get all customers
     * @return
     */
    public ObservableList<Customer> getCustomers() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String getCustomerQuery = "SELECT * FROM customer WHERE active = 1";

        try {
            PreparedStatement stmt = conn.prepareStatement(getCustomerQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerName(rs.getString("customerName"));
                customer.setCustomerID(rs.getInt("customerId"));
                customer.setActive(rs.getInt("active"));
                AddressAccess address = new AddressAccess();
                customer.setAddress(address.getAddress(rs.getInt("addressId")));
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        return customers;
    }

    /**
     * Get customer based on ID
     * @param customerID
     * @return
     */
    public Customer getCustomer(int customerID) {
        String query = "SELECT * FROM customer WHERE customerId = ?";
        Customer customer = new Customer();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customer.setCustomerName(rs.getString("customerName"));
                customer.setCustomerID(rs.getInt("customerId"));
                customer.setActive(rs.getInt("active"));
                AddressAccess address = new AddressAccess();
                customer.setAddress(address.getAddress(rs.getInt("addressId")));
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        return customer;
    }

    /**
     * Add customer
     * @param customer
     * @return
     * @throws SQLException
     */
    public int addCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO customer " +
                "(customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                "VALUES (?, ?, ?, ?, NOW(), ?, NOW(), ?)";
        int customerID = getNewId();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, customerID);
            stmt.setString(2, customer.getCustomerName());
            stmt.setInt(3, customer.getAddress().getAddressID());
            stmt.setInt(4, 1);
            stmt.setString(5, MainApp.userName);
            stmt.setString(6, MainApp.userName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("VendorError: "+e.getErrorCode());
        }
        return customerID;
    }

    /**
     * Update Customer
     * @param customer
     * @param newCustomerName
     */
    public void updateCustomer(Customer customer, String newCustomerName) {
        String query = "UPDATE customer SET customerName=?, lastUpdate=NOW(), lastUpdateBy=? WHERE customerId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, newCustomerName);
            stmt.setString(2, MainApp.userName);
            stmt.setInt(3, customer.getCustomerID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("VendorError: "+e.getErrorCode());
        }
    }

    /**
     * Delete customer
     * @param customer
     */
    public void deleteCustomer(Customer customer) {
        String query = "UPDATE customer SET active=0, lastUpdate=NOW(), lastUpdateBy=? WHERE customerId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, MainApp.userName);
            stmt.setInt(2, customer.getCustomerID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("VendorError: "+e.getErrorCode());
        }
    }

    /**
     * Get new ID
     * @return
     * @throws SQLException
     */
    public int getNewId() throws SQLException{
        int id = 0;
        String query = "SELECT MAX(customerId) FROM customer";
        id = ShareAccess.getId(id, query, conn.createStatement());
        return id + 1;
    }
}
