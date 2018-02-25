package shawn_cheng.model;

public class Country {
    private int countryID;
    private String country;

    public int getCountryID() {
        return countryID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return this.country;
    }
}
