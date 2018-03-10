package shawn_cheng.access;

import java.sql.*;

import shawn_cheng.MainApp;
import shawn_cheng.model.*;

/**
 * Access object for address information
 * @author Shawn Cheng
 */
public class AddressAccess {

    // Get database connection
    Connection conn = MainApp.getDBConnection();

    /**
     * Get address
     * @param addressId
     * @return address
     */
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
        } catch(SQLException e){
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("VendorError: "+e.getErrorCode());
        }
        return address;
    }

    /**
     * Add address
     * @param address
     * @return
     */
    public int addAddress(Address address) throws SQLException {
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
            stmt.executeUpdate();
        } catch(SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("VendorError: "+e.getErrorCode());
        }
        return addressID;
    }

    /**
     * Updates address information
     * @param address
     * @param newAddress1
     * @param newAddress2
     * @param postalCode
     * @param phoneNumber
     */
    public void updateAddress(Address address, String newAddress1, String newAddress2,
                              String postalCode, String phoneNumber) {
        String query = "UPDATE address SET address=?, address2=?, postalCode=?, phone=?, " +
                "lastUpdate=NOW(), lastUpdateBy=? WHERE addressId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, newAddress1);
            stmt.setString(2, newAddress2);
            stmt.setString(3, postalCode);
            stmt.setString(4, phoneNumber);
            stmt.setString(5, MainApp.userName);
            stmt.setInt(6, address.getAddressID());
            stmt.executeUpdate();
        } catch(SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("VendorError: "+e.getErrorCode());
        }
    }

    /**
     * Gets new ID
     * @return
     */
    public int getNewId() throws SQLException {
        int id = 0;
        String query = "SELECT MAX(addressId) FROM address";
        id = ShareAccess.getId(id, query, conn.createStatement());
        return id + 1;
    }
}
