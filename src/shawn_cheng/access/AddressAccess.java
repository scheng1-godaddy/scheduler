package shawn_cheng.access;

import java.sql.*;

import shawn_cheng.MainApp;
import shawn_cheng.model.*;

public class AddressAccess {

    Connection conn = MainApp.conn;

    public Address getAddress(int addressId) {
        String query = "SELECT * FROM address WHERE addressId = ?";
        Address address = new Address();

        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, addressId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                address.setAddressID(rs.getInt("addressId"));
                address.setAddress(rs.getString("address"));
                address.setAddress2(rs.getString("address2"));
                address.setPostalCode(rs.getString("postalCode"));
                address.setPhone(rs.getString("phone"));

                CityAccess city = new CityAccess();
                address.setCity(city.getCity(rs.getInt("cityId")));

            } else {
                return null;
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return address;
    }

    public int addAddress(Address address) {
        System.out.println("addAddress in AddressAccess called");
        String query = "INSERT INTO address " +
                "(addressId, address, address2, cityId, postalCode, phone, " +
                "createDate, createdBy, lastUpdate, lastUpdateBy) " +
                "VALUES (?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?)";
        int addressID = getNewId();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, addressID);
            stmt.setString(2, address.getAddress());
            stmt.setString(3, address.getAddress2());
            stmt.setInt(4, address.getCity().getCityID());
            stmt.setString(5, address.getPostalCode());
            stmt.setString(6, address.getPhone());
            stmt.setString(7, MainApp.userName);
            stmt.setString(8, MainApp.userName);
            System.out.println("Executing the following SQL query " + stmt);
            stmt.executeUpdate();
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return addressID;
    }

    public int getNewId() {
        int id = 0;
        String query = "SELECT MAX(addressId) FROM address";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            if(result.next()) {
                id = result.getInt(1);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("City ID is: " + (id + 1));
        return id + 1;
    }
}
