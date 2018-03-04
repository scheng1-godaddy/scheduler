package shawn_cheng.access;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import shawn_cheng.MainApp;
import shawn_cheng.model.*;


public class CustomerAccess {

    private Connection conn = MainApp.getDBConnection();

    public ObservableList<Customer> getCustomers() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String getCustomerQuery = "SELECT * FROM customer WHERE active = 1";

        try {
            PreparedStatement stmt = conn.prepareStatement(getCustomerQuery);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Executing query to retrieve all customers");
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerName(rs.getString("customerName"));
                customer.setCustomerID(rs.getInt("customerId"));
                customer.setActive(rs.getInt("active"));

                AddressAccess address = new AddressAccess();
                customer.setAddress(address.getAddress(rs.getInt("addressId")));

                customers.add(customer);
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        return customers;
    }

    public Customer getCustomer(int customerID) {
        String query = "SELECT * FROM customer WHERE customerId = ?";
        Customer customer = new Customer();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Executing query to retrieve customer with ID: " + customerID);
            if (rs.next()) {
                customer.setCustomerName(rs.getString("customerName"));
                customer.setCustomerID(rs.getInt("customerId"));
                customer.setActive(rs.getInt("active"));
                AddressAccess address = new AddressAccess();
                customer.setAddress(address.getAddress(rs.getInt("addressId")));
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return customer;
    }

    public int addCustomer(Customer customer) {
        System.out.println("addCustomer in CustomerAccess called");
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
            System.out.println("Executing the following SQL query " + stmt);
            stmt.executeUpdate();
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return customerID;
    }

    public void updateCustomer(Customer customer, String newCustomerName) {
        System.out.println("updateCustomer in CustomerAccess called");
        String query = "UPDATE customer SET customerName=?, lastUpdate=NOW(), lastUpdateBy=? WHERE customerId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, newCustomerName);
            stmt.setString(2, MainApp.userName);
            stmt.setInt(3, customer.getCustomerID());
            System.out.println("Executing the following SQL " + stmt);
            stmt.executeUpdate();
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteCustomer(Customer customer) {
        System.out.println("updateCustomer in CustomerAccess called");
        String query = "UPDATE customer SET active=0, lastUpdate=NOW(), lastUpdateBy=? WHERE customerId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, MainApp.userName);
            stmt.setInt(2, customer.getCustomerID());
            System.out.println("Executing the following SQL " + stmt);
            stmt.executeUpdate();
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int getNewId() {
        int id = 0;
        String query = "SELECT MAX(customerId) FROM customer";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            if(result.next()) {
                id = result.getInt(1);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Customer ID is: " + (id + 1));
        return id + 1;
    }
}
