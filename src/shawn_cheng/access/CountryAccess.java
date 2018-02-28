package shawn_cheng.access;

import shawn_cheng.model.Country;

import java.sql.*;

import shawn_cheng.MainApp;
import sun.applet.Main;

public class CountryAccess {

    Connection conn = MainApp.getDBConnection();

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
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        return country;
    }

    public int addCountry(Country country) {
        System.out.println("addCountry in CountryAccess called");
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
            System.out.println("Executing the following SQL query " + stmt);
            stmt.executeUpdate();
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return countryID;
    }

    public void updateCountry(Country country, String newCountryName) {
        System.out.println("updateCountry in CountryAccess called");
        String query = "UPDATE country SET country=?, lastUpdate=NOW(), lastUpdateBy=? WHERE countryId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, newCountryName);
            stmt.setString(2, MainApp.userName);
            stmt.setInt(3, country.getCountryID());
            System.out.println("Executing the following SQL " + stmt);
            stmt.executeUpdate();
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int getNewId() {
        int id = 0;
        String query = "SELECT MAX(countryId) FROM country";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            if(result.next()) {
                id = result.getInt(1);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Country ID is: " + (id + 1));
        return id + 1;
    }

}
