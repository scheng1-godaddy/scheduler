package shawn_cheng.model;

/**
 * Object for Country information
 *
 * @author Shawn Cheng
 */
public class Country {

    // Instance variables for country information
    private int countryID;
    private String country;

    /**
     * Gets the country ID
     * @return
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Gets the country name
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country ID
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Sets the country name
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Override of toString. Returns the country name
     * @return
     */
    @Override
    public String toString() {
        return this.country;
    }
}
