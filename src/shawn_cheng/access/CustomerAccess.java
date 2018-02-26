package shawn_cheng.access;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public void addCustomer(Customer customer) {

    }

}
