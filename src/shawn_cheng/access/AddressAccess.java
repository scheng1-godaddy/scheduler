package shawn_cheng.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
