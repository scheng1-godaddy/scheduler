package shawn_cheng.access;

import shawn_cheng.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import shawn_cheng.MainApp;


public class CityAccess {

    Connection conn = MainApp.conn;

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
}
