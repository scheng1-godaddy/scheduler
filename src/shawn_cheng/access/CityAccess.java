package shawn_cheng.access;

import shawn_cheng.model.City;

import java.sql.*;

import shawn_cheng.MainApp;
import shawn_cheng.model.Country;


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

    public int addCity(City city) {
        System.out.println("addCity in CityAccess called");
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
            System.out.println("Executing the following SQL query " + stmt);
            stmt.executeUpdate();
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return cityID;
    }

    public int getNewId() {
        int id = 0;
        String query = "SELECT MAX(cityId) FROM city";
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
