package shawn_cheng.access;

import shawn_cheng.model.Country;
import java.sql.*;
import shawn_cheng.MainApp;

/**
 * Access object for the country table
 * @author Shawn Cheng
 */
public class CountryAccess {

    // Get database connection
    Connection conn = MainApp.getDBConnection();

    /**
     * Get country
     * @param countryId
     * @return
     */
    public Country getCountry(int countryId) {
        String query = "SELECT * FROM country WHERE countryId = ?";
        Country country = new Country();
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, countryId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                country.setCountry(rs.getString("country"));
                country.setCountryID(rs.getInt("countryId"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("VendorError: "+e.getErrorCode());
        }
        return country;
    }

    /**
     * Add country
     * @param country
     * @return
     * @throws SQLException
     */
    public int addCountry(Country country) throws SQLException {
        String query = "INSERT INTO country " +
                "(countryId, country, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                "VALUES (?, ?, NOW(), ?, NOW(), ?)";
        int countryID = getNewId();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, countryID);
            stmt.setString(2, country.getCountry());
            stmt.setString(3, MainApp.userName);
            stmt.setString(4, MainApp.userName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("VendorError: "+e.getErrorCode());
        }
        return countryID;
    }

    /**
     * Update country
     * @param country
     * @param newCountryName
     */
    public void updateCountry(Country country, String newCountryName) {
        String query = "UPDATE country SET country=?, lastUpdate=NOW(), lastUpdateBy=? WHERE countryId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, newCountryName);
            stmt.setString(2, MainApp.userName);
            stmt.setInt(3, country.getCountryID());
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
        String query = "SELECT MAX(countryId) FROM country";
        id = ShareAccess.getId(id, query, conn.createStatement());
        return id + 1;
    }
}
