package shawn_cheng.model;

/**
 * Object for city information
 *
 * @author Shawn Cheng
 */
public class City {

    // Instance variables to house city information
    private int cityID;
    private String city;
    private Country country;

    /**
     * Sets the city name
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Sets the city ID value
     * @param cityID
     */
    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    /**
     * Sets the country object
     * @param country
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * Gets the city name
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     * Gets the city ID
     * @return
     */
    public int getCityID() {
        return cityID;
    }

    /**
     * Gets the country object
     * @return
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Override for toString. Returns the city name
     * @return
     */
    @Override
    public String toString() {
        return getCity();
    }
}
