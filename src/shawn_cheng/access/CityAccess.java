package shawn_cheng.access;

import shawn_cheng.model.City;
import java.sql.*;
import shawn_cheng.MainApp;

/**
 * Access object for city information
 */
public class CityAccess {

    // Get database connection
    Connection conn = MainApp.getDBConnection();

    /**
     * Get city
     * @param cityId
     * @return
     */
    public City getCity(int cityId) {
        String query = "SELECT * FROM city WHERE cityId = ?";
        City city = new City();

        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, cityId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                city.setCity(rs.getString("city"));
                city.setCityID(rs.getInt("cityId"));
                CountryAccess country = new CountryAccess();
                city.setCountry(country.getCountry(rs.getInt("countryId")));
            } else {
                return null;
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return city;
    }

    /**
     * Add city to city table
     * @param city
     * @return city ID
     * @throws SQLException
     */
    public int addCity(City city) throws SQLException {
        String query = "INSERT INTO city " +
                "(cityId, city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                "VALUES (?, ?, ?, NOW(), ?, NOW(), ?)";
        int cityID = getNewId();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, cityID);
            stmt.setString(2, city.getCity());
            stmt.setInt(3, city.getCountry().getCountryID());
            stmt.setString(4, MainApp.userName);
            stmt.setString(5, MainApp.userName);
            stmt.executeUpdate();
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return cityID;
    }

    /**
     * Update city table
     * @param city
     * @param newCityName
     */
    public void updateCity(City city, String newCityName) {
        System.out.println("updateCountry in CountryAccess called");
        String query = "UPDATE city SET city=?, lastUpdate=NOW(), lastUpdateBy=? WHERE cityId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, newCityName);
            stmt.setString(2, MainApp.userName);
            stmt.setInt(3, city.getCityID());
            System.out.println("Executing the following SQL " + stmt);
            stmt.executeUpdate();
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Get new ID
     * @return
     * @throws SQLException
     */
    public int getNewId() throws SQLException{
        int id = 0;
        String query = "SELECT MAX(cityId) FROM city";
        id = ShareAccess.getId(id, query, conn.createStatement());
        return id + 1;
    }
}
