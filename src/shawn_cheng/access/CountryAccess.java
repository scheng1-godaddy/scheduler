package shawn_cheng.access;

import shawn_cheng.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import shawn_cheng.MainApp;

public class CountryAccess {

    Connection conn = MainApp.conn;

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
}
