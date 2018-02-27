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

    public int getNewId() {
        int id = 0;
        String query = "SELECT MAX(addressId) FROM customer";
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
